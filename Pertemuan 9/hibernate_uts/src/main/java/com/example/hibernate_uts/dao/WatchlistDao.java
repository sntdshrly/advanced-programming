package com.example.hibernate_uts.dao;

import com.example.hibernate_uts.entity.User;
import com.example.hibernate_uts.entity.Watchlist;
import com.example.hibernate_uts.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class WatchlistDao implements DaoInterface<Watchlist> {
    @Override
    public List<Watchlist> getData() throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Watchlist> criteriaQuery = criteriaBuilder.createQuery(Watchlist.class);
        criteriaQuery.from(Watchlist.class);
        List<Watchlist> wList = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return wList;
    }

    @Override
    public int addData(Watchlist data) throws HibernateException {
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
    public int delData(Watchlist data) throws HibernateException {
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
    public int updateData(Watchlist data) throws HibernateException {
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
    public List<Watchlist> filterData(User userSelect) {
//        String query =
//        "SELECT * FROM watchlist w
//        JOIN movie m ON w.Movie_idMovie = m.idMovie
//        JOIN user u ON w.User_idUser = u.idUser
//        WHERE w.User_idUser = ?";
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Watchlist> criteriaQuery = criteriaBuilder.createQuery(Watchlist.class);
        Root<Watchlist> watchlistRoot = criteriaQuery.from(Watchlist.class);
        Predicate predicateSelectedUser = criteriaBuilder.equal(watchlistRoot.get("user"), userSelect);
        criteriaQuery.where(predicateSelectedUser);
        List<Watchlist> wList = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return wList;
    }

}
