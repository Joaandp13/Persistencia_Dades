package Vista;
import java.util.Scanner;
public class menuPrincipal {
    public void mostrarMenuPrincipal(){
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------INICI---------------");
        System.out.println("1. CREAR");
        System.out.println("2. MODIFICAR");
        System.out.println("3. LLISTAR");
        System.out.println("4. ELIMINAR");
        System.out.println("0. EXIT");
        System.out.println("INTRODUEIX UNA DE LES OPCIONS");
        int op= 0;
        boolean tancar = false;

            op = sc.nextInt();
            switch (op) {
                case 1:
                    menuCrear.menu();
                    break;
                case 2:
                    menuModificar();
                    break;
                case 3:
                    menuLlistar();
                    break;
                case 4:
                    menuEliminar();
                    break;
                case 0:
                    System.out.println("SORTINT...");

                    break;
                default:
                    System.out.println("Has de introduïr un número entre 0-4.");

            }
            sc.close();
        }
    }

