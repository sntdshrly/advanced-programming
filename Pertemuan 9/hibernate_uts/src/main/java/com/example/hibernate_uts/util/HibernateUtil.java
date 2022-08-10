package com.example.hibernate_uts.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY;
    static {
        try{
            Configuration configuration = new Configuration();
            configuration.configure();
            SESSION_FACTORY = configuration.buildSessionFactory();
        }catch (Throwable t){
            t.printStackTrace();
            throw new ExceptionInInitializerError(t);
        }
    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
}
