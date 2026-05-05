package Vista;

import java.util.Scanner;

public class menuPrincipal {
    public void mostrar() {
        Scanner sc = new Scanner(System.in);
        boolean actiu = true;

        while (actiu) {
            System.out.println("\n=======================================");
            System.out.println("       PILLAM Ltd. Co. - ESCALADA      ");
            System.out.println("=======================================");
            System.out.println("1. CREAR");
            System.out.println("2. MODIFICAR");
            System.out.println("3. LLISTAR / CONSULTAR");
            System.out.println("4. ELIMINAR");
            System.out.println("0. SORTIR");
            System.out.print("INTRODUEIX UNA DE LES OPCIONS: ");

            try {
                int op = Integer.parseInt(sc.nextLine());
                switch (op) {
                    case 1 -> menuCrear.menu();
                    case 2 -> menuModificar.menu();
                    case 3 -> menuLlistar.menu();
                    case 4 -> menuEliminar.menu();
                    case 0 -> {
                        System.out.println("SORTINT...");
                        actiu = false;
                    }
                    default -> System.out.println("Has d'introduir un numero entre 0-4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Has d'introduir un numero.");
            }
        }
    }
}
