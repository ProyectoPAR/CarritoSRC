/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.controlador;

import com.par.paronline.modelo.ABMCategoria;
import com.par.paronline.modelo.ListaCategorias;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author root
 * Esta clase se encarga de procesar los parametros para las altas, bajas y modificaciones de las categorias
 * asi como redireccionar nuevamente al ABMCategoria.jsp o ModificarCategoria.jsp
 */
public class ServletABMCategoria extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher =  null;//creamos un dispatcher
        try {
            String accion = request.getParameter("accion");//recuperamos la accion realizada por el usuario
            String lastpage = request.getParameter("lastpage");//tambien la ultima pagina accedida, puede ser ABMCategoria.jsp o ModificarCategoria.jsp
            if(accion != null){
                dispatcher =  request.getRequestDispatcher("ABMCategoria.jsp");
                HttpSession session = request.getSession(true);
                ListaCategorias categorias = (ListaCategorias)session.getAttribute("lista_categorias");
                ABMCategoria abm = new ABMCategoria();
                ArrayList args = new ArrayList();
                if(accion.equals("add")){
                    String descripcion = request.getParameter("descripcion");
                    args.add(descripcion);
                    abm.alta(args);
                }
                if(accion.equals("update")){
                    dispatcher = request.getRequestDispatcher("ModificarCategoria.jsp");
                }
                if(accion.equals("delete")){
                    int id = Integer.parseInt(request.getParameter("id_categoria"));
                    abm.baja(id);
                }
                if(lastpage != null && lastpage.equals("MP")){
                    String descripcion = request.getParameter("descripcion");
                    String id_categoria = request.getParameter("id_categoria");
                    args.add(descripcion);
                    args.add(Integer.parseInt(id_categoria));
                    abm.modificar(args);
                    dispatcher = request.getRequestDispatcher("ABMCategoria.jsp");
                }
                
            }
            else{
                throw new ServletException("No se encuentra la pagina solicitada");
            }
        }
        catch(Exception e){
            request.setAttribute("mensaje_error", e.getMessage());
            dispatcher = request.getRequestDispatcher("PagError.jsp");
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
        try{
            processRequest(request, response);
        }
        catch(ServletException se){
            request.setAttribute("mensaje_error", se.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("PagError.jsp");
            dispatcher.forward(request, response);
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
        try{
            processRequest(request, response);
        }
        catch(ServletException se){
            request.setAttribute("mensaje_error", se.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("PagError.jsp");
            dispatcher.forward(request, response);
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
