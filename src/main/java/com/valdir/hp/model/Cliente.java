package com.valdir.hp.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Cliente extends Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;

	public Cliente() {
		super();
	}

	public Cliente(Integer id, String nome, String cpf, String email, String senha, LocalDate dataCriacao) {
		super(id, nome, cpf, email, senha, dataCriacao);
	}
	
}
