package Controller.Escola;

import Model.DAO.Clases.EscolaDAO;
import Model.Objectes.Escola;
import Vista.Utils;
import java.util.List;
import java.util.Scanner;

public class llistarEscola {

    public static void llistarUna() {
        Scanner sc = new Scanner(System.in);
        EscolaDAO dao = new EscolaDAO();
        System.out.println("----------- LLISTAR ESCOLA -----------");
        try {
            System.out.print("ID de l'escola (buit per sortir): ");
            String input = sc.nextLine();
            if (input.isBlank()) return;

            Escola e = dao.cercarPerId(Integer.parseInt(input));
            if (e == null) System.out.println("Escola no trobada.");
            else {
                System.out.println(e);
                // num_vies no s'emmagatzema a la BDD — es calcula amb COUNT(*)
                System.out.println("  num_vies = " + dao.comptarVies(e.getIdEscola()));
            }
        } catch (NumberFormatException ex) {
            System.out.println("L'id ha de ser un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    public static void llistarTotes() {
        EscolaDAO dao = new EscolaDAO();
        System.out.println("----------- TOTES LES ESCOLES -----------");
        try {
            List<Escola> llista = dao.llistarTotes();
            if (llista.isEmpty()) { System.out.println("No hi ha escoles registrades."); return; }
            for (Escola e : llista)
                System.out.println("[" + e.getIdEscola() + "] "
                    + e.getNom() + " - " + e.getPoblacio()
                    + " | " + e.getPopularitat()
                    + " | vies: " + dao.comptarVies(e.getIdEscola()));
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    // Consulta especifica de l'enunciat: escoles que tenen algun sector amb restriccions
    public static void llistarAmbRestriccions() {
        EscolaDAO dao = new EscolaDAO();
        System.out.println("----------- ESCOLES AMB RESTRICCIONS -----------");
        try {
            List<Escola> llista = dao.llistarAmbRestriccions();
            if (llista.isEmpty()) { System.out.println("Cap escola amb restriccions actives."); return; }
            for (Escola e : llista)
                System.out.println("[" + e.getIdEscola() + "] " + e.getNom()
                    + " - " + e.getPoblacio());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }
}
