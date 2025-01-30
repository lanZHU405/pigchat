package pig.chat.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pig.chat.springboot.domain.Talk;

@SpringBootTest
class SpringbootApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostMapping("/sendMessage")
	@Test
	public void sendMessage(){

		rabbitTemplate.convertAndSend("exchange.pigchat", "", new Talk());
	}

}
