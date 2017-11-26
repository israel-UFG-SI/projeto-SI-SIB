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
@WebServlet(name = "ControladorFuncionario", urlPatterns = {"/ControladorFuncionario"})
public class ControladorFuncionario extends HttpServlet {

     
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
       
       //Variáveis Funcionarios       
       String nomeFuncionario, cpfFuncionario, senhaFuncionario, enderecoFuncionario, telFuncionario;
       int idFuncionario, chFuncionario;
    
        if (idFormulario == 4){ //Funcionário
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