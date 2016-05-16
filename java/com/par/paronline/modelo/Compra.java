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
import javax.naming.NamingException;

public class Compra {
    private static int nro_instancia = 0;
    private Integer nro_compra;
    private Integer id_compra;
    private Integer id_usuario;
    private Double monto_total;
    private Date fecha;
    private String forma_pago;
    private ListaProductos productos;
    
    public Compra(Integer id_usuario, Date fecha, String forma_pago){
        this.id_usuario = id_usuario;
        this.monto_total = 0.0;
        this.fecha = fecha;
        this.forma_pago = forma_pago;
        this.nro_compra = nro_instancia + 1;
        Compra.nro_instancia += 1;
    }
    
    public Compra(Integer id_compra, Integer id_usuario, Date fecha, String forma_pago){
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

    public Date getFecha(){
        return this.fecha;
    }

    public void setFecha(Date fecha){
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
        return productos;
    }

    public void setProductos(ListaProductos productos) {
        this.productos = productos;
    }
    
    /**
     *Esta funcion trae todos los productos de una compra
     */
    public static ListaProductos getComprasDetalle(Integer id_compra) throws NamingException, Exception{
        ArrayList args = new ArrayList();
        ListaProductos productos = new ListaProductos();
        args.add(id_compra);
        //este select trae el id, descripcion, precio, categoria y cantidad comprada de un producto en una compra realizada
        String query = "select p.id_producto idp, p.descripcion desp, p.precio_unitario precio, c.descripcion cat, cd.cantidad can"
                + "  from Compras_detalle cd, Productos p, Categorias c where cd.id_producto = p.id_producto "
                + "and c.id_categoria = p.id_categoria and id_compra = ?";
        ManagerDB man = new ManagerDB();
        man.consultar(query, args);//hacemos la consulta
        while(man.getResult().next()){
            //cargamos cada producto en la lista
            Producto p = new Producto(man.getResult().getInt("idp"), man.getResult().getString("cat"), man.getResult().getString("desp"),man.getResult().getDouble("precio"));
            p.setCantidad_compra(man.getResult().getInt("can"));
            productos.addProducto(p);
        }
        man.cerrarConexion();//cerramos la conexion y retornamos la lista de productos
        return productos;
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
            Compra compra = new Compra(man.getResult().getInt("id_compra"),man.getResult().getInt("id_usuario"), man.getResult().getDate("fecha"),man.getResult().getString("forma_pago"));
            compra.setMonto_total(man.getResult().getDouble("monto_total"));
            compra.setProductos(Compra.getComprasDetalle(compra.getId_compra()));//cargamos los productos de la compra, haciendo uso de una funcion definida en la propia clase
            compras.add(compra);
        }
        man.cerrarConexion();//cerramos la conexion y retornamos la lista de compras de un usuario
        return compras;
    }
    
}
