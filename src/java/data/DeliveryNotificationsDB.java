package data;

import javax.persistence.*;
import business.*;

public class DeliveryNotificationsDB {

    public static void insert(DeliveryNotifications notification) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(notification);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void update(DeliveryNotifications notification) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(notification);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static void delete(DeliveryNotifications notification) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.remove(em.merge(notification));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static DeliveryNotifications selectNotification(Long notificationId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT dn FROM DeliveryNotifications dn WHERE dn.notificationId = :notificationId";
        TypedQuery<DeliveryNotifications> q = em.createQuery(qString, DeliveryNotifications.class);
        q.setParameter("notificationId", notificationId);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static boolean notificationExists(Long notificationId) {
        DeliveryNotifications notification = selectNotification(notificationId);
        return notification != null;
    }
}
