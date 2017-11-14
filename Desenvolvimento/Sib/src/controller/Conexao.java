package controller;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Conexao {

    //Insere driver necess�rio e cria conex�o com o banco de dados
    Connection con;
    Statement stmt;
    ResultSet rs;
    String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "fredy06";
    String driver = "org.postgresql.Driver";
    String sql;

    //Cria Conex�o
    public void ConectarBD() {
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url, user, password);
            stmt = (Statement) con.createStatement();
            System.out.println("Conectou!");
        } catch (Exception e) {
            //System.out.println(e);
            JOptionPane.showMessageDialog(null,
                    "Conex�o Negada.\nVerifique com o suporte do Banco de Dados.",
                    "Erro",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //Deconectar
    public void DesconectarBD(){
        
    }
    
        
   
}
