/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author root
 */
package com.par.paronline.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Conexion {
    public Conexion(){
        
    }
    
    public static Connection getConexion()throws SQLException, ClassNotFoundException, NamingException, Exception{
        Connection conexion = null;
        InitialContext cxt = new InitialContext();
        if ( cxt == null ) {
            throw new Exception("Uh oh -- no context!");
        }

        DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

        if ( ds == null ) {
            throw new Exception("Data source not found!");
        }
        conexion = ds.getConnection();
        return conexion;
    }
    
    public static void closeConexion(Connection conexion)throws SQLException, ClassNotFoundException{
        conexion.close();
    }
     
}
