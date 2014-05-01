package org.aepscolombia.platform.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtilMobile {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernateservices.cfg.xml").buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
