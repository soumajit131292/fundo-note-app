package com.bridgelabz.fundo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "user_registration_details")
public class UserDetailsForRegistration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Column(name = "email")
	private String email;
	@Column(name = "gender")
	private String gender;
	@Column(name = "password")
	private String password;
	@Column(name = "active_status")
	private String activeStatus;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Note> note;

	public void addNote(Note theReview) {
		if (note == null) {
			note = new ArrayList<>();
		}
		note.add(theReview);
	}
}