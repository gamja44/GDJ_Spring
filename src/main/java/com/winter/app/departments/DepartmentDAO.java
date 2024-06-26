package com.winter.app.departments;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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

	public DepartmentDTO getDetail(int num)throws Exception{
		//1.db접속
		Connection con = dbConnection.getConnection();
		
		//2.쿼리문작성
		//아이디를 모르면 ?로 보내자
		String sql = "SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID=?";
	
		//3.미리전송 해킹때문에 preparedstatement를 사용한다
		//완성되어있지않으니 쿼리문을 보내자
		PreparedStatement st = con.prepareStatement(sql);
		
		//4. ?세팅
		//? 순서대로 값을 집어넣기
		st.setInt(1, num);
		
		//5. 최종전송 및 결과처리 
		ResultSet rs = st.executeQuery();
	
		//6.한줄읽고 데이터꺼내기
		//웬만하면 컬럼명을 사용하기
		//한줄만읽어오기때문에 배열을 사용하지않는다
		
		DepartmentDTO departmentDTO=null;
		
		//next는 읽어오기
		if(rs.next()) {
			departmentDTO = new DepartmentDTO();
			departmentDTO.setDepartment_id(rs.getInt(1));
			departmentDTO.setLocation_id(rs.getInt(4));
			departmentDTO.setDepartment_name(rs.getString(2));
			departmentDTO.setManager_id(rs.getLong(3));
		}
		//7.닫아주기
		rs.close();
		st.close();
		con.close();
		
		return departmentDTO;
	}
	
	//add
	public int add(DepartmentDTO departmentDTO)throws Exception {
		//1.
		Connection con = dbConnection.getConnection();
		//2. 3개를 받을수 있는건 DTO
		String sql ="INSERT INTO DEPARTMENTS (DEPARTMENT_ID, DEPARTMENT_NAME, MANAGER_ID, LOCATION_ID)"
				+ "VALUES (DEPARTMENTS_SEQ.NEXTVAL, ?, ?, ?)";
		//3.
		PreparedStatement st = con.prepareStatement(sql);
			
		//4.?
		st.setString(1, departmentDTO.getDepartment_name());
		st.setLong(2, departmentDTO.getManager_id());
		st.setInt(3, departmentDTO.getLocation_id());
		
		//5.최종전송
		int result = st.executeUpdate();
		
		//6.자원해제
		st.close();
		con.close();
		
		return result;
	}
	//delete
	public int delete(DepartmentDTO departmentDTO)throws Exception{
		//1.db연결
		Connection con = dbConnection.getConnection();
		
		//2.sql
		String sql ="DELETE DEPARTMENTS WHERE DEPARTMENT_ID=?";
		
		//3.미리전송
		PreparedStatement st = con.prepareStatement(sql);
		
		//4.?세팅
		st.setInt(1, departmentDTO.getDepartment_id());
		
		//5.최종전송
		int result = st.executeUpdate();
		
		//6.자원해제
		st.close();
		con.close();
		
		return result;
	
	}
	//update
	public int update(DepartmentDTO departmentDTO)throws Exception{
		int result=0;
		Connection con = dbConnection.getConnection();
		String sql = "UPDATE DEPARTMENTS SET DEPARTMENT_NAME=?, MANAGER_ID=?, LOCATION_ID=? WHERE DEPARTMENT_ID=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, departmentDTO.getDepartment_name());
		st.setLong(2, departmentDTO.getManager_id());
		st.setInt(3, departmentDTO.getLocation_id());
		st.setInt(4, departmentDTO.getDepartment_id());
		
		result = st.executeUpdate();
		
		st.close();
		con.close();
	
		return result;
	}
	
	
	
	
	
	
	
	
	
}
