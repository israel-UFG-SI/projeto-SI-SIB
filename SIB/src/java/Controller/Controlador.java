/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import static Model.Emprestimo_.funcionario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Matheus
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
public class Controlador extends HttpServlet {

     
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); // Tipo de retorno
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SIBPU");
        EntityManager em = emf.createEntityManager();
        PrintWriter out = response.getWriter();
        
       //Váriaveis formulario
       int idFormulario; // 1 - Cliente; 2 - Livros; 3 - Emprestimos; 4 - Funcionários
       int tipoFormulario; // 1.1 - Consulta;...
       idFormulario = Integer.parseInt(request.getParameter("idFormulario"));
       tipoFormulario = Integer.parseInt(request.getParameter("tipoFormulario"));
       EntityTransaction tx = em.getTransaction();
       HttpSession session = request.getSession();
       
       //Variáveis Cliente
       String nomeCliente , cpfCliente , telCliente, enderecoCliente, pendenciaCliente;
       int idCliente;
       
       //Variáveis Livro
       String tituloLivro, isbnLivro, autorLivro, editoraLivro, edicaoLivro, secaoLivro;
       int idLivro, qtdLivro, anoLivro;
       
       //Variáveis Funcionarios       
       String nomeFuncionario, cpfFuncionario, senhaFuncionario, enderecoFuncionario, telFuncionario;
       int idFuncionario, chFuncionario;
       
       if (idFormulario == 1){ //Cliente
           switch (tipoFormulario){
                    case 11:{ //Consultar todos
                        TypedQuery<Cliente> query = em.createQuery("" + "Select c from Cliente c", Cliente.class);
                        List<Cliente> clientes = query.getResultList();
                        session.setAttribute("mensagem", "Total de Cliente(s): "+clientes.size() ); 
                        session.setAttribute("clientes", clientes); 
                        response.sendRedirect("View/clientes/consultaTodos.jsp");
                        break;
                    }
                    case 12:{ //Consultar Especifico
                        idCliente = Integer.parseInt(request.getParameter("idUsuario"));
                        Cliente cliente = em.find(Cliente.class, idCliente);  
                        
                        if(cliente != null){// cliente encontrado
                            session.setAttribute("mensagem", "Cliente "+idCliente+" encontrado!"); 
                            session.setAttribute("cliente", cliente);
                        }else{
                            session.setAttribute("mensagem", "Cliente "+idCliente+" não encontrado!"); 
                            session.setAttribute("cliente", null);
                        } 
                        response.sendRedirect("View/clientes/resultado.jsp");
                        break;
                    }
                    case 13:{ // Cadastrar
                        idCliente = Integer.parseInt(request.getParameter("idUsuario"));
                        nomeCliente = request.getParameter("nome");
                        cpfCliente = request.getParameter("cpf");
                        enderecoCliente = request.getParameter("endereco");
                        telCliente = request.getParameter("telefone");
                        pendenciaCliente = "Não";
                        Cliente cliente = new Cliente(idCliente, nomeCliente, cpfCliente, enderecoCliente, telCliente, pendenciaCliente);
                        try {
                            tx.begin();
                            em.persist(cliente);
                            tx.commit();
                            session.setAttribute("mensagem", "Cliente "+idCliente+" cadastrado!"); 
                            session.setAttribute("cliente", cliente);
                        } catch (Exception e) {
                            session.setAttribute("mensagem", "Código "+idCliente+" já está sendo usado! Por favor, tente novamente com outro"); 
                            session.setAttribute("cliente", null);
                        }                                                 
                        response.sendRedirect("View/clientes/resultado.jsp");
                        break;
                    }
                    case 14:{ // Alterar
                        idCliente = Integer.parseInt(request.getParameter("idUsuario"));
                        nomeCliente = request.getParameter("nome");
                        enderecoCliente = request.getParameter("endereco");
                        telCliente = request.getParameter("telefone");
                        Cliente cliente = em.find(Cliente.class, idCliente);  
                        
                        if(cliente != null){// cliente encontrado
                            cpfCliente = cliente.getCpf();
                            pendenciaCliente = cliente.getPendencia();
                            cliente = new Cliente(idCliente, nomeCliente, cpfCliente, enderecoCliente, telCliente, pendenciaCliente);
                            tx.begin();
                            em.merge(cliente);
                            tx.commit();                            
                            session.setAttribute("mensagem", "Cliente "+idCliente+" Alterado!"); 
                            session.setAttribute("cliente", cliente);
                        }else{
                            session.setAttribute("mensagem", "Cliente "+idCliente+" não encontrado!"); 
                            session.setAttribute("cliente", null);
                        } 
                        response.sendRedirect("View/clientes/resultado.jsp");
                        break;
                    }
                    case 15:{ // Excluir
                        idCliente = Integer.parseInt(request.getParameter("idUsuario"));
                        Cliente cliente = em.find(Cliente.class, idCliente);
                        
                        if(cliente != null){// cliente encontrado
                            tx.begin();
                            em.remove(cliente);
                            tx.commit();
                            session.setAttribute("mensagem", "Cliente "+idCliente+" excluido!");                             
                        }else{
                            session.setAttribute("mensagem", "Cliente "+idCliente+" não encontrado!");
                        } 
                        session.setAttribute("cliente", null);
                        response.sendRedirect("View/clientes/resultado.jsp");
                        break;
                    }
           }
           
       }else if(idFormulario == 2){ //LIVRO
           
              switch (tipoFormulario){
                    case 21:{ //Consultar todos
                        TypedQuery<Livro> query = em.createQuery("" + "Select c from Livro c", Livro.class);
                        List<Livro> livros = query.getResultList();
                        session.setAttribute("mensagem", "Total de Livros(s): "+livros.size() ); 
                        session.setAttribute("livros", livros); 
                        response.sendRedirect("View/livros/livros2/consultaTodos.jsp");
                        break;
                    }
                    case 22:{ //Consultar Especifico
                        idLivro = Integer.parseInt(request.getParameter("idLivro"));
                        Livro livro = em.find(Livro.class, idLivro);  
                        
                        if(livro != null){// livro encontrado
                            session.setAttribute("mensagem", "Livro "+idLivro+" encontrado!"); 
                            session.setAttribute("livro", livro);
                        }else{
                            session.setAttribute("mensagem", "Livro "+idLivro+" não encontrado!"); 
                            session.setAttribute("livro", null);
                        } 
                        response.sendRedirect("View/livros/livros2/resultado.jsp");
                        break;
                    }
                    case 23:{ // Cadastrar
                        idLivro = Integer.parseInt(request.getParameter("idLivro"));
                        tituloLivro = request.getParameter("titulo");
                        isbnLivro = request.getParameter("ISBN");
                        autorLivro = request.getParameter("autor");
                        editoraLivro = request.getParameter("editora");                        
                        edicaoLivro = request.getParameter("edicao");
                        anoLivro = Integer.parseInt(request.getParameter("ano"));
                        secaoLivro = request.getParameter("secao");
                        qtdLivro = Integer.parseInt(request.getParameter("qtd"));
                        Livro livro = new Livro(idLivro, tituloLivro, isbnLivro, editoraLivro, secaoLivro, anoLivro, autorLivro, secaoLivro , qtdLivro);
                        try {
                            tx.begin();
                            em.persist(livro);
                            tx.commit();
                            session.setAttribute("mensagem", "Livro "+idLivro+" cadastrado!"); 
                            session.setAttribute("livro", livro);
                        } catch (Exception e) {
                            session.setAttribute("mensagem", "Código "+idLivro+" já está sendo usado! Por favor, tente novamente com outro"); 
                            session.setAttribute("livro", null);
                        }                                                 
                        response.sendRedirect("View/livros/livros2/resultado.jsp");
                        break;
                    }
                    case 24:{ // Alterar
                        idLivro = Integer.parseInt(request.getParameter("idLivro"));
                        tituloLivro = request.getParameter("titulo");
                        isbnLivro = request.getParameter("ISBN");
                        autorLivro = request.getParameter("autor");
                        editoraLivro = request.getParameter("editora");                        
                        edicaoLivro = request.getParameter("edicao");
                        anoLivro = Integer.parseInt(request.getParameter("ano"));
                        secaoLivro = request.getParameter("secao");
                        qtdLivro = Integer.parseInt(request.getParameter("qtd"));
                        Livro livro = em.find(Livro.class, idLivro);  
                        
                        if(livro != null){// livro encontrado
                            livro = new Livro(idLivro, tituloLivro, isbnLivro, editoraLivro, secaoLivro, anoLivro, autorLivro, secaoLivro, qtdLivro);
                            tx.begin();
                            em.merge(livro);
                            tx.commit();                            
                            session.setAttribute("mensagem", "Livro "+idLivro+" Alterado!"); 
                            session.setAttribute("livro", livro);
                        }else{
                            session.setAttribute("mensagem", "Livro "+idLivro+" não encontrado!"); 
                            session.setAttribute("livro", null);
                        } 
                        response.sendRedirect("View/livros/livros2/resultado.jsp");
                        break;
                    }
                    case 25:{ // Atualizar Qtd
                        idLivro = Integer.parseInt(request.getParameter("idLivro"));                        
                        qtdLivro = Integer.parseInt(request.getParameter("qtd"));
                        Livro livro = em.find(Livro.class, idLivro);  
                        
                        if(livro != null){// livro encontrado
                            tituloLivro = livro.getTitulo();
                            isbnLivro = livro.getIsbn();
                            autorLivro = livro.getAutor();
                            editoraLivro = livro.getEditora();
                            edicaoLivro = livro.getEdição();
                            anoLivro = livro.getAno();
                            secaoLivro = livro.getSecao();
                            livro = new Livro(idLivro, tituloLivro, isbnLivro, editoraLivro, secaoLivro, anoLivro, autorLivro, secaoLivro, qtdLivro);
                            tx.begin();
                            em.merge(livro);
                            tx.commit();                            
                            session.setAttribute("mensagem", "Livro "+idLivro+" Alterado!"); 
                            session.setAttribute("livro", livro);
                        }else{
                            session.setAttribute("mensagem", "Livro "+idLivro+" não encontrado!"); 
                            session.setAttribute("livro", null);
                        } 
                        response.sendRedirect("View/livros/livros2/resultado.jsp");
                        break;
                    }
                    case 26:{ // Excluir
                        idLivro = Integer.parseInt(request.getParameter("idLivro"));
                        Livro livro = em.find(Livro.class, idLivro);  
                        
                        if(livro != null){// cliente encontrado
                            tx.begin();
                            em.remove(livro);
                            tx.commit();
                            session.setAttribute("mensagem", "Livro "+idLivro+" excluido!");                             
                        }else{
                            session.setAttribute("mensagem", "Livro "+idLivro+" não encontrado!");
                        } 
                        session.setAttribute("livro", null);
                        response.sendRedirect("View/livros/livros2/resultado.jsp");
                        break;
                    }
           }
       }else if (idFormulario == 3){ //Pagamento
           switch (tipoFormulario){
                case 31:{ //Consultar todos
                    TypedQuery<Pagamento> query = em.createQuery("" + "Select c from Pagamento c", Pagamento.class);
                    List<Pagamento> pagamentos = query.getResultList();
                    session.setAttribute("mensagem", "Total de Pagamento(s): "+pagamentos.size() ); 
                    session.setAttribute("pagamentos", pagamentos); 
                    response.sendRedirect("pagamentos/consultaTodos.jsp");
                    break;
                }
                case 32:{ //Consultar Especifico
                    cpfmascara = request.getParameter("cpf");
                    cpfmascara = cpfmascara.replaceAll("[.-]", "");
                    cpf = Long.parseLong(cpfmascara);
                    codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    PagamentoPK pgtoId = new PagamentoPK(cpf, codCurso);
                    Pagamento pagamento = em.find(Pagamento.class, pgtoId);
                    if(pagamento != null){// pagamento encontrado
                            session.setAttribute("mensagem", "Pagamento "+cpf+", "+codCurso+" encontrado!"); 
                            session.setAttribute("pagamento", pagamento);
                        }else{
                            session.setAttribute("mensagem", "Pagamento "+cpf+", "+codCurso+" não encontrado!"); 
                            session.setAttribute("pagamento", null);
                        } 
                        response.sendRedirect("pagamentos/resultado.jsp");
                    break;
                }
                case 33:{ // Cadastrar
                    cpfmascara = request.getParameter("cpf");
                    cpfmascara = cpfmascara.replaceAll("[.-]", "");
                    cpf = Long.parseLong(cpfmascara);
                    codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    datainscricao = request.getParameter("datainscricao");
                    String dataFormatada = datainscricao;
               try {
                   SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                   Date data;
                   data = formato.parse(datainscricao);
                   formato.applyPattern("dd/MM/yyyy");
                   dataFormatada = formato.format(data);
               } catch (ParseException ex) {
                   Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
               }    
               PagamentoPK pgtoId = new PagamentoPK(cpf, codCurso);  
               Pagamento pagamento = new Pagamento(pgtoId, dataFormatada);
               tx.begin();
               em.persist(pagamento);
               tx.commit();
               session.setAttribute("mensagem", "Pagamento "+cpf+", "+codCurso+" cadastrado!"); 
               session.setAttribute("pagamento", pagamento);                         
               response.sendRedirect("pagamentos/resultado.jsp");
                    break;
                }
                case 34:{ // Alterar
                    cpfmascara = request.getParameter("cpf");
                    cpfmascara = cpfmascara.replaceAll("[.-]", "");
                    cpf = Long.parseLong(cpfmascara);
                    codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    datainscricao = request.getParameter("datainscricao"); 
                    String dataFormatada = datainscricao;
               try {
                   SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                   Date data;
                   data = formato.parse(datainscricao);
                   formato.applyPattern("dd/MM/yyyy");
                   dataFormatada = formato.format(data);
               } catch (ParseException ex) {
                   Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
               }
                PagamentoPK pgtoId = new PagamentoPK(cpf, codCurso);
                Pagamento pagamento = em.find(Pagamento.class, pgtoId);
                if(pagamento != null){// pagamento encontrado
                    pagamento = new Pagamento(pgtoId, dataFormatada);                           
                    tx.begin();
                    em.merge(pagamento);
                    tx.commit();                            
                    session.setAttribute("mensagem", "Pagamento "+cpf+", "+codCurso+" Alterado!"); 
                    session.setAttribute("pagamento", pagamento);
                }else{
                    session.setAttribute("mensagem", "Pagamento "+cpf+", "+codCurso+" não encontrado!"); 
                    session.setAttribute("pagamento", null);
                }                }
                response.sendRedirect("pagamentos/resultado.jsp");
                break;
                case 35:{ // Excluir
                    cpfmascara = request.getParameter("cpf");
                    cpfmascara = cpfmascara.replaceAll("[.-]", "");
                    cpf = Long.parseLong(cpfmascara);
                    codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    PagamentoPK pgtoId = new PagamentoPK(cpf, codCurso);
                    Pagamento pagamento = em.find(Pagamento.class, pgtoId);
                    if(pagamento != null){// pagamento encontrado
                            tx.begin();
                            em.remove(pagamento);
                            tx.commit();
                            session.setAttribute("mensagem", "Pagamento "+cpf+", "+codCurso+" excluido!");                             
                        }else{
                            session.setAttribute("mensagem", "Pagamento "+cpf+", "+codCurso+" não encontrado!");
                        } 
                        session.setAttribute("pagamento", null);
                        response.sendRedirect("pagamentos/resultado.jsp");
                    break;
                }
           }
       }else if (idFormulario == 4){ //Cliente
           switch (tipoFormulario){
                    case 41:{ //Consultar todos
                        TypedQuery<Funcionario> query = em.createQuery("" + "Select c from Funcionario c", Funcionario.class);
                        List<Funcionario> funcionarios = query.getResultList();
                        session.setAttribute("mensagem", "Total de Funcionário(s): "+funcionarios.size() ); 
                        session.setAttribute("funcionarios", funcionarios); 
                        response.sendRedirect("View/funcionarios/consultaTodos.jsp");
                        break;
                    }
                    case 42:{ //Consultar Especifico
                        idFuncionario = Integer.parseInt(request.getParameter("idFuncionario"));
                        Funcionario funcionario = em.find(Funcionario.class, idFuncionario);  
                        
                        if(funcionario != null){// cliente encontrado
                            session.setAttribute("mensagem", "Funcionário "+idFuncionario+" encontrado!"); 
                            session.setAttribute("funcionario", funcionario);
                        }else{
                            session.setAttribute("mensagem", "Funcionário "+idFuncionario+" não encontrado!"); 
                            session.setAttribute("funcionario", null);
                        } 
                        response.sendRedirect("View/funcionarios/resultado.jsp");
                        break;
                    }
                    case 43:{ // Cadastrar
                        idFuncionario = Integer.parseInt(request.getParameter("idFuncionario"));
                        senhaFuncionario = request.getParameter("senha");
                        nomeFuncionario = request.getParameter("nome");
                        cpfFuncionario = request.getParameter("cpf");
                        chFuncionario = Integer.parseInt(request.getParameter("cargaHoraria"));
                        enderecoFuncionario = request.getParameter("endereco");
                        telFuncionario = request.getParameter("telefone");
                        Funcionario funcionario = new Funcionario(idFuncionario, nomeFuncionario, cpfFuncionario, enderecoFuncionario, chFuncionario, telFuncionario, senhaFuncionario, idFormulario);
                        try {
                            tx.begin();
                            em.persist(funcionario);
                            tx.commit();
                            session.setAttribute("mensagem", "Funcionário "+idFuncionario+" cadastrado!"); 
                            session.setAttribute("funcionario", funcionario);
                        } catch (Exception e) {
                            session.setAttribute("mensagem", "Código "+idFuncionario+" já está sendo usado! Por favor, tente novamente com outro"); 
                            session.setAttribute("funcionario", null);
                        }                                                 
                        response.sendRedirect("View/funcionarios/resultado.jsp");
                        break;
                    }
                    case 44:{ // Alterar
                        idFuncionario = Integer.parseInt(request.getParameter("idFuncionario"));
                        senhaFuncionario = request.getParameter("senha");
                        nomeFuncionario = request.getParameter("nome");
                        chFuncionario = Integer.parseInt(request.getParameter("cargaHoraria"));
                        enderecoFuncionario = request.getParameter("endereco");
                        telFuncionario = request.getParameter("telefone");
                        Funcionario funcionario = em.find(Funcionario.class, idFuncionario);  
                        
                        if(funcionario != null){// encontrado
                            cpfFuncionario = funcionario.getCpf();                            
                            funcionario = new Funcionario(idFuncionario, nomeFuncionario, cpfFuncionario, enderecoFuncionario, chFuncionario, telFuncionario, senhaFuncionario, idFormulario);
                            tx.begin();
                            em.merge(funcionario);
                            tx.commit();                            
                            session.setAttribute("mensagem", "Funcionário "+idFuncionario+" Alterado!"); 
                            session.setAttribute("funcionario", funcionario);
                        }else{
                            session.setAttribute("mensagem", "Funcionário "+idFuncionario+" não encontrado!"); 
                            session.setAttribute("funcionario", null);
                        } 
                        response.sendRedirect("View/funcionarios/resultado.jsp");
                        break;
                    }
                    case 45:{ // Excluir
                        idFuncionario = Integer.parseInt(request.getParameter("idFuncionario"));              
                        Funcionario funcionario = em.find(Funcionario.class, idFuncionario);
                        
                        if(funcionario != null){// cliente encontrado
                            tx.begin();
                            em.remove(funcionario);
                            tx.commit();
                            session.setAttribute("mensagem", "Funcionário "+idFuncionario+" excluido!");                             
                        }else{
                            session.setAttribute("mensagem", "Funcionário "+idFuncionario+" não encontrado!");
                        } 
                        session.setAttribute("funcionario", null);
                        response.sendRedirect("View/funcionarios/resultado.jsp");
                        break;
                    }
           }
           
       }
       
    }
    
}
