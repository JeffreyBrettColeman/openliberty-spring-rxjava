package io.jbc.libertyrxjava.application;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoLibertyRxApplicationTests {
	
	@Autowired
	AccountRxJavaRepository accountRxJavaRepository;

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void givenValue_whenFindAllByValue_thenFindAccount() {


	    assertNotNull(accountRxJavaRepository.save(new Account()));
	}


}
