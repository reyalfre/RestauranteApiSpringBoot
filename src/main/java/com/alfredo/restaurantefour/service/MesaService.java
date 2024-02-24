package com.alfredo.restaurantefour.service;

import com.alfredo.restaurantefour.model.Mesa;
import com.alfredo.restaurantefour.model.Reserva;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import static com.alfredo.restaurantefour.service.ReservaService.datosReservaPorMesa;

@Service
public class MesaService implements IMesaService {
    private static int nextId = 1;
    public int capacidadTotalActual = 0;
    private static final Logger log = LoggerFactory.getLogger(MesaService.class);
    public static ConcurrentHashMap<Integer, Mesa> datosDeMesa = new ConcurrentHashMap<>();

    /**
     * Método mesaTodas: Lógica para obtener todas las mesas.
     *
     * @return Todas las mesas.
     */
    @Override
    public Collection<Mesa> mesaTodas() {
        return datosDeMesa.values();
    }

    /**
     * Método nueva: Lógica para crear una nueva.
     *
     * @param nuevoRegistro Un nuevo registro.
     * @return True si se ha creado la mesa. False sino se ha creado la mesa.
     */
    @Override
    public boolean nueva(Mesa nuevoRegistro) {
        int capacidad = nuevoRegistro.getCapacidad();
        if (capacidad < 1 || capacidad > 8) {
            log.error("La capacidad de la mesa está fuera del rango permitido (1-8). No se pudo agregar la mesa.");
            return false;
        }
        // Verificar si al agregar la nueva mesa se excede el aforo máximo de 30 personas
        if (capacidadTotalActual + capacidad > 30) {
            log.error("Al agregar la nueva mesa, se excede el aforo máximo de 30 personas. No se pudo agregar la mesa.");
            return false;
        }
        System.out.println(capacidadTotalActual + " :1");
        nuevoRegistro.setId(nextId++);
        datosDeMesa.put(nuevoRegistro.getId(), nuevoRegistro);
        capacidadTotalActual += capacidad;
        log.info("Insertada mesa " + nuevoRegistro.getId() + ", con capacidad de " + nuevoRegistro.getCapacidad() + " personas y la capacidad actual es de " + capacidadTotalActual);
        System.out.println(capacidadTotalActual + " :2");
        return true;
    }

    /**
     * Método mesita: Lógica para obtener una mesa.
     *
     * @param mesa El id de la mesa.
     * @return Una mesa.
     */
    @Override
    public Mesa mesita(Integer mesa) {
        return datosDeMesa.get(mesa);
    }

    /**
     * Método eliminarMesa: Lógica para eliminar una mesa.
     *
     * @param mesa El id de la mesa.
     * @return True si se ha borrado la mesa. False sino se ha encontrado la mesa a borrar.
     */
    @Override
    public boolean eliminarMesa(Integer mesa) {
        Mesa removed = datosDeMesa.remove(mesa);
        if (removed != null) {
            capacidadTotalActual -= removed.getCapacidad();
            log.info("Borrada mesa " + mesa + ", la capacidad actual es de " + capacidadTotalActual);
            return true;
        } else {
            log.error("No se pudo encontrar la mesa con el ID " + mesa + ". No se pudo eliminar la mesa.");
            return false;
        }

    }

    /**
     * Método actualizarPorMesa: Lógica para actualizar una reserva.
     * @param mesa El id de la mesa.
     * @param mesaActualizada El id a actualizar.
     * @return True si se ha actualizado la mesa. False sino se ha encontrado/actualizado la mesa.
     */
    @Override
    public boolean actualizarPorMesa(Integer mesa, Mesa mesaActualizada) {
        // Verificar si la mesa que se está actualizando existe en el mapa de datos
        if (!datosDeMesa.containsKey(mesa)) {
            log.error("No se encontró una mesa con el ID " + mesa + ". No se pudo actualizar la mesa.");
            return false;
        }

        // Verificar si la capacidad de la mesa actualizada está dentro del rango permitido (1-8)
        int nuevaCapacidad = mesaActualizada.getCapacidad();
        if (nuevaCapacidad < 1 || nuevaCapacidad > 8) {
            log.error("La capacidad de la mesa está fuera del rango permitido (1-8). No se pudo actualizar la mesa.");
            return false;
        }

        // Obtener la mesa antes de la actualización para restar su capacidad del contador
        Mesa mesaAntesDeActualizar = datosDeMesa.get(mesa);
        int capacidadAntesDeActualizar = mesaAntesDeActualizar.getCapacidad();

        // Actualizar la mesa en el mapa de datos
        datosDeMesa.put(mesa, mesaActualizada);

        // Actualizar el contador de capacidadTotalActual
        capacidadTotalActual = capacidadTotalActual - capacidadAntesDeActualizar + nuevaCapacidad;

        log.info("Mesa " + mesa + " actualizada correctamente.");
        return true;
    }

    /**
     * Método mesasDisponibles: Lógica para obtener las mesas disponible en un día y hora en concreto.
     * @param dia El día de la reserva a consultar.
     * @param horaInicio La hora de inicio en la que se inicia la reserva a consultar.
     * @param horaFin La hora en la que se finaliza la reserva a consultar.
     * @return Las mesas disponibles en la fecha especificada.
     */
    @Override
    public Collection<Mesa> mesasDisponibles(int dia, int horaInicio, int horaFin) {
        Collection<Mesa> mesasDisponibles = new ArrayList<>();
        // Obtener las reservas para el día y franja horaria especificados
        Collection<Reserva> reservas = obtenerReservasParaDiaYHora(dia, horaInicio, horaFin);
        log.info("Obteniendo mesas disponibles para el día {} y franja horaria {} - {}", dia, horaInicio, horaFin);
        // Iterar sobre todas las mesas y agregar aquellas que no tengan reservas en la franja horaria especificada
        for (Mesa mesa : datosDeMesa.values()) {
            boolean mesaDisponible = true;
            for (Reserva reserva : reservas) {
                if (reserva.getMesa().equals(mesa.getId())) {
                    mesaDisponible = false;
                    break;
                }
            }
            if (mesaDisponible) {
                mesasDisponibles.add(mesa);
            }
        }
        log.info("Se encontraron {} mesas disponibles.", mesasDisponibles.size());
        return mesasDisponibles;
    }

    /**
     * Verificar si la reserva es para el día y la franja horaria especificada
     * @param dia El día de la reserva a consultar.
     * @param horaInicio La hora de inicio en la que se inicia la reserva a consultar.
     * @param horaFin La hora en la que se finaliza la reserva a consultar.
     * @return La reserva para el dia y hora especificada.
     */
    private Collection<Reserva> obtenerReservasParaDiaYHora(int dia, int horaInicio, int horaFin) {
        Collection<Reserva> reservasParaDiaYHora = new ArrayList<>();

        for (Reserva reserva : datosReservaPorMesa.values()) {
            // Verificar si la reserva es para el día y la franja horaria especificada
            if (reserva.getDia() == dia &&
                    reserva.getHoraInicio() >= horaInicio &&
                    reserva.getHoraFin() <= horaFin) {
                reservasParaDiaYHora.add(reserva);
            }
        }

        return reservasParaDiaYHora;
    }

}
