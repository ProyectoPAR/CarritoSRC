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
public class Categoria {
    private String id_categoria, descripcion;

    public String getId_categoria() {
        return this.id_categoria;
    }

    public void setId_categoria(String id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria(String id_categoria, String descripcion) {
        this.id_categoria = id_categoria;
        this.descripcion = descripcion;
    }

    public Categoria() {
        
    }
    
    
    
}
