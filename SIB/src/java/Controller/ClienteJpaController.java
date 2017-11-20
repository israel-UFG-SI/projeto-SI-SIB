/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import Model.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Emprestimo;
import java.util.ArrayList;
import java.util.Collection;
import Model.Multa;
import Model.Reserva;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Matheus
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getEmprestimoCollection() == null) {
            cliente.setEmprestimoCollection(new ArrayList<Emprestimo>());
        }
        if (cliente.getMultaCollection() == null) {
            cliente.setMultaCollection(new ArrayList<Multa>());
        }
        if (cliente.getReservaCollection() == null) {
            cliente.setReservaCollection(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Emprestimo> attachedEmprestimoCollection = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoCollectionEmprestimoToAttach : cliente.getEmprestimoCollection()) {
                emprestimoCollectionEmprestimoToAttach = em.getReference(emprestimoCollectionEmprestimoToAttach.getClass(), emprestimoCollectionEmprestimoToAttach.getIdemprestimo());
                attachedEmprestimoCollection.add(emprestimoCollectionEmprestimoToAttach);
            }
            cliente.setEmprestimoCollection(attachedEmprestimoCollection);
            Collection<Multa> attachedMultaCollection = new ArrayList<Multa>();
            for (Multa multaCollectionMultaToAttach : cliente.getMultaCollection()) {
                multaCollectionMultaToAttach = em.getReference(multaCollectionMultaToAttach.getClass(), multaCollectionMultaToAttach.getIdmulta());
                attachedMultaCollection.add(multaCollectionMultaToAttach);
            }
            cliente.setMultaCollection(attachedMultaCollection);
            Collection<Reserva> attachedReservaCollection = new ArrayList<Reserva>();
            for (Reserva reservaCollectionReservaToAttach : cliente.getReservaCollection()) {
                reservaCollectionReservaToAttach = em.getReference(reservaCollectionReservaToAttach.getClass(), reservaCollectionReservaToAttach.getIdReserva());
                attachedReservaCollection.add(reservaCollectionReservaToAttach);
            }
            cliente.setReservaCollection(attachedReservaCollection);
            em.persist(cliente);
            for (Emprestimo emprestimoCollectionEmprestimo : cliente.getEmprestimoCollection()) {
                Cliente oldUsuarioOfEmprestimoCollectionEmprestimo = emprestimoCollectionEmprestimo.getUsuario();
                emprestimoCollectionEmprestimo.setUsuario(cliente);
                emprestimoCollectionEmprestimo = em.merge(emprestimoCollectionEmprestimo);
                if (oldUsuarioOfEmprestimoCollectionEmprestimo != null) {
                    oldUsuarioOfEmprestimoCollectionEmprestimo.getEmprestimoCollection().remove(emprestimoCollectionEmprestimo);
                    oldUsuarioOfEmprestimoCollectionEmprestimo = em.merge(oldUsuarioOfEmprestimoCollectionEmprestimo);
                }
            }
            for (Multa multaCollectionMulta : cliente.getMultaCollection()) {
                Cliente oldUsuarioOfMultaCollectionMulta = multaCollectionMulta.getUsuario();
                multaCollectionMulta.setUsuario(cliente);
                multaCollectionMulta = em.merge(multaCollectionMulta);
                if (oldUsuarioOfMultaCollectionMulta != null) {
                    oldUsuarioOfMultaCollectionMulta.getMultaCollection().remove(multaCollectionMulta);
                    oldUsuarioOfMultaCollectionMulta = em.merge(oldUsuarioOfMultaCollectionMulta);
                }
            }
            for (Reserva reservaCollectionReserva : cliente.getReservaCollection()) {
                Cliente oldUsuarioOfReservaCollectionReserva = reservaCollectionReserva.getUsuario();
                reservaCollectionReserva.setUsuario(cliente);
                reservaCollectionReserva = em.merge(reservaCollectionReserva);
                if (oldUsuarioOfReservaCollectionReserva != null) {
                    oldUsuarioOfReservaCollectionReserva.getReservaCollection().remove(reservaCollectionReserva);
                    oldUsuarioOfReservaCollectionReserva = em.merge(oldUsuarioOfReservaCollectionReserva);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getIdusuario()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdusuario());
            Collection<Emprestimo> emprestimoCollectionOld = persistentCliente.getEmprestimoCollection();
            Collection<Emprestimo> emprestimoCollectionNew = cliente.getEmprestimoCollection();
            Collection<Multa> multaCollectionOld = persistentCliente.getMultaCollection();
            Collection<Multa> multaCollectionNew = cliente.getMultaCollection();
            Collection<Reserva> reservaCollectionOld = persistentCliente.getReservaCollection();
            Collection<Reserva> reservaCollectionNew = cliente.getReservaCollection();
            List<String> illegalOrphanMessages = null;
            for (Emprestimo emprestimoCollectionOldEmprestimo : emprestimoCollectionOld) {
                if (!emprestimoCollectionNew.contains(emprestimoCollectionOldEmprestimo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Emprestimo " + emprestimoCollectionOldEmprestimo + " since its usuario field is not nullable.");
                }
            }
            for (Multa multaCollectionOldMulta : multaCollectionOld) {
                if (!multaCollectionNew.contains(multaCollectionOldMulta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Multa " + multaCollectionOldMulta + " since its usuario field is not nullable.");
                }
            }
            for (Reserva reservaCollectionOldReserva : reservaCollectionOld) {
                if (!reservaCollectionNew.contains(reservaCollectionOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaCollectionOldReserva + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Emprestimo> attachedEmprestimoCollectionNew = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoCollectionNewEmprestimoToAttach : emprestimoCollectionNew) {
                emprestimoCollectionNewEmprestimoToAttach = em.getReference(emprestimoCollectionNewEmprestimoToAttach.getClass(), emprestimoCollectionNewEmprestimoToAttach.getIdemprestimo());
                attachedEmprestimoCollectionNew.add(emprestimoCollectionNewEmprestimoToAttach);
            }
            emprestimoCollectionNew = attachedEmprestimoCollectionNew;
            cliente.setEmprestimoCollection(emprestimoCollectionNew);
            Collection<Multa> attachedMultaCollectionNew = new ArrayList<Multa>();
            for (Multa multaCollectionNewMultaToAttach : multaCollectionNew) {
                multaCollectionNewMultaToAttach = em.getReference(multaCollectionNewMultaToAttach.getClass(), multaCollectionNewMultaToAttach.getIdmulta());
                attachedMultaCollectionNew.add(multaCollectionNewMultaToAttach);
            }
            multaCollectionNew = attachedMultaCollectionNew;
            cliente.setMultaCollection(multaCollectionNew);
            Collection<Reserva> attachedReservaCollectionNew = new ArrayList<Reserva>();
            for (Reserva reservaCollectionNewReservaToAttach : reservaCollectionNew) {
                reservaCollectionNewReservaToAttach = em.getReference(reservaCollectionNewReservaToAttach.getClass(), reservaCollectionNewReservaToAttach.getIdReserva());
                attachedReservaCollectionNew.add(reservaCollectionNewReservaToAttach);
            }
            reservaCollectionNew = attachedReservaCollectionNew;
            cliente.setReservaCollection(reservaCollectionNew);
            cliente = em.merge(cliente);
            for (Emprestimo emprestimoCollectionNewEmprestimo : emprestimoCollectionNew) {
                if (!emprestimoCollectionOld.contains(emprestimoCollectionNewEmprestimo)) {
                    Cliente oldUsuarioOfEmprestimoCollectionNewEmprestimo = emprestimoCollectionNewEmprestimo.getUsuario();
                    emprestimoCollectionNewEmprestimo.setUsuario(cliente);
                    emprestimoCollectionNewEmprestimo = em.merge(emprestimoCollectionNewEmprestimo);
                    if (oldUsuarioOfEmprestimoCollectionNewEmprestimo != null && !oldUsuarioOfEmprestimoCollectionNewEmprestimo.equals(cliente)) {
                        oldUsuarioOfEmprestimoCollectionNewEmprestimo.getEmprestimoCollection().remove(emprestimoCollectionNewEmprestimo);
                        oldUsuarioOfEmprestimoCollectionNewEmprestimo = em.merge(oldUsuarioOfEmprestimoCollectionNewEmprestimo);
                    }
                }
            }
            for (Multa multaCollectionNewMulta : multaCollectionNew) {
                if (!multaCollectionOld.contains(multaCollectionNewMulta)) {
                    Cliente oldUsuarioOfMultaCollectionNewMulta = multaCollectionNewMulta.getUsuario();
                    multaCollectionNewMulta.setUsuario(cliente);
                    multaCollectionNewMulta = em.merge(multaCollectionNewMulta);
                    if (oldUsuarioOfMultaCollectionNewMulta != null && !oldUsuarioOfMultaCollectionNewMulta.equals(cliente)) {
                        oldUsuarioOfMultaCollectionNewMulta.getMultaCollection().remove(multaCollectionNewMulta);
                        oldUsuarioOfMultaCollectionNewMulta = em.merge(oldUsuarioOfMultaCollectionNewMulta);
                    }
                }
            }
            for (Reserva reservaCollectionNewReserva : reservaCollectionNew) {
                if (!reservaCollectionOld.contains(reservaCollectionNewReserva)) {
                    Cliente oldUsuarioOfReservaCollectionNewReserva = reservaCollectionNewReserva.getUsuario();
                    reservaCollectionNewReserva.setUsuario(cliente);
                    reservaCollectionNewReserva = em.merge(reservaCollectionNewReserva);
                    if (oldUsuarioOfReservaCollectionNewReserva != null && !oldUsuarioOfReservaCollectionNewReserva.equals(cliente)) {
                        oldUsuarioOfReservaCollectionNewReserva.getReservaCollection().remove(reservaCollectionNewReserva);
                        oldUsuarioOfReservaCollectionNewReserva = em.merge(oldUsuarioOfReservaCollectionNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdusuario();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Emprestimo> emprestimoCollectionOrphanCheck = cliente.getEmprestimoCollection();
            for (Emprestimo emprestimoCollectionOrphanCheckEmprestimo : emprestimoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Emprestimo " + emprestimoCollectionOrphanCheckEmprestimo + " in its emprestimoCollection field has a non-nullable usuario field.");
            }
            Collection<Multa> multaCollectionOrphanCheck = cliente.getMultaCollection();
            for (Multa multaCollectionOrphanCheckMulta : multaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Multa " + multaCollectionOrphanCheckMulta + " in its multaCollection field has a non-nullable usuario field.");
            }
            Collection<Reserva> reservaCollectionOrphanCheck = cliente.getReservaCollection();
            for (Reserva reservaCollectionOrphanCheckReserva : reservaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Reserva " + reservaCollectionOrphanCheckReserva + " in its reservaCollection field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
