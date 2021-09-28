package accenturebank.com.accentureBank.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.domain.Extrato;

public interface ExtratoRepository extends JpaRepository<Extrato, Long> {
	List<Extrato> findByContaCorrente(ContaCorrente contaCorrente);
	
	
}