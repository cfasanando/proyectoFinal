package gestionCitas.servicios;

import gestionCitas.dao.MedicoDAO;
import gestionCitas.modelos.Medico;

import java.util.List;

public class MedicoService {
    private final MedicoDAO medicoDAO;

    public MedicoService() {
        this.medicoDAO = new MedicoDAO();
    }
    
    public List<Medico> obtenerMedicos() {
        return medicoDAO.obtenerMedicos();
    }

    public void registrarMedico(Medico medico) {
        if (medicoDAO.existeMedico(medico.getCodigo())) {
            System.out.println("El médico con el código ingresado ya existe. No se puede registrar un duplicado.");
            return;
        }

        if (medicoDAO.insertarMedico(medico)) {
            System.out.println("Médico registrado correctamente.");
        } else {
            System.out.println("Error al registrar el médico.");
        }
    }

    public void listarMedicos() {
        List<Medico> medicos = medicoDAO.obtenerMedicos();
        if (medicos.isEmpty()) {
            System.out.println("No hay medicos registrados.");
        } else {
            System.out.println("\n--- Lista de Medicos ---");
            for (Medico medico : medicos) {
                System.out.println("Codigo: " + medico.getCodigo() +
                        " - Nombre: " + medico.getNombre() +
                        " - Especialidad: " + medico.getEspecialidad() +
                        " - Disponible: " + (medico.isDisponible() ? "Si" : "No"));
            }
        }
    }

    public Medico buscarMedicoPorCodigo(String codigo) {
        return medicoDAO.buscarMedicoPorCodigo(codigo);
    }

    public void actualizarDisponibilidadMedico(String codigo, boolean disponible) {
        if (medicoDAO.actualizarDisponibilidad(codigo, disponible)) {
            System.out.println("Disponibilidad del medico actualizada correctamente.");
        } else {
            System.out.println("Error al actualizar la disponibilidad del medico.");
        }
    }   
}
