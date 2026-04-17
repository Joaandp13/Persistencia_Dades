package Controller.Vies;
import Controller.Vies.TipusVies.*;
import Controller.Escalador.crearEscalador;
import Controller.Escola.crearEscola;
import Controller.Sector.crearSector;
import java.util.Scanner;
public class crearVies {
    public static void crearVie(){
        System.out.println("----------CREACIO DE VIES----------");
        System.out.println("1. CLASSICA");
        System.out.println("2. ESPORTIVA");
        System.out.println("3. GEL");
        System.out.println("INTRODUEIX UNA DE LES OPCIONS");
        Scanner sc = new Scanner(System.in);
        try {
            int op = sc.nextInt();

            switch (op) {
                case 1:

                default:
                    System.out.println("HAS D'INTRODUÏR UN NÚMERO ENTRE 0-4.");

            }
        }catch(Exception e){System.out.println("Error: "+ e.getMessage());}

    }
}
