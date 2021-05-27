package com.valdir.hp.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.hp.enums.Perfil;
import com.valdir.hp.enums.Prioridade;
import com.valdir.hp.enums.Status;
import com.valdir.hp.model.Chamado;
import com.valdir.hp.model.Cliente;
import com.valdir.hp.model.Tecnico;
import com.valdir.hp.repositories.ChamadoRepository;
import com.valdir.hp.repositories.PessoaRepository;

@Service
public class DBService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ChamadoRepository chamadoRepository;

	public void instanciaDB() {
		Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "550.482.150-95", "valdir@mail.com", "123");
		tec1.addPerfil(Perfil.ROLE_ADMIN);
		
		Cliente cli1 = new Cliente(null, "Linus Torvalds", "778.556.170-27", "linux@mail.com", "123");
		
		Chamado c1 = new Chamado(null, null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "teste chamado 1", tec1, cli1);
		
		tec1.getChamados().addAll(Arrays.asList(c1));
		cli1.getChamados().addAll(Arrays.asList(c1));

		pessoaRepository.saveAll(Arrays.asList(tec1, cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}
}
