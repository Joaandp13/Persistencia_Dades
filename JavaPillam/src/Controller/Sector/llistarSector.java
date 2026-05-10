package Controller.Sector;

import Model.DAO.Clases.SectorDAO;
import Model.Objectes.Sector;
import Vista.Utils;
import java.util.List;
import java.util.Scanner;

public class llistarSector {

    public static void llistarUn() {
        Scanner sc = new Scanner(System.in);
        SectorDAO dao = new SectorDAO();
        System.out.println("----------- LLISTAR SECTOR -----------");
        try {
            System.out.print("ID del sector (buit per sortir): ");
            String input = sc.nextLine();
            if (input.isBlank()) return;

            Sector s = dao.cercarPerId(Integer.parseInt(input));
            if (s == null) System.out.println("Sector no trobat.");
            else System.out.println(s);

        } catch (NumberFormatException ex) {
            System.out.println("L'id ha de ser un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    public static void llistarTots() {
        SectorDAO dao = new SectorDAO();
        System.out.println("----------- TOTS ELS SECTORS -----------");
        try {
            List<Sector> llista = dao.llistarTots();
            if (llista.isEmpty()) { System.out.println("No hi ha sectors registrats."); return; }
            for (Sector s : llista)
                System.out.println("[" + s.getIdSector() + "] "
                    + s.getNom()
                    + " | Escola id: " + s.getIdEscola()
                    + " | Tipus: " + s.getTipusVies()
                    + " | " + s.getPopularitat());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    // Consulta especifica de l'enunciat: sectors amb mes de X vies en estat apte
    public static void llistarAmbMesDeXVies() {
        Scanner sc = new Scanner(System.in);
        SectorDAO dao = new SectorDAO();
        System.out.println("----------- SECTORS PER NOMBRE DE VIES -----------");
        try {
            System.out.print("Nombre minim de vies disponibles: ");
            int x = Integer.parseInt(sc.nextLine());
            List<Sector> llista = dao.llistarAmbMesDeXVies(x);
            if (llista.isEmpty()) { System.out.println("Cap sector amb mes de " + x + " vies."); return; }
            System.out.println("Sectors amb mes de " + x + " vies disponibles:");
            for (Sector s : llista)
                System.out.println("  [" + s.getIdSector() + "] " + s.getNom());
        } catch (NumberFormatException ex) {
            System.out.println("Has d'introduir un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }
}
