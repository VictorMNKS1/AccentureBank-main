package accenturebank.com.accentureBank.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import accenturebank.com.accentureBank.domain.Agencia;
import accenturebank.com.accentureBank.domain.Cliente;
import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.domain.Extrato;
import accenturebank.com.accentureBank.dto.AgenciaDTO;
import accenturebank.com.accentureBank.dto.ContaCorrenteDTO;
import accenturebank.com.accentureBank.entities.enums.TipoDeOperacaoEnum;
import accenturebank.com.accentureBank.exceptions.AgenciaNotFoundException;
import accenturebank.com.accentureBank.exceptions.ContaCorrenteNotFoundException;
import accenturebank.com.accentureBank.interfaces.OperacoesConta;
import accenturebank.com.accentureBank.repositories.AgenciaRepository;
import accenturebank.com.accentureBank.repositories.ContaCorrenteRepository;
import accenturebank.com.accentureBank.repositories.ExtratoRepository;

@Service
public class ContaCorrenteService implements OperacoesConta {
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
		List<ContaCorrente> contasCorrentes = new ArrayList<ContaCorrente>();
		contaCorrenteRepository.findAll().forEach(contaCorrente -> contasCorrentes.add(contaCorrente));
		return contasCorrentes;
	}

	public ContaCorrente getIdContaCorrente(Long id) throws ContaCorrenteNotFoundException {

		Optional<ContaCorrente> contaCorrenteReturn = contaCorrenteRepository.findById(id);
		if (contaCorrenteReturn.isEmpty()) {
			throw new ContaCorrenteNotFoundException("CONTA CORRENTE NÃO ENCONTRADA");
		}
		return contaCorrenteReturn.get();
	}

	public double getSaldoContaCorrenteByIdCliente(long id) throws ContaCorrenteNotFoundException {

		// BUSCAR SALDO PELO PELO ID DO CLIENTE
		ContaCorrente getSaldoContaCorrenteByIdCliente = getAllContasCorrentes().stream()
				.filter(conta -> conta.getId() == id).findFirst().get();

		double saldo = getSaldoContaCorrenteByIdCliente.getContaCorrenteSaldo();

		return saldo;
	}

	public String sacar(Long id, double valorSaque) {

		// VALIDANDO SE A CONTA EXISTE
		contaCorrenteRepository.findById(id);

		// PEGAR O SALDO DA CONTA E CALCULAR O SAQUE
		double contaCorrenteSaldo = contaCorrenteRepository.findById(id).get().getContaCorrenteSaldo();
		double resultadoSaque = contaCorrenteSaldo - valorSaque;

		if ((contaCorrenteSaldo >= valorSaque) && (valorSaque > 0)) {
			operacaoContaCorrente(id, resultadoSaque, valorSaque, TipoDeOperacaoEnum.SAQUE);
			return "Saque efetuado";
		} else {
			return "Saldo insuficiente";
		}

	}

	public String depositar(Long id, double valorDeposito) {

		// VALIDANDO SE A CONTA EXISTE
		contaCorrenteRepository.findById(id);

		// PEGAR O SALDO DA CONTA E CALCULAR O DEPOSITO
		double contaCorrenteSaldo = contaCorrenteRepository.findById(id).get().getContaCorrenteSaldo();
		double resultadoDeposito = contaCorrenteSaldo + valorDeposito;

		if (valorDeposito > 0) {

			// DEPOSITO NA CONTA
			operacaoContaCorrente(id, resultadoDeposito, valorDeposito, TipoDeOperacaoEnum.DEPOSITO);
			return "Deposito efetuado";
		} else {
			return "Valor invalido para deposito";
		}

	}

	public String transferir(long idContaInicial, long idContaDestino, double valorTransferencia) {

		// VALIDANDO SE A CONTA EXISTE
		Optional<ContaCorrente> contaCorrenteInicial = contaCorrenteRepository.findById(idContaInicial);
		Optional<ContaCorrente> contaCorrenteDestino = contaCorrenteRepository.findById(idContaDestino);

		if (valorTransferencia <= 0) {
			throw new ContaCorrenteNotFoundException("Valor inválido.");
		}

		// PEGANDO O SALDO DAS CONTAS

		double contaCorrenteInicialSaldo = contaCorrenteInicial.get().getContaCorrenteSaldo();
		double contaCorrenteDestinoSaldo = contaCorrenteDestino.get().getContaCorrenteSaldo();

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

			return "Transferência efetuada";
		} else {
			return "Valor inválido para transferência";
		}
	}

	public ContaCorrente save(ContaCorrenteDTO contaCorrenteDTO) throws AgenciaNotFoundException {
		Cliente clienteRetorno = clienteService.getClienteById(contaCorrenteDTO.getIdCliente());
		AgenciaDTO agenciaRetorno = agenciaService.getAgenciaById(contaCorrenteDTO.getIdAgencia());

		Cliente cliente = new Cliente(contaCorrenteDTO.getIdCliente(), null, null, null);
		Agencia agencia = new Agencia(contaCorrenteDTO.getIdAgencia(), null, null, null);

		ContaCorrente contaCorrente = new ContaCorrente(null, agencia, gerarNumeroContaCorrente(), 0, cliente);
		ContaCorrente contaCorrenteRetorno = contaCorrenteRepository.save(contaCorrente);

		contaCorrenteRetorno.setAgencia(new Agencia(agenciaRetorno));
		contaCorrenteRetorno.setCliente(clienteRetorno);

		return contaCorrenteRetorno;

	}

	public void operacaoContaCorrente(long id, double resultadoOperacao, double valorOperacao,
			TipoDeOperacaoEnum operacao) {
		Long contaCorrenteId = contaCorrenteRepository.getById(id).getId();
		Agencia agenciaContaCorrente = contaCorrenteRepository.getById(id).getAgencia();
		String numeroContaCorrente = contaCorrenteRepository.getById(id).getContaCorrenteNumero();
		Cliente clienteContaCorrente = contaCorrenteRepository.getById(id).getCliente();

		ContaCorrente contaCorrente = new ContaCorrente(contaCorrenteId, agenciaContaCorrente, numeroContaCorrente,
				resultadoOperacao, clienteContaCorrente);

		contaCorrenteRepository.save(contaCorrente);

		LocalDateTime data = LocalDateTime.now();
		Extrato extratoContaCorrente = new Extrato(null, valorOperacao, data, operacao, contaCorrente);
		extratoRepository.save(extratoContaCorrente);
	}

	public String recalcularSaldo(long id) {
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
            return "O saldo está correto.";
        } else {
            this.getIdContaCorrente(contaId).setContaCorrenteSaldo(valorTotalExtrato);
            contaCorrenteRepository.save(getContaCorrenteByIdCliente);
            return "O seu saldo foi atualizado.";
        }
        }

	public Boolean deleteContaCorrente(long id) throws ContaCorrenteNotFoundException {
		contaCorrenteRepository.deleteById(id);
		return true;

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

}
