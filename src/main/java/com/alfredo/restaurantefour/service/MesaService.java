package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class MesaService implements IMesaService {
    private static int nextId = 1;
    private static final Logger log = LoggerFactory.getLogger(MesaService.class);
    public static ConcurrentHashMap<Integer, Mesa> datosDeMesa = new ConcurrentHashMap<>();
    @Override
    public Collection<Mesa> mesaTodas() {
        return datosDeMesa.values();
    }

    @Override
    public boolean nueva(Mesa nuevoRegistro) {
        /*Mesa mesa = new Mesa(2, 5);
        datosDeMesa.put(mesa.getId(), nuevoRegistro);
        log.info("Insertada nueva mesa " + mesa.getId());
        log.info("PROBANDO LOG!!");
        return true;*/
        int capacidad = nuevoRegistro.getCapacidad();
        if (capacidad < 1 || capacidad > 8) {
            log.error("La capacidad de la mesa está fuera del rango permitido (1-8). No se pudo agregar la mesa.");
            return false;
        }
        nuevoRegistro.setId(nextId++);
        datosDeMesa.put(nuevoRegistro.getId(), nuevoRegistro);
        log.info("Insertada nueva mesa " + nuevoRegistro.getId());
        log.info("PROBANDO LOG!!");
        return true;
    }

    @Override
    public Mesa mesita(Integer mesa) {
        return datosDeMesa.get(mesa);
    }

    @Override
    public boolean eliminarMesa(Integer mesa) {
        Mesa removed = datosDeMesa.remove(mesa);
        return removed != null;
    }

    @Override
    public boolean actualizarPorMesa(Integer mesa, Mesa mesaActualizada) {
        //Cuidado
        if (datosDeMesa.containsKey(mesa)) {
            datosDeMesa.put(mesa, mesaActualizada);
            return true;
        }
        return false;
    }
}
