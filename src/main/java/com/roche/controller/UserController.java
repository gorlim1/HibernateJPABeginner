package com.roche.controller;


import com.roche.model.User;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class UserController {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();


//        addUser(factory, session);
//
//        findUser(factory,session,9);
//
//        updateUser(session,3);
//
//        deleteUser(session,2);

//        findUserHQL(factory, session);

//        getRecordById(factory, session);

//        getRecords(session);

//        getMaxSalary(session);

//        getMaxSalaryGroupBy();

        namedQueryExample(session);

        session.close();
        factory.close();
    }


    public static void addUser(SessionFactory factory, Session session) {
        Transaction transaction = session.beginTransaction();

        User uOne = new User();
        uOne.setEmail("haseeb@gmail.com");
        uOne.setFullName("Moh Haseeb");
        uOne.setPassword("has123");
        uOne.setAge(20);
        uOne.setSalary(2000.69);
        uOne.setCity("NYC");

        User uTwo = new User();
        uTwo.setEmail("james@gmail.com");
        uTwo.setFullName("James Santana");
        uTwo.setPassword("James123");
        uTwo.setAge(25);
        uTwo.setSalary(2060.69);
        uTwo.setCity("Dallas");

        User uThree = new User();
        uThree.setEmail("haseeb@gmail.com");
        uThree.setFullName("Moh Haseeb");
        uThree.setPassword("has123");
        uThree.setAge(20);
        uThree.setSalary(2000.69);
        uThree.setCity("NYC");

        /*========= We can pass value/data by using constructor =========*/
        User uFour = new User("Christ", "christ@gamil.com", "147852",
                35, 35000.3, "NJ");
        User uFive = new User("Sid", "Sid", "s258",
                29, 4000.36, "LA");
        //Integer  userid = null;

        session.persist(uOne);
        session.persist(uTwo);
        session.persist(uThree);
        session.persist(uFour);
        session.persist(uFive);

        transaction.commit();
        System.out.println("successfully saved");

        factory.close();
        session.close();
    }

    public static void findUser(SessionFactory factory, Session session, int USER_ID) {
        Transaction tx = session.beginTransaction();

        User u = session.get(User.class, USER_ID);

        System.out.println("FullName: " + u.getFullName());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Password: " + u.getPassword());
        tx.commit();

        //close resources
        factory.close();
        session.close();
    }

    public static void updateUser(Session session, int USER_ID) {
        Transaction tx = session.beginTransaction();
        User u = new User();

        u.setId(USER_ID);
        u.setEmail("mhaseeb@gmail.com");
        u.setFullName("M Haseeb");
        u.setPassword("123456");

        session.merge(u);
        session.getTransaction().commit();
        session.close();
    }

    public static void deleteUser(Session session, int USER_ID) {
        Transaction tx = session.beginTransaction();
        User u = new User();

        u.setId(USER_ID);
        session.remove(u);
        tx.commit();

        session.close();
    }

    public static void findUserHQL(SessionFactory factory, Session session) {

        String hqlFrom = "FROM User"; // Example of HQL to get all records of user class
        String hqlSelect = "SELECT u FROM User u";

        TypedQuery<User> query = session.createQuery(hqlSelect, User.class);
        List<User> results = query.getResultList();

        System.out.printf("%s%13s%17s%34s%n","|User Id","|Full name","|Email","|Password");
        for (User u : results) {
            System.out.printf(" %-10d %-20s %-30s %s %n", u.getId(), u.getFullName(),
                    u.getEmail(), u.getPassword());

        }
    }

    public static void getRecordById(SessionFactory factory, Session session) {

        String hql = "FROM User u WHERE u.id > 2 ORDER BY u.salary DESC";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        List<User> results = query.getResultList();

        System.out.printf("%s%13s%17s%34s%n","|User Id","|Full name","|Email","|Password");
        for (User u : results) {
            System.out.printf(" %-10d %-20s %-30s %-23s %s %n", u.getId(),
                    u.getFullName(), u.getEmail(), u.getPassword(), u.getSalary());
        }
    }

    public static void getRecords(Session session) {
        TypedQuery<Object[]> query =
                session.createQuery("SELECT U.salary, U.fullName FROM User AS U", Object[].class);
        List<Object[]> results = query.getResultList();

        System.out.printf("%s%13s%n","Salary","City");
        for (Object[] object : results) {
            System.out.printf("%-16s%s%n", object[0], object[1]);
        }
    }

    public static void getMaxSalary(Session session) {
        String hql = "SELECT max(U.salary) FROM User AS U";
        TypedQuery<Object> query = session.createQuery(hql, Object.class);
        Object result = query.getSingleResult();
//        System.out.println(result);
        System.out.printf("%s%s\n", "Maximum Salary: ", result);
    }

    public static void getMaxSalaryGroupBy () {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        String hql = "SELECT SUM(U.salary), U.city FROM User AS U GROUP BY U.city";

        TypedQuery<Object[]> query = session.createQuery(hql);
        List<Object[]> result = query.getResultList();

        for (Object[] object : result) {
            System.out.println("Total salary " + object[0] + " | city: " + object[1]);
        }
    }

    public static void namedQueryExample(Session session) {
        String hql = " FROM User AS u WHERE u.id = :id";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        query.setParameter("id", 5);
        List<User> result = query.getResultList();

        System.out.printf("%s%13s%17s%34s%21s%n", "|User Id", "|Full name", "|Email", "|Password", "|Salary");
        for (User u : result) {
            System.out.printf(" %-10d %-20s %-30s %-23s %s %n", u.getId(), u.getFullName(),
                    u.getEmail(), u.getPassword(), u.getSalary());
        }
    }
}