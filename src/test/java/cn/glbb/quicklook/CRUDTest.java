package cn.glbb.quicklook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CRUDTest {

    EntityManagerFactory factory;
    EntityManager entityManager;
    EntityTransaction transaction;

    @Before
    public void init(){
        String persistenceUnitName = "JpaQuickLook";
        factory  = Persistence.createEntityManagerFactory(persistenceUnitName);
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
    public void testFind(){
        Customer customer = entityManager.find(Customer.class,6);
        System.out.println(customer);
    }

    @Test
    public void testReference(){
        Customer customer = entityManager.getReference(Customer.class,8);
        System.out.println(Customer.class);
        System.out.println("-----------------------------");
        System.out.println(customer);
    }

    // persist方法对临时对象设置了id
    @Test
    public void testPersist() throws ParseException {
        Customer customer = new Customer();
        customer.setCurrentTime(new Date());
        String birthday = "1997-09-29";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birthday);
        customer.setBirthday(date);
        customer.setEmail("kkk@cc.com");
        customer.setAge(20);
        customer.setLastName("DOC");

        entityManager.persist(customer);
        System.out.println("customer id : "+customer.getId());
    }

    @Test
    public void testRemove(){
        Customer customer1 = entityManager.find(Customer.class,8);
        entityManager.remove(customer1);
    }

    // 创建一个临时对象（无id）
    // merge() 内置会创建一个对象（简称A），把临时对象的值复制过去
    // 然后插入A对象
    // 输出
    // merge() 过程 中 发送了 select 和 update 语句（原因 id 生成策略为table ）来生成id
    // commit 时 发送了 insert语句 插入对象
    @Test
    public void testMerge1() throws ParseException {
        Customer customer = new Customer();

        customer.setCurrentTime(new Date());
        customer.setEmail("Janny@cc.com");
        customer.setAge(20);
        customer.setLastName("Janny");

        String birthday = "1997-08-29";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(birthday);
        customer.setBirthday(date);

        System.out.println("-------------merge start---------------------");
        Customer newCustomer = entityManager.merge(customer);
        System.out.println("-------------merge end------------------------");

        System.out.println("newCustomer id : "+newCustomer.getId());
        System.out.println("customer id : "+customer.getId());
    }

    // 创建一个游离对象（有id）
    // 缓存中存在该对象（有相同的id）
    // jpa 会将游离对象的值复制到缓存中的对象上
    // 然后执行update操作
    // hibernate输出结果
    //   find() 时 select 查询该对象
    //   merge() 时 没有输出update语句
    // commit 时 输出 update
    @Test
    public void testMerge2(){
        System.out.println("-------------find start----------------------");
        Customer customer = entityManager.find(Customer.class, 14);
        System.out.println("-------------find end------------------------");

        Customer newCustomer = new Customer();
        newCustomer.setId(14);
        newCustomer.setLastName("Kang");
        newCustomer.setAge(customer.getAge());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setBirthday(customer.getBirthday());
        newCustomer.setCurrentTime(customer.getCurrentTime());

        System.out.println("-------------merge start---------------------");
        entityManager.merge(newCustomer);
        System.out.println("-------------merge end-----------------------");
    }

    // 创建一个游离对象（有id）
    // 缓存中不存在该对象
    // 查找数据库 存在 id 相同的对象
    // 将 游离对象的值赋值到缓存中，执行update语句
    // 输出
    //      merge() 时 select 查找该对象
    //      commit 时 update
    @Test
    public void testMerge3(){
        Customer customer = new Customer();
        customer.setId(16);
        customer.setLastName("TangDD");
        customer.setAge(customer.getAge());
        customer.setEmail(customer.getEmail());
        customer.setBirthday(customer.getBirthday());
        customer.setCurrentTime(customer.getCurrentTime());

        System.out.println("-------------merge start---------------------");
        entityManager.merge(customer);
        System.out.println("-------------merge end-----------------------");
    }

    // 创建一个游离对象（有id）
    // 缓存中不存在该对象
    // 查找数据库 不存在 id 相同的对象
    // 将 游离对象的值赋值到缓存中，执行update语句
    // 输出
    //      merge() select该对象是否在数据库中存在，数据库中存在，为游离对象重新设置id
    //     commit 发送 insert语句
    @Test
    public void testMerge4(){
        Customer customer = new Customer();
        customer.setId(100);
        customer.setCurrentTime(new Date());
        customer.setBirthday(new Date());
        customer.setEmail("Unis@gl.com");
        customer.setAge(20);
        customer.setLastName("Unis");

        System.out.println("-------------merge start---------------------");
        entityManager.merge(customer);
        System.out.println("-------------merge end-----------------------");
    }

    // 无 flush
    // commit 才会发送update语句
    @Test
    public void test1(){
        Customer customer = entityManager.find(Customer.class, 4);
        System.out.println(customer);
        customer.setLastName("Zhilis");
        System.out.println("-------------------------------");
    }

    @Test
    public void testInsertManyToOne(){
        Customer customer = entityManager.find(Customer.class,4);

        Order order1 = new Order();
        order1.setOrderName(customer.getLastName());
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setOrderName(customer.getLastName());
        order2.setCustomer(customer);

        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    @Test
    public void testInsertManyToOne2(){
        Customer customer = new Customer();
        customer.setLastName("Tomas");
        customer.setAge(40);
        customer.setEmail("tomas@tomas.com");
        customer.setBirthday(new Date());
        customer.setCurrentTime(new Date());

        Order order1 = new Order();
        order1.setOrderName(customer.getLastName());
        order1.setCustomer(customer);

        Order order2 = new Order();
        order2.setOrderName(customer.getLastName());
        order2.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    // 使用左外连接查询
    @Test
    public void findOrder(){
        System.out.println("-------------------------find start--------------------------------");
        Order order = entityManager.find(Order.class, 2);
        System.out.println("-------------------------find end--------------------------------");
        System.out.println(order.getCustomer());
    }

    // 在order的customer属性上配置了@ManyToOne(fetch = FetchType.LAZY)
    @Test
    public void lazyFind(){
        System.out.println("-------------------------find start--------------------------------");
        Order order = entityManager.find(Order.class, 2);
        System.out.println("-------------------------find end--------------------------------");
        System.out.println(order.getCustomer());
    }

    @Test
    public void update(){
        Order order = entityManager.find(Order.class, 3);
        Customer customer = entityManager.find(Customer.class, 10);
        order.setCustomer(customer);
    }

    @Test
    public void delete(){
        Order order = entityManager.find(Order.class,4);
        entityManager.remove(order);
    }

}
