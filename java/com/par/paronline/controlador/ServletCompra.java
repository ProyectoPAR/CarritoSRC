/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.controlador;

import com.par.paronline.modelo.ListaProductos;
import com.par.paronline.modelo.Compra;
import com.par.paronline.modelo.ManagerDB;
import com.par.paronline.modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;


/**
 *
 * @author root
 */
public class ServletCompra extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession(true);
        Usuario user = (Usuario)session.getAttribute("user");
        try{
            /*la primera parte debe encargarse de verificar si el usuario esta logueado con su cuenta*/
            if(user.getNombre() == null){
                //si ya esta loqueado reenviar al formulario para completar los datos de la tarjeta de credito  
                dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
            }
            else{
                //si el usuario no esta loguado el dispatcher redirige al index para loguearse o registrerse
                dispatcher = request.getRequestDispatcher("index.html");
                response.getWriter().println("logueado");
            }
            /**************fin de la verificacion****************/
            //aqui procedemos a realizar la transaccion
            ListaProductos carrito = (ListaProductos)session.getAttribute("carrito");
            if (carrito.size() == 0) throw new Exception("El carrito esta vacio"); 
            ManagerDB man = new ManagerDB();
            String forma_pago = (String) request.getParameter("forma_pago");
            Date fecha_hoy = Calendar.getInstance().getTime();
            Compra compra = new Compra(user.getId_usuario(),fecha_hoy,forma_pago);//provicionalmente enviamos 2, es el unico cliente
            compra.setDireccion_envio((String)request.getParameter("direccion_envio"));
            compra.setMonto_total(carrito.getMonto_total());
            System.out.println("Direccion de envio de la compra"+compra.getDireccion_envio());
            if(request.getParameter("numero_tarjeta") != null) compra.setNumero_tarjeta(Integer.parseInt(request.getParameter("numero_tarjeta")));
            else compra.setNumero_tarjeta(0);
            int id_compra = man.insertar_compra(compra);//se inserta la compra desde el manager, ahora el detalle de la compra
            for(int i = 0 ; i < carrito.size() ; i ++){
                //por cada producto del carrito insertaremos el producto y la cantidad comprada en la tabla compra_detalle con un unico id de compra
                man.insertar_compra_detalle(id_compra, carrito.get(i).getId_producto(),carrito.get(i).getCantidad_compra());
            }
            man.cerrarConexion();
            carrito = new ListaProductos();
            session.setAttribute("carrito", carrito);
            dispatcher = request.getRequestDispatcher("Carrito.jsp");
            
        }
        catch(ServletException se){
            request.setAttribute("mensaje_error",se.getMessage());
            dispatcher = request.getRequestDispatcher("PagError.jsp");
        }
        catch(IOException ie){
            request.setAttribute("mensaje_error",ie.getMessage());
            dispatcher = request.getRequestDispatcher("PagError.jsp");
        }
        catch(SQLException sqe){
            request.setAttribute("mensaje_error",sqe.getMessage());
            dispatcher = request.getRequestDispatcher("PagError.jsp");
        }
        catch(NamingException ne){
            request.setAttribute("mensaje_error",ne.getMessage());
            dispatcher = request.getRequestDispatcher("PagError.jsp");
        }
        catch(Exception e){
            request.setAttribute("mensaje_error",e.getMessage());
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

        try {
            processRequest(request, response);
        } catch (Exception ex) {
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
        try{
        processRequest(request, response);
        }
        catch(Exception e){
            response.getWriter().println(e);
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
