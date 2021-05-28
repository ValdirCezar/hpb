package com.valdir.hp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.hp.dtos.TecnicoDTO;
import com.valdir.hp.model.Pessoa;
import com.valdir.hp.model.Tecnico;
import com.valdir.hp.repositories.PessoaRepository;
import com.valdir.hp.repositories.TecnicoRepository;
import com.valdir.hp.services.exceptions.DataIntegrityViolationException;
import com.valdir.hp.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Tecnico.class.getSimpleName()));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		
		if(findByCpf(objDTO.getCpf()) != null) {
			throw new DataIntegrityViolationException("CPF " + objDTO.getCpf() + " já cadastrado no sistema!");
		}
		
		if(findByEmail(objDTO.getEmail()) != null) {
			throw new DataIntegrityViolationException("E-mail " + objDTO.getEmail() + " já cadastrado no sistema!");
		}
		
		return repository.save(new Tecnico(objDTO));
	}

	public Tecnico update(Integer id, TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico old = findById(id);
		validaUpdate(objDTO);
		old = new Tecnico(objDTO);
		return repository.save(old);
	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);

		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException(
					"Técnico " + obj.getNome() + " possui chamados associados e não pode ser deletado! Id: " + id);
		} else {
			repository.deleteById(id);
		}
	}
	
	public Optional<Pessoa> findByCpf(String cpf) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(cpf);
		return obj.isPresent() ? obj : null;
	}
	
	public Optional<Pessoa> findByEmail(String email) {
		Optional<Pessoa> obj = pessoaRepository.findByEmail(email);
		return obj.isPresent() ? obj : null;
	}
	
	private void validaUpdate(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) 
			throw new DataIntegrityViolationException("CPF " + objDTO.getCpf() + " já cadastrado no sistema!");
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) 
			throw new DataIntegrityViolationException("E-mail " + objDTO.getEmail() + " já cadastrado no sistema!");
	}
}
