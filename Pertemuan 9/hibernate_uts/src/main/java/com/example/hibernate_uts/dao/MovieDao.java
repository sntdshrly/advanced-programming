package com.example.hibernate_uts.dao;

import com.example.hibernate_uts.entity.Movie;
import com.example.hibernate_uts.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class MovieDao implements DaoInterface<Movie> {
    @Override
    public List<Movie> getData() throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        criteriaQuery.from(Movie.class);
        List<Movie> mList = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return mList;
    }

    @Override
    public int addData(Movie data) throws HibernateException {
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
    public int delData(Movie data) throws HibernateException {
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
    public int updateData(Movie data) throws HibernateException {
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

    public List<Movie> filterMovie(String selectedGenre) {
//        String query = "SELECT * FROM Movie WHERE Genre LIKE '%' ? '%'";
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> watchlistRoot = criteriaQuery.from(Movie.class);
        Predicate predicateSelectedGenre = criteriaBuilder.like(watchlistRoot.get("genre"), "%" + selectedGenre + "%");
        criteriaQuery.where(predicateSelectedGenre);
        List<Movie> mList = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return mList;
    }

}

