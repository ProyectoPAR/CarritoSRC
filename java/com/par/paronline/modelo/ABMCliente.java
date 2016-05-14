/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.modelo;

import com.par.paronline.utils.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.NamingException;
/**
 *
 * @author User
 */

public class ABMCliente {

    public static ArrayList<Usuario> listar() throws SQLException, Exception{
        String sql = "select id_usuario,nombre,apellido,email, nombre_usuario from usuarios;";
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        Connection conn = Conexion.getConexion();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
            Usuario u = new Usuario();
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setEmail(rs.getString("email"));
            u.setNombre_usuario(rs.getString("nombre_usuario"));
            usuarios.add(u);
        }
        stmt.close();
        Conexion.closeConexion(conn);
        return usuarios;
    }
    //el metodo buscar usuario debe ser buscado mediante otro parametro por ejemplo el nombre de usuario
    public static Usuario buscarUsuario(String id_usuario) throws ClassNotFoundException, SQLException, Exception{
        Usuario u = null;
        Connection conn = Conexion.getConexion();
        PreparedStatement stmt = conn.prepareStatement("select * from usuarios where id_usuario = ?");
        stmt.setString(1,id_usuario);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            u = new Usuario();
            u.setId_usuario(rs.getInt("id_usuario"));
            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setEmail(rs.getString("email"));
            u.setDireccion(rs.getString("direccion"));
            u.setContrasenha(rs.getString("contrasenha"));
            
            return u;
        }
        return u;
    }
    
    public static void agregar(Usuario u){
        //la funcion no se encarga de agregar el id_usuario ya que este es una sequencia en la base de datos
        try{
            Connection conn = Conexion.getConexion();
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement("insert into Usuarios (nombre, apellido, contrasenha, direccion, email, rol, nombre_usuario)"
                    + " values (?,?,?,?,?,?,?)");
            stmt.setString(1,u.getNombre());
            stmt.setString(2,u.getApellido());
            stmt.setString(3,u.getContrasenha());
            stmt.setString(4,u.getDireccion());
            stmt.setString(5,u.getEmail());
            stmt.setString(6,"U");
            stmt.setString(7, u.getNombre_usuario());//aqui seteamos el nombre de usuario
            stmt.executeUpdate();
            stmt.close();
            Conexion.closeConexion(conn);
        }catch(Exception e){System.out.println("Error al guardar usuario:"+e);}
    }
    //no hace falta que la funcion reciba el mail ni el id.
    public static void actualizar (Usuario u, int id, String mail) throws SQLException, Exception{
        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(true);
        PreparedStatement stmt = conn.prepareStatement("update usuarios set"
                + "nombre = ?,apellido = ?,contrasenha = ? ,direccion = ? ,email = ? where id_usuario = ?");
        stmt.setString(1,u.getNombre());
        stmt.setString(2,u.getApellido());
        stmt.setString(3,u.getContrasenha());
        stmt.setString(4,u.getDireccion());
        stmt.setString(5,u.getEmail());
        stmt.setInt(6,u.getId_usuario());
        
        
        stmt.executeUpdate();
        stmt.close();
        Conexion.closeConexion(conn);
    }
    
    public boolean existeUsuario(String id_usuario) {
        boolean existe = false;
        String sql = "select * from usuario where id_usuario ="+id_usuario+";";
        try{
        Connection conn = Conexion.getConexion();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            existe = true;
            return existe;
        }
        }catch(Exception e){System.out.print("Error:"+e);}
        
        return existe;
    }
    
    public static void borrarUsuario(String id_usuario) throws SQLException, Exception{
        Connection conn = Conexion.getConexion();
        conn.setAutoCommit(true);
        PreparedStatement stmt = conn.prepareStatement("delete from usuarios where id_usuario =?");
        stmt.setString(1,id_usuario);
        stmt.executeUpdate();
        stmt.close();
        Conexion.closeConexion(conn);
    } 
    
    public static String verificarEmail(String email){
        String existe = null;
        try{
        Connection conn = Conexion.getConexion();
        PreparedStatement stmt = conn.prepareStatement("select email from usuarios where email = ?");
        stmt.setString(1,email);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            existe = "La direccion de correo electronico ya se encuentra en uso!";
            return existe;
        } else return "";
        }catch(Exception e){System.out.print("Error:"+e);}
        
        return existe;        
    
    }
    //debe verificar el nombre de usuario
    public static String verificarId(String id_usuario){
        String existe = null;
        try{
        Connection conn = Conexion.getConexion();
        PreparedStatement stmt = conn.prepareStatement("select id_usuario from usuarios where id_usuario=?");
        stmt.setString(1,id_usuario);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            existe = "El nombre de usuario elegido ya se encuentra en uso!";
            return existe;
        } else return "";
        }catch(Exception e){System.out.print("Error:"+e);}
        
        return existe;        
    
    }
    //debe comprobar el nombre de usuario y contrasenha     
    public static boolean comprobarCredenciales(String id_usuario, String contrasenha) throws ClassNotFoundException, NamingException, Exception{
            Connection conn = Conexion.getConexion();
            conn.setAutoCommit(true);
            PreparedStatement stmt = conn.prepareStatement("select id_usuario, contrasenha from usuarios where id_usuario = ? and contrasenha = ?");
            stmt.setString(1,id_usuario);
            stmt.setString(2,contrasenha);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return true;
            }
            
            return false;
    }
        
           
}
