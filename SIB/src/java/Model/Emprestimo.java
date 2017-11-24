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

    @Basic(optional = false)
    @Column(name = "dataEmprestimo")
    private String dataEmprestimo;
    @Basic(optional = false)
    @Column(name = "dataDevProg")
    private String dataDevProg;
    @Column(name = "dataDevEfetiva")
    private String dataDevEfetiva;
    @JoinColumn(name = "exemplar", referencedColumnName = "idexemplar")
    @ManyToOne(optional = false)
    private Exemplar exemplar;

    @Basic(optional = false)
    @Column(name = "situa\u00e7\u00e3o")
    private String situação;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idemprestimo")
    private Integer idemprestimo;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Cliente usuario;

    public Emprestimo() {
    }

    public Emprestimo(Integer idemprestimo) {
        this.idemprestimo = idemprestimo;
    }

    public Emprestimo(Integer idemprestimo, String dataEmprestimo, String dataDevProg, String situação , Exemplar exemplar, Cliente usuario) {
        this.idemprestimo = idemprestimo;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevProg = dataDevProg;
        this.situação = situação;
        this.exemplar = exemplar;
        this.usuario = usuario;
    }

    public Integer getIdemprestimo() {
        return idemprestimo;
    }

    public void setIdemprestimo(Integer idemprestimo) {
        this.idemprestimo = idemprestimo;
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

    public String getSituação() {
        return situação;
    }

    public void setSituação(String situação) {
        this.situação = situação;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevProg() {
        return dataDevProg;
    }

    public void setDataDevProg(String dataDevProg) {
        this.dataDevProg = dataDevProg;
    }

    public String getDataDevEfetiva() {
        return dataDevEfetiva;
    }

    public void setDataDevEfetiva(String dataDevEfetiva) {
        this.dataDevEfetiva = dataDevEfetiva;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }
    
}
