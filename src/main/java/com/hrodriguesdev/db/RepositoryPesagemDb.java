package com.hrodriguesdev.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hrodriguesdev.entities.Motorista;
import com.hrodriguesdev.entities.Pesagem;

public class RepositoryPesagemDb {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public List<Motorista> getMotorista(){	
		
		return new ArrayList<>();
	}
		
	public Pesagem getById(Long id) {
		conn = DB.getConnection();	
		Pesagem pesagem = new Pesagem();	
		selectFronPesagem();
		try {
			while (rs.next()) 
				if( rs.getLong("id") == id) {
					pesagem = augumaCoisa(rs);
					DB.closeResultSet(rs);
					DB.closeStatement(st);	
					return pesagem;
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					

		DB.closeResultSet(rs);
		DB.closeStatement(st);		
		return pesagem;
	}


	public List<Pesagem> getByDescarregando(Boolean descarregando) {
		List<Pesagem> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_pesagem;");
			
			
			while (rs.next()) 	
				if( rs.getBoolean("descarregando") == true)
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

	public Boolean update(Long idPesagem, Long idMotorista) {
		boolean ok = false;		
		try {
			conn = DB.getConnection();
			
			pst = conn.prepareStatement("UPDATE tb_pesagem "
											+"SET descarregando = " + false + ", "
											+" motorista_id = ? "
											+"WHERE "
											+"(id = ?)");
			
			pst.setLong(1, idMotorista );
			pst.setLong(2, idPesagem );
			
			int rowsAccepted = pst.executeUpdate();
			if(rowsAccepted>0) {
				ok = true;
			}
			
		}catch (SQLException e) {
			ok=false;
			System.out.println(e.getMessage());	
		}
		finally {
			DB.closeStatement(pst);

		}
		return ok;
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

	public Pesagem findPesagemById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Pesagem augumaCoisa(ResultSet rs) {
		Pesagem pesagem = new Pesagem();
		try {
			pesagem.setId( rs.getLong("Id") );	
			pesagem.setData( rs.getString("data") );
			pesagem.setDescarregando(rs.getBoolean("descarregando"));
			pesagem.setHora(rs.getString("hora"));
			pesagem.setNumeroCaixote(rs.getInt("numero_caixote"));
			pesagem.setPeso( rs.getDouble(7));
			pesagem.setResponsavel( rs.getString(8));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pesagem;		
	}
	
	
	private void selectFronPesagem() {
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_pesagem;");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public Boolean save(Pesagem pesagem) {
		// TODO Auto-generated method stub
		return null;
	}
	
}










/*
 st = conn.prepareStatement("insert into department (Name) values ('D1'), ('D2')",  Statement.RETURN_GENERATED_KEYS);
+ "VALUES "
+ "( " + motorista.getCidade() + ", " 
	+ motorista.getCnh() + ", "
	+ motorista.getData() + ", "
	+ motorista.getEstado() + ", "
	+ true + ", "
	+ motorista.getHora() + ", "
	+ motorista.getName() + ", "
	+ motorista.getPhone() + ", "
	+ motorista.getPlaca() + ") ",*/





