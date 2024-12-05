package data;

import javax.persistence.*;
import business.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB {

    public static void insert(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.persist(category);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    // Update method in CategoryDB class
    public static boolean update(long id_category, Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            // Find existing category
            Category existingCategory = em.find(Category.class, id_category);
            if (existingCategory != null) {
                // Update category properties
                existingCategory.setName(category.getName());
                existingCategory.setDescription(category.getDescription());

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

    public static boolean delete(Category category) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            // Kiểm tra tồn tại
            Category existingCategory = em.find(Category.class, category.getId_category());
            if (existingCategory == null) {
                return false;
            }
            em.remove(em.merge(category));
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

    public static Category selectCategory(int id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    public static Category selectCategoryByName(String name) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT c FROM Category c WHERE c.name = :name";
        TypedQuery<Category> q = em.createQuery(qString, Category.class);
        q.setParameter("name", name);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public static boolean categoryExists(String name) {
        return selectCategoryByName(name) != null;
    }
    
    public static List<Category> getAllCategories() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Category> categories = null;
        try {
            // Sửa lại query để khớp với tên Entity
            String query = "SELECT c FROM Category c";  
            TypedQuery<Category> typedQuery = em.createQuery(query, Category.class);
            categories = typedQuery.getResultList();
            return categories;
        } catch (Exception e) {
            System.err.println("Error in getAllCategories: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

}
