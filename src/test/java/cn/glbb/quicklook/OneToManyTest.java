package cn.glbb.quicklook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;

public class OneToManyTest {
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
    public void testInsert(){
        Student student1 = new Student();
        student1.setName("Tomas");

        Student student2 = new Student();
        student2.setName("Jack");

        Teacher teacher = new Teacher();
        teacher.getStudents().add(student1);
        teacher.getStudents().add(student2);

        entityManager.persist(teacher);
        entityManager.persist(student1);
        entityManager.persist(student2);
    }

    // 对于关联的多的一方对象 如 student 使用的是懒加载
    @Test
    public void testFind(){
        System.out.println("----------------------------find start---------------------------------");
        Teacher teacher = entityManager.find(Teacher.class, 5);
        System.out.println("----------------------------find end-----------------------------------");
        Student student = teacher.getStudents().iterator().next();
        System.out.println(student);
    }

    @Test
    public void addSomeTeacher(){
        Teacher teacher = new Teacher();
        teacher.setName("Zhangning");

        Student student1 = new Student();
        student1.setName("Puging");

        teacher.getStudents().add(student1);

        entityManager.persist(teacher);
        entityManager.persist(student1);
    }

    @Test
    public void update(){
        Student student = entityManager.find(Student.class, 6);
        Teacher teacher = entityManager.find(Teacher.class,5);
        teacher.getStudents().remove(student);

        Teacher teacher1 = entityManager.find(Teacher.class, 1);
        teacher1.getStudents().add(student);
    }

    // 删除teacher 把学生相关老师外键置空
    @Test
    public void deleteTeacher(){
        Teacher teacher = entityManager.find(Teacher.class, 5);
        entityManager.remove(teacher);
    }

    // 加入了 cascade属性  @OneToMany(cascade = {CascadeType.REMOVE})
    // 删除 老师 同时 也删除了 和老师有关的学生
    @Test
    public void testCascade(){
        Teacher teacher = entityManager.find(Teacher.class,2);
        entityManager.remove(teacher);
    }

    // 多的一方（teacher）放弃维护关系
    // 使用mappedby属性    @OneToMany(cascade = {CascadeType.REMOVE},mappedBy = "teacher") ，
    // 使用了mappedby就不能使用JoinColumn属性
    // 双向一对多
    @Test
    public void testMutipyInsert(){
        Student student1 = new Student();
        student1.setName("Lising");

        Student student2 = new Student();
        student2.setName("WangMz");

        Teacher teacher = new Teacher();
        teacher.setName("Pony");

        student1.setTeacher(teacher);
        student2.setTeacher(teacher);
        // 没有添加teacher的集合中

        entityManager.persist(teacher);
        entityManager.persist(student1);
        entityManager.persist(student2);

    }


}
