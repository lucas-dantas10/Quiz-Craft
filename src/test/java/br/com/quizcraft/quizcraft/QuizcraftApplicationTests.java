package br.com.quizcraft.quizcraft;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
		locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class QuizcraftApplicationTests {

	@Test
	void contextLoads() {
	}

}
