package Controller.Escalador;

import Model.DAO.Clases.EscaladorDAO;
import Model.Objectes.Escalador;
import java.util.List;
import java.util.Scanner;

public class llistarEscalador {

    public static void llistarUn() {
        Scanner sc = new Scanner(System.in);
        EscaladorDAO dao = new EscaladorDAO();
        System.out.println("----------- LLISTAR ESCALADOR -----------");
        try {
            System.out.print("ID de l'escalador (buit per sortir): ");
            String input = sc.nextLine();
            if (input.isBlank()) return;

            Escalador e = dao.cercarPerId(Integer.parseInt(input));
            if (e == null) System.out.println("Escalador no trobat.");
            else System.out.println(e);

        } catch (NumberFormatException ex) {
            System.out.println("L'id ha de ser un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void llistarTots() {
        EscaladorDAO dao = new EscaladorDAO();
        System.out.println("----------- TOTS ELS ESCALADORS -----------");
        try {
            List<Escalador> llista = dao.llistarTots();
            if (llista.isEmpty()) { System.out.println("No hi ha escaladors registrats."); return; }
            for (Escalador e : llista)
                System.out.println("[" + e.getIdEscalador() + "] "
                    + e.getNom() + " (" + e.getAlias() + ")"
                    + " | Nivell: " + e.getNivell()
                    + " | Estil: " + e.getEstilPreferit());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Consulta especifica: escaladors amb el mateix nivell maxim
    public static void llistarPerNivell() {
        Scanner sc = new Scanner(System.in);
        EscaladorDAO dao = new EscaladorDAO();
        System.out.println("----------- ESCALADORS PER NIVELL -----------");
        try {
            System.out.print("Nivell a cercar (ex: 7a, 8b+): ");
            String nivell = sc.nextLine();
            List<Escalador> llista = dao.llistarPerNivell(nivell);
            if (llista.isEmpty()) { System.out.println("Cap escalador amb nivell " + nivell + "."); return; }
            System.out.println("Escaladors amb nivell " + nivell + ":");
            for (Escalador e : llista)
                System.out.println("  [" + e.getIdEscalador() + "] "
                    + e.getNom() + " | Via max: " + e.getViaMaxNivell());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
