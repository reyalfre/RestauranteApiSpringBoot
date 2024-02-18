package com.alfredo.restaurantefour.model;

public class Mesa {
    private int id;
    private int capacidad;

    public Mesa(int id, int capacidad) {
        this.id = id;
        this.capacidad = capacidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
