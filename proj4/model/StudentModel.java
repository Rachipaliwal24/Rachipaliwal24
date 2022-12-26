package in.co.rays.proj4.model;

 import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import com.mysql.cj.jdbc.JdbcConnection;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class StudentModel {
	
	
	private static Logger log = Logger.getLogger(StudentModel.class);
	
	
	public int nextPK() throws DatabaseException{
		log.debug("Model NextPk Started");
		
		Connection conn = null;
		int pk=0;
		try {
			conn= JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_STUDENT");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
			pk= rs.getInt(1);
		}
		rs.close();
		}catch(Exception e) {
		log.error("Database Exception..",e);
		throw new DatabaseException("Exception: Exception in getting PK");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk+1;
		}
	
	
	public long add(StudentBean bean) throws DuplicateRecordException, ApplicationException {
		log.debug("Moldel add Started");
		Connection conn = null;
		
		//get College Name+
		CollegeModel cModel = new CollegeModel();
		CollegeBean collegeBean = cModel.findByPK(bean.getColegeId());
		bean.setCollegeName(collegeBean.getName());
		
		StudentBean duplicatName = findByEmailId(bean.getEmail());
		int pk=0;
		
		if(duplicatName!= null) {
			throw new DuplicateRecordException("Email already exist");
			}
		
		try {
			 conn = JDBCDataSource.getConnection();
			 pk= nextPK();
			 System.out.println(pk +"in Modeljdbc");
			 conn.setAutoCommit(false);
			 
			 PreparedStatement pstmt =  conn.prepareStatement("INSERT INTO ST_STUDENT VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
		     pstmt.setInt(1, pk);
		     pstmt.setLong(2, bean.getColegeId());
		     pstmt.setString(3, bean.getCollegeName());
		     pstmt.setString(4,bean.getFirstName());
		     pstmt.setString(5, bean.getLastName());		     //casting of date in mysql
		     pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
		     pstmt.setString(7, bean.getMobileNo());
		    pstmt.setString(8,bean.getEmail());
		    pstmt.setString(9,bean.getCreatedBy());
		    pstmt.setString(10, bean.getModifiedBy());
		    pstmt.setTimestamp(11, bean.getCreatedDatetime());
		    pstmt.setTimestamp(12, bean.getModifieddatetime());
		    
		    pstmt.executeUpdate();
		    conn.commit();
		    pstmt.close();
		}catch(Exception e) {
			log.error("Database Excepion..", e);
			try {
				conn.rollback();
			}catch (Exception ex) {
				throw new ApplicationException("Exception: add rollback exception"+ ex.getMessage());
			
			}
			
			throw new ApplicationException("Exception: Exception  in add Student");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model add End");
		return pk;
	}
	
	
	

public void delete(StudentBean  bean) throws ApplicationException{
	log.debug("Model delete Started");
	Connection conn = null;
	try {
		conn= JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_STUDENT WHERE ID=?");
		pstmt.setLong(1, bean.getId());
		pstmt.executeUpdate();
		conn.commit();
		pstmt.close();
	}catch (Exception e) {
	log.error("Database Exception..", e);
	try {
		conn.rollback();
		
	}catch(Exception ex) {
		throw new ApplicationException("Exception: delete rollback Exception" +ex.getMessage());
		}
	throw new ApplicationException("Exception: Exception in delete Student");
}finally{
	JDBCDataSource.closeConnection(conn);
}
log.debug("model delete Started");
}

public StudentBean findByEmailId(String Email) throws ApplicationException {
	log.debug("Model findBy Email Started ");
	StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE EMAIL = ?");
	StudentBean bean = null;
	Connection conn = null;
	try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		pstmt.setString(1, Email);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		bean= new StudentBean();
		bean.setId(rs.getLong(1));
		bean.setColegeId(rs.getLong(2));
		bean.setCollegeName(rs.getString(3));
		bean.setFirstName(rs.getString(4));
		bean.setLastName(rs.getString(5));
		bean.setDob(rs.getDate(6));
		bean.setMobileNo(rs.getString(7));
		bean.setEmail(rs.getString(8));
		bean.setCreatedBy(rs.getString(9));
		bean.setModifiedBy(rs.getString(10));
		bean.setCreatedDatetime(rs.getTimestamp(11));
		bean.setModifieddatetime(rs.getTimestamp(12));
		}
	rs.close();
	}catch (Exception e) {
		log.error("Database Exception...", e);
		throw new ApplicationException("Exception : Exception is getting user by Email");
		
	}finally {
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model findby Email end");
	return bean;
    }

public StudentBean findByPk(long pk) throws ApplicationException{
	log.debug("Model findByPk Started");
	StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE ID=?");
	StudentBean bean= null;
	Connection conn = null;
	try {
		conn =JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		pstmt.setLong(1, pk);
		ResultSet rs =pstmt.executeQuery();
		while(rs.next()) {
			bean= new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setColegeId(rs.getLong(2));
			bean.setCollegeName(rs.getString(3));
			bean.setFirstName(rs.getString(4));
			bean.setLastName(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setEmail(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));
			}
		rs.close();
		} catch (Exception e) {
		  log.error("Databaseb Exception..", e);
		  throw new ApplicationException("Exception: Exception is getting user by pk");
		}finally {
		JDBCDataSource.closeConnection(conn);	
		}
	log.debug("Model findBy pk end");
	return bean;
	
}

public void update(StudentBean bean) throws ApplicationException, DuplicateRecordException{
	log.debug("Model update Started");
	Connection conn = null;
	
	StudentBean beanExist = findByEmailId(bean.getEmail());
	if(beanExist!= null  && beanExist.getId()!= bean.getId()) {
	throw new DuplicateRecordException("Email id is already Exist");
	}
	
	CollegeModel cModel= new CollegeModel ();
	CollegeBean collegeBean = cModel.findByPK(bean.getColegeId());
	bean.setCollegeName(collegeBean.getName());
	
try {
	conn= JDBCDataSource.getConnection();
	conn.setAutoCommit(false);
	PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_STUDENT SET COLLEGE_ID=?, COLLEGE_NAME=?, FIRST_NAME=?, LAST_NAME=?, DATE_OF_BIRTH=?, MOBILE_NO=?, EMAIL=?, CREATED_BY=?, MODIFIED_BY=?, CREATED_DATETIME=?, MODIFIED_DATETIME=? WHERE ID=?");
    pstmt.setLong(1, bean.getColegeId());
    pstmt.setString(2, bean.getCollegeName());
    pstmt.setString(3, bean.getFirstName());
    pstmt.setString(4, bean.getLastName());
    pstmt.setDate(5,new java.sql.Date (bean.getDob().getTime()));
    pstmt.setString(6, bean.getMobileNo());
    pstmt.setString(7, bean.getEmail());
    pstmt.setString(8, bean.getModifiedBy());
    pstmt.setString(9, bean.getCreatedBy());
    pstmt.setTimestamp(10, bean.getCreatedDatetime());
    pstmt.setTimestamp(11, bean.getModifieddatetime());
    pstmt.setLong(12, bean.getId());
    
    pstmt.executeUpdate();
    conn.commit();
    pstmt.close();
    }catch (Exception e) {
    	log.error ("Database Exception..", e);
    	try {
    		conn.rollback();
    		e.printStackTrace();
    	}catch (Exception ex) {
    		e.printStackTrace();
    		//throw new ApplicationException("Exception in updating Student");
    	}JDBCDataSource.closeConnection(conn);

log.debug("model update end");
}
}

public List search(StudentBean bean)throws ApplicationException{
 return search(bean,0,0);
}

public List search(StudentBean bean, int pageNo, int pagesize) throws ApplicationException{
	log.debug("Model search Started");
	StringBuffer sql = new StringBuffer("Select * from St_Student where 1=1");
	
	    if(bean!= null){
		if(bean.getId()>0) {
	    sql.append(" AND id= "+bean.getId());
	    }
		if(bean.getFirstName()!= null && bean.getFirstName().length()>0) {
		sql.append(" AND FIRST_NAME like'" + bean.getFirstName()+"%'");
		}
		if(bean.getLastName()!=null && bean.getFirstName().length()>0) {
			sql.append(" AND LAST_NAME like'"+ bean.getLastName()+"%'");
		}
		if (bean.getDob()!=null && bean.getDob().getDate()>0){
		sql.append(" AND DOB =" +bean.getDob());
		}
		if(bean.getMobileNo()!= null && bean.getMobileNo().length()>0) {
			sql.append(" AND MOBILE_NO like'" + bean.getMobileNo()+"%'");
		}
		if(bean.getEmail()!= null && bean.getEmail().length()>0) {
			sql.append(" AND EMAIL like '"+ bean.getEmail()+"%'");
	    }
		if(bean.getCollegeName()!= null && bean.getCollegeName().length()>0) {
			sql.append(" AND COLLEGE_NAME="+ bean.getCollegeName());
		}
		}
	    // if page size greater than 0 than apply pagination
	    if (pagesize >0) {
	    	
	    //calculate start record index
	    	pageNo = (pageNo-1)*pagesize;
	    	sql.append(" Limit "+ pageNo+","+pagesize);
	    }
	    System.out.println(sql);
	 ArrayList list = new ArrayList();
	 Connection conn = null;
	 try {
		 conn= JDBCDataSource.getConnection();
		 PreparedStatement pstmt= conn.prepareStatement(sql.toString());
		 ResultSet rs = pstmt.executeQuery();
		 while(rs.next()) {
		 bean= new StudentBean();
		 bean.setId(rs.getLong(1));
		 bean.setColegeId(rs.getLong(2));
		 bean.setCollegeName(rs.getString(3));
		 bean.setFirstName(rs.getString(4));
		 bean.setLastName(rs.getString(5));
		 bean.setDob(rs.getDate(6));
		 bean.setMobileNo(rs.getString(7));
		 bean.setEmail(rs.getString(8));
		 bean.setCreatedBy(rs.getString(9));
		 bean.setModifiedBy(rs.getString(10));
		 bean.setCreatedDatetime(rs.getTimestamp(11));
		 bean.setModifieddatetime(rs.getTimestamp(12));
		 list.add(bean);
		 }
		 rs.close();
	 }catch (Exception e){
		 log.error("Database Exception.." , e);
		 e.printStackTrace();
		// throw new ApplicationException("Exception: Exception in search Student");
	 }finally {
		 JDBCDataSource.closeConnection(conn);
     }
	 log.debug("Model search End");
	 return list;
	 }

public List list() throws ApplicationException{
	return list(0,0);
}

public List list(int pageNo, int PageSize) throws ApplicationException{
	log.debug("Model list Started");
	ArrayList list = new ArrayList();
	StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
	// if page size is greater than zero than apply pagination 
	if(PageSize>0) {
		//Calculate start record index;
		
		pageNo= (pageNo-1)*PageSize;
		sql.append(" limit " +pageNo+","+PageSize);
		}
	Connection conn= null;
	try {
		conn= JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery() ;
		while(rs.next()) {
			StudentBean bean= new StudentBean();
			bean.setId(rs.getLong(1));
			bean.setColegeId(rs.getLong(2));
			bean.setCollegeName(rs.getString(3));
			bean.setFirstName(rs.getString(4));
			bean.setLastName(rs.getString(5));
			bean.setDob(rs.getDate(6));
			bean.setMobileNo(rs.getString(7));
			bean.setEmail(rs.getString(8));
			bean.setCreatedBy(rs.getString(9));
			bean.setModifiedBy(rs.getString(10));
			bean.setCreatedDatetime(rs.getTimestamp(11));
			bean.setModifieddatetime(rs.getTimestamp(12));
			list.add(bean);
			} 
		rs.close();
	}catch(Exception e) {
		log.error("Database Exception..", e);
		//throw new ApplicationException("Exception: Exception in getting list of Student");
		e.printStackTrace();
	}finally {
		JDBCDataSource.closeConnection(conn);
		}
	log.debug("model list end");
	return list;
	}
}

	    












	



	
	
		
		
		
		
		
		
		


	
	
	
	

	


