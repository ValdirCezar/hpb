package com.valdir.hp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.hp.model.Chamado;
import com.valdir.hp.repositories.ChamadoRepository;
import com.valdir.hp.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	
	/**
	 * Busca um Chamado por ID
	 * 
	 * @param id
	 * @return
	 */
	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Chamado.class.getSimpleName()));
	}
	
	/**
	 * Lista todos os Chamados do banco
	 * 
	 * @return
	 */
	public List<Chamado> findAll() {
		return repository.findAll();
	}
	
}
