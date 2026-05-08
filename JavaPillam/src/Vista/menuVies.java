package Vista;

import Controller.Vies.TipusVies.crearClassica;
import Controller.Vies.TipusVies.crearEsportiva;
import Controller.Vies.TipusVies.crearGel;
import java.util.Scanner;

public class menuVies {
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------VIES---------------");
        System.out.println("1. VIA ESPORTIVA");
        System.out.println("2. VIA CLASSICA");
        System.out.println("3. VIA DE GEL");
        System.out.println("0. TORNAR");
        System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");
        try {
            int op = Integer.parseInt(sc.nextLine());
            switch (op) {
                case 1 -> crearEsportiva.crearesportiva();
                case 2 -> crearClassica.crearclassica();
                case 3 -> crearGel.creargel();
                case 0 -> {}
                default -> System.out.println("HAS D'INTRODUIR UN NUMERO ENTRE 0-3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Has d'introduir un numero.");
        }
    }
}
