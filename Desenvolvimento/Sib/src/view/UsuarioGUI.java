package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import model.Exemplar;
import model.Livro;

public final class UsuarioGUI extends JFrame {
    
    JPanel painel;
    JLabel imagemSIBIM, filtro, imagemSIBIM1, imagemSIBIM2;
    JComboBox selecaoProcura;
    JTable livrosFiltrados;
    JScrollPane scroll;
    JTextField textoProcura;
    JButton pesquisar, limpar;
    List exemplares;
    String returnSelecao;
    String[] colunas;
    String[][] linhas;
    
    public UsuarioGUI(){
    setResizable(false); 
    setSize(1380,750);
    /*setSize(new Dimension (
                (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));*/
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("SIBIM - Sistema de Biblioteca Municipal");
    
    filtro = new JLabel("FILTRO"); // PALAVRA "FILTRO" NO FRAME
    // COLOCA A LOGO DO SIBIM NO FRAME
    imagemSIBIM = new JLabel(new ImageIcon(getClass().getClassLoader().
            getResource("imagens/Logo.png")));
    imagemSIBIM1 = new JLabel(new ImageIcon(getClass().getClassLoader().
            getResource("imagens/Logo1.png")));
    imagemSIBIM2 = new JLabel(new ImageIcon(getClass().getClassLoader().
            getResource("imagens/Logo.png")));
    painel = new JPanel();
    
    // SELECIONA UM DOS PARÂMETROS DE PESQUISA
    returnSelecao = new String();
    selecaoProcura = new JComboBox();
    selecaoProcura.addItem("TÍTULO");
    selecaoProcura.addItem("AUTOR");
    selecaoProcura.addItem("ASSUNTO");
    selecaoProcura.addItem("EDITORA");
    selecaoProcura.addItem("ISBN");
    /*selecaoProcura.addActionListener(new ActionListener() {

           @Override
            public void actionPerformed(ActionEvent ae) {
               returnSelecao = selecaoProcura.getSelectedItem() + "";
               if(returnSelecao.equals("TÍTULO")){
                   
               }
            }
        });*/
    
    //ESCREVER O NOME RELACIONADO AO PARÂMETRO ESCOLHIDO
    textoProcura = new JTextField();
    
    // BOTÃO QUE ACIONA A PESQUISA
    pesquisar = new JButton("PESQUISAR");
    pesquisar.addActionListener(new ActionListener() {

           @Override
            public void actionPerformed(ActionEvent ae) {
               
               
            }
        });
    
    limpar = new JButton("LIMPAR");
    limpar.addActionListener(new ActionListener() {

           @Override
            public void actionPerformed(ActionEvent ae) {
              
               selecaoProcura.setSelectedItem("TÍTULO");
               textoProcura.setText("");
               // !!!!!!!!!!TABLE!!!!!!
            }
        });
    // TABELA ONDE SERÁ RETORNADO AS INFORMAÇÕES
    exemplares = new LinkedList<>();
    colunas = new String[]{"TÍTULO","ISBN","AUTOR","CÓDIGO","EDIÇÃO","EDITORA",
    "ANO","SEÇÃO","SITUAÇÃO"};
    linhas = new String[][]{{"",""},{"",""}};
    DefaultTableModel modelo = new DefaultTableModel(linhas,colunas);
    livrosFiltrados = new JTable(modelo);
    livrosFiltrados.setEnabled(false);
    livrosFiltrados.setBorder(new LineBorder(Color.BLACK));
    livrosFiltrados.setGridColor(Color.BLACK);
    livrosFiltrados.setShowGrid(true);
    scroll = new JScrollPane();
    scroll.getViewport().setBorder(null);
    scroll.getViewport().add(livrosFiltrados);
    
    
    painel.setLayout(null);
    imagemSIBIM.setBounds(300,0,200,120);
    imagemSIBIM1.setBounds(400,0,600,120);
    imagemSIBIM2.setBounds(900,0,200,120);
    filtro.setBounds(130, 165, 40,25);
    selecaoProcura.setBounds(190,165,100,25);
    textoProcura.setBounds(300,165,700,25);
    pesquisar.setBounds(1010, 165, 120, 25);
    limpar.setBounds(1140, 165, 100, 25);
    scroll.setBounds(10,200,1340,520);
    painel.add(imagemSIBIM);
    painel.add(imagemSIBIM1);
    painel.add(imagemSIBIM2);
    painel.add(filtro);
    painel.add(selecaoProcura);
    painel.add(textoProcura);
    painel.add(pesquisar);
    painel.add(limpar);
    painel.add(scroll);
    add(painel);
    
    }
    
    
    /* Deixar o frame do tamanho da tela do usuário
    public final Dimension telaInteira(){
        return(
                new Dimension (
                (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
    }*/
    
    
}
