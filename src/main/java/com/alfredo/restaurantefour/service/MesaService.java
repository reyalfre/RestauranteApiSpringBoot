package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class MesaService implements IMesaService {
    private static final Logger log = LoggerFactory.getLogger(MesaService.class);
    private static ConcurrentHashMap<String, Mesa> datosDeMesa = new ConcurrentHashMap<>();

    @Override
    public Collection<Mesa> mesaTodas() {
        return datosDeMesa.values();
    }

    @Override
    public boolean nueva(Mesa nuevoRegistro) {
        Mesa mesa = new Mesa(2, 5);
        datosDeMesa.put(String.valueOf(mesa.getId()), nuevoRegistro);
        log.info("Insertada nueva mesa " + mesa.getId());
        log.info("PROBANDO LOG!!");
        return true;
    }

    @Override
    public Mesa mesita(String mesa) {
        return datosDeMesa.get(mesa);
    }

    @Override
    public boolean eliminarMesa(String mesa) {
        Mesa removed = datosDeMesa.remove(mesa);
        return removed != null;
    }

    @Override
    public boolean actualizarPorMesa(String mesa, Mesa mesaActualizada) {
        if (datosDeMesa.containsKey(mesa)) {
            datosDeMesa.put(mesa, mesaActualizada);
            return true;
        }
        return false;
    }
}
