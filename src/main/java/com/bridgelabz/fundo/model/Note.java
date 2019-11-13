package com.bridgelabz.fundo.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "note")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "createdOn")
	private Timestamp createdOn;
	@Column(name = "updatedOn")
	private Timestamp updatedOn;
	@Column(name = "inTrash")
	private boolean inTrash;
	@Column(name = "isPinned")
	private boolean isPinned;
	@Column(name = "isArchive")
	private boolean isArchive;
	@Column(name="colorCode")
	private String colorCode;
	@Column(name = "remainder")
	private LocalDateTime remainder;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "label_note", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "label_id") })
	
	private List<Label> labels;

	
	public void addLabel(Label theLabel) {
		if (labels == null) {
			labels = new ArrayList<>();
		}
		labels.add(theLabel);
	}
	public void removLabel(Label theLabel) {
		if (labels == null) {
			labels = new ArrayList<>();
		}
		labels.remove(theLabel);
	}


	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "colab_note", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	@JsonIgnore
	private List<UserDetailsForRegistration> colabsUser;

	public void addColab(UserDetailsForRegistration theNote) {
		if (colabsUser == null) {
			colabsUser = new ArrayList<>();
		}
		colabsUser.add(theNote);
	}
	public void removeColab(UserDetailsForRegistration theNote) {
		if (colabsUser == null) {
			colabsUser = new ArrayList<>();
		}
		colabsUser.remove(theNote);
	}
	


}