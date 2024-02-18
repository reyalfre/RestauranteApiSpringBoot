package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Reserva;

import java.util.Collection;

public interface IReservaService {
    public void generarReservaFake();
    public Collection<Reserva> reservaTodas();
    public boolean nueva(Reserva nuevoRegistro);
    public Reserva reservaPorMesa(String reserva);
}
