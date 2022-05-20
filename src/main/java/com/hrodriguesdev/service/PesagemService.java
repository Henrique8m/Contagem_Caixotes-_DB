package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.db.RepositoryDb;
import com.hrodriguesdev.entities.Pesagem;

//@Service
public class PesagemService {
	
	
	private RepositoryDb repository = new RepositoryDb();
	
	public Pesagem getPesagemById(Long id) {
		return (Pesagem) repository.findPesagemById(id);		
	}

	public List<Pesagem> getByMotoristaId(Long id) {
		
		return repository.findByMotoristaId(id);
	}

	public Boolean addPesagem(Pesagem pesagem) {
		Pesagem save = repository.save(pesagem);
		//return repository.findById(save.getId()).isPresent();
		return true;
	}

	public List<Pesagem> getByDescarregando(boolean descarregando) {
		return repository.getByDescarregando(descarregando);
	}


	public void savePessagem(Pesagem pesagem) {
		repository.save(pesagem);
		repository.flush();
		
	}
}
