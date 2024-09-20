package com.jsp.uber;

import com.jsp.uber.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UberApplicationTests {

	@Autowired
	private EmailSenderService emailSenderService;

	@Test
	void contextLoads() {
		emailSenderService.sendEmail(
				"fifeti9072@sigmazon.com",
				"Test this email address",
				"Test this email Body"
		);
	}

	@Test
	void sendEmailMultiple(){

		String[] emails = {"redipid629@ofionk.com",
				};

		emailSenderService.sendEmail(
                emails,
                "Hello from UBER APPLICATION demo created by Manjunath",
                "This mail is to Test the sending of multiple emails " +
						"No matter which field of work you want to go in, it is of great importance to learn" +
						" at least one programming language. "
        );
	}
}
