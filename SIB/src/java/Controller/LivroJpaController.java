/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Exemplar;
import Model.Livro;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matheus
 */
public class LivroJpaController implements Serializable {

    public LivroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Livro livro) throws PreexistingEntityException, Exception {
        if (livro.getExemplarCollection() == null) {
            livro.setExemplarCollection(new ArrayList<Exemplar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Exemplar> attachedExemplarCollection = new ArrayList<Exemplar>();
            for (Exemplar exemplarCollectionExemplarToAttach : livro.getExemplarCollection()) {
                exemplarCollectionExemplarToAttach = em.getReference(exemplarCollectionExemplarToAttach.getClass(), exemplarCollectionExemplarToAttach.getIdexemplar());
                attachedExemplarCollection.add(exemplarCollectionExemplarToAttach);
            }
            livro.setExemplarCollection(attachedExemplarCollection);
            em.persist(livro);
            for (Exemplar exemplarCollectionExemplar : livro.getExemplarCollection()) {
                Livro oldLivroOfExemplarCollectionExemplar = exemplarCollectionExemplar.getLivro();
                exemplarCollectionExemplar.setLivro(livro);
                exemplarCollectionExemplar = em.merge(exemplarCollectionExemplar);
                if (oldLivroOfExemplarCollectionExemplar != null) {
                    oldLivroOfExemplarCollectionExemplar.getExemplarCollection().remove(exemplarCollectionExemplar);
                    oldLivroOfExemplarCollectionExemplar = em.merge(oldLivroOfExemplarCollectionExemplar);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLivro(livro.getIdlivro()) != null) {
                throw new PreexistingEntityException("Livro " + livro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Livro livro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Livro persistentLivro = em.find(Livro.class, livro.getIdlivro());
            Collection<Exemplar> exemplarCollectionOld = persistentLivro.getExemplarCollection();
            Collection<Exemplar> exemplarCollectionNew = livro.getExemplarCollection();
            List<String> illegalOrphanMessages = null;
            for (Exemplar exemplarCollectionOldExemplar : exemplarCollectionOld) {
                if (!exemplarCollectionNew.contains(exemplarCollectionOldExemplar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Exemplar " + exemplarCollectionOldExemplar + " since its livro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Exemplar> attachedExemplarCollectionNew = new ArrayList<Exemplar>();
            for (Exemplar exemplarCollectionNewExemplarToAttach : exemplarCollectionNew) {
                exemplarCollectionNewExemplarToAttach = em.getReference(exemplarCollectionNewExemplarToAttach.getClass(), exemplarCollectionNewExemplarToAttach.getIdexemplar());
                attachedExemplarCollectionNew.add(exemplarCollectionNewExemplarToAttach);
            }
            exemplarCollectionNew = attachedExemplarCollectionNew;
            livro.setExemplarCollection(exemplarCollectionNew);
            livro = em.merge(livro);
            for (Exemplar exemplarCollectionNewExemplar : exemplarCollectionNew) {
                if (!exemplarCollectionOld.contains(exemplarCollectionNewExemplar)) {
                    Livro oldLivroOfExemplarCollectionNewExemplar = exemplarCollectionNewExemplar.getLivro();
                    exemplarCollectionNewExemplar.setLivro(livro);
                    exemplarCollectionNewExemplar = em.merge(exemplarCollectionNewExemplar);
                    if (oldLivroOfExemplarCollectionNewExemplar != null && !oldLivroOfExemplarCollectionNewExemplar.equals(livro)) {
                        oldLivroOfExemplarCollectionNewExemplar.getExemplarCollection().remove(exemplarCollectionNewExemplar);
                        oldLivroOfExemplarCollectionNewExemplar = em.merge(oldLivroOfExemplarCollectionNewExemplar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = livro.getIdlivro();
                if (findLivro(id) == null) {
                    throw new NonexistentEntityException("The livro with id " + id + " no longer exists.");
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
            Livro livro;
            try {
                livro = em.getReference(Livro.class, id);
                livro.getIdlivro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The livro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Exemplar> exemplarCollectionOrphanCheck = livro.getExemplarCollection();
            for (Exemplar exemplarCollectionOrphanCheckExemplar : exemplarCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Livro (" + livro + ") cannot be destroyed since the Exemplar " + exemplarCollectionOrphanCheckExemplar + " in its exemplarCollection field has a non-nullable livro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(livro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Livro> findLivroEntities() {
        return findLivroEntities(true, -1, -1);
    }

    public List<Livro> findLivroEntities(int maxResults, int firstResult) {
        return findLivroEntities(false, maxResults, firstResult);
    }

    private List<Livro> findLivroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Livro.class));
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

    public Livro findLivro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Livro.class, id);
        } finally {
            em.close();
        }
    }

    public int getLivroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Livro> rt = cq.from(Livro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
