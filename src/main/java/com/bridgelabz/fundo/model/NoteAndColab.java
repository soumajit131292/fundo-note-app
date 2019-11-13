package com.bridgelabz.fundo.model;

import java.util.List;

import lombok.Data;

@Data
public class NoteAndColab {

	private Note note;
	private List<UserDetailsForRegistration> user;
}
