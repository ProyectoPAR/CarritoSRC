/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.par.paronline.modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import com.par.paronline.modelo.Categoria;
import com.par.paronline.modelo.ManagerDB;

/**
 *
 * @author root
 */
public class ListaCategorias {
    private ArrayList<Categoria> categorias;
    
    public ListaCategorias(){
        this.categorias = new ArrayList<Categoria>();
    }
    
    public void addCategoria(Categoria c){
        this.categorias.add(c);
    }
    
    public void removeCategoria(Integer id_categoria){
        Categoria p = buscarId(id_categoria);
        this.categorias.remove(this.buscarId(id_categoria));
    }
    
    public void removeCategoria(Categoria c){
        this.categorias.remove(c);
    }
    
    public Categoria buscarId(Integer id_categoria){
        for (int i = 0 ; i < this.categorias.size() ; i ++)
            if (this.categorias.get(i).getId_categoria() == id_categoria)
                return this.categorias.get(i);
        return null;//retorna null si no hay ningun producto con ese id
    }
    
    public Categoria buscarDescripcion(String descripcion){
        for (int i = 0 ; i < this.categorias.size() ; i ++)
            if (this.categorias.get(i).getDescripcion().equals(descripcion))
                return this.categorias.get(i);
        return null;//retorna null si no hay ningun producto con ese id
    }
    
    public Boolean existeCategoria(Categoria p){
        if(this.buscarId(p.getId_categoria()) == null) return false;
        else return true;
    }
    
    public Categoria get(int i){
        return this.categorias.get(i);
    }
    
    public int size(){
        return this.categorias.size();
    }
    
    public void getListaCategorias() throws SQLException, ClassNotFoundException, Exception{
        ManagerDB man = new ManagerDB();
        String query = "select id_categoria, descripcion from Categorias";
        man.consultar(query);
            
        while(man.getResult().next()){
            Integer id_categoria = man.getResult().getInt("id_categoria");
            String descripcion = man.getResult().getString("descripcion");
            this.addCategoria(new Categoria(id_categoria,descripcion));
        }
        man.cerrarConexion();
    }
}
