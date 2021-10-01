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
        cc.setContaCorrenteNumero("5776");
        cc.setContaCorrenteSaldo(2000.0);
        contaCorrenteRepository.saveAndFlush(cc);
        
        testRead = contaCorrenteRepository.findById(cc.getId()).orElseThrow(NoSuchElementException::new);
        
        assertThat(testRead.getContaCorrenteSaldo()).isEqualTo(2000.0);
        assertThat(testRead.getContaCorrenteNumero()).isEqualTo("5776");
    }
	
    @Test
    public void testUpdateContaCorrente() {
        ContaCorrente testeUpdate;

        ContaCorrente cc = new ContaCorrente();
        cc.setContaCorrenteNumero("5776");
        cc.setContaCorrenteSaldo(2000.0);
        contaCorrenteRepository.saveAndFlush(cc);

        cc.setContaCorrenteNumero("66666");
        cc.setContaCorrenteSaldo(4000.0);
        contaCorrenteRepository.saveAndFlush(cc);

        testeUpdate = contaCorrenteRepository.findById(cc.getId()).orElseThrow(NoSuchElementException::new);

        assertThat(testeUpdate.getContaCorrenteNumero()).isEqualTo("66666");
        assertThat(testeUpdate.getContaCorrenteSaldo()).isEqualTo(4000.0);
    }
    
 
	
	}


  