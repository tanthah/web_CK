package data;

import business.Cart;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CartDB {

    // Thêm mới Cart
    public static String insert(Cart cart) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(cart);
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

    // Cập nhật Cart
    public static boolean update(long idCart, Cart cart) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            Cart existingCart = em.find(Cart.class, idCart);
            if (existingCart != null) {
                // Cập nhật các thuộc tính
                existingCart.setEmail(cart.getEmail());
                existingCart.setName(cart.getName());
                existingCart.setPrice(cart.getPrice());
                existingCart.setQuantity(cart.getQuantity());
                existingCart.setTotal(cart.getTotal());
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

    // Xóa Cart
    public static boolean delete(long idCart) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            Cart existingCart = em.find(Cart.class, idCart);
            if (existingCart != null) {
                em.remove(existingCart);
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

    // Lấy thông tin Cart theo ID
    public static Cart selectCart(long idCart) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            return em.find(Cart.class, idCart);
        } finally {
            em.close();
        }
    }

    // Kiểm tra sự tồn tại của Cart theo tên
    public static boolean cartExists(String name) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT c FROM Cart c WHERE c.name = :name";
        TypedQuery<Cart> tq = em.createQuery(query, Cart.class);
        tq.setParameter("name", name);
        try {
            return tq.getResultList().size() > 0;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            em.close();
        }
    }

    // Get cart by name and email
    public static Cart getCartByNameAndEmail(String name, String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            String query = "SELECT c FROM Cart c WHERE c.name = :name AND c.email = :email";
            TypedQuery<Cart> tq = em.createQuery(query, Cart.class);
            tq.setParameter("name", name);
            tq.setParameter("email", email);
            List<Cart> results = tq.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        } finally {
            em.close();
        }
    }
    // Lấy tất cả Cart
    public static List<Cart> getAllCarts( ) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            String query = "SELECT c FROM Cart c";
            TypedQuery<Cart> tq = em.createQuery(query, Cart.class);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    
    // Get all carts for a specific email
    public static List<Cart> getCartsByEmail(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Cart> cart = null;
        try {
            String query = "SELECT c FROM Cart c WHERE c.email = :email";
            TypedQuery<Cart> tq = em.createQuery(query, Cart.class);
            tq.setParameter("email", email);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }
    
// Thêm phương thức này vào CartDB
public static List<Cart> getCartsByIds(String[] cartIds) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    List<Cart> selectedCarts = new ArrayList<>();
    
    try {
        for (String idStr : cartIds) {
            try {
                long cartId = Long.parseLong(idStr);
                Cart cart = em.find(Cart.class, cartId);
                if (cart != null) {
                    selectedCarts.add(cart);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format: " + idStr);
            }
        }
    } finally {
        em.close();
    }
    return selectedCarts;
}
}
