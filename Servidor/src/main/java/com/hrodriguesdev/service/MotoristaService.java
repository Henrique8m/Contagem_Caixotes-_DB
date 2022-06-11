package com.hrodriguesdev.service;

import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.db.MotoristaRepository;
import com.hrodriguesdev.entities.Motorista;

//@Service
public class MotoristaService {


	private MotoristaRepository repository = new MotoristaRepository();
	
	
	public Motorista getMotoristaById(Long id) {
		return repository.findById(id);		
	}

	//Foi Concluido
	public List<Motorista> getByFila(Boolean fila) {		
		return repository.getByFila(fila);
	}

	public Long addMotorista(Motorista motorista) {
			
		return repository.save(motorista);	
	}
	
	
	public List<Motorista> findAll(Motorista obj) {
		List<Motorista> list = new ArrayList<>();	
		
		if( obj.getData()!= null && obj.getPlaca()!= null ) {
			List<Motorista> listData =  repository.getByData(obj.getData() );
			for(Motorista objData: listData) {
				if( objData.getPlaca().equalsIgnoreCase( obj.getPlaca() ) ){
					list.add(objData);
				}
			}
			return list;
			
		}else {
			if(obj.getData()!= null) {
				//System.out.println("Entrou 7");
				list.addAll( repository.getByData(obj.getData()) );	
				return list;
			}
			if(obj.getPlaca()!= null) {
				//System.out.println("Entrou 6");
				list.addAll( repository.getByPlaca(obj.getPlaca()) );
				return list;
			}
		}

		return list;
	}

	public void saveMotorista(Motorista motorista) {
		repository.save(motorista);
		
	}

	public Boolean updateMotorista(Long idMotorista) {
		return repository.update(idMotorista);
		
	}

	public List<Motorista> findAllFirst() {
		
		return repository.findAllFirst();
	}


		
		
		
		
		
}
