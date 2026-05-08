package Vista;

import Controller.Escalador.eliminarEscalador;
import Controller.Escola.eliminarEscola;
import Controller.Sector.eliminarSector;
import Controller.Vies.eliminarVies;
import java.util.Scanner;

public class menuEliminar {
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------ELIMINAR---------------");
        System.out.println("1. ESCALADOR");
        System.out.println("2. ESCOLA");
        System.out.println("3. SECTOR");
        System.out.println("4. VIES");
        System.out.println("0. TORNAR");
        System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");
        try {
            int op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> eliminarEscalador.eliminarEsc();
                case 2 -> eliminarEscola.eliminarEsco();
                case 3 -> eliminarSector.eliminarSect();
                case 4 -> eliminarVies.eliminarVie();
                case 0 -> {}
                default -> System.out.println("HAS D'INTRODUIR UN NUMERO ENTRE 0-4.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Has d'introduir un numero.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
