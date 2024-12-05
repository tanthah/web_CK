package data;

import javax.persistence.*;
import business.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDB {

    public static void insert(Delivery deliveryService) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(deliveryService);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public static boolean update(long deliveryId, Delivery delivery) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();

            // Find existing category
            Delivery existingdelivery = em.find(Delivery.class, deliveryId);
            if (existingdelivery != null) {
                // Update category properties
                existingdelivery.setName(delivery.getName());
                existingdelivery.setPhone(delivery.getPhone());
                existingdelivery.setAddress(delivery.getAddress());
                existingdelivery.setDescription(delivery.getDescription());

                // Commit transaction
                trans.commit();
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
            return false;

        } finally {
            em.close();
        }
    }

    public static boolean delete(Delivery delivery) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            // Kiểm tra tồn tại
            Delivery existingdelivery = em.find(Delivery.class, delivery.getDeliveryId());
            if (existingdelivery == null) {
                return false;
            }
            em.remove(em.merge(delivery));
            trans.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public static Delivery selectDeliveryService(String name) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT d FROM Delivery d WHERE d.name = :name";
        TypedQuery<Delivery> q = em.createQuery(qString, Delivery.class);
        q.setParameter("name", name);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static boolean deliveryServiceExists(String name) {
        return selectDeliveryService(name) != null;
    }

    public static List<Delivery> getAllDeliveries() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Delivery> deliveries = null;
        try {
            // Sửa lại query để khớp với tên Entity
            String query = "SELECT d FROM Delivery d";  
            TypedQuery<Delivery> typedQuery = em.createQuery(query, Delivery.class);
            deliveries = typedQuery.getResultList();
            return deliveries;
        } catch (Exception e) {
            System.err.println("Error in getAllCategories: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
