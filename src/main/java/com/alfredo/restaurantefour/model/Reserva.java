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
    private String nombre;
    private String apellido;
    private String telefono;
    private int numeroComensales;

    public Reserva() {
    }

    public Reserva(int id, Integer mesa, int dia, int horaInicio, int horaFin, String nombre, String apellido, String telefono, int numeroComensales) {
        this.id = id;
        this.mesa = mesa;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getNumeroComensales() {
        return numeroComensales;
    }

    public void setNumeroComensales(int numeroComensales) {
        this.numeroComensales = numeroComensales;
    }
}
