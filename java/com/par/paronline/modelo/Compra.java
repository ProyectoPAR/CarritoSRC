/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.modelo;

/**
 *
 * @author root
 */

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.naming.NamingException;

public class Compra {
    
    private Integer nro_compra, id_compra, id_usuario, numero_tarjeta;
    private Double monto_total;
    private Calendar fecha;
    private String forma_pago, direccion_envio;
    private ListaProductos productos;
    
    public Compra(Integer id_usuario, Calendar fecha, String forma_pago){
        this.id_usuario = id_usuario;
        this.monto_total = 0.0;
        this.fecha = fecha;
        this.forma_pago = forma_pago;
        
    }
    
    public Compra(Integer id_compra, Integer id_usuario, Calendar fecha, String forma_pago){
        this.id_compra = id_compra;
        this.id_usuario = id_usuario;
        this.monto_total = 0.0;
        this.fecha = fecha;
        this.forma_pago = forma_pago;
    }

    public Integer getId_compra() {
        return this.id_compra;
    }

    public void setId_compra(Integer id_compra) {
        this.id_compra = id_compra;
    }

    public Integer getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Double getMonto_total() {
        return this.monto_total;
    }

    public void setMonto_total(Double monto_total) {
        this.monto_total = monto_total;
    }

    public Calendar getFecha(){
        return this.fecha;
    }
    
    public String getFechaString(){
        return Integer.toString(this.fecha.get(Calendar.DAY_OF_MONTH))+"/"+Integer.toString(this.fecha.get(Calendar.MONTH)+1)+"/"+Integer.toString(this.fecha.get(Calendar.YEAR));
    }

    public void setFecha(Calendar fecha){
        this.fecha = fecha;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public Integer getNro_compra() {
        return nro_compra;
    }

    public void setNro_compra(Integer nro_compra) {
        this.nro_compra = nro_compra;
    }

    public ListaProductos getProductos() {
        return this.productos;
    }

    public void setProductos(ListaProductos productos) {
        this.productos = productos;
    }

    public String getDireccion_envio() {
        return this.direccion_envio;
    }

    public void setDireccion_envio(String direccion_envio){
        this.direccion_envio = direccion_envio;
    }

    public Integer getNumero_tarjeta() {
        return numero_tarjeta;
    }

    public void setNumero_tarjeta(Integer numero_tarjeta) {
        this.numero_tarjeta = numero_tarjeta;
    }
    
    
    
    /**
     *Esta funcion trae todos los productos de una compra
     */
    public static ListaProductos getComprasDetalle(Integer id_compra) throws NamingException, Exception{
        ArrayList args = new ArrayList();
        ListaProductos productos_comprados = new ListaProductos();
        ListaProductos productos = new ListaProductos();
        productos.getListaProductos("all", "");
        args.add(id_compra);
        //este select trae todos los detalles en una compra realizada
        String query = "select * from Compras_detalle where id_compra = ?";
        ManagerDB man = new ManagerDB();
        man.consultar(query, args);//hacemos la consulta
        while(man.getResult().next()){
            //cargamos cada producto buscando por su id en la lista de producto, el id lo obtenemos en cada fila de compra detalle
            Producto p = productos.buscarId(man.getResult().getInt("id_producto"));
            p.setCantidad_compra(man.getResult().getInt("cantidad"));
            //System.out.println("Producto: "+p);
            productos_comprados.addProducto(p);
        }
        man.cerrarConexion();//cerramos la conexion y retornamos la lista de productos
        return productos_comprados;
    }
    
    public static ArrayList<Compra> getComprasCliente(Integer id_usuario) throws NamingException, Exception{
        ArrayList args = new ArrayList();
        ArrayList<Compra> compras = new ArrayList<Compra>();//la lista de compras de un solo usuario
        args.add(id_usuario);
        ManagerDB man = new ManagerDB();
        String query1 = "select * from Compras where id_usuario = ?";//realizamos la consulta
        man.consultar(query1, args);
        while(man.getResult().next()){
            //cargamos una por una las compras realizadas por el usuario
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(man.getResult().getDate("fecha"));
            Compra compra = new Compra(man.getResult().getInt("id_compra"),man.getResult().getInt("id_usuario"), fecha,man.getResult().getString("forma_pago"));
            compra.setMonto_total(man.getResult().getDouble("monto_total"));
            compra.setDireccion_envio(man.getResult().getString("direccion_envio"));
            compra.setNumero_tarjeta(man.getResult().getInt("nro_tarjeta"));
            //System.out.println("Compra: "+compra);
            compra.setProductos(Compra.getComprasDetalle(compra.getId_compra()));//cargamos los productos de la compra, haciendo uso de una funcion definida en la propia clase
            compras.add(compra);
        }
        man.cerrarConexion();//cerramos la conexion y retornamos la lista de compras de un usuario
        return compras;
    }
    
    public String toString(){
        return Integer.toString(this.getId_compra()) + " " + Integer.toString(this.getId_usuario()) + " "+ Double.toString(this.getMonto_total());
    }
    
}
