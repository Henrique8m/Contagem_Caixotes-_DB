package com.hrodriguesdev.controller;

import java.util.List;

import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;
import com.hrodriguesdev.service.MotoristaService;
import com.hrodriguesdev.service.PesagemService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Controller {
	
	//@Autowired
	private MotoristaService motoristaService = new MotoristaService();
	
	//@Autowired
	private PesagemService pesagemService = new PesagemService();
		
	//@GetMapping(value = "/motorista/{id}")
	public Motorista getMotoristaById(Long id) {
		return motoristaService.getMotoristaById(id);		
	}
	
	//@GetMapping(value = "/motorista/fila")
	public ObservableList<Motorista> getByFila(Boolean fila){
		ObservableList<Motorista> obs = FXCollections.observableArrayList();		
		List<Motorista> list = motoristaService.getByFila(true);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return obs;
	}
	
	public ObservableList<Pesagem> getByDescarregando(boolean descarregando) {
		ObservableList<Pesagem> obs = FXCollections.observableArrayList();
		List<Pesagem> list = pesagemService.getByDescarregando(descarregando);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;
	}
	
	//@GetMapping(value = "/pesagem/{id}")
	public Pesagem getMPesgemById( Long id) {
		return pesagemService.getPesagemById(id);		
	}
	
	//@GetMapping(value = "/pesagem/motorista/{id}")
	public ObservableList<Pesagem> getByMotoristaId( Long id){
		
		ObservableList<Pesagem> obs = FXCollections.observableArrayList();
		List<Pesagem> list = pesagemService.getByMotoristaId(id);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;	
	}

	public Boolean addMotorista(Motorista motorista) {
		return motoristaService.addMotorista(motorista);
		
	}
	
	public Boolean addPesagem(Pesagem pesagem) {
		return pesagemService.addPesagem(pesagem);
		
	}
	
	public ObservableList<Motorista> findAll(Motorista obj) {
		ObservableList<Motorista> obs = FXCollections.observableArrayList();
		List<Motorista> list = motoristaService.findAll(obj);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;	
		
	}

	public ObservableList<Pesagem> findByIdPessagem(Long id) {
		ObservableList<Pesagem> obs = FXCollections.observableArrayList();
		List<Pesagem> list = pesagemService.getByMotoristaId(id);
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;	
		
	}

	public ObservableList<Motorista> findPageable() {
		ObservableList<Motorista> obs = FXCollections.observableArrayList();
		List<Motorista> list = motoristaService.findAll();
		if(list!=null) {
			obs.addAll(list);
			return obs;
		}
		return null;	
	}


	public Boolean editMotoristaDaPesagem(Long idPesagem, Long idMotorista) {
		Motorista motorista = getMotoristaById(idMotorista);
		Pesagem pesagem = pesagemService.getPesagemById(idPesagem);
		motorista.setPesagem(pesagem);
		pesagem.setMotorista(motorista);
		pesagem.setDescarregando(false);
		saveMotorista(motorista);
		savePessagem(pesagem);
		return null;
		
	}

	public void saveMotorista(Motorista motorista) {
		motoristaService.saveMotorista(motorista);
		
	}
	
	public void savePessagem(Pesagem pesagem) {
		pesagemService.savePessagem(pesagem);
		
	}



	

}
