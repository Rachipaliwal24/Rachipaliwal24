package in.co.rays.proj4.model.test;

import java.beans.beancontext.BeanContext;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.StudentModel;

public class StudentModelTest {
	public static StudentModel model = new StudentModel();
	
	
	public static void main(String[] args) throws ParseException, ApplicationException {
		
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByPK(); 
		//testFindByEmailId();
		//testSearch();
		testList();
		
		}
    	
	

	


		public static void testAdd() throws ParseException{
			try{
				StudentBean bean= new  StudentBean();
			
               SimpleDateFormat sdf= new SimpleDateFormat("dd/mm/yyyy");
		  // bean.setId(1L);
            bean.setColegeId(1);
           
			bean.setFirstName("Rachi");
			bean.setLastName("paliwal");
			bean.setDob(sdf.parse("24/12/1995"));
			bean.setMobileNo("7698088277");
			bean.setEmail("Rachipaliwal@gmail.com");
		    bean.setCreatedBy("rachi");
			bean.setModifiedBy("rachi");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifieddatetime(new Timestamp(new Date().getTime()));
		
			long pk = model.add(bean);
			StudentBean addedbean = model.findByPk(pk);
		     System.out.println("pass");
			if (addedbean== null) {
				System.out.println("Test add fail");
				}
			}catch (ApplicationException e) {
				e.printStackTrace();
			}catch (DuplicateRecordException e) {
	            e.printStackTrace();
		
	}
     }
			
        public static void testDelete(){
				try {
					StudentBean bean = new StudentBean ();
					long pk= 2L;
					bean.setId(pk);
					model.delete(bean);
					StudentBean deletedBean = model.findByPk(pk);
					System.out.println("Delete");
					if (deletedBean!= null) {
						System.out.println("test delete fail");
						}
					}catch(ApplicationException e) {
						e.printStackTrace();
					}
			}
        
        public static void testUpdate() throws ApplicationException {
         try {
        		
          StudentBean bean = model.findByPk(3L);
          bean.setColegeId(1L);
          bean.setFirstName("kesh");
          bean.setLastName("adav");
          
          model.update(bean);
          //date(bean);ate(bean);
          System.out.println("update succesfull");
			/*
			 * StudentBean updatedbean = model.findByPk(3L);
			 * 
			 * if (!"kesh".equals(updatedbean.getFirstName())) {
			 * System.out.println("TEST UPDSTE FAIL"); }
			 * 
			 * }catch(ApplicationException e) { e.printStackTrace();
			 */
		  
        	
    	  }catch(DuplicateRecordException e) {
			e.printStackTrace();
		  }
        }

   public static void testFindByPK() {
	   try {
		   StudentBean bean= new StudentBean();
		   long pk= 4L;
		   
		   bean= model.findByPk(pk);
		   if(bean== null) {
			   System.out.println("Test find by pk fail");
			   
		   }
		   System.out.println(bean.getColegeId());
		   System.out.println(bean.getFirstName());
		   System.out.println(bean.getLastName());
		   System.out.println(bean.getDob());
           System.out.println(bean.getMobileNo());
           System.out.println(bean.getEmail());
           System.out.println(bean.getColegeId());
           
		   
	   }catch(ApplicationException e) {
		   e.printStackTrace();
	   }
   }
   
   
   public static void testFindByEmailId() {
	   try {
		   StudentBean bean = new StudentBean();
		   bean = model.findByEmailId("achipaliwal@gmail.com");
		   if(bean != null) {
		   System.out.println("Test find my Email fail");
		   
	   }
		
	   System.out.println(bean.getId());
	   System.out.println(bean.getFirstName());
	   System.out.println(bean.getLastName());
	   System.out.println(bean.getDob());
	   System.out.println(bean.getMobileNo());
	   System.out.println(bean.getEmail());
	   System.out.println(bean.getColegeId());
	   
	   }catch (ApplicationException e) {
		   e.printStackTrace();
	   }
	   }
   
	public static void testSearch() {
		try {
			StudentBean bean= new StudentBean();
			List list =new ArrayList();
			//bean.setFirstName("Rachi");
			list= model.search(bean, 0, 0);
			if(list.size()<0) {
				System.out.println("test serch fail");
				
			}
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean= (StudentBean) it.next();            
				System.out.println(bean.getId());
                System.out.println(bean.getFirstName());
                System.out.println(bean.getLastName());
                System.out.println(bean.getDob());
                System.out.println(bean.getMobileNo());
                System.out.println(bean.getEmail());
                System.out.println(bean.getColegeId());
				}
		
			
		
	}catch (ApplicationException e) {
	e.printStackTrace();
	}
    }
	
	
	public static void testList() {
		try {
			StudentBean bean= new StudentBean();
			List list =new ArrayList();
			
			list= model.list(0, 0);
			if(list.size()<0) {
				System.out.println("test list fail");
				
			}
			
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean= (StudentBean) it.next();            
				System.out.println(bean.getId());
                System.out.println(bean.getFirstName());
                System.out.println(bean.getLastName());
                System.out.println(bean.getDob());
                System.out.println(bean.getMobileNo());
                System.out.println(bean.getEmail());
                System.out.println(bean.getColegeId());
                System.out.println(bean.getCreatedBy());
                System.out.println(bean.getModifiedBy());
                System.out.println(bean.getCreatedDatetime());
                System.out.println(bean.getModifieddatetime());
				}
			
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

	
	
	
	
	
        
        
        
				
				
	
}

	
	
	
	
	


