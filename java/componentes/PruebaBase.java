/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentes;

import com.par.paronline.modelo.ABMProducto;
import com.par.paronline.modelo.ListaCategorias;
import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.sql.rowset.CachedRowSet;
import com.par.paronline.utils.Conexion;
import com.par.paronline.modelo.Producto;


/**
 *
 * @author root
 */
public class PruebaBase {
    String descripcion_prod = "";
    String precio = "";
    String producto = "";
    String categoria = "";
    String id_producto = "";
    String url = "";
    String user = "postgres";
    String pass = "sate150495";
    ArrayList<Producto> productos = new ArrayList<Producto>();
    public void f() throws SQLException, ClassNotFoundException, Exception{
            String cadena = "hola";
            Integer nro = 5;
            Double nro_double = 5.5;
            System.out.println(cadena.getClass().getName());
            System.out.println(nro.getClass().getName());
            System.out.println(nro_double.getClass().getName());
    }
    public static void main(String args[]) throws SQLException, ClassNotFoundException, Exception{
        PruebaBase p = new PruebaBase();
        p.f();
    }
    
}
