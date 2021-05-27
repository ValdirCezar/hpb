package com.valdir.hp.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;

@Entity
public class Tecnico extends Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;

	public Tecnico() {
		super();
	}

	public Tecnico(Integer id, String nome, String cpf, String email, String senha, LocalDate dataCriacao) {
		super(id, nome, cpf, email, senha, dataCriacao);
	}
	
}
