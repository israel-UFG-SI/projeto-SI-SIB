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
@Table(name = "multa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Multa.findAll", query = "SELECT m FROM Multa m")
    , @NamedQuery(name = "Multa.findByIdmulta", query = "SELECT m FROM Multa m WHERE m.idmulta = :idmulta")
    , @NamedQuery(name = "Multa.findByValor", query = "SELECT m FROM Multa m WHERE m.valor = :valor")
    , @NamedQuery(name = "Multa.findByDataPagamento", query = "SELECT m FROM Multa m WHERE m.dataPagamento = :dataPagamento")})
public class Multa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idmulta")
    private Integer idmulta;
    @Basic(optional = false)
    @Column(name = "valor")
    private float valor;
    @Basic(optional = false)
    @Column(name = "dataPagamento")
    @Temporal(TemporalType.DATE)
    private Date dataPagamento;    
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Cliente usuario;

    public Multa() {
    }

    public Multa(Integer idmulta) {
        this.idmulta = idmulta;
    }

    public Multa(Integer idmulta, float valor, Date dataPagamento) {
        this.idmulta = idmulta;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
    }

    public Integer getIdmulta() {
        return idmulta;
    }

    public void setIdmulta(Integer idmulta) {
        this.idmulta = idmulta;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
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
        hash += (idmulta != null ? idmulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Multa)) {
            return false;
        }
        Multa other = (Multa) object;
        if ((this.idmulta == null && other.idmulta != null) || (this.idmulta != null && !this.idmulta.equals(other.idmulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Multa[ idmulta=" + idmulta + " ]";
    }
    
}
