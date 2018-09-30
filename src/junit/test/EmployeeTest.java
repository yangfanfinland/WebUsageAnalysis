package junit.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lumiinsight.bean.privilege.Employee;
import com.lumiinsight.service.privilege.EmployeeService;

public class EmployeeTest {
	@Test
	public void runtest(){
		//检测JPA框架是否集成成功		
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("lumiinsight");
//		EntityManager em = factory.createEntityManager();
//		em.getTransaction().begin();
//		em.persist(new Employee());
//		em.getTransaction().commit();
//		factory.close();
		
		//检测SPRING框架是否集成成功
//		ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
//		DataSource dataSource = (DataSource) cxt.getBean("dataSource");
//		System.out.println(dataSource);
		
		ApplicationContext cxt = new ClassPathXmlApplicationContext("beans.xml");
		EmployeeService employeeService = (EmployeeService) cxt.getBean("employeeServiceBean");
		employeeService.save(new Employee());
	}
}
