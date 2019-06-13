package com.secuencia.saz.sazmobileventas.Modelo;

import java.io.Serializable;

public class Pago implements Serializable {
    String efectivo;
    String formaPago;

    public Pago() {
        this.efectivo = efectivo;
        this.formaPago = formaPago;
    }

    public String getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(String efectivo) {
        this.efectivo = efectivo;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }
}
