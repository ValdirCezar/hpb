package com.valdir.hp.enums;

public enum Status {

	ABERTO(0, "Aberto"), ANDAMENTO(1, "Andamento"), ENCERRADO(2, "Encerrado");

	private Integer cod;
	private String descricao;

	private Status(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Status toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Status x : Status.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Status inválido!");
	}
}
