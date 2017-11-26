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
@WebServlet(name = "ControladorEmprestimo", urlPatterns = {"/ControladorEmprestimo"})
public class ControladorEmprestimo extends HttpServlet {

     
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
       int idCliente;
       
       //Variáveis Livro e Exemplares       
       int idLivro, idExemplar;
       
       
       //Váriáveis Empréstimo E Reserva
       int idEmprestimo, idReserva, temReserva=0, idMulta;
       String dataDevProgramada, dataEmprestimo, dataDevEfetiva, situacao, dataReserva = null , situacaoMulta , dataPagamento;
       float valorMulta;
       
        if (idFormulario == 5){ //Empréstimo
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
