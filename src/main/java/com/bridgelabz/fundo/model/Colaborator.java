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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "colaborator")
public class Colaborator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "colabId")
	private Integer colabId;
	@Column(name = "userEmailId")
	private String userEmailId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "colab_id")
	@JsonIgnore
	private List<Note> note;

	public void addCollabIdOnNote(Note theNote) {
		if (note == null) {
			note = new ArrayList<>();
		}
		note.add(theNote);
	}

}
