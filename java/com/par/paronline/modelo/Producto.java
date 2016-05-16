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
public class Producto implements java.io.Serializable {
    private String categoria, descripcion;
    private Integer cantidad_compra, id_producto;
    private Double precio;

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    
    public Producto(){
        //Constructor sin argumentos
    }

    public Producto(Integer id,String cat, String des, Double valor){
        this.id_producto = id;
        this.categoria = cat;
        this.descripcion = des;
        this.precio = valor;
    }

    public Integer getCantidad_compra() {
        return cantidad_compra;
    }

    public void setCantidad_compra(Integer cantidad_compra) {
        this.cantidad_compra = cantidad_compra;
    }
    
    
    public String toString(){
        return this.categoria + this.descripcion + this.precio;
    }
}