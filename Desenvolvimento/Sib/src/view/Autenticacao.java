package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public final class Autenticacao extends JFrame {
    
    JPanel painel;
    JButton usuario, logar, sair;
    JTextField textoIdEmp;
    JLabel idEmp, nomeSenha, logo, cadeado;
    JPasswordField senha;
    
    public Autenticacao(){
    setTitle("AUTENTICAÇÃO");
    setVisible(true); 
    setResizable(false); 
    setSize(400,200);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        
    idEmp = new JLabel("LOGIN:");
    nomeSenha = new JLabel("SENHA:");
    logo = new JLabel(new ImageIcon(getClass().getClassLoader().
            getResource("imagens/Logo.png")));
    cadeado = new JLabel(new ImageIcon(getClass().getClassLoader().
            getResource("imagens/cadeado.png")));
    senha = new JPasswordField(10);
    textoIdEmp = new JTextField();
    
    usuario = new JButton("USUÁRIO");
    usuario.addActionListener(new ActionListener() {

           @Override
            public void actionPerformed(ActionEvent ae) {
               UsuarioGUI user = new UsuarioGUI();
               setVisible(false);
               user.setVisible(true); 
              
            }
        });
    
    logar = new JButton("ENTRAR");
    logar.addActionListener(new ActionListener() {

           @Override
            public void actionPerformed(ActionEvent ae) {
               FuncionarioGUI func = new FuncionarioGUI();
               setVisible(false);
               func.setVisible(true);
               func.setLocationRelativeTo(null);
            }
        });
    
    sair = new JButton("SAIR");
    sair.addActionListener(new ActionListener() {

           @Override
            public void actionPerformed(ActionEvent ae) {
               System.exit(0);
            }
        });
    
    logo.setBounds(3, 5, 150,120);
    idEmp.setBounds(170, 5, 100, 25);
    nomeSenha.setBounds(170, 55, 100, 25);
    textoIdEmp.setBounds(170, 30, 100, 25);
    senha.setBounds(170, 80, 100, 25);
    logar.setBounds(135, 125, 120, 25);
    sair.setBounds(265, 125, 120, 25);
    usuario.setBounds(8, 125, 120, 25);
    cadeado.setBounds(250, 5, 150, 120);
    
    painel = new JPanel();
    painel.setLayout(null);
    painel.add(usuario);
    painel.add(logar);
    painel.add(sair);
    painel.add(idEmp);
    painel.add(nomeSenha);
    painel.add(logo);
    painel.add(textoIdEmp);
    painel.add(senha);
    painel.add(cadeado);
    
    
    add(painel);
    }
    
    public static void main(String[] args){
        Autenticacao a = new Autenticacao(); 
        a.setVisible(true); 
        a.setLocationRelativeTo(null);
    }
}
