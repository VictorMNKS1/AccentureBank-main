package accenturebank.com.accentureBank;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import accenturebank.com.accentureBank.domain.ContaCorrente;
import accenturebank.com.accentureBank.repositories.ContaCorrenteRepository;

@SpringBootTest
public class ContaCorrenteRepositoryTest {
	
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;

	@Test
    public void testReadContaCorrente() {
		ContaCorrente testRead;
		
		assertThat(contaCorrenteRepository).isNotNull();
		
        ContaCorrente cc = new ContaCorrente();
        cc.setNumero("5776");
        cc.setSaldo(2000.0);
        contaCorrenteRepository.saveAndFlush(cc);
        
        testRead = contaCorrenteRepository.findById(cc.getId()).orElseThrow(NoSuchElementException::new);
        
        assertThat(testRead.getSaldo()).isEqualTo(2000.0);
        assertThat(testRead.getNumero()).isEqualTo("5776");
    }
	
    @Test
    public void testUpdateContaCorrente() {
        ContaCorrente testeUpdate;

        ContaCorrente cc = new ContaCorrente();
        cc.setNumero("5776");
        cc.setSaldo(2000.0);
        contaCorrenteRepository.saveAndFlush(cc);

        cc.setNumero("66666");
        cc.setSaldo(4000.0);
        contaCorrenteRepository.saveAndFlush(cc);

        testeUpdate = contaCorrenteRepository.findById(cc.getId()).orElseThrow(NoSuchElementException::new);

        assertThat(testeUpdate.getNumero()).isEqualTo("66666");
        assertThat(testeUpdate.getSaldo()).isEqualTo(4000.0);
    }
    
 
	
	}


  