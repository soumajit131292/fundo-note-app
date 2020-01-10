package com.bridgelabz.fundo.dto;

import lombok.Data;

@Data

public class ResetPassword {
	public ResetPassword(String pass) {
		password=pass;
	}
	private String password;
}
