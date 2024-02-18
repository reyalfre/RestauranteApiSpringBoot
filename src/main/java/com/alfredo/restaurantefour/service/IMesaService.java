package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;

import java.util.Collection;

public interface IMesaService {
    public Collection<Mesa> mesaTodas();

    public boolean nueva(Mesa nuevoRegistro);

    public Mesa mesita(String mesa);

    public boolean eliminarMesa(String mesa);

    public boolean actualizarPorMesa(String mesa, Mesa mesaActualizada);
}
