package com.bridgelabz.fundo.exception;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
		
	public ErrorResponse(int status, String message, String token,Object object) {
		this.status = status;
		this.message = message;
		this.token = token;
		this.object=object;
	}
	public ErrorResponse()
	{}
	private int status;
	private String message;
	private String token;
	private Object object;
	
}
