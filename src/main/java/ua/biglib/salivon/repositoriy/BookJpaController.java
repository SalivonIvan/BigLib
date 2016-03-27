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
import ua.biglib.salivon.entity.Book;
import ua.biglib.salivon.repositoriy.exceptions.IllegalOrphanException;
import ua.biglib.salivon.repositoriy.exceptions.NonexistentEntityException;

public class BookJpaController implements Serializable {

    public BookJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Book book) {
        if (book.getCartCollection() == null) {
            book.setCartCollection(new ArrayList<Cart>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cart> attachedCartCollection = new ArrayList<Cart>();
            for (Cart cartCollectionCartToAttach : book.getCartCollection()) {
                cartCollectionCartToAttach = em.getReference(cartCollectionCartToAttach.getClass(), cartCollectionCartToAttach.getId());
                attachedCartCollection.add(cartCollectionCartToAttach);
            }
            book.setCartCollection(attachedCartCollection);
            em.persist(book);
            for (Cart cartCollectionCart : book.getCartCollection()) {
                Book oldIdBookOfCartCollectionCart = cartCollectionCart.getIdBook();
                cartCollectionCart.setIdBook(book);
                cartCollectionCart = em.merge(cartCollectionCart);
                if (oldIdBookOfCartCollectionCart != null) {
                    oldIdBookOfCartCollectionCart.getCartCollection().remove(cartCollectionCart);
                    oldIdBookOfCartCollectionCart = em.merge(oldIdBookOfCartCollectionCart);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Book book) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Book persistentBook = em.find(Book.class, book.getId());
            Collection<Cart> cartCollectionOld = persistentBook.getCartCollection();
            Collection<Cart> cartCollectionNew = book.getCartCollection();
            List<String> illegalOrphanMessages = null;
            for (Cart cartCollectionOldCart : cartCollectionOld) {
                if (!cartCollectionNew.contains(cartCollectionOldCart)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cart " + cartCollectionOldCart + " since its idBook field is not nullable.");
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
            book.setCartCollection(cartCollectionNew);
            book = em.merge(book);
            for (Cart cartCollectionNewCart : cartCollectionNew) {
                if (!cartCollectionOld.contains(cartCollectionNewCart)) {
                    Book oldIdBookOfCartCollectionNewCart = cartCollectionNewCart.getIdBook();
                    cartCollectionNewCart.setIdBook(book);
                    cartCollectionNewCart = em.merge(cartCollectionNewCart);
                    if (oldIdBookOfCartCollectionNewCart != null && !oldIdBookOfCartCollectionNewCart.equals(book)) {
                        oldIdBookOfCartCollectionNewCart.getCartCollection().remove(cartCollectionNewCart);
                        oldIdBookOfCartCollectionNewCart = em.merge(oldIdBookOfCartCollectionNewCart);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = book.getId();
                if (findBook(id) == null) {
                    throw new NonexistentEntityException("The book with id " + id + " no longer exists.");
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
            Book book;
            try {
                book = em.getReference(Book.class, id);
                book.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The book with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cart> cartCollectionOrphanCheck = book.getCartCollection();
            for (Cart cartCollectionOrphanCheckCart : cartCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Book (" + book + ") cannot be destroyed since the Cart " + cartCollectionOrphanCheckCart + " in its cartCollection field has a non-nullable idBook field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(book);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Book> findBookEntities() {
        return findBookEntities(true, -1, -1);
    }

    public List<Book> findBookEntities(int maxResults, int firstResult) {
        return findBookEntities(false, maxResults, firstResult);
    }

    private List<Book> findBookEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Book.class));
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

    public Book findBook(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }

    public int getBookCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Book> rt = cq.from(Book.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
