package com.alfredo.restaurantefour.model;

public class Mesa {
    private Long id;
    private int capacidad;

    public Mesa(Long id, int capacidad) {
        this.id = id;
        this.capacidad = capacidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
