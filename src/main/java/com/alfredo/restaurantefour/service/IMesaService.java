package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;

import java.util.Collection;

public interface IMesaService {
    public Collection<Mesa> mesaTodas();

    public boolean nueva(Mesa nuevoRegistro);

    public Mesa mesita(Integer mesa);

    public boolean eliminarMesa(Integer mesa);

    public boolean actualizarPorMesa(Integer mesa, Mesa mesaActualizada);
}
