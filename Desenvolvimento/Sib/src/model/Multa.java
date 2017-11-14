package model;

import java.util.Date;

public class Multa {
    
    private float valor;
    private Date dataPagamento; 
    private Funcionario funcionario;
    private Usuario usuario;

    public Multa(float valor, Date dataPagamento, Funcionario funcionario, Usuario usuario) {
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.funcionario = funcionario;
        this.usuario = usuario;
    }

    public float getValor() {
        return valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
