/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
       
       //Variáveis Livro e Exemplares
       String tituloLivro, isbnLivro, autorLivro, editoraLivro, edicaoLivro, secaoLivro, DisponivelExemplar;
       int idLivro, qtdLivro, anoLivro , idExemplar;
       
       //Variáveis Funcionarios       
       String nomeFuncionario, cpfFuncionario, senhaFuncionario, enderecoFuncionario, telFuncionario;
       int idFuncionario, chFuncionario;
       
       //Váriáveis Empréstimo E Reserva
       int idEmprestimo, idReserva, temReserva=0, idMulta;
       String dataDevProgramada, dataEmprestimo, dataDevEfetiva, situacao, dataReserva = null , situacaoMulta , dataPagamento;
       float valorMulta;
       
    
       
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
           }
           
       }else if(idFormulario == 2){ //LIVRO
           
              switch (tipoFormulario){
                    case 21:{ //Consultar todos
                        TypedQuery<Livro> query = em.createQuery("" + "Select c from Livro c", Livro.class);
                        List<Livro> livros = query.getResultList();
                        session.setAttribute("mensagem", "Total de Livro(s): "+livros.size() ); 
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
       }else if (idFormulario == 3){ //Exemplares
           switch (tipoFormulario){
                    case 31:{ //Consultar todos
                        TypedQuery<Exemplar> query = em.createQuery("" + "Select c from Exemplar c", Exemplar.class);
                        List<Exemplar> exemplares = query.getResultList();
                        session.setAttribute("mensagem", "Total de Exemplar(es): "+exemplares.size() ); 
                        session.setAttribute("exemplares", exemplares); 
                        response.sendRedirect("View/livros/exemplares/consultaTodos.jsp");
                        break;
                    }
                    case 32:{ //Consultar Especifico
                       idExemplar = Integer.parseInt(request.getParameter("idExemplar"));
                        Exemplar exemplar = em.find(Exemplar.class, idExemplar);  
                        
                        if(exemplar != null){// Exemplar encontrado
                            session.setAttribute("mensagem", "Exemplar "+idExemplar+" encontrado!"); 
                            session.setAttribute("exemplar", exemplar);
                        }else{
                            session.setAttribute("mensagem", "Exemplar "+idExemplar+" não encontrado!"); 
                            session.setAttribute("exemplar", null);
                        } 
                        response.sendRedirect("View/livros/exemplares/resultado.jsp");
                        break;
                    }
                    case 33:{ // Cadastrar
                        idLivro = Integer.parseInt(request.getParameter("idLivro"));
                        idExemplar = Integer.parseInt(request.getParameter("idExemplar"));
                        
                        Livro livro = em.find(Livro.class, idLivro);  
                        
                        if(livro != null){// livro encontrado
                            qtdLivro = livro.getQuantidade();
                            qtdLivro++;
                            livro.setQuantidade(qtdLivro);
                            DisponivelExemplar = "Sim";
                            Exemplar exemplar = new Exemplar(idExemplar, livro, DisponivelExemplar);
                        try {
                            tx.begin();
                            em.persist(exemplar);
                            em.merge(livro);
                            tx.commit();
                            session.setAttribute("mensagem", "Exemplar "+idExemplar+" cadastrado!"); 
                            session.setAttribute("exemplar", exemplar);
                        } catch (Exception e) {
                            session.setAttribute("mensagem", "Código "+idExemplar+" já está sendo usado! Por favor, tente novamente com outro"); 
                            session.setAttribute("exemplar", null);
                        }
                        }else{
                            session.setAttribute("mensagem", "Código do Livro "+idLivro+" não encontrado! Verifique se está correto e tente novamente"); 
                            session.setAttribute("exemplar", null);
                        }        
                        response.sendRedirect("View/livros/exemplares/resultado.jsp");
                        break;
                        }
                    case 34:{ // Excluir
                       idExemplar = Integer.parseInt(request.getParameter("idExemplar"));
                        Exemplar exemplar = em.find(Exemplar.class, idExemplar); 
                        
                        if(exemplar != null){// cliente encontrado
                            Livro livro = exemplar.getLivro();
                            qtdLivro=livro.getQuantidade();
                            qtdLivro--;
                            Livro livro2 = new Livro(livro.getIdlivro(), livro.getTitulo(), livro.getIsbn(), livro.getEditora(), livro.getEdição(), livro.getAno(), livro.getAutor(), livro.getSecao(), qtdLivro);
                            tx.begin();
                            em.merge(livro2);
                            em.remove(exemplar);
                            tx.commit();
                            session.setAttribute("mensagem", "Exemplar "+idExemplar+" excluido!");                             
                        }else{
                            session.setAttribute("mensagem", "Exemplar "+idExemplar+" não encontrado!");
                        } 
                        session.setAttribute("exemplar", null);
                        response.sendRedirect("View/livros/exemplares/resultado.jsp");
                        break;
                        }
                    }
           }else if (idFormulario == 4){ //Funcionário
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
           
       }else if (idFormulario == 5){ //Empréstimo
                switch (tipoFormulario){
                    case 51:{ //Consultar todos
                        TypedQuery<Emprestimo> query = em.createQuery("" + "Select c from Emprestimo c", Emprestimo.class);
                        List<Emprestimo> emprestimos = query.getResultList();
                        session.setAttribute("mensagem", "Total de Empréstimo(s): "+emprestimos.size() ); 
                        session.setAttribute("emprestimos", emprestimos); 
                        response.sendRedirect("View/emprestimos/emprestimos2/ConsultaTodos/consultaTodos.jsp");
                        break;
                    }
                    case 56:{ //Consultar todos Aberto
                        TypedQuery<Emprestimo> query = em.createQuery("" + "Select c from Emprestimo c", Emprestimo.class);
                        List<Emprestimo> preEmprestimos = query.getResultList();
                        List<Emprestimo> emprestimos = new ArrayList();                       
                        if(preEmprestimos != null){                          
                            for (Emprestimo aux: preEmprestimos){
                                if(aux.getSituação().equals("Aberto")){
                                        emprestimos.add(aux);
                                    }                                
                                }                            
                            session.setAttribute("mensagem", "Total de Empréstimo(s) em Aberto: "+emprestimos.size() ); 
                            session.setAttribute("emprestimos", emprestimos);
                        }else{
                            session.setAttribute("mensagem", "Nenhum Emprestimo em aberto localizado!"); 
                            session.setAttribute("exemplares", null);
                        }
                        response.sendRedirect("View/emprestimos/emprestimos2/ConsultaTodos/consultaTodos.jsp");
                        break;
                    }
                    case 57:{ //Consultar todos Aberto
                        TypedQuery<Emprestimo> query = em.createQuery("" + "Select c from Emprestimo c", Emprestimo.class);
                        List<Emprestimo> preEmprestimos = query.getResultList();
                        List<Emprestimo> emprestimos = new ArrayList();                       
                        if(preEmprestimos != null){                          
                            for (Emprestimo aux: preEmprestimos){
                                if(aux.getSituação().equals("Encerrado")){
                                        emprestimos.add(aux);
                                    }                                
                                }                            
                            session.setAttribute("mensagem", "Total de Empréstimo(s) Encerrado(s): "+emprestimos.size() ); 
                            session.setAttribute("emprestimos", emprestimos);
                        }else{
                            session.setAttribute("mensagem", "Nenhum Emprestimo em encerrado localizado!"); 
                            session.setAttribute("exemplares", null);
                        }
                        response.sendRedirect("View/emprestimos/emprestimos2/ConsultaTodos/consultaTodos.jsp");
                        break;
                    }
                    case 52:{ //Consultar Especifico
                        idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));                    
                        Emprestimo emprestimo = em.find(Emprestimo.class, idEmprestimo);  
                        
                        if(emprestimo != null){// Empréstimo encontrado
                            session.setAttribute("mensagem", "Empréstimo "+idEmprestimo+" encontrado!"); 
                            session.setAttribute("emprestimo", emprestimo);
                        }else{
                            session.setAttribute("mensagem", "Empréstimo "+idEmprestimo+" não encontrado!"); 
                            session.setAttribute("emprestimo", null);
                        } 
                        response.sendRedirect("View/emprestimos/emprestimos2/resultado.jsp");
                        break;
                    }
                    case 53:{ // Cadastrar
                        idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));
                        idExemplar = Integer.parseInt(request.getParameter("idExemplar"));
                        idCliente = Integer.parseInt(request.getParameter("idCliente"));
                        dataEmprestimo = request.getParameter("DataEmprestimo");
                        dataDevProgramada = request.getParameter("dataDev");
                        situacao = "Aberto";                       
                        
                        Exemplar exemplar = em.find(Exemplar.class, idExemplar); 
                       if( exemplar != null){ // Exemplar Existe
                           if (exemplar.getDisponivel().equals("Sim")){
                                Cliente cliente = em.find(Cliente.class, idCliente);
                                if (cliente != null){ // Cliente Existe
                                    if(cliente.getPendencia().equals("Não")){
                                        Emprestimo emprestimo = new Emprestimo(idEmprestimo, dataEmprestimo, dataDevProgramada, situacao, exemplar, cliente);
                                        exemplar.setDisponivel("Não");
                                        try {
                                            tx.begin();
                                            em.persist(emprestimo);
                                            em.merge(exemplar);
                                            tx.commit();
                                            session.setAttribute("mensagem", "Empréstimo "+idExemplar+" Aberto!"); 
                                            session.setAttribute("emprestimo", emprestimo);
                                        } catch (Exception e) {
                                            session.setAttribute("mensagem", "Código "+idEmprestimo+" já está sendo usado! Por favor, tente novamente com outro"); 
                                            session.setAttribute("emprestimo", null);
                                        }
                                    }else{
                                        session.setAttribute("mensagem", "Empréstimo cancelado! Código do Cliente "+idCliente+" possui pendência! Por favor verifique na seção de multas"); 
                                        session.setAttribute("emprestimo", null);
                                    }                                    
                                }else{
                                    session.setAttribute("mensagem", "Código do Cliente "+idCliente+" não encontrado! Verifique se está correto e tente novamente"); 
                                    session.setAttribute("emprestimo", null);
                                }
                            }else{
                                session.setAttribute("mensagem", "Código do Exemplar "+idExemplar+" não está disponivel para empréstimo! Verifique a disponibildiade e tente novamente ou realize uma reserva."); 
                                session.setAttribute("emprestimo", null);
                           }                                                    
                        }else{
                            session.setAttribute("mensagem", "Código do Exemplar "+idExemplar+" não encontrado! Verifique se está correto e tente novamente"); 
                            session.setAttribute("emprestimo", null);
                        }                         
                        response.sendRedirect("View/emprestimos/emprestimos2/resultado.jsp");
                        break;
                    }
                    case 54:{ // Alterar
                        idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));
                        dataDevProgramada = request.getParameter("dataDev"); 
                        temReserva = -1;
                        Emprestimo emprestimo = em.find(Emprestimo.class, idEmprestimo);  
                        if(emprestimo != null){// encontrado
                            //IF VERIFICAÇÃO DA DATA VAI AQUI *********************************
                            TypedQuery<Reserva> query = em.createQuery("" + "Select c from Reserva c", Reserva.class);
                            List<Reserva> reservas = query.getResultList();
                            for (Reserva aux: reservas){
                                if (aux.getExemplar().getIdexemplar().equals(emprestimo.getExemplar().getIdexemplar())){
                                    temReserva = 1;
                                }
                            }
                            if (temReserva==-1){
                                emprestimo.setDataDevProg(dataDevProgramada);
                                tx.begin();
                                em.merge(emprestimo);
                                tx.commit();                            
                                session.setAttribute("mensagem", "Emprestimo "+idEmprestimo+" Renovado!"); 
                                session.setAttribute("emprestimo", emprestimo);
                            }else{
                                session.setAttribute("mensagem", "Não é possível renovar o empréstimo "+idEmprestimo+", pois há uma reserva em aberta para o exemplar!"); 
                                session.setAttribute("emprestimo", null);
                            }
                            
                        }else{
                            session.setAttribute("mensagem", "Emprestimo "+idEmprestimo+" não encontrado!"); 
                            session.setAttribute("emprestimo", null);
                        } 
                        response.sendRedirect("View/emprestimos/emprestimos2/resultado.jsp");
                        break;
                    }
                    case 55:{ // Excluir
                        idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));
                        dataDevEfetiva = request.getParameter("dataEfetiva");
                        Emprestimo emprestimo = em.find(Emprestimo.class, idEmprestimo);
                        if(emprestimo != null){// Empréstimo encontrado
                            if (emprestimo.getSituação().equals("Aberto")){
                                //VERIFICAÇÃO DA DATA VAI AQUI ***************************************
                                /* SITUAÇÃO: BLOQUEADO*/
                                emprestimo.setDataDevEfetiva(dataDevEfetiva);
                                emprestimo.setSituação("Encerrado");
                                Exemplar exemplar = emprestimo.getExemplar();
                                exemplar.setDisponivel("Sim");
                                tx.begin();
                                em.merge(exemplar);
                                em.merge(emprestimo);
                                tx.commit();
                                session.setAttribute("mensagem", "Emprestimo "+idEmprestimo+" encerrado!");  
                            }else{
                                session.setAttribute("mensagem", "Operação abortada! Emprestimo "+idEmprestimo+" não está mais aberto!");  
                            }
                        }else{
                            session.setAttribute("mensagem", "Emprestimo "+idEmprestimo+" não encontrado!");
                        } 
                        session.setAttribute("emprestimo", null);
                        response.sendRedirect("View/emprestimos/emprestimos2/resultado.jsp");
                        break;
                    }
           }
           
       } else if (idFormulario == 6){ //Multa
           switch (tipoFormulario){
                    case 61:{ //Consultar todos
                        TypedQuery<Multa> query = em.createQuery("" + "Select c from Multa c", Multa.class);
                        List<Multa> multas = query.getResultList();
                        session.setAttribute("mensagem", "Total de Multa(s): "+multas.size() ); 
                        session.setAttribute("multas", multas); 
                        response.sendRedirect("View/emprestimos/multas/ConsultaTodos/consultaTodos.jsp");
                        break;
                    }
                    case 66:{ //Consultar todos Aberto
                        TypedQuery<Multa> query = em.createQuery("" + "Select c from Multa c", Multa.class);
                        List<Multa> preMultas = query.getResultList();
                        List<Multa> multas = new ArrayList();                       
                        if(preMultas != null){                          
                            for (Multa aux: preMultas){
                                if(aux.getSituacao().equals("Aberta")){
                                        multas.add(aux);
                                    }                                
                                }                            
                            session.setAttribute("mensagem", "Total de Multa(s) em Aberto: "+multas.size() ); 
                            session.setAttribute("multas", multas);
                        }else{
                            session.setAttribute("mensagem", "Nenhuma multa em aberto localizado!"); 
                            session.setAttribute("multa", null);
                        }
                        response.sendRedirect("View/emprestimos/multas/ConsultaTodos/consultaTodos.jsp");
                        break;
                    }
                    case 67:{ //Consultar todos Pagas
                        TypedQuery<Multa> query = em.createQuery("" + "Select c from Multa c", Multa.class);
                        List<Multa> preMultas = query.getResultList();
                        List<Multa> multas = new ArrayList();                       
                        if(preMultas != null){                          
                            for (Multa aux: preMultas){
                                if(aux.getSituacao().equals("Paga")){
                                        multas.add(aux);
                                    }                                
                                }                            
                            session.setAttribute("mensagem", "Total de Multa(s) Paga(s): "+multas.size() ); 
                            session.setAttribute("multas", multas);
                        }else{
                            session.setAttribute("mensagem", "Nenhuma multa paga localizada!"); 
                            session.setAttribute("multa", null);
                        }
                        response.sendRedirect("View/emprestimos/multas/ConsultaTodos/consultaTodos.jsp");
                        break;
                    }
                    case 62:{ //Consultar Especifico
                        idCliente = Integer.parseInt(request.getParameter("idUsuario"));
                        TypedQuery<Multa> query = em.createQuery("" + "Select c from Multa c", Multa.class);
                        List<Multa> preMultas = query.getResultList();
                        List<Multa> multas = new ArrayList();                       
                        if(preMultas != null){                          
                            for (Multa aux: preMultas){
                                if(aux.getUsuario().getIdusuario().equals(idCliente)){
                                        multas.add(aux);
                                    }                                
                                }                            
                            session.setAttribute("mensagem", "Total de Multa(s) do Cliente: "+multas.size() ); 
                            session.setAttribute("multas", multas);
                        }else{
                            session.setAttribute("mensagem", "Nenhuma multa do Cliente "+idCliente+" localizada!"); 
                            session.setAttribute("multa", null);
                        }                       
                        response.sendRedirect("View/emprestimos/multas/consulta/consultaTodos.jsp");
                        break;
                    }
                    case 68:{ //Consultar Especifico
                        idMulta = Integer.parseInt(request.getParameter("idEmprestimo"));                    
                        Multa multa = em.find(Multa.class, idMulta);  
                        
                        if(multa != null){// Multa encontrado
                            session.setAttribute("mensagem", "Multa "+idMulta+" encontrada!"); 
                            session.setAttribute("multa", multa);
                        }else{
                            session.setAttribute("mensagem", "Multa "+idMulta+" não encontrada!"); 
                            session.setAttribute("multa", null);
                        } 
                        response.sendRedirect("View/emprestimos/multas/consulta/resultado.jsp");
                        break;
                    }
                    case 63:{ // Cadastrar
                        /*idEmprestimo = Integer.parseInt(request.getParameter("idEmprestimo"));
                        idExemplar = Integer.parseInt(request.getParameter("idExemplar"));
                        idCliente = Integer.parseInt(request.getParameter("idCliente"));
                        dataEmprestimo = request.getParameter("DataEmprestimo");
                        dataDevProgramada = request.getParameter("dataDev");
                        situacao = "Aberto";                       
                        
                        Exemplar exemplar = em.find(Exemplar.class, idExemplar); 
                       if( exemplar != null){ // Exemplar Existe
                           if (exemplar.getDisponivel().equals("Sim")){
                                Cliente cliente = em.find(Cliente.class, idCliente);
                                if (cliente != null){ // Cliente Existe
                                    if(cliente.getPendencia().equals("Não")){
                                        Emprestimo emprestimo = new Emprestimo(idEmprestimo, dataEmprestimo, dataDevProgramada, situacao, exemplar, cliente);
                                        exemplar.setDisponivel("Não");
                                        try {
                                            tx.begin();
                                            em.persist(emprestimo);
                                            em.merge(exemplar);
                                            tx.commit();
                                            session.setAttribute("mensagem", "Empréstimo "+idExemplar+" Aberto!"); 
                                            session.setAttribute("emprestimo", emprestimo);
                                        } catch (Exception e) {
                                            session.setAttribute("mensagem", "Código "+idEmprestimo+" já está sendo usado! Por favor, tente novamente com outro"); 
                                            session.setAttribute("emprestimo", null);
                                        }
                                    }else{
                                        session.setAttribute("mensagem", "Empréstimo cancelado! Código do Cliente "+idCliente+" possui pendência! Por favor verifique na seção de multas"); 
                                        session.setAttribute("emprestimo", null);
                                    }                                    
                                }else{
                                    session.setAttribute("mensagem", "Código do Cliente "+idCliente+" não encontrado! Verifique se está correto e tente novamente"); 
                                    session.setAttribute("emprestimo", null);
                                }
                            }else{
                                session.setAttribute("mensagem", "Código do Exemplar "+idExemplar+" não está disponivel para empréstimo! Verifique a disponibildiade e tente novamente ou realize uma reserva."); 
                                session.setAttribute("emprestimo", null);
                           }                                                    
                        }else{
                            session.setAttribute("mensagem", "Código do Exemplar "+idExemplar+" não encontrado! Verifique se está correto e tente novamente"); 
                            session.setAttribute("emprestimo", null);
                        }                         
                        response.sendRedirect("View/emprestimos/emprestimos2/resultado.jsp");
                        break;*/
                    }                    
                    case 65:{ // Excluir
                        idMulta = Integer.parseInt(request.getParameter("idEmprestimo"));
                        dataPagamento = request.getParameter("dataPagamento");
                        Multa multa = em.find(Multa.class, idMulta);
                        if(multa != null){// Multa encontrado
                            if (multa.getSituacao().equals("Aberta")){ 
                                Cliente cliente = multa.getUsuario();
                                cliente.setPendencia("Não");
                                multa.setDataPagamento(dataPagamento);
                                multa.setSituacao("Paga");
                                tx.begin();
                                em.merge(multa);
                                em.merge(cliente);
                                tx.commit();
                                session.setAttribute("mensagem", "Baixa dada para Multa "+idMulta+"!");  
                            }else{
                                session.setAttribute("mensagem", "Operação abortada! Multa "+idMulta+" já está paga!");  
                            }
                        }else{
                            session.setAttribute("mensagem", "Multa "+idMulta+" não encontrada!");
                        } 
                        session.setAttribute("multa", null);
                        response.sendRedirect("View/emprestimos/multas/resultado.jsp");
                        break;
                    }
                }
            }else if (idFormulario == 7){ //RESERVA
                switch (tipoFormulario){
                    case 71:{ //Consultar todos
                        TypedQuery<Reserva> query = em.createQuery("" + "Select c from Reserva c", Reserva.class);
                        List<Reserva> reservas = query.getResultList();
                        session.setAttribute("mensagem", "Total de Reserva(s): "+reservas.size() ); 
                        session.setAttribute("reservas", reservas); 
                        response.sendRedirect("View/emprestimos/reservas/consultaTodos.jsp");
                        break;
                    }
                    case 72:{ //Consultar Especifico
                        idReserva = Integer.parseInt(request.getParameter("idReserva"));
                        Reserva reserva = em.find(Reserva.class, idReserva);  
                        
                        if(reserva != null){// reserva encontrada
                            session.setAttribute("mensagem", "Reserva "+idReserva+" encontrada!"); 
                            session.setAttribute("reserva", reserva);
                        }else{
                            session.setAttribute("mensagem", "Reserva "+idReserva+" não encontrada!"); 
                            session.setAttribute("reserva", null);
                        } 
                        response.sendRedirect("View/emprestimos/reservas/resultado.jsp");
                        break;
                    }
                    case 73:{ // Cadastrar
                        idReserva = Integer.parseInt(request.getParameter("idReserva"));
                        idExemplar = Integer.parseInt(request.getParameter("idExemplar"));
                        idCliente = Integer.parseInt(request.getParameter("idCliente"));
                        
                        Exemplar exemplar = em.find(Exemplar.class, idExemplar); 
                       if( exemplar != null){ // Exemplar Existe
                           if (exemplar.getDisponivel().equals("Não")){
                                Cliente cliente = em.find(Cliente.class, idCliente);
                                if (cliente != null){ // Cliente Existe
                                    if(cliente.getPendencia().equals("Não")){
                                        TypedQuery<Emprestimo> query = em.createQuery("" + "Select c from Emprestimo c", Emprestimo.class);
                                        List<Emprestimo> emprestimos = query.getResultList();
                                        for (Emprestimo aux: emprestimos){
                                            if (aux.getExemplar().getIdexemplar().equals(exemplar.getIdexemplar())){
                                            dataReserva= aux.getDataDevProg();                                            
                                            }
                                        }  
                                        Reserva reserva = new Reserva(idReserva, dataReserva, cliente, exemplar);
                                        try {
                                            tx.begin();
                                            em.persist(reserva);
                                            tx.commit();
                                                session.setAttribute("mensagem", "Reserva "+idReserva+" Aberta!"); 
                                            session.setAttribute("reserva", reserva);
                                        } catch (Exception e) {
                                            session.setAttribute("mensagem", "Código "+idReserva+" já está sendo usado! Por favor, tente novamente com outro"); 
                                            session.setAttribute("reserva", null);
                                        }
                                    }else{
                                        session.setAttribute("mensagem", "Reserva cancelada! Código do Cliente "+idCliente+" possui pendência! Por favor verifique na seção de multas"); 
                                        session.setAttribute("reserva", null);
                                    }                                    
                                }else{
                                    session.setAttribute("mensagem", "Código do Cliente "+idCliente+" não encontrado! Verifique se está correto e tente novamente"); 
                                    session.setAttribute("reserva", null);
                                }
                            }else{
                                session.setAttribute("mensagem", "Código do Exemplar "+idExemplar+" está disponível não é preciso realizar reserva"); 
                                session.setAttribute("reserva", null);
                           }                                                    
                        }else{
                            session.setAttribute("mensagem", "Código do Exemplar "+idExemplar+" não encontrado! Verifique se está correto e tente novamente"); 
                            session.setAttribute("reserva", null);
                        }
                                                                         
                        response.sendRedirect("View/emprestimos/reservas/resultado.jsp");
                        break;
                    }                    
                    case 75:{ // Excluir
                        idReserva = Integer.parseInt(request.getParameter("idReserva"));
                        Reserva reserva = em.find(Reserva.class, idReserva);
                        
                        if(reserva != null){// reserva encontrado
                            tx.begin();
                            em.remove(reserva);
                            tx.commit();
                            session.setAttribute("mensagem", "Reserva "+idReserva+" encerrada!");                             
                        }else{
                            session.setAttribute("mensagem", "Reserva "+idReserva+" não encontrada!");
                        } 
                        session.setAttribute("reserva", null);
                        response.sendRedirect("View/emprestimos/reservas/resultado.jsp");
                        break;
                    }
           }
           
       }else if(idFormulario == 8){ //DISPONIBILIDADE
           
              switch (tipoFormulario){
                    case 82:{ //Consultar todos
                        TypedQuery<Exemplar> query = em.createQuery("" + "Select c from Exemplar c", Exemplar.class);
                        List<Exemplar> exemplares = query.getResultList();
                        List<Exemplar> exemplaresSelecionados = new ArrayList();
                        idLivro = Integer.parseInt(request.getParameter("idLivro"));
                        Livro livro = em.find(Livro.class, idLivro);  
                        
                        if(livro != null){// livro encontrado                           
                            for (Exemplar aux: exemplares){
                                if(aux.getDisponivel().equals("Sim")){
                                    livro = aux.getLivro();
                                    if(livro.getIdlivro()==idLivro){
                                        exemplaresSelecionados.add(aux);
                                    }                                
                                }
                            }
                            session.setAttribute("mensagem", "Total de Exemplar(es) Disponíveis: "+exemplaresSelecionados.size() ); 
                            session.setAttribute("exemplaresSelecionados", exemplaresSelecionados);
                        }else{
                            session.setAttribute("mensagem", "Livro "+idLivro+" não encontrado!"); 
                            session.setAttribute("exemplaresSelecionados", null);
                        }
                        response.sendRedirect("View/emprestimos/disponibilidade/consultaTodos.jsp");
                        break;
                    }       
                }
            }
        }
}
