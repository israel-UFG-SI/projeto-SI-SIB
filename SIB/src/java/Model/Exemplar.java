/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "exemplar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exemplar.findAll", query = "SELECT e FROM Exemplar e")
    , @NamedQuery(name = "Exemplar.findByIdexemplar", query = "SELECT e FROM Exemplar e WHERE e.idexemplar = :idexemplar")
    , @NamedQuery(name = "Exemplar.findByQuantidade", query = "SELECT e FROM Exemplar e WHERE e.quantidade = :quantidade")})
public class Exemplar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idexemplar")
    private Integer idexemplar;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private int quantidade;
    @JoinColumn(name = "livro", referencedColumnName = "idlivro")
    @ManyToOne(optional = false)
    private Livro livro;

    public Exemplar() {
    }

    public Exemplar(Integer idexemplar) {
        this.idexemplar = idexemplar;
    }

    public Exemplar(Integer idexemplar, int quantidade) {
        this.idexemplar = idexemplar;
        this.quantidade = quantidade;
    }

    public Integer getIdexemplar() {
        return idexemplar;
    }

    public void setIdexemplar(Integer idexemplar) {
        this.idexemplar = idexemplar;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idexemplar != null ? idexemplar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exemplar)) {
            return false;
        }
        Exemplar other = (Exemplar) object;
        if ((this.idexemplar == null && other.idexemplar != null) || (this.idexemplar != null && !this.idexemplar.equals(other.idexemplar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Exemplar[ idexemplar=" + idexemplar + " ]";
    }
    
}
