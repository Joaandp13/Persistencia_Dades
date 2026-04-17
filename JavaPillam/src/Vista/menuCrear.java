package Vista;
import java.util.Scanner;

import Controller.Escalador.*;
import Controller.Escola.*;
import Controller.Sector.*;
import Controller.Vies.*;
public class menuCrear {
    public static void menu(){

        Scanner sc = new Scanner(System.in);
        System.out.println("---------------CREAR---------------");
        System.out.println("1. ESCALADOR");
        System.out.println("2. ESCOLA");
        System.out.println("3. SECTOR");
        System.out.println("4. VIES");
        System.out.println("0. SORTIR");
        System.out.println("INTRODUEIX UNA DE LES OPCIONS");
        try {
            int op = sc.nextInt();

        switch (op) {
            case 1:
                crearEscalador.crearesc();
                break;
            case 2:
                crearEscola.crearEsco();
                break;
            case 3:
                crearSector.crearSect();
                break;
            case 4:
                crearVies.crearVie();
                break;
            case 0:
                break;
            default:
                System.out.println("HAS D'INTRODUÏR UN NÚMERO ENTRE 0-4.");

        }
        }catch(Exception e){System.out.println("Error: "+ e.getMessage());}

    }
}
