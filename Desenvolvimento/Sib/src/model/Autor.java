package model;

public class Autor {
    private String nome; 
    private int idAutor;

    public Autor(String nome, int idAutor) {
        this.nome = nome;
        this.idAutor = idAutor;
    }

    public String getNome() {
        return nome;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
    
    
}
