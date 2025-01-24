package com.roche.controller;


import com.roche.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class UserController {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();


//        addUser(factory, session);
//
        findUser(factory,session,5);
//
//        updateUser(session,3);
//
//        deleteUser(session,2);

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
}