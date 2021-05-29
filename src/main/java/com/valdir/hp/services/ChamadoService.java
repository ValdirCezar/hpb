package com.valdir.hp.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valdir.hp.dtos.ChamadoDTO;
import com.valdir.hp.enums.Prioridade;
import com.valdir.hp.enums.Status;
import com.valdir.hp.model.Chamado;
import com.valdir.hp.model.Cliente;
import com.valdir.hp.model.Tecnico;
import com.valdir.hp.repositories.ChamadoRepository;
import com.valdir.hp.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;

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

	/**
	 * Cria um novo chamado
	 * 
	 * @param objDTO
	 * @return
	 */
	public Chamado create(ChamadoDTO obj) {
		return repository.save(newChamado(obj));
	}
	
	/**
	 * Atualiza Chamado
	 * @param id
	 * @param objDTO
	 * @return
	 */
	public Chamado update(Integer id, ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);
		return repository.save(oldObj);
	}

	/*
	 * Metodo para criar novo chamado
	 */
	private Chamado newChamado(ChamadoDTO obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());

		Chamado newObj = new Chamado();
		
		if(obj.getId() != null) {
			newObj.setId(obj.getId());
		}
		
		newObj.setDataAbertura(LocalDate.now());

		if (obj.getStatus().equals(2)) {
			newObj.setDataFechamento(LocalDate.now());
		}

		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));
		newObj.setCliente(cliente);
		newObj.setTecnico(tecnico);
		newObj.setTitulo(obj.getTitulo());
		newObj.setDescricao(obj.getDescricao());
		return newObj;
	}

}
