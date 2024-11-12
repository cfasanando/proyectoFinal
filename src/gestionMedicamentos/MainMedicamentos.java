package gestionMedicamentos;

import gestionMedicamentos.servicios.MedicamentoService;
import gestionMedicamentos.ui.MenuMedicamentos;

public class MainMedicamentos {
    public static void main(String[] args) {
        MedicamentoService medicamentoService = MedicamentoService.getInstance();
        MenuMedicamentos menuMedicamentos = new MenuMedicamentos(medicamentoService);
        menuMedicamentos.mostrarMenu();
    }
}
