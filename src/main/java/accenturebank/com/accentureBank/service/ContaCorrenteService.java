package accenturebank.com.accentureBank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Agencia;
import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.domain.Extrato;
import accenturebank.com.accentureBank.dto.ContaCorrenteDTO;
import accenturebank.com.accentureBank.entities.enums.TipoDeOperacaoEnum;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.CampoObrigatorioEmptyException;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.exceptions.DatabaseException;
import accenturebank.com.accentureBank.exceptions.SaldoInsuficienteException;
import accenturebank.com.accentureBank.interfaces.ContaCorrenteCRUD;
import accenturebank.com.accentureBank.repositories.AgenciaRepository;
import accenturebank.com.accentureBank.repositories.ContaCorrenteRepository;
import accenturebank.com.accentureBank.repositories.ExtratoRepository;

@Service
public class ContaCorrenteService implements ContaCorrenteCRUD {
	@Autowired
	ContaCorrenteRepository contaCorrenteRepository;
	@Autowired
	ClienteService clienteService;
	@Autowired
	AgenciaService agenciaService;
	@Autowired
	AgenciaRepository agenciaRepository;
	@Autowired
	ExtratoRepository extratoRepository;
	@Autowired
	ExtratoService extratoContaCorrenteService;

	public List<ContaCorrente> getAllContasCorrentes() {
		return contaCorrenteRepository.findAll();

	}

	public ContaCorrente getContaCorrenteById(Long id){

		Optional<ContaCorrente> obj = contaCorrenteRepository.findById(id);
		return obj.orElseThrow(() -> new ContaCorrenteNotFoundException("Conta Corrente nao encontrada " + id));
	}

	public double getSaldoContaCorrenteByIdCliente(long id){

		double saldo = contaCorrenteRepository.findById(id).get().getSaldo();

		return saldo;
	}

	public Double sacar(Long id, double valorSaque) {

		// VALIDANDO SE A CONTA EXISTE
		contaCorrenteRepository.findById(id);

		// PEGAR O SALDO DA CONTA E CALCULAR O SAQUE
		double contaCorrenteSaldo = contaCorrenteRepository.findById(id).get().getSaldo();
		double resultadoSaque = contaCorrenteSaldo - valorSaque;

		if ((contaCorrenteSaldo >= valorSaque) && (valorSaque > 0)) {
			operacaoContaCorrente(id, resultadoSaque, valorSaque, TipoDeOperacaoEnum.SAQUE);
			return valorSaque;
		} else {
			throw new SaldoInsuficienteException("Saldo insuficiente para saque");
		}

	}

	public Double depositar(Long id, double valorDeposito) {

		// VALIDANDO SE A CONTA EXISTE
		contaCorrenteRepository.findById(id);

		// PEGAR O SALDO DA CONTA E CALCULAR O DEPOSITO
		double contaCorrenteSaldo = contaCorrenteRepository.findById(id).get().getSaldo();
		double resultadoDeposito = contaCorrenteSaldo + valorDeposito;

		if (valorDeposito > 0) {
			operacaoContaCorrente(id, resultadoDeposito, valorDeposito, TipoDeOperacaoEnum.DEPOSITO);
			return valorDeposito;

		}
		throw new SaldoInsuficienteException("O valor de deposito tem que ser maior que 1");

	}

	public Double transferir(long idContaInicial, long idContaDestino, double valorTransferencia) {

		// VALIDANDO SE A CONTA EXISTE
		Optional<ContaCorrente> contaCorrenteInicial = contaCorrenteRepository.findById(idContaInicial);
		Optional<ContaCorrente> contaCorrenteDestino = contaCorrenteRepository.findById(idContaDestino);

		if (valorTransferencia <= 0) {
			throw new ContaCorrenteNotFoundException("Valor inválido.");
		}

		// PEGANDO O SALDO DAS CONTAS

		double contaCorrenteInicialSaldo = contaCorrenteInicial.get().getSaldo();
		double contaCorrenteDestinoSaldo = contaCorrenteDestino.get().getSaldo();

		// CALCULOS DAS OPERAÇÕES

		double depositoContaCorrenteDestino = contaCorrenteDestinoSaldo + valorTransferencia;
		double saqueContaCorrenteInicial = contaCorrenteInicialSaldo - valorTransferencia;

		if (contaCorrenteInicialSaldo >= valorTransferencia) {

			// SAQUE NA CONTA INICIAL
			operacaoContaCorrente(idContaInicial, saqueContaCorrenteInicial, valorTransferencia,
					TipoDeOperacaoEnum.TRANSFERENCIA);

			// DEPOSITO NA CONTA DESTINO
			operacaoContaCorrente(idContaDestino, depositoContaCorrenteDestino, valorTransferencia,
					TipoDeOperacaoEnum.TRANSFERENCIA);

			return valorTransferencia;
		} else {
			throw new SaldoInsuficienteException("Saldo em conta inferior a valor de transferência");
		}
	}

	public ContaCorrente save(ContaCorrenteDTO contaCorrenteDTO) throws AgenciaNotFoundException {

		validate(contaCorrenteDTO);
		Cliente clienteRetorno = clienteService.getClienteById(contaCorrenteDTO.getIdCliente());
		Agencia agenciaRetorno = agenciaService.getAgenciaById(contaCorrenteDTO.getIdAgencia());

		Cliente cliente = new Cliente(contaCorrenteDTO.getIdCliente());
		Agencia agencia = new Agencia(contaCorrenteDTO.getIdAgencia());

		ContaCorrente contaCorrente = new ContaCorrente(agencia, gerarNumeroContaCorrente(), 0, cliente);
		ContaCorrente contaCorrenteRetorno = contaCorrenteRepository.save(contaCorrente);

		contaCorrenteRetorno.setAgencia(agenciaRetorno);
		contaCorrenteRetorno.setCliente(clienteRetorno);

		return contaCorrenteRetorno;

	}

	public void operacaoContaCorrente(long id, double resultadoOperacao, double valorOperacao,
			TipoDeOperacaoEnum operacao) {
		Long contaCorrenteId = contaCorrenteRepository.getById(id).getId();
		Agencia agenciaContaCorrente = contaCorrenteRepository.getById(id).getAgencia();
		String numeroContaCorrente = contaCorrenteRepository.getById(id).getNumero();
		Cliente clienteContaCorrente = contaCorrenteRepository.getById(id).getCliente();
		double saldoContaCorrente = contaCorrenteRepository.getById(id).getSaldo();

		ContaCorrente contaCorrente = new ContaCorrente(contaCorrenteId, agenciaContaCorrente, numeroContaCorrente,
				resultadoOperacao, clienteContaCorrente);

		contaCorrenteRepository.save(contaCorrente);

		LocalDateTime data = LocalDateTime.now();
		Extrato extratoContaCorrente = new Extrato(valorOperacao, saldoContaCorrente, data, operacao, contaCorrente);
		extratoRepository.save(extratoContaCorrente);
	}

	public Double recalcularSaldo(long id) {
		double saldoAtual = this.getSaldoContaCorrenteByIdCliente(id);
		List<Extrato> listExtrato = extratoContaCorrenteService.getAllExtratoporCliente(id);

		double valorSaques = 0, valorDepositos = 0, valorTransferenciasRealizadas = 0;
		double valorTotalExtrato = 0;
		for (Extrato operacao : listExtrato) {
			if (operacao.getOperacao().equals(TipoDeOperacaoEnum.SAQUE)) {
				valorSaques = valorSaques + operacao.getValorOperacao();
			}
			if (operacao.getOperacao().equals(TipoDeOperacaoEnum.DEPOSITO)) {
				valorDepositos = valorDepositos + operacao.getValorOperacao();
			}
			if (operacao.getOperacao().equals(TipoDeOperacaoEnum.TRANSFERENCIA)) {
				valorTransferenciasRealizadas = valorTransferenciasRealizadas + operacao.getValorOperacao();
			}

		}
		valorTotalExtrato = (valorDepositos + valorTransferenciasRealizadas) - (valorSaques);

		// BUSCAR CONTA CORRENTE PELO ID DO CLIENTE
		// buscar id da conta
		ContaCorrente getContaCorrenteByIdCliente = getAllContasCorrentes().stream()
				.filter(idconta -> idconta.getCliente().getId() == id).findFirst().get();
		long contaId = getContaCorrenteByIdCliente.getId();

		if (valorTotalExtrato == saldoAtual) {
			return saldoAtual;
		} else {
			this.getContaCorrenteById(contaId).setSaldo(valorTotalExtrato);
			contaCorrenteRepository.save(getContaCorrenteByIdCliente);
			return (double) 0;
		}
	}

	public void delete(long id) {
		try {
			contaCorrenteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new AgenciaNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}


	}

	public ContaCorrente getContaCorrenteByCliente(Cliente cliente) {
		return contaCorrenteRepository.findByCliente(cliente);
	}

	private String gerarNumeroContaCorrente() {
		Integer size = this.getAllContasCorrentes().size();
		int numero = size + 1;
		String numeroContaCorrente = Integer.toString(numero);
		return numeroContaCorrente;
	}

	private void validate(ContaCorrenteDTO obj) {

		if (obj.getIdAgencia() == null) {
			throw new CampoObrigatorioEmptyException("Por favor informe o id da agencia");
		}
		if (obj.getIdCliente() == null) {
			throw new CampoObrigatorioEmptyException("Por favor informe o id do cliente");
		}

	}

}
