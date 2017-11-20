/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "emprestimo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emprestimo.findAll", query = "SELECT e FROM Emprestimo e")
    , @NamedQuery(name = "Emprestimo.findByIdemprestimo", query = "SELECT e FROM Emprestimo e WHERE e.idemprestimo = :idemprestimo")
    , @NamedQuery(name = "Emprestimo.findByDataEmprestimo", query = "SELECT e FROM Emprestimo e WHERE e.dataEmprestimo = :dataEmprestimo")
    , @NamedQuery(name = "Emprestimo.findByDataDevProg", query = "SELECT e FROM Emprestimo e WHERE e.dataDevProg = :dataDevProg")
    , @NamedQuery(name = "Emprestimo.findByDataDevEfetiva", query = "SELECT e FROM Emprestimo e WHERE e.dataDevEfetiva = :dataDevEfetiva")})
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idemprestimo")
    private Integer idemprestimo;
    @Basic(optional = false)
    @Column(name = "dataEmprestimo")
    @Temporal(TemporalType.DATE)
    private Date dataEmprestimo;
    @Basic(optional = false)
    @Column(name = "dataDevProg")
    @Temporal(TemporalType.DATE)
    private Date dataDevProg;
    @Column(name = "dataDevEfetiva")
    @Temporal(TemporalType.DATE)
    private Date dataDevEfetiva;
    @JoinColumn(name = "funcionario", referencedColumnName = "idfuncionario")
    @ManyToOne(optional = false)
    private Funcionario funcionario;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Cliente usuario;

    public Emprestimo() {
    }

    public Emprestimo(Integer idemprestimo) {
        this.idemprestimo = idemprestimo;
    }

    public Emprestimo(Integer idemprestimo, Date dataEmprestimo, Date dataDevProg) {
        this.idemprestimo = idemprestimo;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevProg = dataDevProg;
    }

    public Integer getIdemprestimo() {
        return idemprestimo;
    }

    public void setIdemprestimo(Integer idemprestimo) {
        this.idemprestimo = idemprestimo;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevProg() {
        return dataDevProg;
    }

    public void setDataDevProg(Date dataDevProg) {
        this.dataDevProg = dataDevProg;
    }

    public Date getDataDevEfetiva() {
        return dataDevEfetiva;
    }

    public void setDataDevEfetiva(Date dataDevEfetiva) {
        this.dataDevEfetiva = dataDevEfetiva;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getUsuario() {
        return usuario;
    }

    public void setUsuario(Cliente usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idemprestimo != null ? idemprestimo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emprestimo)) {
            return false;
        }
        Emprestimo other = (Emprestimo) object;
        if ((this.idemprestimo == null && other.idemprestimo != null) || (this.idemprestimo != null && !this.idemprestimo.equals(other.idemprestimo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Emprestimo[ idemprestimo=" + idemprestimo + " ]";
    }
    
}
