/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Funcionario;
import Model.Cliente;
import Model.Multa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matheus
 */
public class MultaJpaController implements Serializable {

    public MultaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Multa multa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario funcionario = multa.getFuncionario();
            if (funcionario != null) {
                funcionario = em.getReference(funcionario.getClass(), funcionario.getIdfuncionario());
                multa.setFuncionario(funcionario);
            }
            Cliente usuario = multa.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                multa.setUsuario(usuario);
            }
            em.persist(multa);
            if (funcionario != null) {
                funcionario.getMultaCollection().add(multa);
                funcionario = em.merge(funcionario);
            }
            if (usuario != null) {
                usuario.getMultaCollection().add(multa);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMulta(multa.getIdmulta()) != null) {
                throw new PreexistingEntityException("Multa " + multa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Multa multa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Multa persistentMulta = em.find(Multa.class, multa.getIdmulta());
            Funcionario funcionarioOld = persistentMulta.getFuncionario();
            Funcionario funcionarioNew = multa.getFuncionario();
            Cliente usuarioOld = persistentMulta.getUsuario();
            Cliente usuarioNew = multa.getUsuario();
            if (funcionarioNew != null) {
                funcionarioNew = em.getReference(funcionarioNew.getClass(), funcionarioNew.getIdfuncionario());
                multa.setFuncionario(funcionarioNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                multa.setUsuario(usuarioNew);
            }
            multa = em.merge(multa);
            if (funcionarioOld != null && !funcionarioOld.equals(funcionarioNew)) {
                funcionarioOld.getMultaCollection().remove(multa);
                funcionarioOld = em.merge(funcionarioOld);
            }
            if (funcionarioNew != null && !funcionarioNew.equals(funcionarioOld)) {
                funcionarioNew.getMultaCollection().add(multa);
                funcionarioNew = em.merge(funcionarioNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getMultaCollection().remove(multa);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getMultaCollection().add(multa);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = multa.getIdmulta();
                if (findMulta(id) == null) {
                    throw new NonexistentEntityException("The multa with id " + id + " no longer exists.");
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
            Multa multa;
            try {
                multa = em.getReference(Multa.class, id);
                multa.getIdmulta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The multa with id " + id + " no longer exists.", enfe);
            }
            Funcionario funcionario = multa.getFuncionario();
            if (funcionario != null) {
                funcionario.getMultaCollection().remove(multa);
                funcionario = em.merge(funcionario);
            }
            Cliente usuario = multa.getUsuario();
            if (usuario != null) {
                usuario.getMultaCollection().remove(multa);
                usuario = em.merge(usuario);
            }
            em.remove(multa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Multa> findMultaEntities() {
        return findMultaEntities(true, -1, -1);
    }

    public List<Multa> findMultaEntities(int maxResults, int firstResult) {
        return findMultaEntities(false, maxResults, firstResult);
    }

    private List<Multa> findMultaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Multa.class));
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

    public Multa findMulta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Multa.class, id);
        } finally {
            em.close();
        }
    }

    public int getMultaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Multa> rt = cq.from(Multa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
