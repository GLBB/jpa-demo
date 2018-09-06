package cn.glbb.quicklook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

// 开启二级缓存步骤
// 1. 导包
//    <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-ehcache -->
//    <dependency>
//        <groupId>org.hibernate</groupId>
//        <artifactId>hibernate-ehcache</artifactId>
//        <version>5.2.13.Final</version>
//    </dependency>
// 2. 加入配置文件在src目录下
//         ehcache.xml
// 3. 在persistence.xml文件中配置
//          <shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
//          和在properties中            <!-- 二级缓存相关 -->
//            <property name="hibernate.cache.use_second_level_cache" value="true"/>
//            <property name="hibernate.cache.region.factory_class" value="org.hibernate.cache.ehcache.EhCacheRegionFactory"/>
//            <property name="hibernate.cache.use_query_cache" value="true"/>
// 4. 在要缓存的类上加入@Cacheable(true)

public class SecondCache {

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

    // 使用一级缓存
    @Test
    public void test(){
        Student student = entityManager.find(Student.class, 2);
        System.out.println(student);
        Student student1 = entityManager.find(Student.class, 2);
        System.out.println(student);
        System.out.println(student == student1); // 控制台输出 true
    }

    // 没开启二级缓存
    //      两条 相同的 select
    //      控制台输出false
    // 开启了二级缓存
    //      一条select
    //      控制台输出false
    @Test
    public void testSecondCache(){
        Student student = entityManager.find(Student.class, 2);
        entityManager.close();
        entityManager = factory.createEntityManager();
        Student student1 = entityManager.find(Student.class, 2);
        System.out.println(student);
        System.out.println(student1);
        System.out.println(student == student1);

    }

}
