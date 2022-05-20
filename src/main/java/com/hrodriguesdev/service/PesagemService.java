package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.db.RepositoryPesagemDb;
import com.hrodriguesdev.entities.Pesagem;

//@Service
public class PesagemService {
	
	
	private RepositoryPesagemDb repository = new RepositoryPesagemDb();
	
	public Pesagem getById(Long id) {
		return repository.getById(id);		
	}

	public List<Pesagem> getByMotoristaId(Long id) {
		
		return repository.findByMotoristaId(id);
	}

	public Boolean addPesagem(Pesagem pesagem) {
		return repository.save(pesagem);
	}

	public List<Pesagem> getByDescarregando(boolean descarregando) {
		return repository.getByDescarregando(descarregando);
	}


	public Boolean updatePesagem(Long idPesagem, Long idMotorista) {
		return repository.update(idPesagem, idMotorista);
	}
}
