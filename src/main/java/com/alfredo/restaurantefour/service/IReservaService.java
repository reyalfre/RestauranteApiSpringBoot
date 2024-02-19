package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Reserva;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface IReservaService {
    public void generarReservaFake();

    public Collection<Reserva> reservaTodas();

    public boolean nueva(Reserva nuevoRegistro);

    public Reserva reservaPorMesa(Integer reserva);

    //Agregando de mi api personalizada
    public boolean eliminarReserva(Integer reserva);

    public boolean actualizarPorReserva(Integer reserva, Reserva reservaActualizada);

    // public boolean actualizarParcialPorReserva(String reserva, Reserva reservaPacial);
    //Consultas especiales
    public Collection<Reserva> reservasDelDia();
}
