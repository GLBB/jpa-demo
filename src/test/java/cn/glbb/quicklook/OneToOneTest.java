package cn.glbb.quicklook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OneToOneTest {
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init(){
        factory = Persistence.createEntityManagerFactory("JpaQuickLook");
        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void after(){
        transaction.commit();
        entityManager.close();
        factory.close();
    }

    @Test
    public void testPersist(){
        Manager manager = new Manager();
        manager.setManagerName("Tomas");

        Departement departement = new Departement();
        departement.setDeptName("研发部");

        manager.setDept(departement);
        departement.setManager(manager);

        entityManager.persist(manager);
        entityManager.persist(departement);
    }

    // 查询 维护关系的一方 默认会使用左外链接查询 关联的对象， 可以在 维护关系的一方配置fetch=lazy
    @Test
    public void testFind(){
        System.out.println("------------------------departement find start--------------------------------");
        Departement departement = entityManager.find(Departement.class, 7);
        System.out.println("------------------------departement find end----------------------------------");
        System.out.println(departement.getManager().getManagerName());
    }

}
