package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.model.Reserva;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReservaService implements IReservaService {
    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);
    private static ConcurrentHashMap<String, Reserva> datosReservaPorMesa = new ConcurrentHashMap<>();

    @Override
    public void generarReservaFake() {
        Reserva reservaFake = new Reserva(1, 1, 1, 1, 2, 4);
        datosReservaPorMesa.put(String.valueOf(reservaFake.getId()), reservaFake);
    }

    @Override
    public Collection<Reserva> reservaTodas() {
        return datosReservaPorMesa.values();
    }

    @Override
    public boolean nueva(Reserva nuevoRegistro) {
        Reserva reserva = new Reserva(1, 1, 1, 1, 2, 4);
        //Cuidado(puse un cast porque no sé que poner en reserva.get)
        datosReservaPorMesa.put(String.valueOf(reserva.getId()), nuevoRegistro);
        log.info("Insertada nueva reserva " + reserva.getId());
        log.info("PROBANDO LOG!!");
        return true;
    }

    @Override
    public Reserva reservaPorMesa(String reserva) {
        return datosReservaPorMesa.get(reserva);
    }


    //Agregados de mi api
    @Override
    public boolean eliminarReserva(String reserva) {
        Reserva removed = datosReservaPorMesa.remove(reserva);
        return removed != null;
    }

    @Override
    public boolean actualizarPorReserva(String reserva, Reserva reservaActualizada) {
        if (datosReservaPorMesa.containsKey(reserva)) {
            datosReservaPorMesa.put(reserva, reservaActualizada);
            return true; //Actualización exitosa
        }
        return false;//La reserva no existe, actualización no exitosa
    }
}
