package com.hrodriguesdev.service;

import java.util.List;

import com.hrodriguesdev.db.PesagemRepository;
import com.hrodriguesdev.entities.Pesagem;

//@Service
public class PesagemService {
	
	
	private PesagemRepository repository = new PesagemRepository();
	
	public Pesagem getById(Long id) {
		return repository.getById(id);		
	}

	public Long addPesagem(Pesagem pesagem) {
		return repository.save(pesagem);
	}

	public List<Pesagem> getByDescarregando(boolean descarregando) {
		return repository.getByDescarregando(descarregando);
	}


	public Boolean updatePesagem(Long idPesagem, Long idMotorista) {
		return repository.update(idPesagem, idMotorista);
	}

	public List<Pesagem> getPesagemByMotoristaId(Long id) {
		return repository.getPesagemByMotoristaId(id);
	}
}
