package com.alfredo.restaurantefour.model;

/**
 * Clase modelo: Reserva
 */
public class Reserva {
    private int id;
    private Integer mesa;
    private int dia;
    private int horaInicio;
    private int horaFin;
    private int numeroComensales;

    public Reserva() {
    }

    public Reserva(int id, int mesa, int dia, int horaInicio, int horaFin, int numeroComensales) {
        this.id = id;
        this.mesa = mesa;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.numeroComensales = numeroComensales;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    public int getNumeroComensales() {
        return numeroComensales;
    }

    public void setNumeroComensales(int numeroComensales) {
        this.numeroComensales = numeroComensales;
    }
}
