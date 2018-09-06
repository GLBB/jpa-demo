package cn.glbb.quicklook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;

public class ManyToManyTest {

    EntityManagerFactory factory;
    EntityManager entityManager;
    EntityTransaction transaction;

    @Before
    public void init(){
        factory = Persistence.createEntityManagerFactory("JpaQuickLook");
        entityManager = factory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy(){
        transaction.commit();
        entityManager.close();
        factory.close();
    }

    @Test
    public void test(){
        Item apple = new Item();
        apple.setName("苹果");

        Item litchi = new Item();
        litchi.setName("荔枝");

        Item pear = new Item();
        pear.setName("梨");

        Catagory catagory1 = new Catagory();
        catagory1.setName("玩的");

        Catagory catagory2 = new Catagory();
        catagory2.setName("吃的");

        apple.getCatagories().add(catagory1);
        apple.getCatagories().add(catagory2);

        litchi.getCatagories().add(catagory1);

        pear.getCatagories().add(catagory2);

        catagory1.getItems().add(apple);
        catagory1.getItems().add(litchi);

        catagory2.getItems().add(apple);
        catagory2.getItems().add(pear);

        entityManager.persist(catagory1);
        entityManager.persist(catagory2);
        entityManager.persist(apple);
        entityManager.persist(litchi);
        entityManager.persist(pear);
    }
}
