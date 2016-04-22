package ua.biglib.salivon.repositoriy;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ua.biglib.salivon.entity.Cart;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ua.biglib.salivon.entity.Customer;
import ua.biglib.salivon.repositoriy.exceptions.IllegalOrphanException;
import ua.biglib.salivon.repositoriy.exceptions.NonexistentEntityException;

@Service(value = "customerJpaController")
@Repository
public class CustomerJpaController implements Serializable {

    @Autowired
    public CustomerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) {
        if (customer.getCartCollection() == null) {
            customer.setCartCollection(new ArrayList<Cart>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cart> attachedCartCollection = new ArrayList<Cart>();
            for (Cart cartCollectionCartToAttach : customer.getCartCollection()) {
                cartCollectionCartToAttach = em.getReference(cartCollectionCartToAttach.getClass(), cartCollectionCartToAttach.getId());
                attachedCartCollection.add(cartCollectionCartToAttach);
            }
            customer.setCartCollection(attachedCartCollection);
            em.persist(customer);
            for (Cart cartCollectionCart : customer.getCartCollection()) {
                Customer oldIdCustomerOfCartCollectionCart = cartCollectionCart.getIdCustomer();
                cartCollectionCart.setIdCustomer(customer);
                cartCollectionCart = em.merge(cartCollectionCart);
                if (oldIdCustomerOfCartCollectionCart != null) {
                    oldIdCustomerOfCartCollectionCart.getCartCollection().remove(cartCollectionCart);
                    oldIdCustomerOfCartCollectionCart = em.merge(oldIdCustomerOfCartCollectionCart);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer persistentCustomer = em.find(Customer.class, customer.getId());
            Collection<Cart> cartCollectionOld = persistentCustomer.getCartCollection();
            Collection<Cart> cartCollectionNew = customer.getCartCollection();
            List<String> illegalOrphanMessages = null;
            for (Cart cartCollectionOldCart : cartCollectionOld) {
                if (!cartCollectionNew.contains(cartCollectionOldCart)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cart " + cartCollectionOldCart + " since its idCustomer field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cart> attachedCartCollectionNew = new ArrayList<Cart>();
            for (Cart cartCollectionNewCartToAttach : cartCollectionNew) {
                cartCollectionNewCartToAttach = em.getReference(cartCollectionNewCartToAttach.getClass(), cartCollectionNewCartToAttach.getId());
                attachedCartCollectionNew.add(cartCollectionNewCartToAttach);
            }
            cartCollectionNew = attachedCartCollectionNew;
            customer.setCartCollection(cartCollectionNew);
            customer = em.merge(customer);
            for (Cart cartCollectionNewCart : cartCollectionNew) {
                if (!cartCollectionOld.contains(cartCollectionNewCart)) {
                    Customer oldIdCustomerOfCartCollectionNewCart = cartCollectionNewCart.getIdCustomer();
                    cartCollectionNewCart.setIdCustomer(customer);
                    cartCollectionNewCart = em.merge(cartCollectionNewCart);
                    if (oldIdCustomerOfCartCollectionNewCart != null && !oldIdCustomerOfCartCollectionNewCart.equals(customer)) {
                        oldIdCustomerOfCartCollectionNewCart.getCartCollection().remove(cartCollectionNewCart);
                        oldIdCustomerOfCartCollectionNewCart = em.merge(oldIdCustomerOfCartCollectionNewCart);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customer.getId();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cart> cartCollectionOrphanCheck = customer.getCartCollection();
            for (Cart cartCollectionOrphanCheckCart : cartCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Cart " + cartCollectionOrphanCheckCart + " in its cartCollection field has a non-nullable idCustomer field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
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

    public Customer findCustomer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
