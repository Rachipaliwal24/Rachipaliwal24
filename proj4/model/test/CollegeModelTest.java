package in.co.rays.proj4.model.test;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.modelmbean.ModelMBean;

public class CollegeModelTest {
	public static CollegeModel model = new CollegeModel();
	public static void main(String[] args)throws DuplicateRecordException {
		testAdd();
		//testDelete();
		//testUpdate();
		//testfindByName();
		//testfindByPK();
		//testSearch();
		//testList();
		
		
		}
	
	public static void testAdd() throws DuplicateRecordException {
	try {
		CollegeBean bean= new CollegeBean();
		bean.setName("SDITS");
		bean.setAddress("Sumer nagar");
		bean.setState("MP");
		bean.setCity("Khandwa");
		bean.setPhoneNo("07332243021");
		bean.setCreatedBy("Rachi");
		bean.setModifiedBy("Rachi");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifieddatetime(new Timestamp(new Date().getTime()));
		long pk =model.add(bean);
		System.out.println("Test add Sucess");
		//CollegeBean addedBean = model.findByPK(pk);
		//if(addedBean == null) {
		//	System.out.println("tested add fail");
			
		//}
		
	} catch (ApplicationException e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
	}
	
	public static void testDelete() {
		try {
			CollegeBean bean= new CollegeBean();
			long pk=2L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ");
			CollegeBean deleteBean = model.findByPK(pk);
			if(deleteBean!=null) {
				System.out.println("Test delete fail");
			}
		}catch (ApplicationException e) {
			e.printStackTrace();
			
		}	
		}
	
	 public static void testUpdate() throws DuplicateRecordException {

	        try {
	            CollegeBean bean = model.findByPK(1);
	            bean.setName("RGPV");
	            bean.setAddress("bhopal");
	            model.update(bean);
	            System.out.println("Test Update succ");
	            
	           
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	 }
	    

public static void testfindByName() {
	
	
	
	try {
		CollegeBean bean = model.findByName("sdits");
		if(bean == null) {
			System.out.println("Test find By Name fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifieddatetime());
	    } catch (ApplicationException e) {
	    	e.printStackTrace();
	    	
	     }
	     }

public static void testfindByPK() {
	try {
		CollegeBean bean = new CollegeBean();
		long pk= 1L;
		bean = model.findByPK(pk);
		if(bean== null) {
			System.out.println("Test Find by Pk fail");
			}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifieddatetime());
	}catch (ApplicationException e) {
	e.printStackTrace();
    }
    }

public static void testSearch() {
	try {
		CollegeBean bean = new CollegeBean();
		List list = new ArrayList();
		bean.setName("davv");
		list= model.search(bean, 1, 10);
		if(list.size()<0) {
		System.out.println("Test Search fail");
	    }
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean= (CollegeBean)it.next();
			System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getAddress());
            System.out.println(bean.getState());
            System.out.println(bean.getCity());
            System.out.println(bean.getPhoneNo());
            System.out.println(bean.getCreatedBy());
            System.out.println(bean.getCreatedDatetime());
            System.out.println(bean.getModifiedBy());
            System.out.println(bean.getModifiedBy());
        }
    } catch (ApplicationException e) {
        e.printStackTrace();
    }	
		}

public static void testList() {
	try {
		CollegeBean bean= new CollegeBean();
		List list = new ArrayList();
		list= model.list(1,10);
		if(list.size()<0) {
			System.out.println("Test list fail");
			}
		Iterator it = list.iterator();
	    while (it.hasNext()) {
	    	bean= (CollegeBean)it.next();
	        System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getAddress());
            System.out.println(bean.getState());
            System.out.println(bean.getCity());
            System.out.println(bean.getPhoneNo());
            System.out.println(bean.getCreatedBy());
            System.out.println(bean.getCreatedDatetime());
            System.out.println(bean.getModifiedBy());
            System.out.println(bean.getModifieddatetime());
        }

    } catch (ApplicationException e) {
        e.printStackTrace();
    }
}

}
	    	




	 
	















	
	
	
 
	
	
	
	
	
	

	
	
	
	






