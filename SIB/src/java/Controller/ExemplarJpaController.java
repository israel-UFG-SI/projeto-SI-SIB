/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Model.Exemplar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Livro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matheus
 */
public class ExemplarJpaController implements Serializable {

    public ExemplarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Exemplar exemplar) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Livro livro = exemplar.getLivro();
            if (livro != null) {
                livro = em.getReference(livro.getClass(), livro.getIdlivro());
                exemplar.setLivro(livro);
            }
            em.persist(exemplar);
            if (livro != null) {
                livro.getExemplarCollection().add(exemplar);
                livro = em.merge(livro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExemplar(exemplar.getIdexemplar()) != null) {
                throw new PreexistingEntityException("Exemplar " + exemplar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Exemplar exemplar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Exemplar persistentExemplar = em.find(Exemplar.class, exemplar.getIdexemplar());
            Livro livroOld = persistentExemplar.getLivro();
            Livro livroNew = exemplar.getLivro();
            if (livroNew != null) {
                livroNew = em.getReference(livroNew.getClass(), livroNew.getIdlivro());
                exemplar.setLivro(livroNew);
            }
            exemplar = em.merge(exemplar);
            if (livroOld != null && !livroOld.equals(livroNew)) {
                livroOld.getExemplarCollection().remove(exemplar);
                livroOld = em.merge(livroOld);
            }
            if (livroNew != null && !livroNew.equals(livroOld)) {
                livroNew.getExemplarCollection().add(exemplar);
                livroNew = em.merge(livroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = exemplar.getIdexemplar();
                if (findExemplar(id) == null) {
                    throw new NonexistentEntityException("The exemplar with id " + id + " no longer exists.");
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
            Exemplar exemplar;
            try {
                exemplar = em.getReference(Exemplar.class, id);
                exemplar.getIdexemplar();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exemplar with id " + id + " no longer exists.", enfe);
            }
            Livro livro = exemplar.getLivro();
            if (livro != null) {
                livro.getExemplarCollection().remove(exemplar);
                livro = em.merge(livro);
            }
            em.remove(exemplar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Exemplar> findExemplarEntities() {
        return findExemplarEntities(true, -1, -1);
    }

    public List<Exemplar> findExemplarEntities(int maxResults, int firstResult) {
        return findExemplarEntities(false, maxResults, firstResult);
    }

    private List<Exemplar> findExemplarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Exemplar.class));
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

    public Exemplar findExemplar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Exemplar.class, id);
        } finally {
            em.close();
        }
    }

    public int getExemplarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Exemplar> rt = cq.from(Exemplar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
