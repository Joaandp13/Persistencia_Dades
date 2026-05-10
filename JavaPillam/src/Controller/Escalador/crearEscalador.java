package Controller.Escalador;

import Model.DAO.Clases.EscaladorDAO;
import Model.Objectes.Escalador;
import java.util.Scanner;

public class crearEscalador {

    public static void crearesc() {
        Scanner sc = new Scanner(System.in);
        EscaladorDAO dao = new EscaladorDAO();

        System.out.println("----------- CREAR ESCALADOR -----------");

        try {
            System.out.print("Nom: ");
            String nom = sc.nextLine();

            System.out.print("Àlies (deixa buit si no en té): ");
            String alias = sc.nextLine();
            if (alias.isBlank()) alias = null;

            System.out.print("Edat: ");
            int edat = sc.nextInt();
            sc.nextLine(); // neteja el buffer després de nextInt()

            System.out.print("Nivell màxim assolit (ex: 6a, 7b+, 9c+): ");
            String nivell = sc.nextLine();
            if (nivell.isBlank()) nivell = null;

            System.out.print("Nom de la via on ha assolit el nivell màxim: ");
            String viaMaxNivell = sc.nextLine();
            if (viaMaxNivell.isBlank()) viaMaxNivell = null;

            System.out.print("Estil preferit (esportiva / classica / gel): ");
            String estilPreferit = sc.nextLine();
            if (estilPreferit.isBlank()) estilPreferit = null;

            // Els setters validen format i rang — si alguna dada és incorrecta, llancen IllegalArgumentException
            Escalador e = new Escalador(nom, alias, edat, nivell, viaMaxNivell, estilPreferit, null);
            dao.inserir(e);

            System.out.println("Escalador creat correctament amb id: " + e.getIdEscalador());

        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error en crear l'escalador: " + ex.getMessage());
        }
    }
}
