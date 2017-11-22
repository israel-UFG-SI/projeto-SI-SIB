/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
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
                    default:{
                        System.out.println("Opção escolhida não identificada, tente novamente");
                        break;
                    }
           }
           
       }else if(idFormulario == 2){ //Curso
           
           switch (tipoFormulario){
                case 21:{ //Consultar todos
                    TypedQuery<Curso> query = em.createQuery("" + "Select c from Curso c", Curso.class);
                    List<Curso> cursos = query.getResultList();
                    session.setAttribute("mensagem", "Total de Cursos(s): "+cursos.size() ); 
                    session.setAttribute("cursos", cursos); 
                    response.sendRedirect("cursos/consultaTodos.jsp");
                    break;
                }
                case 22:{ //Consultar Especifico
                    codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    Curso curso = em.find(Curso.class, codCurso);
                    if(curso != null){// curso encontrado
                            session.setAttribute("mensagem", "Curso "+codCurso+" encontrado!"); 
                            session.setAttribute("curso", curso);
                        }else{
                            session.setAttribute("mensagem", "Curso "+codCurso+" não encontrado!"); 
                            session.setAttribute("curso", null);
                        } 
                        response.sendRedirect("cursos/resultado.jsp");
                    break;
                }
                case 23:{ // Cadastrar
                    codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    nomeCurso = request.getParameter("nome");
                    valor = Integer.parseInt(request.getParameter("valor"));
                    site = request.getParameter("site"); 
                    Curso curso = new Curso(codCurso, nomeCurso, valor, site);
                    tx.begin();
                    em.persist(curso);
                    tx.commit();
                    session.setAttribute("mensagem", "Curso "+codCurso+" cadastrado!"); 
                    session.setAttribute("curso", curso);                         
                    response.sendRedirect("cursos/resultado.jsp");                        
                    break;
                }
                case 24:{ // Alterar
                    codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    nomeCurso = request.getParameter("nome");
                    valor = Integer.parseInt(request.getParameter("valor"));
                    site = request.getParameter("site");
                    Curso curso = em.find(Curso.class, codCurso);
                    if(curso != null){// curso encontrado
                            curso = new Curso(codCurso, nomeCurso, valor, site);                            
                            tx.begin();
                            em.merge(curso);
                            tx.commit();                            
                            session.setAttribute("mensagem", "Curso "+codCurso+" Alterado!"); 
                            session.setAttribute("curso", curso);
                        }else{
                            session.setAttribute("mensagem", "Curso "+codCurso+" não encontrado!"); 
                            session.setAttribute("curso", null);
                        } 
                        response.sendRedirect("cursos/resultado.jsp");
                    break;
                }
                case 25:{ // Excluir
                    
                codCurso = Integer.parseInt(request.getParameter("cdcurso"));
                    Curso curso = em.find(Curso.class, codCurso);
                     if(curso != null){// curso encontrado
                            tx.begin();
                            em.remove(curso);
                            tx.commit();
                            session.setAttribute("mensagem", "Curso "+codCurso+" excluido!");                             
                        }else{
                            session.setAttribute("mensagem", "Curso "+codCurso+" não encontrado!");
                        } 
                        session.setAttribute("curso", null);
                        response.sendRedirect("cursos/resultado.jsp");
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
       }
       
    }
    
}
