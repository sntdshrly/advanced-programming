package com.example.hibernate_uts.dao;

import com.example.hibernate_uts.entity.User;
import com.example.hibernate_uts.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDao implements DaoInterface<User>{
    @Override
    public List<User> getData() throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.from(User.class);
        List<User> uList = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return uList;
    }

    @Override
    public int addData(User data) throws HibernateException {
        int result = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.save(data);
            result = 1;
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
        }
        session.close();
        return result;
    }

    @Override
    public int delData(User data) throws HibernateException {
        int result = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.delete(data);
            result = 1;
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
        }
        session.close();
        return result;
    }

    @Override
    public int updateData(User data) throws HibernateException {
        int result = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.update(data);
            result = 1;
            transaction.commit();
        }catch (HibernateException e){
            transaction.rollback();
        }
        session.close();
        return result;
    }
}
