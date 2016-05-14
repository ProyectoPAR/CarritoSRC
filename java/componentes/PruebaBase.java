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
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Tienda", "postgres", "sate150495");
            String query = "insert into Compras (id_usuario, monto_total, fecha, forma_pago, nro_factura) values (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,2);
            statement.setDouble(2, 0.0);
            java.util.Date fecha = new Date();
            statement.setDate(3, new java.sql.Date(fecha.getYear(), fecha.getMonth(), fecha.getDay()));
            statement.setString(4, "efectivo");
            statement.setInt(5, 2);
            statement.executeUpdate();
            statement.close();
            conn.close();
    }
    public static void main(String args[]) throws SQLException, ClassNotFoundException, Exception{
        PruebaBase p = new PruebaBase();
        p.f();
    }
    
}
