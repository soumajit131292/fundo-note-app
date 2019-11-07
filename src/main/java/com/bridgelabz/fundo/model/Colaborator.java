package com.bridgelabz.fundo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "colaborator")
public class Colaborator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "colabId")
	private Integer colabId;
	@Column(name = "userId")
	private Integer userId;
	@Column(name = "userEmailId")
	private String userEmailId;
	
}
