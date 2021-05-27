package com.valdir.hp.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import com.valdir.hp.enums.Perfil;

public class TecnicoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	@CPF
	private String cpf;

	@Email
	private String email;
	private String senha;
	private LocalDateTime dataCriacao;
	private Set<Integer> perfis = new HashSet<>();

	public TecnicoDTO() {
		super();
		this.dataCriacao = LocalDateTime.now();
	}

	public TecnicoDTO(Integer id, String nome, @CPF String cpf, @Email String email, String senha,
			LocalDateTime dataCriacao, Perfil perfil) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.dataCriacao = dataCriacao;
		addPerfil(perfil);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(Perfil perfil) {
		this.perfis.add(perfil.getCod());
	}

}
