/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*LISTA DE CAMBIOS:
    *CAMBIADO EL METODO DE BORRAR
    *NO FUNCIONA EL METODO DE ACTUALIZAR DA UN ERROR RELACIONADO CON
    LA SENTENCIA SQL,DEBE REVISARSE
*/
package com.par.paronline.modelo;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
/**
 *
 * @author User
 */
public class ABMCliente {

    public static ArrayList<Usuario> listar() throws SQLException, Exception{
        ManagerDB db = new ManagerDB();
        String sql = "select * from Usuarios;";
        ArrayList<Usuario> usuarios = new ArrayList();
        db.consultar(sql);
        while(db.getResult().next()){
            Usuario u = new Usuario();
            u.setId_usuario(db.getResult().getInt("id_usuario"));
            u.setNombre(db.getResult().getString("nombre"));
            u.setApellido(db.getResult().getString("apellido"));
            u.setNombre_usuario(db.getResult().getString("nombre_usuario"));
            u.setDireccion(db.getResult().getString("direccion"));
            u.setEmail(db.getResult().getString("email"));
            u.setRol(db.getResult().getString("rol"));
            u.setNombre_usuario(db.getResult().getString("nombre_usuario"));//AGREGADO EL NOMBRE DE USUARIO
            usuarios.add(u);
        }
        db.cerrarConexion();
        return usuarios;
    }
    
    public static Usuario buscarUsuario(int id_usuario) throws ClassNotFoundException, SQLException, Exception{
        //CAMBIADO EL METODO DE CONSULTA, LO QUE GENERABA UN ERROR A LA HORA DE 
        //USAR ESTE METODO, AHORA SE USA EL METODO CONSULTAR() DE 
        //MANAGERDB
        Usuario u = null;
        ManagerDB db = new ManagerDB();
        String query = "select * from usuarios where id_usuario = "+id_usuario;
        db.consultar(query);
        if(db.getResult().next()){
            u = new Usuario();
            u.setId_usuario(db.getResult().getInt("id_usuario"));
            u.setNombre(db.getResult().getString("nombre"));
            u.setApellido(db.getResult().getString("apellido"));
            u.setEmail(db.getResult().getString("email"));
            u.setDireccion(db.getResult().getString("direccion"));
            u.setContrasenha(db.getResult().getString("contrasenha"));
            u.setNombre_usuario(db.getResult().getString("nombre_usuario"));//AGREGADO EL NOMBRE DE USUARIO
        }
        db.cerrarConexion();
        return u;
    }
    
    public static Integer agregar(Usuario u) throws NamingException, Exception{
        ManagerDB db = new ManagerDB();
        PreparedStatement stmt = null;
        String sql = "insert into Usuarios(nombre,apellido,contrasenha,direccion,email,rol,nombre_usuario) values (?,?,?,?,?,?,?)";
        stmt = db.getPrepareStatement(sql, stmt.RETURN_GENERATED_KEYS);
        stmt.setString(1,u.getNombre());
        stmt.setString(2,u.getApellido());
        stmt.setString(3,u.getContrasenha());
        stmt.setString(4,u.getDireccion());
        stmt.setString(5,u.getEmail());
        stmt.setString(6,u.getRol());
        stmt.setString(7,u.getNombre_usuario());
        db.iduquery(stmt);
        stmt.getGeneratedKeys().next();
        Integer id_usuario = stmt.getGeneratedKeys().getInt("id_usuario");
        db.closeStatement(stmt);
        db.cerrarConexion();
        return id_usuario;
    }
    
    public static void actualizar (Usuario u) throws SQLException, Exception{
        ManagerDB db = new ManagerDB();
        String sql = "update usuarios set "
                + "nombre = ?, apellido = ?, contrasenha = ? , direccion = ?, email = ?, nombre_usuario = ? "
                + "where id_usuario = ?";
        PreparedStatement stmt = db.getPrepareStatement(sql);
        stmt.setString(1,u.getNombre());
        stmt.setString(2,u.getApellido());
        stmt.setString(3,u.getContrasenha());
        stmt.setString(4,u.getDireccion());
        stmt.setString(5, u.getEmail());
        stmt.setString(6, u.getNombre_usuario());
        stmt.setInt(7,u.getId_usuario());
        db.iduquery(stmt);
        db.closeStatement(stmt);
        db.cerrarConexion();
    }
    
    public boolean existeUsuario(String email) throws NamingException, Exception {
        boolean existe = false;
        String sql = "select * from usuario where email = ?;";
        ManagerDB db = new ManagerDB();
        PreparedStatement stmt = db.getPrepareStatement(sql);
        stmt.setString(1,email);
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            existe = true;
            return existe;
        }
        db.cerrarConexion();
        return existe;
    }
    
    public static void borrarUsuario(int id) throws SQLException, Exception{
        //ESTE METODO AHORA BUSCA MEDIANTE EL ID NUMERICO, ANTES LO HACIA POR EL
        //EMAIL
        ManagerDB db = new ManagerDB();
        db.consultar("select * from Compras where id_usuario = "+id);
        if(db.getResult().next()) throw new SQLException("No se puede eliminar usuario porque hay compras que dependen de el");
        String sql = "delete from usuarios where id_usuario =?";
        PreparedStatement stmt = db.getPrepareStatement(sql);
        stmt.setInt(1,id);
        stmt.executeUpdate();
        stmt.close();
        db.cerrarConexion();
    } 
    
    public static boolean verificarEmail(String email) throws NamingException, Exception{
        String sql = "select * from usuarios where email = ?";
        ManagerDB db = new ManagerDB();
        ArrayList<String> args = new ArrayList();
        args.add(email);
        db.consultar(sql, args);
        Boolean existe = db.getResult().next();
        db.cerrarConexion();
        return existe;
    }
    
    public static boolean verificarNombreUsuario(String nombre_usuario) throws NamingException, Exception{
        String sql = "select * from usuarios where nombre_usuario = ?";
        ManagerDB db = new ManagerDB();
        ArrayList<String> args = new ArrayList();
        args.add(nombre_usuario);
        db.consultar(sql, args);
        Boolean existe = db.getResult().next();//el result.next() trae un valor booleano true si hay resultado y false en caso contrario
        db.cerrarConexion();
        return existe;
    }
            
    
    //deberia comprobarse por nombre de usuario    
    public static Usuario comprobarLogin(String email, String contrasenha) throws ClassNotFoundException, NamingException, Exception{
        ManagerDB db = new ManagerDB();
        Usuario u = null;
        ArrayList<String> args = new ArrayList();
        args.add(email);
        args.add(contrasenha);
           
        db.consultar("select * from usuarios where email = ? and contrasenha = ?", args);

        if(db.getResult().next()){
            u = new Usuario();
            u.setId_usuario(db.getResult().getInt("id_usuario"));
            u.setNombre(db.getResult().getString("nombre"));
            u.setApellido(db.getResult().getString("apellido"));
            u.setEmail(db.getResult().getString("email"));
            u.setRol(db.getResult().getString("rol"));
            return u;
        }
        return u;
    }
        
           
}
