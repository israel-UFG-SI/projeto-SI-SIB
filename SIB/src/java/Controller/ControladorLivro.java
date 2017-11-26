/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ControladorLivro", urlPatterns = {"/ControladorLivro"})
public class ControladorLivro extends HttpServlet {
     
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
       
             
       //Variáveis Livro e Exemplares
       String tituloLivro, isbnLivro, autorLivro, editoraLivro, edicaoLivro, secaoLivro, DisponivelExemplar;
       int idLivro, qtdLivro, anoLivro , idExemplar;
       
       
        if(idFormulario == 2){ //LIVRO
           
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
            }
        }
    }