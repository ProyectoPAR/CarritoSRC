/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.controlador;

import com.par.paronline.modelo.Usuario;
import com.par.paronline.modelo.ABMCliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(name = "ServletReg", urlPatterns = {"/Reg"})
public class ServletReg extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = null;
        try {
            
            if(!ABMCliente.verificarEmail(request.getParameter("email")) &&//deberia se existeEmail.
            !ABMCliente.verificarNombreUsuario(request.getParameter("nombre_usuario"))){//deberia ser existeNombreUsuario.
                //si es que no existe el mail y el user procedera al registro
                //pendiente la pagina de error que mostrara si no pasa la validacion
                
                Usuario u = new Usuario();
                u.setNombre(request.getParameter("nombre"));
                u.setApellido(request.getParameter("apellido"));
                u.setDireccion(request.getParameter("direccion"));
                u.setNombre_usuario(request.getParameter("nombre_usuario"));//obtenemos del jsp el nombre usuario y se setea al objeto u
                u.setEmail(request.getParameter("email"));
                u.setRol("U");
                u.setContrasenha(request.getParameter("contrasenha"));
                Integer id_usuario = ABMCliente.agregar(u);
                u.setId_usuario(id_usuario);
                request.getSession().setAttribute("user", u); 
            }
            dispatcher = request.getRequestDispatcher("index.jsp");
        }
        catch(Exception e){
            dispatcher = request.getRequestDispatcher("PagError.jsp");
            request.setAttribute("mensaje_error", e.getMessage());
        }
        finally{
            dispatcher.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletReg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ServletReg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
