package Vista;

import Controller.Escalador.modificarEscalador;
import Controller.Escola.modificarEscola;
import Controller.Sector.modificarSector;
import Controller.Vies.modificarVies;
import java.util.Scanner;

public class menuModificar {
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------MODIFICAR---------------");
        System.out.println("1. ESCALADOR");
        System.out.println("2. ESCOLA");
        System.out.println("3. SECTOR");
        System.out.println("4. VIES");
        System.out.println("0. TORNAR");
        System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");
        try {
            int op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> modificarEscalador.modificarEsc();
                case 2 -> modificarEscola.modificarEsco();
                case 3 -> modificarSector.modificarSect();
                case 4 -> modificarVies.modificarVie();
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
