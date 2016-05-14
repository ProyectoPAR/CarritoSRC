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

import java.util.Date;

public class Compra {
    private static int nro_instancia = 0;
    private int nro_compra;
    private int id_compra;
    private int id_usuario;
    private Double monto_total;
    private Date fecha;
    private String forma_pago;
    
    public Compra(int id_usuario, Date fecha, String forma_pago){
        this.id_usuario = id_usuario;
        this.monto_total = 0.0;
        this.fecha = fecha;
        this.forma_pago = forma_pago;
        this.nro_compra = nro_instancia + 1;
        Compra.nro_instancia += 1;
    }
    
    public Compra(int id_compra, int id_usuario, Date fecha, String forma_pago){
        this.id_compra = id_compra;
        this.id_usuario = id_usuario;
        this.monto_total = 0.0;
        this.fecha = fecha;
        this.forma_pago = forma_pago;
    }

    public int getId_compra() {
        return this.id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public int getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Double getMonto_total() {
        return this.monto_total;
    }

    public void setMonto_total(Double monto_total) {
        this.monto_total = monto_total;
    }

    public Date getFecha(){
        return this.fecha;
    }

    public void setFecha(Date fecha){
        this.fecha = fecha;
    }

    public String getForma_pago() {
        return forma_pago;
    }

    public void setForma_pago(String forma_pago) {
        this.forma_pago = forma_pago;
    }

    public int getNro_compra() {
        return nro_compra;
    }

    public void setNro_compra(int nro_compra) {
        this.nro_compra = nro_compra;
    }
    
}
