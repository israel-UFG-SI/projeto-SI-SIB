package model;

public class Secao {
    private String nome;
    private String idSecao;

    public Secao(String nome, String idSecao) {
        this.nome = nome;
        this.idSecao = idSecao;
    }

    public String getNome() {
        return nome;
    }

    public String getIdSecao() {
        return idSecao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdSecao(String idSecao) {
        this.idSecao = idSecao;
    }
    
    
}
