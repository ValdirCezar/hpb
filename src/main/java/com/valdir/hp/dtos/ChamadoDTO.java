package com.valdir.hp.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.valdir.hp.model.Chamado;

public class ChamadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;

	@NotNull(message = "Campo PRIORIDADE é requerido")
	private Integer prioridade;
	@NotNull(message = "Campo STATUS é requerido")
	private Integer status;
	@NotNull(message = "Campo TITULO é requerido")
	private String titulo;
	@NotNull(message = "Campo DESCRIÇÃO é requerido")
	private String descricao;
	@NotNull(message = "Campo TECNICO é requerido")
	private Integer tecnico;
	@NotNull(message = "Campo CLIENTE é requerido")
	private Integer cliente;

	private String nomeCliente;
	private String nomeTecnico;

	public ChamadoDTO() {
		super();
	}

	public ChamadoDTO(Chamado obj) {
		super();
		this.id = obj.getId();
		this.dataAbertura = obj.getDataAbertura();
		this.dataFechamento = obj.getDataFechamento();
		this.prioridade = obj.getPrioridade().getCod();
		this.status = obj.getStatus().getCod();
		this.titulo = obj.getTitulo();
		this.descricao = obj.getDescricao();
		this.tecnico = obj.getTecnico().getId();
		this.cliente = obj.getCliente().getId();
		this.nomeCliente = obj.getCliente().getNome();
		this.nomeTecnico = obj.getTecnico().getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getTecnico() {
		return tecnico;
	}

	public void setTecnico(Integer tecnico) {
		this.tecnico = tecnico;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

}
