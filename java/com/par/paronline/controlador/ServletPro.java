/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.par.paronline.modelo.ManagerDB;
import com.par.paronline.modelo.ListaProductos;
import com.par.paronline.modelo.Producto;
import com.par.paronline.utils.*;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author root
 */
public class ServletPro extends HttpServlet {
    String descripcion_prod = "";
    String precio = "";
    String id_producto = "";
    String categoria = "";
    String query = "";
    ListaProductos productos = null;
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
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        ListaProductos productos = null;//lista de productos null previamente
        HttpSession session = request.getSession(true);//se recupera la session
        RequestDispatcher dispatcher = null;//el dispatcher null
        try{
            productos = new ListaProductos();//se inicializa la lista de productos
            String descripcion = "";
            //verificamos, primero si la categoria es null, si es asi seleccionaremos todas la categorias, si la descripcion es null no se hace nada
            if(request.getParameter("categoria") == null && request.getParameter("descripcion") == null){
                categoria = "all";
                descripcion = "";
                dispatcher = request.getRequestDispatcher("Producto.jsp");
            }
            else{//si ninguno de los dos es null entonces se recuperan del request
                categoria = request.getParameter("categoria");
                descripcion = '%'+request.getParameter("descripcion")+'%';
                dispatcher = request.getRequestDispatcher("Busqueda.jsp");//tambien despacharemos a la pagina de busqueda, dado que se ha elegido una categoria y una descripcion
            }
            productos.getListaProductos(categoria, descripcion);//obtenemos la lista de productos con esa categoria y esa descripcion
            session.setAttribute("lista_productos", productos);//esa lista de productos se anhade a la session
            if(dispatcher != null){
                dispatcher.forward(request, response);//despachamos
            }
            
        }
        catch(Exception e){
            session.setAttribute("excepcion", e);
            response.getWriter().println(e);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
