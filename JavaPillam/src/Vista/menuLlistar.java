package Vista;

import Controller.Escalador.llistarEscalador;
import Controller.Escola.llistarEscola;
import Controller.Sector.llistarSector;
import Controller.Vies.llistarVies;
import java.util.Scanner;

public class menuLlistar {
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        boolean tornar = false;

        while (!tornar) {
            System.out.println("\n---------------LLISTAR / CONSULTAR---------------");
            System.out.println("--- CRUD ---");
            System.out.println("1. Un escalador");
            System.out.println("2. Tots els escaladors");
            System.out.println("3. Una escola");
            System.out.println("4. Totes les escoles");
            System.out.println("5. Un sector");
            System.out.println("6. Tots els sectors");
            System.out.println("7. Una via");
            System.out.println("8. Totes les vies");
            System.out.println("--- CONSULTES ESPECIFIQUES ---");
            System.out.println("9.  Vies disponibles d'una escola");
            System.out.println("10. Cercar vies per rang de dificultat");
            System.out.println("11. Cercar vies per estat");
            System.out.println("12. Escoles amb restriccions actives");
            System.out.println("13. Sectors amb mes de X vies disponibles");
            System.out.println("14. Escaladors amb el mateix nivell maxim");
            System.out.println("15. Vies que han passat a APTE recentment");
            System.out.println("16. Vies mes llargues d'una escola");
            System.out.println("0.  TORNAR");
            System.out.print("OPCIO: ");

            try {
                int op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1  -> llistarEscalador.llistarUn();
                    case 2  -> llistarEscalador.llistarTots();
                    case 3  -> llistarEscola.llistarUna();
                    case 4  -> llistarEscola.llistarTotes();
                    case 5  -> llistarSector.llistarUn();
                    case 6  -> llistarSector.llistarTots();
                    case 7  -> llistarVies.llistarUna();
                    case 8  -> llistarVies.llistarTotes();
                    case 9  -> llistarVies.viesDisponiblesEscola();
                    case 10 -> llistarVies.cercarPerRangGrau();
                    case 11 -> llistarVies.cercarPerEstat();
                    case 12 -> llistarEscola.llistarAmbRestriccions();
                    case 13 -> llistarSector.llistarAmbMesDeXVies();
                    case 14 -> llistarEscalador.llistarPerNivell();
                    case 15 -> llistarVies.viesRecentmentAptes();
                    case 16 -> llistarVies.viesMesLlargues();
                    case 0  -> tornar = true;
                    default -> System.out.println("Has d'introduir un numero entre 0-16.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Has d'introduir un numero.");
            }
        }
    }
}
