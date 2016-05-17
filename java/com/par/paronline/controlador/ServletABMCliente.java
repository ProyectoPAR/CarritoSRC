/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.par.paronline.modelo.Usuario;
import com.par.paronline.modelo.ABMCliente;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author User
 */
@WebServlet(name = "ServletABMCliente", urlPatterns = {"/ServletABMCliente"})
public class ServletABMCliente extends HttpServlet {

    public void actualizar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception{
        
        Usuario u = new Usuario();
        u.setNombre(request.getParameter("nombre"));
        u.setApellido(request.getParameter("apellido"));
        u.setId_usuario(Integer.parseInt(request.getParameter("id_usuario")));
        u.setDireccion(request.getParameter("direccion"));
        u.setEmail(request.getParameter("email"));
        u.setContrasenha(request.getParameter("contrasenha"));
        u.setNombre_usuario(request.getParameter("nombre_usuario"));
        ABMCliente.actualizar(u);
        request.getSession(true).setAttribute("user", u);
        
    }
   
    public void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception{
        //todos los id deben ser casteados a Integer o int
        ABMCliente.borrarUsuario(Integer.parseInt(request.getParameter("id_usuario")));
        
    }
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = null;
        try{
            String accion = request.getParameter("accion");
            request.setAttribute("accion",accion);
            rd = request.getRequestDispatcher("ABMCliente.jsp");
            if("eliminar".equals(accion)){
                eliminar(request,response);
            }

            if("editar".equals(accion)){

                rd = request.getRequestDispatcher("EditCliente.jsp");
                
            }

            if("grabarCambios".equals(accion)){
                if(request.getParameter("lastpage").equals("perfil")) rd = request.getRequestDispatcher("Perfil.jsp");
                actualizar(request,response);
            }
        }
        catch(SQLException sqle){
            request.setAttribute("mensaje_error", sqle.getMessage());
            rd = request.getRequestDispatcher("PagError.jsp");
        }
        catch(Exception e){
            request.setAttribute("mensaje_error", e.getMessage());
            rd = request.getRequestDispatcher("PagError.jsp");
        }
        finally{
            rd.forward(request, response);
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
            Logger.getLogger(ServletABMCliente.class.getName()).log(Level.SEVERE, null, ex);
            response.getWriter().println(ex.getMessage());
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
            Logger.getLogger(ServletABMCliente.class.getName()).log(Level.SEVERE, null, ex);
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
