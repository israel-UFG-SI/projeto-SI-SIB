/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Matheus
 */
@Entity
@Table(name = "livro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Livro.findAll", query = "SELECT l FROM Livro l")
    , @NamedQuery(name = "Livro.findByIdlivro", query = "SELECT l FROM Livro l WHERE l.idlivro = :idlivro")
    , @NamedQuery(name = "Livro.findByTitulo", query = "SELECT l FROM Livro l WHERE l.titulo = :titulo")
    , @NamedQuery(name = "Livro.findByIsbn", query = "SELECT l FROM Livro l WHERE l.isbn = :isbn")
    , @NamedQuery(name = "Livro.findByEditora", query = "SELECT l FROM Livro l WHERE l.editora = :editora")
    , @NamedQuery(name = "Livro.findByEdi\u00e7\u00e3o", query = "SELECT l FROM Livro l WHERE l.edi\u00e7\u00e3o = :edi\u00e7\u00e3o")
    , @NamedQuery(name = "Livro.findByAno", query = "SELECT l FROM Livro l WHERE l.ano = :ano")
    , @NamedQuery(name = "Livro.findByAutor", query = "SELECT l FROM Livro l WHERE l.autor = :autor")
    , @NamedQuery(name = "Livro.findBySecao", query = "SELECT l FROM Livro l WHERE l.secao = :secao")
    , @NamedQuery(name = "Livro.findByQuantidade", query = "SELECT l FROM Livro l WHERE l.quantidade = :quantidade")})
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idlivro")
    private Integer idlivro;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "isbn")
    private String isbn;
    @Basic(optional = false)
    @Column(name = "editora")
    private String editora;
    @Basic(optional = false)
    @Column(name = "edi\u00e7\u00e3o")
    private String edição;
    @Basic(optional = false)
    @Column(name = "ano")
    private int ano;
    @Basic(optional = false)
    @Column(name = "autor")
    private String autor;
    @Basic(optional = false)
    @Column(name = "secao")
    private String secao;
    @Column(name = "quantidade")
    private Integer quantidade;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "livro")
    private Collection<Exemplar> exemplarCollection;

    public Livro() {
    }

    public Livro(Integer idlivro) {
        this.idlivro = idlivro;
    }

    public Livro(Integer idlivro, String titulo, String isbn, String editora, String edição, int ano, String autor, String secao, Integer quantidade) {
        this.idlivro = idlivro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.editora = editora;
        this.edição = edição;
        this.ano = ano;
        this.autor = autor;
        this.secao = secao;
        this.quantidade = quantidade;
    }

    public Integer getIdlivro() {
        return idlivro;
    }

    public void setIdlivro(Integer idlivro) {
        this.idlivro = idlivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getEdição() {
        return edição;
    }

    public void setEdição(String edição) {
        this.edição = edição;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @XmlTransient
    public Collection<Exemplar> getExemplarCollection() {
        return exemplarCollection;
    }

    public void setExemplarCollection(Collection<Exemplar> exemplarCollection) {
        this.exemplarCollection = exemplarCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlivro != null ? idlivro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        if ((this.idlivro == null && other.idlivro != null) || (this.idlivro != null && !this.idlivro.equals(other.idlivro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Livro[ idlivro=" + idlivro + " ]";
    }
    
}
