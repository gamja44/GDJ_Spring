package com.winter.app.locations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
			//System.out.println(id+":"+city);
			
			locationDTO.setLocation_id(id);
			locationDTO.setCity(city);
			locationDTO.setCountry_id("COUNTRY_ID");
			
			ar.add(locationDTO);
			
		}
		rs.close();
		st.close();
		con.close();
		
		return ar;
		
	}
	
}
