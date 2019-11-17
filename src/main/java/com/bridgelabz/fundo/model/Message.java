package com.bridgelabz.fundo.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@Data
public class Message implements Serializable{

	
		private static final long serialVersionUID = 1L;
		private String to;
		private String text;
		private String subject;

	
}
