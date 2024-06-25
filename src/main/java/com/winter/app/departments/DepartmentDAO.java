package com.winter.app.departments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.winter.app.util.DBConnection;

@Repository
public class DepartmentDAO {
	//DI, 스프링에게 설명서를 써주자IOC
	//객체만드는 어노테이션와 
	@Autowired
	private DBConnection dbConnection;
	
	//파일대신 데이터베이스읽기
	public List<DepartmentDTO> getList() throws Exception {
		Connection con = dbConnection.getConnection();
		
		//SQL문 작성
				String sql="SELECT * FROM DEPARTMENTS ORDER BY DEPARTMENT_ID ASC";
				
				//미리전송
				PreparedStatement st = con.prepareStatement(sql);
				
				//? 값 세팅
				
				//최종 전송 및 결과처리
				ResultSet rs = st.executeQuery();
				
				ArrayList<DepartmentDTO> ar = new ArrayList<DepartmentDTO>();
				
				while(rs.next()) {
					DepartmentDTO departmentDTO = new DepartmentDTO();
					
					//한줄읽고 어떻게 꺼내는냐?
					int id=rs.getInt("DEPARTMENT_ID");
					String name =rs.getString("DEPARTMENT_NAME");
					//System.out.println(id+":"+name);
					
					departmentDTO.setDepartment_id(id);
					departmentDTO.setDepartment_name(name);
					departmentDTO.setManager_id(rs.getLong("MANAGER_ID"));
					departmentDTO.setLocation_id(rs.getInt("LOCATION_ID"));
					
					ar.add(departmentDTO);
				}
				//자원해제
				rs.close();
				st.close();
				con.close();
				
				return ar;
	}
}
