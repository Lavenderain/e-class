package aurora.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class Test {

    public static void main(String[] args){


        ApplicationContext ctx = new FileSystemXmlApplicationContext(new String[]{"classpath:spring.xml","classpath:spring-hibernate.xml"});
        SessionFactory fac = (SessionFactory) ctx.getBean("sessionFactory");
        Session s = fac.openSession();

        //Clazz c = new Clazz();

        //s.beginTransaction();
        //s.save(c);
        s.getTransaction().commit();

    }}