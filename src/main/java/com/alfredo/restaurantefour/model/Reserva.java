package com.alfredo.restaurantefour.model;

public class Reserva {
    private Long id;
    private Mesa mesa;
    private int dia;
    private int horaInicio;
    private int horaFin;
    private int numeroComensales;

    public Reserva() {
    }

    public Reserva(Long id, Mesa mesa, int dia, int horaInicio, int horaFin, int numeroComensales) {
        this.id = id;
        this.mesa = mesa;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.numeroComensales = numeroComensales;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
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
