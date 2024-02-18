package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Reserva;

import java.util.Collection;

public interface IReservaService {
    public void generarReservaFake();

    public Collection<Reserva> reservaTodas();

    public boolean nueva(Reserva nuevoRegistro);

    public Reserva reservaPorMesa(String reserva);

    //Agregando de mi api personalizada
    public boolean eliminarReserva(String reserva);

    public boolean actualizarPorReserva(String reserva, Reserva reservaActualizada);

   // public boolean actualizarParcialPorReserva(String reserva, Reserva reservaPacial);
}
