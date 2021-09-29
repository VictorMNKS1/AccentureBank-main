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
import accenturebank.com.accentureBank.repositories.AgenciaRepository;
import accenturebank.com.accentureBank.repositories.ContaCorrenteRepository;
import accenturebank.com.accentureBank.repositories.ExtratoRepository;

@Service
public class ContaCorrenteService {
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

		// VALIDANDO SE A CONTA EXISTE
		Optional<ContaCorrente> contaCorrenteReturn = contaCorrenteRepository.findById(id);
		if (contaCorrenteReturn.isEmpty()) {
			throw new ContaCorrenteNotFoundException("Conta Corrente não encontrada");
		}
		return contaCorrenteReturn.get();
	}

	public double getSaldoByIdCliente(long id) throws ContaCorrenteNotFoundException {

		// BUSCAR SALDO PELO O ID DO CLIENTE
		ContaCorrente getSaldoByIdCliente = getAllContasCorrentes().stream()
				.filter(conta -> conta.getCliente().getId() == id).findFirst().get();

		double saldo = getSaldoByIdCliente.getContaCorrenteSaldo();

		return saldo;
	}

	public String Saque(Long id, double valorSaque) throws ContaCorrenteNotFoundException {

		// VALIDANDO SE A CONTA EXISTE
		Optional<ContaCorrente> contaCorrenteReturn = contaCorrenteRepository.findById(id);
		if (contaCorrenteReturn.isEmpty()) {
			throw new ContaCorrenteNotFoundException("Conta Corrente não encontrada");
	}

		// PEGAR O SALDO DA CONTA E CALCULAR O SAQUE
		double contaCorrenteSaldo = contaCorrenteRepository.findById(id).get().getContaCorrenteSaldo();
		double resultadoSaque = contaCorrenteSaldo - valorSaque;

		if ((contaCorrenteSaldo >= valorSaque) && (valorSaque > 0) ) {
			operacaoContaCorrente(id, resultadoSaque, valorSaque, TipoDeOperacaoEnum.SAQUE);
			return "Saque efetuado";
		} else {
			return "Saldo insuficiente";
		}

	}

	public String Depositar(Long id, double valorDeposito) throws ContaCorrenteNotFoundException {

		// VALIDANDO SE A CONTA EXISTE
		Optional<ContaCorrente> contaCorrenteReturn = contaCorrenteRepository.findById(id);
		if (contaCorrenteReturn.isEmpty()) {
			throw new ContaCorrenteNotFoundException("Conta Corrente não encontrada");
	}
		// PEGAR O SALDO DA CONTA E CALCULAR O DEPOSITO
		double contaCorrenteSaldo = contaCorrenteRepository.findById(id).get().getContaCorrenteSaldo();
		double resultadoDeposito = contaCorrenteSaldo + valorDeposito;

		if (valorDeposito > 0) {
			
			//DEPOSITO NA CONTA
			operacaoContaCorrente(id, resultadoDeposito, valorDeposito, TipoDeOperacaoEnum.DEPOSITO);
			return "Deposito efetuado";
		} else {
			return "Valor invalido para deposito";
		}

	}
	
	 public String transferencia(long idContaInicial,long idContaDestino,double valorTransferencia) throws ContaCorrenteNotFoundException {

	        //VALIDANDO SE A CONTA EXISTE
	        Optional<ContaCorrente> contaCorrenteInicial = contaCorrenteRepository.findById(idContaInicial);
	        Optional<ContaCorrente> contaCorrenteDestino = contaCorrenteRepository.findById(idContaDestino);
	        if (contaCorrenteInicial.isEmpty() || contaCorrenteDestino.isEmpty()) {
	            throw new ContaCorrenteNotFoundException("Conta Corrente não encontrada.");
	        }

	        if (valorTransferencia <= 0) {
	            throw new ContaCorrenteNotFoundException("Valor inválido.");
	        }

	        //PEGANDO O SALDO DAS CONTAS
	        
	        double contaCorrenteInicialSaldo = contaCorrenteInicial.get().getContaCorrenteSaldo();
	        double contaCorrenteDestinoSaldo = contaCorrenteDestino.get().getContaCorrenteSaldo();

	        //CALCULOS DAS OPERAÇÕES
	        
	        double depositoContaCorrenteDestino = contaCorrenteDestinoSaldo + valorTransferencia;
	        double saqueContaCorrenteInicial = contaCorrenteInicialSaldo - valorTransferencia;


	        if (contaCorrenteInicialSaldo >= valorTransferencia) {
	            //SAQUE NA CONTA INICIAL
	            operacaoContaCorrente(idContaInicial, saqueContaCorrenteInicial, valorTransferencia, TipoDeOperacaoEnum.TRANSFERENCIA);

	            //DEPOSITO NA CONTA DESTINO
	            operacaoContaCorrente(idContaDestino, depositoContaCorrenteDestino, valorTransferencia, TipoDeOperacaoEnum.TRANSFERENCIA);

	            return "Transferência efetuada";
	        } else {
	            return "Valor inválido para transferência";
	        }
	    }

	public ContaCorrente saveOrUpdate(ContaCorrenteDTO contaCorrenteDTO) throws AgenciaNotFoundException {
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

	public void operacaoContaCorrente(long id, double resultadoOperacao, double valorOperacao,TipoDeOperacaoEnum operacao) {
		Long contaCorrenteId = contaCorrenteRepository.getById(id).getId();
		Agencia agenciaContaCorrente = contaCorrenteRepository.getById(id).getAgencia();
		String numeroContaCorrente = contaCorrenteRepository.getById(id).getContaCorrenteNumero();
		Cliente clienteContaCorrente = contaCorrenteRepository.getById(id).getCliente();
		

		ContaCorrente contaCorrente = new ContaCorrente(contaCorrenteId, agenciaContaCorrente, numeroContaCorrente,
				resultadoOperacao, clienteContaCorrente);

		contaCorrenteRepository.save(contaCorrente);
		
		
		LocalDateTime data = LocalDateTime.now();
		Extrato extratoContaCorrente = new Extrato(null,data,operacao, contaCorrente);
		extratoRepository.save(extratoContaCorrente);
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
