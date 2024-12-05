package data;

import javax.persistence.*;
import business.*;

public class OrderItemsDB {

    public static void insert(OrderItems orderItem) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(orderItem);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void update(OrderItems orderItem) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(orderItem);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void delete(OrderItems orderItem) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.remove(em.merge(orderItem));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static OrderItems selectOrderItem(Long orderItemId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT oi FROM OrderItems oi WHERE oi.orderItemId = :orderItemId";
        TypedQuery<OrderItems> q = em.createQuery(qString, OrderItems.class);
        q.setParameter("orderItemId", orderItemId);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static boolean orderItemExists(Long orderItemId) {
        OrderItems orderItem = selectOrderItem(orderItemId);
        return orderItem != null;
    }
}
