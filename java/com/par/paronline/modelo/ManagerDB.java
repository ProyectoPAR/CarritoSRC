/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.modelo;

/**
 *La clase ProductManager se encarga de traer la lista de productos de la base de datos, buscar productos en la lista de productos
 * y realizar altas, bajas y modificaciones en la base de datos.
 * Metodos a implementar
 *  -getListaProductos()
 *  -buscarProducto() por descripcion
 *  -buscarProducto() por id
 *  -alta() agregar un producto en la base de datos
 *  -baja() eliminar un producto de la base de datos
 *  -modificar() modificar un producto en la base datos
 * @author fabricio
 */
import java.util.ArrayList;
import com.par.paronline.modelo.ListaProductos;
import com.par.paronline.modelo.Producto;
import com.par.paronline.utils.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;

//verificar si es necesario cerrar la conexion en cada metodo
public class ManagerDB {
    
    private ArrayList<Producto> productos = new ArrayList<Producto>();
    private Connection conexion = null;
    private PreparedStatement statement = null;
    private ResultSet result = null;
    
    public ManagerDB() throws ClassNotFoundException, NamingException, Exception{
        //constructor
        this.conexion = Conexion.getConexion();
    }
    
    
    
    public void consultar(String query) throws SQLException, ClassNotFoundException{
        this.statement = this.getPrepareStatement(query);
        this.result = this.statement.executeQuery();
        
    }
    
    public void consultar(String query, ArrayList args) throws SQLException, ClassNotFoundException{
        
        this.statement = this.getPrepareStatement(query);
        for(int i = 0 ; i < args.size() ; i ++){
            if(args.get(i).getClass().getName().equals("java.lang.String"))
                this.statement.setString(i + 1, (String)args.get(i));
            else if(args.get(i).getClass().getName().equals("java.lang.Integer"))
                this.statement.setInt(i + 1, (Integer)args.get(i));
            else if(args.get(i).getClass().getName().equals("java.lang.Double"))
                this.statement.setDouble(i + 1, (Double)args.get(i));
        }
        System.out.println(query);
        this.result = this.statement.executeQuery();
        
    }
    
    public ResultSet getResult(){
        return this.result;
    }
    
    public PreparedStatement getPrepareStatement(String query)throws SQLException, ClassNotFoundException{
        this.statement = this.conexion.prepareStatement(query);
        return this.statement;
    }
    
    public PreparedStatement getPrepareStatement(String query, int return_generated_keys)throws SQLException, ClassNotFoundException{
        this.statement = this.conexion.prepareStatement(query, return_generated_keys);
        return this.statement;
    }
    
    public void closeStatement(PreparedStatement statement) throws SQLException{
        statement.close();
    }
    
    public void iduquery (PreparedStatement sentencia)throws SQLException, ClassNotFoundException{
        sentencia.executeUpdate();
    }
    //esta funcion se encarga de insertar la cabecera de una compra desde el mismo manager, por lo que usa objetos de la misma instancia
    public int insertar_compra(Compra compra) throws SQLException, ClassNotFoundException{
        int id_compra;
        String query= "insert into Compras (id_usuario, monto_total,fecha, forma_pago, nro_factura) values (?, ?, ?, ?, ?)";
        this.statement = this.getPrepareStatement(query, this.statement.RETURN_GENERATED_KEYS);
        this.statement.setInt(1, compra.getId_usuario());
        this.statement.setDouble(2, compra.getMonto_total());
        this.statement.setDate(3, new java.sql.Date(compra.getFecha().getYear(), compra.getFecha().getMonth(), compra.getFecha().getDay()));
        this.statement.setString(4, compra.getForma_pago());
        this.statement.setInt(5, compra.getNro_compra());
        this.statement.executeUpdate();
        this.statement.getGeneratedKeys().next();
        id_compra = Integer.parseInt(this.statement.getGeneratedKeys().getString("id_compra"));
        return id_compra;
    }
    
    public void insertar_compra_detalle(int id_compra, int id_producto, int cantidad) throws SQLException, ClassNotFoundException{
        String query = "insert into Compras_detalle (id_compra, id_producto, cantidad) values (?, ?, ?)";
        this.statement = this.getPrepareStatement(query);
        this.statement.setInt(1,id_compra);
        this.statement.setInt(2, id_producto);
        this.statement.setInt(3, cantidad);
        this.iduquery(this.statement);
    }
    
    public int getId_compra(ArrayList args) throws SQLException, ClassNotFoundException{
        String query = "select id_compra from Compras where nro_factura = "+Integer.toString((int)args.get(0));
        this.consultar(query);
        return this.getResult().getInt("id_compra");
    }
    
    public void cerrarConexion() throws SQLException, ClassNotFoundException{
        if (this.statement != null )this.statement.close();
        if (this.result != null )this.result.close();
        Conexion.closeConexion(this.conexion);
    }
}
