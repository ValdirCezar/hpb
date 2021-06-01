package com.valdir.hp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * Busca um Tecnico por ID
	 * 
	 * @param id
	 * @return
	 */
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Tecnico.class.getSimpleName()));
	}

	/**
	 * Lista todos os Tecnicos do banco
	 * 
	 * @return
	 */
	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	/**
	 * Cria um novo Tecnico
	 * 
	 * @param objDTO
	 * @return
	 */
	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEmail(objDTO);
		return repository.save(new Tecnico(objDTO));
	}

	/**
	 * Atualiza as informações de um Tecnico
	 * 
	 * @param id
	 * @param objDTO
	 * @return
	 */
	public Tecnico update(Integer id, TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico old = findById(id);
		validaPorCpfEmail(objDTO);
		old = new Tecnico(objDTO);
		return repository.save(old);
	}

	/**
	 * Deleta um Tecnico
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		Tecnico obj = findById(id);

		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException(
					"Técnico " + obj.getNome() + " possui chamados associados e não pode ser deletado! Id: " + id);
		} else {
			repository.deleteById(id);
		}
	}

	/**
	 * Busca Tecnico por CPF
	 * 
	 * @param cpf
	 * @return
	 */
	public Optional<Pessoa> findByCpf(String cpf) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(cpf);
		return obj.isPresent() ? obj : null;
	}

	/**
	 * Busca Tecnico por E-mail
	 * 
	 * @param email
	 * @return
	 */
	public Optional<Pessoa> findByEmail(String email) {
		Optional<Pessoa> obj = pessoaRepository.findByEmail(email);
		return obj.isPresent() ? obj : null;
	}

	/**
	 * Valida se existe Alguma pessoa cadastrada no banco com o CPF ou E-mail
	 * passado como parametro
	 * 
	 * @param objDTO
	 */
	private void validaPorCpfEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId())
			throw new DataIntegrityViolationException("CPF " + objDTO.getCpf() + " já cadastrado no sistema!");

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId())
			throw new DataIntegrityViolationException("E-mail " + objDTO.getEmail() + " já cadastrado no sistema!");
	}
}
