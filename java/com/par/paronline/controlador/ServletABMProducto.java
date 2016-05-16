/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.par.paronline.modelo.ABMProducto;
import com.par.paronline.modelo.ListaCategorias;
import javax.servlet.http.HttpSession;
import com.par.paronline.modelo.ListaProductos;
import com.par.paronline.modelo.Producto;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
/**
 *
 * @author root
 */
public class ServletABMProducto extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        
            String accion = request.getParameter("accion");
            String lastpage = request.getParameter("lastpage");
            if(accion != null){
            //falta castear el precio a double
                RequestDispatcher dispatcher =  request.getRequestDispatcher("ABMProducto.jsp");
                HttpSession session = request.getSession(true);
                ListaProductos productos = (ListaProductos)session.getAttribute("lista_productos");
                ABMProducto abm = new ABMProducto();
                ArrayList args = new ArrayList();
                ListaCategorias categorias = new ListaCategorias();
                categorias.getListaCategorias();
                
                if(accion.equals("add")){
                    
                    String descripcion = request.getParameter("descripcion");
                    double precio = Double.parseDouble(request.getParameter("precio"));
                    String categoria = request.getParameter("categoria");
                    String imagen = "/ruta/imagen";
                    args.add(descripcion);
                    args.add(imagen);
                    args.add(precio);
                    args.add(categorias.buscarDescripcion(categoria).getId_categoria());
                    
                    abm.alta(args);
                }
                if(accion.equals("update")){
                    dispatcher = request.getRequestDispatcher("ModificarProducto.jsp");
                }
                if(accion.equals("delete")){
                    int id = Integer.parseInt(request.getParameter("id_producto"));
                    abm.baja(id);
                }
                if(lastpage != null && lastpage.equals("MP")){
                    String id_producto = request.getParameter("id_producto");
                    String descripcion = request.getParameter("descripcion");
                    double precio = Double.parseDouble(request.getParameter("precio"));
                    String categoria = request.getParameter("categoria");
                    String imagen = "/ruta/imagen";
                    args.add(descripcion);
                    args.add(imagen);
                    args.add(precio);
                    args.add(categorias.buscarDescripcion(categoria).getId_categoria());
                    args.add(Integer.parseInt(id_producto));
                    abm.modificar(args);
                    dispatcher = request.getRequestDispatcher("ABMProducto.jsp");
                }
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
        } catch (SQLException ex) {
            Logger.getLogger(ServletABMProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletABMProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServletABMProducto.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(ServletABMProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletABMProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServletABMProducto.class.getName()).log(Level.SEVERE, null, ex);
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
