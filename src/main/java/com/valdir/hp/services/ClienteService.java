package com.valdir.hp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.hp.dtos.ClienteDTO;
import com.valdir.hp.model.Cliente;
import com.valdir.hp.model.Pessoa;
import com.valdir.hp.repositories.ClienteRepository;
import com.valdir.hp.repositories.PessoaRepository;
import com.valdir.hp.services.exceptions.DataIntegrityViolationException;
import com.valdir.hp.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	/**
	 * Busca um Cliente por ID
	 * 
	 * @param id
	 * @return
	 */
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo " + Cliente.class.getSimpleName()));
	}

	/**
	 * Lista todos os Clientes do banco
	 * 
	 * @return
	 */
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	/**
	 * Cria um novo Cliente
	 * 
	 * @param objDTO
	 * @return
	 */
	public Cliente create(ClienteDTO objDTO) {
		validaPorCpfEmail(objDTO);
		return repository.save(new Cliente(objDTO));
	}

	/**
	 * Atualiza as informações de um Cliente
	 * 
	 * @param id
	 * @param objDTO
	 * @return
	 */
	public Cliente update(Integer id, ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente old = findById(id);
		validaPorCpfEmail(objDTO);
		old = new Cliente(objDTO);
		return repository.save(old);
	}

	/**
	 * Deleta um Cliente
	 * 
	 * @param id
	 */
	public void delete(Integer id) {
		Cliente obj = findById(id);

		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException(
					"Técnico " + obj.getNome() + " possui chamados associados e não pode ser deletado! Id: " + id);
		} else {
			repository.deleteById(id);
		}
	}

	/**
	 * Busca Cliente por CPF
	 * 
	 * @param cpf
	 * @return
	 */
	public Optional<Pessoa> findByCpf(String cpf) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(cpf);
		return obj.isPresent() ? obj : null;
	}

	/**
	 * Busca Cliente por E-mail
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
	private void validaPorCpfEmail(ClienteDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId())
			throw new DataIntegrityViolationException("CPF " + objDTO.getCpf() + " já cadastrado no sistema!");

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId())
			throw new DataIntegrityViolationException("E-mail " + objDTO.getEmail() + " já cadastrado no sistema!");
	}
}
