package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Emprestimo {
    
    private List<Exemplar> exemplares = new ArrayList(); 
    private Usuario usuario;
    private Funcionario funcionario;
    private Date dataEmp;
    private Date dataDev;
    private Date dataDevPrev;
    private int idEmp;
    private Multa multa;

    public Emprestimo(Usuario usuario, Funcionario funcionario, Date dataEmp, Date dataDev, Date dataDevPrev, int idEmp, Multa multa) {
        this.usuario = usuario;
        this.funcionario = funcionario;
        this.dataEmp = dataEmp;
        this.dataDev = dataDev;
        this.dataDevPrev = dataDevPrev;
        this.idEmp = idEmp;
        this.multa = multa;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Date getDataEmp() {
        return dataEmp;
    }

    public Date getDataDev() {
        return dataDev;
    }

    public Date getDataDevPrev() {
        return dataDevPrev;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public void setDataEmp(Date dataEmp) {
        this.dataEmp = dataEmp;
    }

    public void setDataDev(Date dataDev) {
        this.dataDev = dataDev;
    }

    public void setDataDevPrev(Date dataDevPrev) {
        this.dataDevPrev = dataDevPrev;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
    }
    
    
    
}
