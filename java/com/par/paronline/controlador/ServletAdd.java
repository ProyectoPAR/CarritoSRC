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
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import com.par.paronline.modelo.Producto;
import com.par.paronline.modelo.ListaProductos;
import javax.servlet.RequestDispatcher;

/**
 *Servlet para agregar producto a carrito
 * @author root
 */
public class ServletAdd extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * El ServletAdd se encarga de agregar o quitar un producto del carrito.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = null;//creamos el dispatcher previamente null
        try{
            //el requesta dispatcher debe redirigir a la pagina de donde vino
            String lastpage = request.getParameter("lastpage");//el parametro lastpage es usado para saber si vino desde Busqueda.jsp y Producto.jsp
            if(lastpage.equals("busqueda")) dispatcher = request.getRequestDispatcher("Busqueda.jsp");
            else dispatcher = request.getRequestDispatcher("Producto.jsp");
            HttpSession session = request.getSession(true);//se recupera la session
            ListaProductos productos = (ListaProductos)session.getAttribute("lista_productos");//se recupera la lista de productos cargados
            Integer id_producto = Integer.parseInt(request.getParameter("id_producto"));//se obtiene el parametro id_producto
            Integer cantidad = Integer.parseInt(request.getParameter("cantidad"));//se recupera la cantidad seteada por el cliente
            ListaProductos carrito = (ListaProductos)session.getAttribute("carrito");//obtenemos el carrito de la session
            String addpop = (String) request.getParameter("agregar-sacar");//la acccion, que decide si el producto se quita o se agrega al carrito
            Producto p = productos.buscarId(id_producto);
            if(addpop.equals("Agregar")){//si la accion es agregar
                if(!carrito.existeProducto(p)){//si no existe el producto en el carrito lo agregara
                    p.setCantidad_compra(cantidad);//agregamos la cantidad
                    carrito.addProducto(p);//agregamos al carrito el producto, que por ahora es solo un string
                }
            }
            else{//si la accion no es agreagar, deberia ser quitar
                p.setCantidad_compra(0);//seteamos de nuevo la cantidad de compra a 0
                carrito.removeProducto(p.getId_producto());//sacamos el producto
            }
            if(dispatcher != null) dispatcher.forward(request, response);
        }
        catch (Exception e){
            request.setAttribute("mensaje_error", e.getMessage());
            dispatcher = request.getRequestDispatcher("PagError.jsp");
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
