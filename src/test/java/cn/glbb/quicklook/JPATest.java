package cn.glbb.quicklook;

import org.hibernate.Criteria;
import org.junit.Test;

import javax.persistence.*;
import java.util.Date;

public class JPATest {
    @Test
    public void test1(){
        String persistenceUnitName = "JpaQuickLook";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer();

        customer.setLastName("C");
        customer.setAge(20);
        customer.setEmail("986144668@qq.com");
        customer.setBirthday(new Date());
        customer.setCurrentTime(new Date());

        entityManager.persist(customer);

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();


    }

    @Test
    public void test2(){

    }
}
