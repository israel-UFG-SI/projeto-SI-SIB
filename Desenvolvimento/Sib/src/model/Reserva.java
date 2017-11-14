package model;

import java.util.Date;

public class Reserva {
    
    private Usuario usuario;
    private Funcionario funcionario;
    private Date dataReserva;
    private Livro livro; 

    public Reserva(Usuario usuario, Funcionario funcionario, Date dataReserva, Livro livro) {
        this.usuario = usuario;
        this.funcionario = funcionario;
        this.dataReserva = dataReserva;
        this.livro = livro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    
    
    
    
}
