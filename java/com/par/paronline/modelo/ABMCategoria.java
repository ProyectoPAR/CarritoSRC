/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.modelo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author root
 */
public class ABMCategoria {
    private PreparedStatement statement;
    private String query;
    ManagerDB man;
    
    public void alta(ArrayList args) throws SQLException, ClassNotFoundException, Exception{
        man = new ManagerDB();
        query = "insert into Categorias (descripcion) values (?)";
        statement = man.getPrepareStatement(query);
        statement.setString(1, (String) args.get(0));
        man.iduquery(statement);
        man.closeStatement(statement);
        man.cerrarConexion();
    }
    
    public void baja(Integer id_categoria) throws SQLException, ClassNotFoundException, Exception{
        man = new ManagerDB();
        man.consultar("select * from Productos where id_categoria = "+id_categoria);
        if (man.getResult().next()) throw new Exception("No se puede eliminar la categoria porque hay productos que dependen de el");
        query = "delete from Categorias where id_categoria = ?";
        statement = man.getPrepareStatement(query);
        statement.setInt(1, id_categoria);
        man.iduquery(statement);
        man.closeStatement(statement);
        man.cerrarConexion();
    }
    
    public void modificar(ArrayList args) throws SQLException, ClassNotFoundException, Exception{
        man = new ManagerDB();
        query = "update Categorias set descripcion = ? where id_categoria = ?";
        statement = man.getPrepareStatement(query);
        statement.setString(1, (String)args.get(0));
        statement.setString(2, (String)args.get(1));
        man.iduquery(statement);
        man.closeStatement(statement);
        man.cerrarConexion();
    }
}
