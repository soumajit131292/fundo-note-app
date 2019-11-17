package com.bridgelabz.fundo.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundo.model.Message;

@Component
public class RabbitMqUtil {
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private AmqpTemplate amqpTemplate;

	private String exchange = "spring.boot.exchange";

	/**** my.queue.key *****/
	private String routingKey = "spring";

	public void Producemessage(Message message) {
		amqpTemplate.convertAndSend(exchange, routingKey, message);
	}

	@RabbitListener
	public void sendEmail(Message message) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(message.getTo());
		helper.setSubject(message.getSubject());
		helper.setText(message.getText());

		emailSender.send(mimeMessage);
	}

}
