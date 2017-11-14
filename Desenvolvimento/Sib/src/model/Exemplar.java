package model;

public class Exemplar {
    private Livro livro;
    private int idExemplar;

    public Exemplar(Livro livro, int idExemplar) {
        this.livro = livro;
        this.idExemplar = idExemplar;
    }

    public Livro getLivro() {
        return livro;
    }

    public int getIdExemplar() {
        return idExemplar;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public void setIdExemplar(int idExemplar) {
        this.idExemplar = idExemplar;
    }
    
    
    
}
