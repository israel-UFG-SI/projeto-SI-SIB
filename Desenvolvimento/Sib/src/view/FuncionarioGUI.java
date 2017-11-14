package view;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public final class FuncionarioGUI extends JFrame {
 
    //----------------------------------------------- Variáveis Funcionario GUI
    JButton  usuario, livro, emprestimo,
            devolucao, reserva, multa, sair, manutencao, relatorio;
    JPanel centro;
    JLabel fundo;
    String nome,codigo;
    //---------------------------------------------------------VARIÁVEIS USUARIO
    JLabel cabecalho, nomeL,cpfL,telefoneL,endL,codigoL;
    JButton cadastrar, remover, editar, voltar;
    JTextField nomeT,cpfT,endT,telefoneT,codigoT;
    //-----------------------------------------------------------VARIÁVEIS LIVRO
    JLabel tituloL, isbnL, secaoL, editoraL, edicaoL, assuntoL, autorL, anoL; 
    JTextField tituloT, isbnT, secaoT, editoraT, edicaoT, assuntoT,autorT, anoT;
    //------------------------------------------------------VARIÁVEIS MANUTENÇÃO
    JLabel exemplarL, dataL;
    JTextField exemplarT, dataT;
    //------------------------------------------------------VARIÁVEIS EMPRÉSTIMO
    JLabel codigo2L;
    JTable tabelaLivros;
    JTextField codigo2T;
    JScrollPane scroll;
    JButton renovar;
    public FuncionarioGUI(){

    setResizable(false); 
    setSize(600,600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("FUNCIONÁRIO: ");
    
    /*fundo = new JLabel(new ImageIcon(getClass().getClassLoader()
             .getResource("imagens/fundo.jpg")));*/
    centro = new JPanel();
    //----------------- S A I R ----------------
    sair = new JButton("LOGOUT");
    sair.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            Autenticacao a = new Autenticacao();
            a.setVisible(true);
        }
        });
    //----------------- R E L A T Ó R I O ----------------
    relatorio = new JButton("RELATÓRIO");
    relatorio.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
        }
        });
    //----------------- U S U A R I O ----------------
    usuario = new JButton("USUÁRIO");
    usuario.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            Usuario();
        }
        }); 
    //----------------- L I V R O ----------------
    livro = new JButton("LIVRO");
    livro.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            Livro();
        }
        }); 
   
    //----------------- E M P R E S T I M O ----------------
    emprestimo = new JButton("EMPRÉSTIMO");
    emprestimo.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            Emprestimo();
        }
        });
    
    //----------------- R E S E R V A ----------------
    reserva = new JButton("RESERVA");
    reserva.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            Reserva();
        }
        });
    //----------------- M A N U T E N C A O ----------------
    manutencao = new JButton("MANUTENÇÃO");
    manutencao.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            Manutencao();
        }
        });

    //----------------- M U L T A ----------------
    multa = new JButton("MULTA");
    multa.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            setVisible(false);
            Multa();
        }
        });
    
    
    centro.setLayout(null);
    
    // POSIÇÃO DOS JBUTTONS
    
    livro.setBounds(190,10,230,50);
    usuario.setBounds(190,65,230,50);
    emprestimo.setBounds(190,175,230,50);
    reserva.setBounds(190,285,230,50);
    multa.setBounds(190,340,230,50);
    manutencao.setBounds(190,395,230,50);
    relatorio.setBounds(190,450,230,50);
    sair.setBounds(190,505,230,50);
    //fundo.setBounds(0,0,400,600);

    
    centro.add(livro);
    centro.add(usuario);
    centro.add(emprestimo);
    centro.add(reserva);
    centro.add(multa);
    centro.add(manutencao);
    centro.add(relatorio);
    centro.add(sair);
    add(centro);
    
    }
    
    // ------------------------------------------------------------------USUARIO
    public void Usuario(){
    final JFrame user = new JFrame("GERENCIAMENTO DE USUÁRIOS");
    user.setLocationRelativeTo(null);
    user.setVisible(true);
    user.setResizable(false); 
    user.setSize(580,300);
    user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    nomeL = new JLabel("NOME:");
    cpfL = new JLabel("CPF:");
    endL = new JLabel("ENDEREÇO:");
    telefoneL = new JLabel("TELEFONE:");
    codigoL = new JLabel("CÓDIGO:");
    
    nomeT = new JTextField();
    cpfT = new JTextField();
    endT = new JTextField();
    telefoneT = new JTextField();
    codigoT = new JTextField();
    
    cadastrar = new JButton("(+)\nCADASTRAR");
    cadastrar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    
    remover = new JButton("(-)\nREMOVER");
    remover.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    editar = new JButton("EDITAR");
    editar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    
    voltar = new JButton("VOLTAR");
    voltar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            user.setVisible(false);
            FuncionarioGUI func = new FuncionarioGUI();
            func.setLocationRelativeTo(null);
            func.setVisible(true);
        }
        });
    
    centro = new JPanel();
    centro.setLayout(null);
    
    nomeL.setBounds(10, 90, 50, 25);
    nomeT.setBounds(55, 90, 500, 25);
    cpfL.setBounds(15,125,50,25);
    cpfT.setBounds(55, 125, 130, 25);
    telefoneL.setBounds(190,125, 100, 25);
    telefoneT.setBounds(253, 125, 130, 25);
    codigoL.setBounds(390, 125, 70, 25);
    codigoT.setBounds(440, 125, 114, 25);
    endL.setBounds(10, 160, 80, 25);
    endT.setBounds(80, 160, 480, 25);
    cadastrar.setBounds(10, 200, 130, 50);
    remover.setBounds(160, 200, 130, 50);
    editar.setBounds(310, 200, 130, 50);
    voltar.setBounds(460, 200, 110, 50);
    
    centro.add(nomeL);
    centro.add(nomeT);
    centro.add(cpfL);
    centro.add(cpfT);
    centro.add(telefoneL);
    centro.add(telefoneT);
    centro.add(codigoL);
    centro.add(codigoT);
    centro.add(endL);
    centro.add(endT);
    centro.add(cadastrar);
    centro.add(remover);
    centro.add(editar);
    centro.add(voltar);
    user.add(centro);
    
    }
    
    // --------------------------------------------------------------------LIVRO
    public void Livro(){
    final JFrame livro = new JFrame("GERENCIAMENTO DE LIVROS");
    livro.setLocationRelativeTo(null);
    livro.setVisible(true);
    livro.setResizable(false); 
    livro.setSize(700,300);
    livro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    tituloL = new JLabel("TÍTULO:");
    isbnL = new JLabel("ISBN:");
    codigoL = new JLabel("CÓDIGO:");
    secaoL = new JLabel("SEÇÃO:");
    editoraL = new JLabel("EDITORA:");
    edicaoL = new JLabel("EDIÇÃO:");
    assuntoL = new JLabel("ASSUNTO:");
    autorL = new JLabel("AUTOR:");
    anoL = new JLabel("ANO:");
    
    tituloT = new JTextField();
    isbnT = new JTextField();
    codigoT = new JTextField();
    secaoT = new JTextField();
    editoraT = new JTextField();
    edicaoT = new JTextField();
    assuntoT = new JTextField();
    autorT = new JTextField();
    anoT = new JTextField();
    
    cadastrar = new JButton("(+)\nCADASTRAR");
    cadastrar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    
    remover = new JButton("(-)\nREMOVER");
    remover.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    editar = new JButton("EDITAR");
    editar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    
    voltar = new JButton("VOLTAR");
    voltar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            livro.setVisible(false);
            FuncionarioGUI func = new FuncionarioGUI();
            func.setLocationRelativeTo(null);
            func.setVisible(true);
        }
        });
    centro = new JPanel();
    centro.setLayout(new FlowLayout(FlowLayout.LEFT));
    
    /*tituloL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    tituloT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    isbnL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    isbnT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    codigoL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    codigoT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    secaoL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    secaoT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    editoraL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    editoraT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    edicaoL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    edicaoT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    assuntoL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    assuntoT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    autorL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    autorT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    anoL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    anoT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    cadastrar.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    remover.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    editar.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    voltar.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);*/
    
    centro.add(tituloL);
    centro.add(tituloT);
    centro.add(isbnL);
    centro.add(isbnT);
    centro.add(codigoL);
    centro.add(codigoT);
    centro.add(secaoL);
    centro.add(secaoT);
    centro.add(editoraL);
    centro.add(editoraT);
    centro.add(edicaoL);
    centro.add(edicaoT);
    centro.add(assuntoL);
    centro.add(assuntoT);
    centro.add(autorL);
    centro.add(autorT);
    centro.add(anoL);
    centro.add(anoT);
    centro.add(cadastrar);
    centro.add(remover);
    centro.add(editar);
    centro.add(voltar);
    
    livro.add(centro);
    
    }
    
    // ---------------------------------------------------------------EMPRÉSTIMO
    public void Emprestimo(){
    final JFrame emp = new JFrame("GERENCIAMENTO DE EMPRÉSTIMO");
    emp.setLocationRelativeTo(null);
    emp.setVisible(true);
    emp.setResizable(false); 
    emp.setSize(700,300);
    emp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    codigoL = new JLabel("CPF:");
    nomeL = new JLabel("NOME:");
    codigo2L = new JLabel("CÓDIGO:");
    
    codigoT = new JTextField();
    nomeT = new JTextField();
    codigo2T = new JTextField();
    
    tabelaLivros = new JTable();
    tabelaLivros.setEnabled(false);
    tabelaLivros.setBorder(new LineBorder(Color.BLACK));
    tabelaLivros.setGridColor(Color.BLACK);
    tabelaLivros.setShowGrid(true);
    scroll = new JScrollPane();
    scroll.getViewport().setBorder(null);
    scroll.getViewport().add(tabelaLivros);
    
    emprestimo = new JButton("EMPRÉSTIMO");
    emprestimo.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
        }
        });
    
    devolucao = new JButton("DEVOLUÇÃO");
    devolucao.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
        }
        });
    renovar = new JButton("RENOVAR");
    renovar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
        }
        });
     voltar = new JButton("VOLTAR");
    voltar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            emp.setVisible(false);
            FuncionarioGUI func = new FuncionarioGUI();
            func.setLocationRelativeTo(null);
            func.setVisible(true);
        }
        });
    
    centro = new JPanel();
    centro.setLayout(null);
    
    codigoL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    codigoT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    nomeL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    nomeT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    codigo2L.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    codigo2T.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    scroll.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    emprestimo.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    devolucao.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    renovar.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    voltar.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    
    centro.add(codigoL);
    centro.add(codigoT);
    centro.add(nomeL);
    centro.add(nomeT);
    centro.add(codigo2L);
    centro.add(codigo2T);
    centro.add(scroll);
    centro.add(emprestimo);
    centro.add(devolucao);
    centro.add(renovar);
    centro.add(voltar);
    emp.add(centro);
    
    
    }
    
    // ------------------------------------------------------------------RESERVA
    public void Reserva(){
    final JFrame reserva = new JFrame("GERENCIAMENTO DE RESERVAS");
    reserva.setLocationRelativeTo(null);
    reserva.setVisible(true);
    reserva.setResizable(false); 
    reserva.setSize(700,300);
    reserva.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }
    
    // ---------------------------------------------------------------MANUTENÇÃO
    public void Manutencao(){
    final JFrame manut = new JFrame("GERENCIAMENTO DE MANUTENÇÃO");
    manut.setLocationRelativeTo(null);
    manut.setVisible(true);
    manut.setResizable(false); 
    manut.setSize(700,300);
    manut.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    exemplarL = new JLabel("CÓDIGO EXEMPLAR:");
    dataL = new JLabel("DATA:");
    
    exemplarT = new JTextField();
    dataT = new JTextField();
    
    cadastrar = new JButton("(+)\nCADASTRAR");
    cadastrar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    
    remover = new JButton("FINALIZAR");
    remover.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            
        }
        });
    
    voltar = new JButton("VOLTAR");
    voltar.addActionListener(new ActionListener() {
         
        @Override
            public void actionPerformed(ActionEvent ae) {
            manut.setVisible(false);
            FuncionarioGUI func = new FuncionarioGUI();
            func.setLocationRelativeTo(null);
            func.setVisible(true);
        }
        });
    
    centro = new JPanel();
    centro.setLayout(null);
    
    exemplarL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    exemplarT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    dataL.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    dataT.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    cadastrar.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    remover.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    voltar.setBounds(WIDTH, WIDTH, WIDTH, WIDTH);
    
    }
    
    // --------------------------------------------------------------------MULTA
    public void Multa(){
    final JFrame multa = new JFrame("GERENCIAMENTO DE MULTAS");
    multa.setLocationRelativeTo(null);
    multa.setVisible(true);
    multa.setResizable(false); 
    multa.setSize(700,300);
    multa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    }
    
    //--------------------------------------------------------------- T E S T E 
     public static void main(String[] args){
        FuncionarioGUI a = new FuncionarioGUI(); 
        a.setVisible(true); 
        a.setLocationRelativeTo(null);
    }
}

