package model;

import java.util.ArrayList;
import java.util.List;

public class Livro {
    
    private String titulo;
    private String isbn;
    private String editora;
    private String edicao;
    private int idLivro;
    private String assunto;
    private int ano;
    private Autor autor;
    private Secao secao;
    private List<Exemplar> exemplares = new ArrayList();
    private List<Emprestimo> emprestimo = new ArrayList();

    public Livro(String titulo, String isbn, String editora, String edicao, 
            int idLivro, String assunto, int ano, Autor autor, Secao secao) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.editora = editora;
        this.edicao = edicao;
        this.idLivro = idLivro;
        this.assunto = assunto;
        this.ano = ano;
        this.autor = autor;
        this.secao = secao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEditora() {
        return editora;
    }

    public String getEdicao() {
        return edicao;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public String getAssunto() {
        return assunto;
    }

    public int getAno() {
        return ano;
    }

    public Autor getAutor() {
        return autor;
    }

    public Secao getSecao() {
        return secao;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public List<Emprestimo> getEmprestimo() {
        return emprestimo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

    public void setEmprestimo(List<Emprestimo> emprestimo) {
        this.emprestimo = emprestimo;
    }
    
    
    
    }
