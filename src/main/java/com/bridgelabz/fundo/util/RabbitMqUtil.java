package com.bridgelabz.fundo.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class RabbitMqUtil {
	@Autowired
private JavaMailSender emailSender;
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void message()
	{
		//amqpTemplate.convertAndSend(message, messagePostProcessor);
	}

	
				
		
		
		@RabbitListener
		public void sendEmail(String url, String generatedToken, String emailId) throws MessagingException {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(emailId);
			helper.setSubject("verify");
			helper.setText(url + generatedToken);

			emailSender.send(message);
		}
	
}
