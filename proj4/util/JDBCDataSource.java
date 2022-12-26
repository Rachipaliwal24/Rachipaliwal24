package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;



import com.mchange.v2.c3p0.ComboPooledDataSource;

import in.co.rays.proj4.exception.ApplicationException;

public class JDBCDataSource {
	
	private static JDBCDataSource datasource;
	private JDBCDataSource() {
		}
	
	private ComboPooledDataSource cpds= null;
	
	public static JDBCDataSource getinstance() {
		if(datasource==null) {
			ResourceBundle rb= ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");
		
			
			datasource =new JDBCDataSource();
			datasource.cpds=new ComboPooledDataSource();
			
			try {
				datasource.cpds.setDriverClass(rb.getString("driver"));
				 datasource.cpds.setJdbcUrl(rb.getString("url"));
				    datasource.cpds.setUser(rb.getString("username"));
				    datasource.cpds.setPassword(rb.getString("password"));
				   
				    datasource.cpds.setInitialPoolSize(new Integer((String)rb.getString("initialPoolSize")));
				    datasource.cpds.setAcquireIncrement(new Integer ((String)rb.getString("acquireIncrement")));
					datasource.cpds.setMaxPoolSize (new Integer ((String)rb.getString("maxPoolSize")));		
					datasource.cpds.setMaxIdleTime(DataUtility.getInt(rb.getString("timeout")));	
					datasource.cpds.setMinPoolSize(new Integer ((String)rb.getString("minPoolSize")));	
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
   
		
		
		}
		return datasource;
		}
	
	public static Connection getConnection() throws Exception {
		return getinstance().cpds.getConnection();	
	}
	
	public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }

    public static void trnRollback(Connection connection)
            throws ApplicationException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new ApplicationException(ex.toString());
            }
        }
    }

}

	

	
	
	

	
	
	

