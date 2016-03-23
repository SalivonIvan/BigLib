/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.biglib.salivon.repositoriy;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ua.biglib.salivon.entity.Customer;
import ua.biglib.salivon.entity.Book;
import ua.biglib.salivon.entity.Cart;
import ua.biglib.salivon.repositoriy.exceptions.NonexistentEntityException;

/**
 *
 * @author Salivon Ivan
 */
public class CartJpaController implements Serializable {

    public CartJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cart cart) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer idCustomer = cart.getIdCustomer();
            if (idCustomer != null) {
                idCustomer = em.getReference(idCustomer.getClass(), idCustomer.getId());
                cart.setIdCustomer(idCustomer);
            }
            Book idBook = cart.getIdBook();
            if (idBook != null) {
                idBook = em.getReference(idBook.getClass(), idBook.getId());
                cart.setIdBook(idBook);
            }
            em.persist(cart);
            if (idCustomer != null) {
                idCustomer.getCartCollection().add(cart);
                idCustomer = em.merge(idCustomer);
            }
            if (idBook != null) {
                idBook.getCartCollection().add(cart);
                idBook = em.merge(idBook);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cart cart) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cart persistentCart = em.find(Cart.class, cart.getId());
            Customer idCustomerOld = persistentCart.getIdCustomer();
            Customer idCustomerNew = cart.getIdCustomer();
            Book idBookOld = persistentCart.getIdBook();
            Book idBookNew = cart.getIdBook();
            if (idCustomerNew != null) {
                idCustomerNew = em.getReference(idCustomerNew.getClass(), idCustomerNew.getId());
                cart.setIdCustomer(idCustomerNew);
            }
            if (idBookNew != null) {
                idBookNew = em.getReference(idBookNew.getClass(), idBookNew.getId());
                cart.setIdBook(idBookNew);
            }
            cart = em.merge(cart);
            if (idCustomerOld != null && !idCustomerOld.equals(idCustomerNew)) {
                idCustomerOld.getCartCollection().remove(cart);
                idCustomerOld = em.merge(idCustomerOld);
            }
            if (idCustomerNew != null && !idCustomerNew.equals(idCustomerOld)) {
                idCustomerNew.getCartCollection().add(cart);
                idCustomerNew = em.merge(idCustomerNew);
            }
            if (idBookOld != null && !idBookOld.equals(idBookNew)) {
                idBookOld.getCartCollection().remove(cart);
                idBookOld = em.merge(idBookOld);
            }
            if (idBookNew != null && !idBookNew.equals(idBookOld)) {
                idBookNew.getCartCollection().add(cart);
                idBookNew = em.merge(idBookNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cart.getId();
                if (findCart(id) == null) {
                    throw new NonexistentEntityException("The cart with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cart cart;
            try {
                cart = em.getReference(Cart.class, id);
                cart.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cart with id " + id + " no longer exists.", enfe);
            }
            Customer idCustomer = cart.getIdCustomer();
            if (idCustomer != null) {
                idCustomer.getCartCollection().remove(cart);
                idCustomer = em.merge(idCustomer);
            }
            Book idBook = cart.getIdBook();
            if (idBook != null) {
                idBook.getCartCollection().remove(cart);
                idBook = em.merge(idBook);
            }
            em.remove(cart);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cart> findCartEntities() {
        return findCartEntities(true, -1, -1);
    }

    public List<Cart> findCartEntities(int maxResults, int firstResult) {
        return findCartEntities(false, maxResults, firstResult);
    }

    private List<Cart> findCartEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cart.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cart findCart(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cart.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cart> rt = cq.from(Cart.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
