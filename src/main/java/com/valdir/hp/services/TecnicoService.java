package com.valdir.hp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.hp.dtos.TecnicoDTO;
import com.valdir.hp.model.Tecnico;
import com.valdir.hp.repositories.TecnicoRepository;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElse(null);
	}
	
	public List<Tecnico> findAll() {
		return repository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		return repository.save(new Tecnico(objDTO));
	}

	public Tecnico update(Integer id, TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico old = findById(id);
		old = new Tecnico(objDTO);
		return repository.save(old);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getChamados().size() > 0) {
			throw new RuntimeException("Técnico possui chamados associados e não pode ser deletado");
		} else {
			repository.deleteById(id);
		}
	}
}
