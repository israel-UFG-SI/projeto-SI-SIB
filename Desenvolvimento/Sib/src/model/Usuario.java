package model;

public class Usuario extends Pessoa {
    
    private String codUser;

    public Usuario(String codUser, String nome, String cpf, String telefone, 
            String endereco) {
        super(nome, cpf, telefone, endereco);
        this.codUser = codUser;
    }

    public String getCodUser() {
        return codUser;
    }

    public void setCodUser(String codUser) {
        this.codUser = codUser;
    }
      
}
