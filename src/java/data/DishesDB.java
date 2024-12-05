package data;

import javax.persistence.*;
import business.*;
import java.util.ArrayList;
import java.util.List;

public class DishesDB {

    // Insert a new dish into the database
    public static String insert(Dishes dish) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(dish);
            trans.commit();
            return null; // trả về null nếu thành công
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            return e.getMessage(); // trả về thông báo lỗi
        } finally {
            em.close();
        }
    }

    // Update an existing dish
    public static void update(Dishes dish) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(dish);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
     public static Dishes selectDishById(Long dishId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            String qString = "SELECT d FROM Dishes d WHERE d.dishId = :dishId";
            TypedQuery<Dishes> q = em.createQuery(qString, Dishes.class);
            q.setParameter("dishId", dishId);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    // Delete a dish by merging the detached entity
    public static void delete(Dishes dish) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(dish));
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Select a dish by its ID
    public static Dishes selectDish(String name) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT d FROM Dishes d WHERE d.name = :name";
        TypedQuery<Dishes> q = em.createQuery(qString, Dishes.class);
        q.setParameter("name", name);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    // Check if a dish exists in the database
    public static boolean dishExists(String name) {
        return selectDish(name) != null;
    }

    public static List<Dishes> getAllDishes() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Dishes> dishes = null;
        try {
            // Sử dụng ORDER BY để đảm bảo thứ tự nhất quán
            String query = "SELECT d FROM Dishes d ORDER BY d.dishId";
            TypedQuery<Dishes> typedQuery = em.createQuery(query, Dishes.class);
            dishes = typedQuery.getResultList();
            return dishes;
        } catch (Exception e) {
            System.err.println("Error in getAllDishes: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
}
