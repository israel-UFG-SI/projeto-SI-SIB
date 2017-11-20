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
import Model.Funcionario;
import java.util.ArrayList;
import java.util.Collection;
import Model.Emprestimo;
import Model.Multa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matheus
 */
public class FuncionarioJpaController implements Serializable {

    public FuncionarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Funcionario funcionario) throws PreexistingEntityException, Exception {
        if (funcionario.getFuncionarioCollection() == null) {
            funcionario.setFuncionarioCollection(new ArrayList<Funcionario>());
        }
        if (funcionario.getFuncionarioCollection1() == null) {
            funcionario.setFuncionarioCollection1(new ArrayList<Funcionario>());
        }
        if (funcionario.getEmprestimoCollection() == null) {
            funcionario.setEmprestimoCollection(new ArrayList<Emprestimo>());
        }
        if (funcionario.getMultaCollection() == null) {
            funcionario.setMultaCollection(new ArrayList<Multa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Funcionario> attachedFuncionarioCollection = new ArrayList<Funcionario>();
            for (Funcionario funcionarioCollectionFuncionarioToAttach : funcionario.getFuncionarioCollection()) {
                funcionarioCollectionFuncionarioToAttach = em.getReference(funcionarioCollectionFuncionarioToAttach.getClass(), funcionarioCollectionFuncionarioToAttach.getIdfuncionario());
                attachedFuncionarioCollection.add(funcionarioCollectionFuncionarioToAttach);
            }
            funcionario.setFuncionarioCollection(attachedFuncionarioCollection);
            Collection<Funcionario> attachedFuncionarioCollection1 = new ArrayList<Funcionario>();
            for (Funcionario funcionarioCollection1FuncionarioToAttach : funcionario.getFuncionarioCollection1()) {
                funcionarioCollection1FuncionarioToAttach = em.getReference(funcionarioCollection1FuncionarioToAttach.getClass(), funcionarioCollection1FuncionarioToAttach.getIdfuncionario());
                attachedFuncionarioCollection1.add(funcionarioCollection1FuncionarioToAttach);
            }
            funcionario.setFuncionarioCollection1(attachedFuncionarioCollection1);
            Collection<Emprestimo> attachedEmprestimoCollection = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoCollectionEmprestimoToAttach : funcionario.getEmprestimoCollection()) {
                emprestimoCollectionEmprestimoToAttach = em.getReference(emprestimoCollectionEmprestimoToAttach.getClass(), emprestimoCollectionEmprestimoToAttach.getIdemprestimo());
                attachedEmprestimoCollection.add(emprestimoCollectionEmprestimoToAttach);
            }
            funcionario.setEmprestimoCollection(attachedEmprestimoCollection);
            Collection<Multa> attachedMultaCollection = new ArrayList<Multa>();
            for (Multa multaCollectionMultaToAttach : funcionario.getMultaCollection()) {
                multaCollectionMultaToAttach = em.getReference(multaCollectionMultaToAttach.getClass(), multaCollectionMultaToAttach.getIdmulta());
                attachedMultaCollection.add(multaCollectionMultaToAttach);
            }
            funcionario.setMultaCollection(attachedMultaCollection);
            em.persist(funcionario);
            for (Funcionario funcionarioCollectionFuncionario : funcionario.getFuncionarioCollection()) {
                funcionarioCollectionFuncionario.getFuncionarioCollection().add(funcionario);
                funcionarioCollectionFuncionario = em.merge(funcionarioCollectionFuncionario);
            }
            for (Funcionario funcionarioCollection1Funcionario : funcionario.getFuncionarioCollection1()) {
                funcionarioCollection1Funcionario.getFuncionarioCollection().add(funcionario);
                funcionarioCollection1Funcionario = em.merge(funcionarioCollection1Funcionario);
            }
            for (Emprestimo emprestimoCollectionEmprestimo : funcionario.getEmprestimoCollection()) {
                Funcionario oldFuncionarioOfEmprestimoCollectionEmprestimo = emprestimoCollectionEmprestimo.getFuncionario();
                emprestimoCollectionEmprestimo.setFuncionario(funcionario);
                emprestimoCollectionEmprestimo = em.merge(emprestimoCollectionEmprestimo);
                if (oldFuncionarioOfEmprestimoCollectionEmprestimo != null) {
                    oldFuncionarioOfEmprestimoCollectionEmprestimo.getEmprestimoCollection().remove(emprestimoCollectionEmprestimo);
                    oldFuncionarioOfEmprestimoCollectionEmprestimo = em.merge(oldFuncionarioOfEmprestimoCollectionEmprestimo);
                }
            }
            for (Multa multaCollectionMulta : funcionario.getMultaCollection()) {
                Funcionario oldFuncionarioOfMultaCollectionMulta = multaCollectionMulta.getFuncionario();
                multaCollectionMulta.setFuncionario(funcionario);
                multaCollectionMulta = em.merge(multaCollectionMulta);
                if (oldFuncionarioOfMultaCollectionMulta != null) {
                    oldFuncionarioOfMultaCollectionMulta.getMultaCollection().remove(multaCollectionMulta);
                    oldFuncionarioOfMultaCollectionMulta = em.merge(oldFuncionarioOfMultaCollectionMulta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFuncionario(funcionario.getIdfuncionario()) != null) {
                throw new PreexistingEntityException("Funcionario " + funcionario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Funcionario funcionario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Funcionario persistentFuncionario = em.find(Funcionario.class, funcionario.getIdfuncionario());
            Collection<Funcionario> funcionarioCollectionOld = persistentFuncionario.getFuncionarioCollection();
            Collection<Funcionario> funcionarioCollectionNew = funcionario.getFuncionarioCollection();
            Collection<Funcionario> funcionarioCollection1Old = persistentFuncionario.getFuncionarioCollection1();
            Collection<Funcionario> funcionarioCollection1New = funcionario.getFuncionarioCollection1();
            Collection<Emprestimo> emprestimoCollectionOld = persistentFuncionario.getEmprestimoCollection();
            Collection<Emprestimo> emprestimoCollectionNew = funcionario.getEmprestimoCollection();
            Collection<Multa> multaCollectionOld = persistentFuncionario.getMultaCollection();
            Collection<Multa> multaCollectionNew = funcionario.getMultaCollection();
            List<String> illegalOrphanMessages = null;
            for (Emprestimo emprestimoCollectionOldEmprestimo : emprestimoCollectionOld) {
                if (!emprestimoCollectionNew.contains(emprestimoCollectionOldEmprestimo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Emprestimo " + emprestimoCollectionOldEmprestimo + " since its funcionario field is not nullable.");
                }
            }
            for (Multa multaCollectionOldMulta : multaCollectionOld) {
                if (!multaCollectionNew.contains(multaCollectionOldMulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Multa " + multaCollectionOldMulta + " since its funcionario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Funcionario> attachedFuncionarioCollectionNew = new ArrayList<Funcionario>();
            for (Funcionario funcionarioCollectionNewFuncionarioToAttach : funcionarioCollectionNew) {
                funcionarioCollectionNewFuncionarioToAttach = em.getReference(funcionarioCollectionNewFuncionarioToAttach.getClass(), funcionarioCollectionNewFuncionarioToAttach.getIdfuncionario());
                attachedFuncionarioCollectionNew.add(funcionarioCollectionNewFuncionarioToAttach);
            }
            funcionarioCollectionNew = attachedFuncionarioCollectionNew;
            funcionario.setFuncionarioCollection(funcionarioCollectionNew);
            Collection<Funcionario> attachedFuncionarioCollection1New = new ArrayList<Funcionario>();
            for (Funcionario funcionarioCollection1NewFuncionarioToAttach : funcionarioCollection1New) {
                funcionarioCollection1NewFuncionarioToAttach = em.getReference(funcionarioCollection1NewFuncionarioToAttach.getClass(), funcionarioCollection1NewFuncionarioToAttach.getIdfuncionario());
                attachedFuncionarioCollection1New.add(funcionarioCollection1NewFuncionarioToAttach);
            }
            funcionarioCollection1New = attachedFuncionarioCollection1New;
            funcionario.setFuncionarioCollection1(funcionarioCollection1New);
            Collection<Emprestimo> attachedEmprestimoCollectionNew = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoCollectionNewEmprestimoToAttach : emprestimoCollectionNew) {
                emprestimoCollectionNewEmprestimoToAttach = em.getReference(emprestimoCollectionNewEmprestimoToAttach.getClass(), emprestimoCollectionNewEmprestimoToAttach.getIdemprestimo());
                attachedEmprestimoCollectionNew.add(emprestimoCollectionNewEmprestimoToAttach);
            }
            emprestimoCollectionNew = attachedEmprestimoCollectionNew;
            funcionario.setEmprestimoCollection(emprestimoCollectionNew);
            Collection<Multa> attachedMultaCollectionNew = new ArrayList<Multa>();
            for (Multa multaCollectionNewMultaToAttach : multaCollectionNew) {
                multaCollectionNewMultaToAttach = em.getReference(multaCollectionNewMultaToAttach.getClass(), multaCollectionNewMultaToAttach.getIdmulta());
                attachedMultaCollectionNew.add(multaCollectionNewMultaToAttach);
            }
            multaCollectionNew = attachedMultaCollectionNew;
            funcionario.setMultaCollection(multaCollectionNew);
            funcionario = em.merge(funcionario);
            for (Funcionario funcionarioCollectionOldFuncionario : funcionarioCollectionOld) {
                if (!funcionarioCollectionNew.contains(funcionarioCollectionOldFuncionario)) {
                    funcionarioCollectionOldFuncionario.getFuncionarioCollection().remove(funcionario);
                    funcionarioCollectionOldFuncionario = em.merge(funcionarioCollectionOldFuncionario);
                }
            }
            for (Funcionario funcionarioCollectionNewFuncionario : funcionarioCollectionNew) {
                if (!funcionarioCollectionOld.contains(funcionarioCollectionNewFuncionario)) {
                    funcionarioCollectionNewFuncionario.getFuncionarioCollection().add(funcionario);
                    funcionarioCollectionNewFuncionario = em.merge(funcionarioCollectionNewFuncionario);
                }
            }
            for (Funcionario funcionarioCollection1OldFuncionario : funcionarioCollection1Old) {
                if (!funcionarioCollection1New.contains(funcionarioCollection1OldFuncionario)) {
                    funcionarioCollection1OldFuncionario.getFuncionarioCollection().remove(funcionario);
                    funcionarioCollection1OldFuncionario = em.merge(funcionarioCollection1OldFuncionario);
                }
            }
            for (Funcionario funcionarioCollection1NewFuncionario : funcionarioCollection1New) {
                if (!funcionarioCollection1Old.contains(funcionarioCollection1NewFuncionario)) {
                    funcionarioCollection1NewFuncionario.getFuncionarioCollection().add(funcionario);
                    funcionarioCollection1NewFuncionario = em.merge(funcionarioCollection1NewFuncionario);
                }
            }
            for (Emprestimo emprestimoCollectionNewEmprestimo : emprestimoCollectionNew) {
                if (!emprestimoCollectionOld.contains(emprestimoCollectionNewEmprestimo)) {
                    Funcionario oldFuncionarioOfEmprestimoCollectionNewEmprestimo = emprestimoCollectionNewEmprestimo.getFuncionario();
                    emprestimoCollectionNewEmprestimo.setFuncionario(funcionario);
                    emprestimoCollectionNewEmprestimo = em.merge(emprestimoCollectionNewEmprestimo);
                    if (oldFuncionarioOfEmprestimoCollectionNewEmprestimo != null && !oldFuncionarioOfEmprestimoCollectionNewEmprestimo.equals(funcionario)) {
                        oldFuncionarioOfEmprestimoCollectionNewEmprestimo.getEmprestimoCollection().remove(emprestimoCollectionNewEmprestimo);
                        oldFuncionarioOfEmprestimoCollectionNewEmprestimo = em.merge(oldFuncionarioOfEmprestimoCollectionNewEmprestimo);
                    }
                }
            }
            for (Multa multaCollectionNewMulta : multaCollectionNew) {
                if (!multaCollectionOld.contains(multaCollectionNewMulta)) {
                    Funcionario oldFuncionarioOfMultaCollectionNewMulta = multaCollectionNewMulta.getFuncionario();
                    multaCollectionNewMulta.setFuncionario(funcionario);
                    multaCollectionNewMulta = em.merge(multaCollectionNewMulta);
                    if (oldFuncionarioOfMultaCollectionNewMulta != null && !oldFuncionarioOfMultaCollectionNewMulta.equals(funcionario)) {
                        oldFuncionarioOfMultaCollectionNewMulta.getMultaCollection().remove(multaCollectionNewMulta);
                        oldFuncionarioOfMultaCollectionNewMulta = em.merge(oldFuncionarioOfMultaCollectionNewMulta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = funcionario.getIdfuncionario();
                if (findFuncionario(id) == null) {
                    throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.");
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
            Funcionario funcionario;
            try {
                funcionario = em.getReference(Funcionario.class, id);
                funcionario.getIdfuncionario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The funcionario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Emprestimo> emprestimoCollectionOrphanCheck = funcionario.getEmprestimoCollection();
            for (Emprestimo emprestimoCollectionOrphanCheckEmprestimo : emprestimoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Emprestimo " + emprestimoCollectionOrphanCheckEmprestimo + " in its emprestimoCollection field has a non-nullable funcionario field.");
            }
            Collection<Multa> multaCollectionOrphanCheck = funcionario.getMultaCollection();
            for (Multa multaCollectionOrphanCheckMulta : multaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Funcionario (" + funcionario + ") cannot be destroyed since the Multa " + multaCollectionOrphanCheckMulta + " in its multaCollection field has a non-nullable funcionario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Funcionario> funcionarioCollection = funcionario.getFuncionarioCollection();
            for (Funcionario funcionarioCollectionFuncionario : funcionarioCollection) {
                funcionarioCollectionFuncionario.getFuncionarioCollection().remove(funcionario);
                funcionarioCollectionFuncionario = em.merge(funcionarioCollectionFuncionario);
            }
            Collection<Funcionario> funcionarioCollection1 = funcionario.getFuncionarioCollection1();
            for (Funcionario funcionarioCollection1Funcionario : funcionarioCollection1) {
                funcionarioCollection1Funcionario.getFuncionarioCollection().remove(funcionario);
                funcionarioCollection1Funcionario = em.merge(funcionarioCollection1Funcionario);
            }
            em.remove(funcionario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Funcionario> findFuncionarioEntities() {
        return findFuncionarioEntities(true, -1, -1);
    }

    public List<Funcionario> findFuncionarioEntities(int maxResults, int firstResult) {
        return findFuncionarioEntities(false, maxResults, firstResult);
    }

    private List<Funcionario> findFuncionarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Funcionario.class));
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

    public Funcionario findFuncionario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Funcionario.class, id);
        } finally {
            em.close();
        }
    }

    public int getFuncionarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Funcionario> rt = cq.from(Funcionario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
