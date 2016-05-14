/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        String sql = "select id_usuario,nombre,apellido,email from usuarios;";
        ArrayList<Usuario> usuarios = new ArrayList();
        db.consultar(sql);
        while(db.getResult().next()){
            Usuario u = new Usuario();
            u.setId_usuario(db.getResult().getInt("id_usuario"));
            u.setNombre(db.getResult().getString("nombre"));
            u.setApellido(db.getResult().getString("apellido"));
            u.setDireccion(db.getResult().getString("direccion"));
            u.setEmail(db.getResult().getString("email"));
            u.setRol(db.getResult().getString("rol"));
            
            usuarios.add(u);
        }
        db.cerrarConexion();
        return usuarios;
    }
    
    public static Usuario buscarUsuario(String email) throws ClassNotFoundException, SQLException, Exception{
        Usuario u = null;
        ManagerDB db = new ManagerDB();
        PreparedStatement stmt = db.getPrepareStatement("select*from usuarios where email =?");
        stmt.setString(1,email);
        db.iduquery(stmt);
        if(db.getResult().next()){
            u = new Usuario();
            u.setId_usuario(db.getResult().getInt("id_usuario"));
            u.setNombre(db.getResult().getString("nombre"));
            u.setApellido(db.getResult().getString("apellido"));
            u.setEmail(db.getResult().getString("email"));
            u.setDireccion(db.getResult().getString("direccion"));
            u.setContrasenha(db.getResult().getString("contrasenha"));
            
            return u;
        }
        return u;
    }
    
    public static void agregar(Usuario u){
        try{
        ManagerDB db = new ManagerDB();
        String sql = "insert into usuarios(nombre,apellido,contrasenha,direccion,email,rol,nombre_usuario) values (?,?,?,?,?,?,?)";
        db.getPrepareStatement(sql);
        PreparedStatement stmt = db.getPrepareStatement(sql);
        stmt.setString(1,u.getNombre());
        stmt.setString(2,u.getApellido());
        stmt.setString(3,u.getContrasenha());
        stmt.setString(4,u.getDireccion());
        stmt.setString(5,u.getEmail());
        stmt.setString(6,u.getRol());
        stmt.setString(7,u.getNombre_usuario());
        
        db.iduquery(stmt);
        db.cerrarConexion();
        }catch(Exception e){System.out.println("Error al guardar usuario:"+e);}
    }
    
    public static void actualizar (Usuario u) throws SQLException, Exception{
        ManagerDB db = new ManagerDB();
        PreparedStatement stmt = db.getPrepareStatement("update usuarios set"
                + "nombre = ?,apellido = ?,contrasenha = ? ,direccion = ? where email = ?");
        stmt.setString(1,u.getNombre());
        stmt.setString(2,u.getApellido());
        stmt.setString(3,u.getContrasenha());
        stmt.setString(4,u.getDireccion());
        db.iduquery(stmt);
        db.cerrarConexion();
    }
    
    public boolean existeUsuario(String email) {
        boolean existe = false;
        String sql = "select * from usuario where email = ?;";
        try{
        ManagerDB db = new ManagerDB();
        PreparedStatement stmt = db.getPrepareStatement(sql);
        stmt.setString(1,email);
        ResultSet rs = stmt.executeQuery(sql);
        if(rs.next()){
            existe = true;
            return existe;
        }
        }catch(Exception e){System.out.print("Error:"+e);}
        
        return existe;
    }
    
    public static void borrarUsuario(String email) throws SQLException, Exception{
        ManagerDB db = new ManagerDB();
        String sql = "delete from usuarios where email =?";
        PreparedStatement stmt = db.getPrepareStatement(sql);
        stmt.setString(1,email);
        stmt.executeUpdate();
        stmt.close();
        db.cerrarConexion();
    } 
    
    public static boolean verificarEmail(String email){
        String sql = "select email from usuarios where email = ?";
      
        try{
        ManagerDB db = new ManagerDB();
        PreparedStatement stmt = db.getPrepareStatement(sql);
        stmt.setString(1,email);
        db.iduquery(stmt);
        if(db.getResult().next()){
            return true;
        }
        }catch(Exception e){System.out.print("Error:"+e);}      
        
        return false;
    }
            
    
        
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
