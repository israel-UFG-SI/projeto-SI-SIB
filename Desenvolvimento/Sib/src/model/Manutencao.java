package model;

import java.util.Date;

public class Manutencao {
    
    private String idManut;
    private Exemplar exemplar;
    private Funcionario funcionario;
    private Date dataManut;
    private boolean status;

    public Manutencao(String idManut, Exemplar exemplar, Funcionario funcionario,
            Date dataManut, boolean status) {
        this.idManut = idManut;
        this.exemplar = exemplar;
        this.funcionario = funcionario;
        this.dataManut = dataManut;
        this.status = status;
    }

    public String getIdManut() {
        return idManut;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Date getDataManut() {
        return dataManut;
    }

    public void setIdManut(String idManut) {
        this.idManut = idManut;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setDataManut(Date dataManut) {
        this.dataManut = dataManut;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    
}
