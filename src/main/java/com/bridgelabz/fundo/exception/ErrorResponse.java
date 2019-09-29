package com.bridgelabz.fundo.exception;

import lombok.Data;

@Data
public class ErrorResponse {
		public ErrorResponse(int value, String string, Object object) {
	}
	public ErrorResponse() {
	}
	private int status;
	private String message;
	private String token;
}
