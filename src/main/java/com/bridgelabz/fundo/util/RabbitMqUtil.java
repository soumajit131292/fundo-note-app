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

	private String exchange = "my_queue_exchange";

	
	private String routingKey = "my.queue.key";

	public void Producemessage(Message message) {
		System.out.println("hey");
		amqpTemplate.convertAndSend(exchange, routingKey, message);
	}

	@RabbitListener(queues="my.queue.key")
	public void sendEmail(Message message) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(message.getTo());
		helper.setSubject(message.getSubject());
		helper.setText(message.getText());
		emailSender.send(mimeMessage);
	}

}
