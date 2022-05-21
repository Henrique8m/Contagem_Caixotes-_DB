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

public class PesagemRepository {

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	public List<Motorista> getMotorista(){	
		return new ArrayList<>();
	}
	
	public List<Pesagem> getPesagemByMotoristaId(Long id) {
				List<Pesagem> list = new ArrayList<>();		
		try {
			conn = DB.getConnection();			
			st = conn.createStatement();			
			rs = st.executeQuery("SELECT * FROM carvaodb.tb_pesagem;");
			
			
			while (rs.next()) 	
				if( rs.getLong("motorista_id") == id)
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
		}catch (Exception e1) {
			e1.printStackTrace();
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

	public Long save(Pesagem pesagem) {
		Long id = null;
		try {
			conn = DB.getConnection();
			pst = conn.prepareStatement("INSERT INTO tb_pesagem "
					+ "(data, descarregando, hora, numero_caixote, peso, responsavel) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);	
			
			pst.setString(1, pesagem.getData());
			pst.setBoolean(2, true);
			pst.setString(3, pesagem.getHora());
			pst.setInt(4, pesagem.getNumeroCaixote());
			pst.setDouble(5, pesagem.getPeso());
			pst.setString(6, pesagem.getResponsavel());				
			
			int rowsAffected = pst.executeUpdate();
			
			if(rowsAffected> 0) {
				ResultSet rs = pst.getGeneratedKeys();
				while(rs.next()) {
					id = rs.getLong(1);	
					System.out.println(rs.getLong(1));
					
				}
				
			}
			else System.out.println("No rown affected");
			
		}
		catch (SQLException e) {
		System.out.println(e.getMessage());	
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(pst);

		}
		return id;
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
}
