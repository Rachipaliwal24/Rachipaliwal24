package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CollegeModel {

	private static Logger log = Logger.getLogger(CollegeModel.class);

	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPk Started");
		Connection conn = null;
		int pk = 0;
		try {
			
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception:Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPk End");

		return pk + 1;
	}

	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("model add Started");
		Connection conn = null;
		int pk = 0;
		
		  CollegeBean duplicateCollegeName = findByName(bean.getName());
		  
		  
		  if (duplicateCollegeName !=null) { 
			  
			  throw new DuplicateRecordException("College Name already exists"); }
		 
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifieddatetime());
			pstmt.executeUpdate();
			conn.commit(); // END transaction
			pstmt.close();
		} catch (Exception e) {
		
			log.error("Database Exception..", e);

			try {
				conn.rollback();

			} catch (Exception ex) {
				ex.printStackTrace();
				 throw new ApplicationException( "Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException( "Exception : Exception in add College");
			//e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	public void delete(CollegeBean bean) throws ApplicationException {
		log.debug("Model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {

				throw new ApplicationException("Exception:Exception in delete college");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model delete Started");
		}
	}

	public CollegeBean findByName(String name) throws ApplicationException {
		log.debug("Model findByName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE NAME=?");
		CollegeBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifieddatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			log.error("DatabaseException.., e");
			throw new ApplicationException("Exception: Exception in getting college by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model finalByName End");
		return bean;
	}

	public CollegeBean findByPK(long PK) throws ApplicationException {
		log.debug("Model findByPk Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE ID=?");
		CollegeBean bean = null;
		Connection Conn = null;
		try {
			Conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = Conn.prepareStatement(sql.toString());
			pstmt.setLong(1, PK);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifieddatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception: Exception in getting College by pk");

		} finally {
			JDBCDataSource.closeConnection(Conn);
		}
		log.debug("Model findByPK End");
		return bean;

	}

	public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Started");
		Connection conn = null;

		CollegeBean beanExist = findByName(bean.getName());
		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateRecordException("college name already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET NAME=?, ADDRESS=?, STATE=?, CITY=?,PHONE_NO=?,CREATED_BY=?, MODIFIED_BY=?,CREATED_DATETIME=?, MODIFIED_DATETIME=?  WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifieddatetime());
			pstmt.setLong(10, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();

			} catch (Exception ex) {
				throw new ApplicationException("Exception: Delete rollback Exception" + ex.getMessage());
			}
			//throw new ApplicationException("Exception in updating College");
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public List search(CollegeBean bean, int pageNo, int pageSize)
            throws ApplicationException {
        log.debug("Model search Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM ST_COLLEGE WHERE 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" AND id = " + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" AND NAME like '" + bean.getName() + "%'");
            }
            if (bean.getAddress() != null && bean.getAddress().length() > 0) {
                sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
            }
            if (bean.getState() != null && bean.getState().length() > 0) {
                sql.append(" AND STATE like '" + bean.getState() + "%'");
            }
            if (bean.getCity() != null && bean.getCity().length() > 0) {
                sql.append(" AND CITY like '" + bean.getCity() + "%'");
            }
            if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
                sql.append(" AND PHONE_NO = " + bean.getPhoneNo());
            }

        }

        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" Limit " + pageNo + ", " + pageSize);
            // sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList list = new ArrayList();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneNo(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifieddatetime(rs.getTimestamp(10));
                list.add(bean);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search college");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model search End");
        return list;
    }

 
	 
	  public List search(CollegeBean bean) throws ApplicationException {
	        return search(bean, 0, 0);
	    }
	  
	    public List list() throws ApplicationException {
	        return list(0, 0);
	    }

	   
	    public List list(int pageNo, int pageSize) throws ApplicationException {
	        log.debug("Model list Started");
	        ArrayList list = new ArrayList();
	        StringBuffer sql = new StringBuffer("select * from ST_COLLEGE");
	        // if page size is greater than zero then apply pagination
	        if (pageSize > 0) {
	            // Calculate start record index
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();
	            while (rs.next()) {
	                CollegeBean bean = new CollegeBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifieddatetime(rs.getTimestamp(10));
	                list.add(bean);
	            }
	            rs.close();
	        } catch (Exception e) {
	            log.error("Database Exception..", e);
	            throw new ApplicationException(
	                    "Exception : Exception in getting list of users");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        log.debug("Model list End");
	        return list;

	    }
	}

