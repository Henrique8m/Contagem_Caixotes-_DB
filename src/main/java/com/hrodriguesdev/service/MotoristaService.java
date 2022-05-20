package com.hrodriguesdev.service;

import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.db.RepositoryDb;
import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;

import javafx.collections.ObservableList;

//@Service
public class MotoristaService {


	private RepositoryDb repository = new RepositoryDb();
	
	
	public Motorista getMotoristaById(Long id) {
		return repository.findById(id);		
	}

	//Foi Concluido
	public List<Motorista> getByFila(Boolean fila) {		
		return repository.getByFila(fila);
	}

	public Boolean addMotorista(Motorista motorista) {
		Motorista save = repository.save(motorista);		
		//return repository.findById(save.getId())).isPresent();
		return true;
	}
	
	public Boolean editMotorista(ObservableList<Pesagem> obs, Long id) {
		Motorista edit = repository.getById(id);
		System.out.println("1");
		List<Pesagem> list = new ArrayList<>();
		list.addAll(obs);
		//edit.setPesagem(list);
		System.out.println("2");
		repository.save(edit);
		return null;
	}
	
	public List<Motorista> findAll(Motorista obj) {
		List<Motorista> list = new ArrayList<>();	
	
		/*
		if(obj.getPlaca()!=null && obj.getName()!= null && obj.getData()!=null) {
			System.out.println("Entrou 1");
			return repository.getByPlacaAndNameAndData(obj.getPlaca(), obj.getName(), obj.getData());			
			
		}else if(obj.getPlaca()!=null && obj.getName()!= null) {
			System.out.println("Entrou 2");
			return repository.getByPlacaAndName(obj.getPlaca(), obj.getName());
			
		}else if(obj.getData()!=null && obj.getName()!= null) {
			System.out.println("Entrou 3");
			return repository.getByDataAndName(obj.getData(), obj.getName());			
			
		}else if(obj.getPlaca()!=null && obj.getData()!=null) {
			System.out.println("Entrou 4");
			return repository.getByPlacaAndData(obj.getPlaca(),  obj.getData());
			
		
		}else if(obj.getName()!= null) {
			System.out.println("Entrou 5");
			return repository.getByName(obj.getName());	
			
		
		
		
		}else if(obj.getPlaca()!= null) {
			System.out.println("Entrou 6");
			return repository.getByPlaca(obj.getPlaca());	
			
		}else if(obj.getData()!= null) {
			System.out.println("Entrou 7");
			return repository.getByData(obj.getData());	
			
		}			
		;*/
		if(obj.getData()!= null) {
			System.out.println("Entrou 7");
			list.addAll( repository.getByData(obj.getData()) );	
		}
		if(obj.getPlaca()!= null) {
			System.out.println("Entrou 6");
			list.addAll( repository.getByPlaca(obj.getPlaca()) );
		}
		return list;
	}

	public void saveMotorista(Motorista motorista) {
		repository.save(motorista);
		repository.flush();
		
		
	}

	public List<Motorista> findAll() {
		return repository.findAll();
	}


		
		
		
		
		
}
