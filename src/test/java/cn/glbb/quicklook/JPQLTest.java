package cn.glbb.quicklook;

import net.sf.ehcache.search.expression.Or;
import org.hibernate.annotations.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JPQLTest {
    EntityManagerFactory factory;
    EntityManager entityManager;
    EntityTransaction transaction;

    @Before
    public void init(){
        String persistName = "JpaQuickLook";
        factory = Persistence.createEntityManagerFactory(persistName);
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
    public void testJPQLHello(){
        String jpql = "from Customer c where c.age > ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(0, 20);
        List<Customer> resultList = query.getResultList();
        for (Customer customer: resultList){
            System.out.println(customer);
        }
    }

    // 查询部分属性
    @Test
    public void partlyPropertiesTest(){
        String jpql = "select c.lastName, c.age from Customer c where c.age > ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(0,25);
        List resultList = query.getResultList();
        for (int i=0; i<resultList.size(); i++){
            Object[] o = (Object[]) resultList.get(i);
            for (int j=0;j<o.length;j++){
                if (j%2==0){
                    String name = (String) o[j];
                    System.out.println(name);
                }else {
                    int age = (int) o[j];
                    System.out.println(age);
                }
            }
        }
    }

    @Test
    public void test(){
        Object o = new Object();
        Object[] oArray = new Object[5];
        Object o1 = oArray;
        Object o2 = new Object[6];
    }

    // 查询部分 返回列表Customer
    @Test
    public void test1(){
        String jpql = "select new Customer(c.lastName, c.age) from Customer c where c.age > ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(0,25);
        List<Customer> list = query.getResultList();
        for (Customer customer : list ){
            System.out.println(customer);
        }
    }

    // 命名查询
    // 这个站位符索引从一开始
    @Test
    public void test2(){
        Query namedQuery = entityManager.createNamedQuery("namedQuery");
        namedQuery.setParameter(1, 15);
        List<Customer> list = namedQuery.getResultList();
        for (Customer customer : list){
            System.out.println(customer);
        }
    }

    @Test
    public void nativeSql(){
        String sql = "select age from jpa_customers where id = 20";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        int singleResult = (int) nativeQuery.getSingleResult();
        System.out.println(singleResult);
    }

    // 查询缓存
    //      不加入查询缓存
    //      会发送两条select
    // 加入查询缓存
    //      1. 在persistence.xml加入配置
    //              <property name="hibernate.cache.use_query_cache" value="true"/>
    //      2.  Query query = entityManager.createQuery(jpql).setHint(QueryHints.CACHEABLE,true);
    // 只会发送一条select
    @Test
    public void testQueryCache(){
        String jpql = "select new Customer(c.lastName, c.age) from Customer c where c.age > ?";
        Query query = entityManager.createQuery(jpql).setHint(QueryHints.CACHEABLE,true);
        query.setParameter(0,25);
        List<Customer> list = query.getResultList();
        for (Customer customer : list ){
            System.out.println(customer);
        }

        query = entityManager.createQuery(jpql).setHint(QueryHints.CACHEABLE,true);
        query.setParameter(0,25);
        list = query.getResultList();
        for (Customer customer : list ){
            System.out.println(customer);
        }
    }

    // order by
    @Test
    public void testOrderBy(){
        String jpql = "from Customer c order by c.id desc";
        Query query = entityManager.createQuery(jpql);
        List<Customer> list = query.getResultList();
        for (Customer customer: list){
            System.out.println(customer);
        }
    }

    // group by
    @Test
    public void testGroupBy(){
        String jpql = "select o.customer from Order o group by o.customer having count(o.customer)>1";
        Query query = entityManager.createQuery(jpql);
        List<Customer> list = query.getResultList();
        for (Customer customer: list){
            System.out.println(customer);
        }
    }



    // 关联查询 加了fetch
    // 关联查寻发送一条select
    // 不关联发送两条 一条查item ,另一条查询 catagories
    @Test
    public void test3(){
        String jpql = "from Item i left outer join fetch i.catagories where i.id = 10";
        Query query = entityManager.createQuery(jpql);
        Item item = (Item) query.getSingleResult();
        System.out.println(item);
        System.out.println(item.getCatagories().size());

    }

    // 关联查询不加 fetch
    // resultList 结构
    //   [ [苹果，吃的], [苹果，玩的] ]
    @Test
    public void test4(){
        String jpql = "from Item i left outer join i.catagories where i.id = 10";
        Query query = entityManager.createQuery(jpql);
        List<Object[]> resultList = query.getResultList();
        System.out.println(resultList);

    }

    // 子查询
    @Test
    public void test5(){
        String jpql = "select o from Order o where o.customer = (select c from Customer c where c.lastName = 'Zhilis')";
        Query query = entityManager.createQuery(jpql);
        List<Order> list = query.getResultList();
        System.out.println(list);
    }

    // 使用jpql 内置函数
    @Test
    public void test6(){
        String jpql = "select upper(c.email) from Customer c";
        Query query = entityManager.createQuery(jpql);
        List<String> list = query.getResultList();
        System.out.println(list);
    }

    @Test
    public void test7(){
        String jpql = "update Customer c set c.lastName = 'David' where c.lastName='D'";
        Query query = entityManager.createQuery(jpql);
        query.executeUpdate();
    }
}
