package com.bridgelabz.fundo.exception;

import lombok.Data;

@Data
public class ErrorResponse {
		
	public ErrorResponse(int status, String message, String token) {
		this.status = status;
		this.message = message;
		this.token = token;
	}
	public ErrorResponse()
	{}
	private int status;
	private String message;
	private String token;
}
