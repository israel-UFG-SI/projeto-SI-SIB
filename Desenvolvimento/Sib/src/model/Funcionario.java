package model;

public class Funcionario extends Pessoa {
    private String cargaHoraria;
    private String senha;
    private String cpfSup;

    public Funcionario(String nome,String cpf, String telefone, String endereco,
            String cargaHoraria, String senha, String cpfSup) {
        super(nome, cpf, telefone, endereco);
        this.cargaHoraria = cargaHoraria;
        this.senha = senha;
        this.cpfSup = cpfSup;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpfSup() {
        return cpfSup;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCpfSup(String cpfSup) {
        this.cpfSup = cpfSup;
    }
    
    
    
    
}
