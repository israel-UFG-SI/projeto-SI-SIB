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
@WebServlet(name = "ControladorCliente", urlPatterns = {"/ControladorCliente"})
public class ControladorCliente extends HttpServlet {

     
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
        }
    }
}