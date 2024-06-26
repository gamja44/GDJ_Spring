package com.winter.app.locations;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.naming.spi.DirStateFactory.Result;
import javax.tools.DocumentationTool.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.winter.app.departments.DepartmentDTO;
import com.winter.app.util.DBConnection;

@Repository
public class LocationDAO {
	
	@Autowired
	private DBConnection dbConnection;
	
	public List<LocationDTO> getList() throws Exception {
		//데이터베이스를 가져오는 클래스 connection
		Connection con = dbConnection.getConnection();
		
		//sql
		String sql="SELECT * FROM LOCATIONS ORDER BY LOCATION_ID ASC";
		
		//미리전송
		PreparedStatement st = con.prepareStatement(sql);
		
		//최종저송 및 결과처리
		ResultSet rs = st.executeQuery();
		
		ArrayList<LocationDTO> ar = new ArrayList<LocationDTO>();
		
		while(rs.next()) {
			LocationDTO locationDTO = new LocationDTO();
			
			//한줄씩 꺼내기
			int id= rs.getInt("LOCATION_ID");
			String city = rs.getString("CITY");
			String c =rs.getString("country_id");
			
			locationDTO.setLocation_id(id);
			locationDTO.setCity(city);
			locationDTO.setCountry_id(c);
			
			ar.add(locationDTO);
			
		}
		rs.close();
		st.close();
		con.close();
		
		return ar;
		
	}
	
	public LocationDTO getDetail(int num) throws Exception {
		//1.데이터베이스가져오기
		Connection con = dbConnection.getConnection();
		
		//2.sql문 작성하기
		//아이디를 모르니까 ?로 보내기
		String sql = "SELECT * FROM LOCATIONS WHERE LOCATION_ID=?";
			
		//3.preparedstatement를 사용해서 쿼리문을 미리전송하기
		PreparedStatement st = con.prepareStatement(sql);
		
		//4.?세팅
		st.setInt(1, num);
		
	   //5.최종전송 및 결과처리
		ResultSet rs = st.executeQuery();
		
		//6.한줄읽고 데이터꺼내기
		//웬만하면 컬럼명을 사용하기
		LocationDTO locationDTO=null;
		
		
		if(rs.next()) {
			locationDTO = new LocationDTO();
			locationDTO.setLocation_id(rs.getInt(1));
			locationDTO.setCity(rs.getString("CITY"));
			locationDTO.setCountry_id(rs.getString(6));
		
		}
		rs.close();
		st.close();
		con.close();
		
		return locationDTO;
		
		
	}
	public int add(LocationDTO locationDTO)throws Exception {
		//1. db연결
		Connection con = dbConnection.getConnection();
		//2. 쿼리작성
		String sql = "INSERT INTO LOCATIONS(LOCATION_ID, CITY, COUNTRY_ID) "
				+ " VALUES(LOCATIONS_SEQ.NEXTVAL, ?, ?) ";
		//3.미리전송
		PreparedStatement st = con.prepareStatement(sql);
		
		//4.?세팅
	
		st.setString(1, locationDTO.getCity());
		st.setString(2, locationDTO.getCountry_id());

		//5. 최종전송 및 결과처리
		int result = st.executeUpdate();
		
		//6.자원해제
		st.close();
		con.close();
		
		return result; 
		
		
		
		
		
	
	
	
	
	
	}
}
