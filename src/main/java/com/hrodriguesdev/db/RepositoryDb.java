package com.hrodriguesdev.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;

public class RepositoryDb {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	
	public List<Motorista> getMotorista(){	
		
		return new ArrayList<>();
	}
		

		

	public Motorista findById(Long id) {
		conn = DB.getConnection();	
		int idint = Integer.parseInt(String.valueOf(id) );
		Motorista moto = new Motorista();	
		selectFronMotorista();
		try {
			if( rs.absolute(idint) ) 
				moto = augumaCoisa(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					

		DB.closeResultSet(rs);
		DB.closeStatement(st);		
		return moto;
	}






	public List<Motorista> getByFila(Boolean fila) {
		List<Motorista> list = new ArrayList<>();
		
		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");
			
			
			while (rs.next()) 
				list.add(augumaCoisa(rs));
				
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);

			conn = null;
			st = null;
			rs = null;
		}

		return list;
	}

	public Motorista save(Motorista motorista) {
		// TODO Auto-generated method stub
		return null;
	}

	public Motorista getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Motorista> getByPlacaAndNameAndData(String placa, String name, String data) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Motorista> getByPlacaAndName(String placa, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Motorista> getByDataAndName(String data, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Motorista> getByPlacaAndData(String placa, String data) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Motorista> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Motorista> getByPlaca(String placa) {
		List<Motorista> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");			
			
			while (rs.next())  
				if( rs.getString("placa").equalsIgnoreCase(placa))
					list.add(augumaCoisa(rs));	
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);

			conn = null;
			st = null;
			rs = null;
		}

		return list;
	}

	public List<Motorista> getByData(String data) {
		List<Motorista> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");			
			
			while (rs.next())  
				if( rs.getString("data").equalsIgnoreCase(data))
					list.add(augumaCoisa(rs));	
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);

			conn = null;
			st = null;
			rs = null;
		}

		return list;
	}

	public void flush() {
		// TODO Auto-generated method stub
		
	}

	public List<Motorista> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pesagem> findByMotoristaId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pesagem save(Pesagem pesagem) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Pesagem> getByDescarregando(boolean descarregando) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}




	public Pesagem findPesagemById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Motorista augumaCoisa(ResultSet rs) {
		Motorista moto = new Motorista();
		try {
			moto.setId( rs.getLong("Id") );	
			moto.setCidade( rs.getString("cidade") );
			moto.setCnh( rs.getLong("cnh") );  
			moto.setData( rs.getString("data") );
			moto.setEstado( rs.getString("estado") );
			moto.setFila(rs.getBoolean("fila"));
			moto.setHora(rs.getString("hora"));
			moto.setName(rs.getString("name"));	
			moto.setPhone( rs.getString("phone") );
			moto.setPlaca( rs.getString("placa") );	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moto;		
	}
	
	
	private void selectFronMotorista() {
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_motorista;");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
}
