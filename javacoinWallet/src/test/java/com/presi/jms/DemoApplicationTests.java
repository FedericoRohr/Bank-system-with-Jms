package com.presi.jms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.presi.jms.jmsUtilities.MessageSenderImp;

@SpringBootTest
class DemoApplicationTests {
	
	@Autowired
	MessageSenderImp sender;

	@Test
	void sendAndReceive() {
	//	System.out.println("mando");
		//sender.send("Se mantiene up");
	}

}
