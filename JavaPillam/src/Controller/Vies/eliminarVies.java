package Controller.Vies;

import Model.DAO.Clases.TramDAO;
import Model.DAO.Clases.Via.ViaClassicaDAO;
import Model.DAO.Clases.Via.ViaEsportivaDAO;
import Model.DAO.Clases.Via.ViaGelDAO;
import Model.Objectes.ViaClassica;
import Model.Objectes.ViaEsportiva;
import Model.Objectes.ViaGel;
import java.util.Scanner;

public class eliminarVies {
    public static void eliminarVie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- ELIMINAR VIA -----------");
        System.out.println("1. ESPORTIVA  2. CLASSICA  3. GEL");
        System.out.print("Tipus: ");

        try {
            int tipus = Integer.parseInt(sc.nextLine());
            switch (tipus) {
                case 1 -> eliminarEsportiva(sc);
                case 2 -> eliminarClassica(sc);
                case 3 -> eliminarGel(sc);
                default -> System.out.println("Tipus no valid.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Has d'introduir un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void eliminarEsportiva(Scanner sc) throws Exception {
        ViaEsportivaDAO dao = new ViaEsportivaDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.print("ID de la via esportiva (buit per sortir): ");
        String input = sc.nextLine();
        if (input.isBlank()) return;

        ViaEsportiva v = dao.cercarPerId(Integer.parseInt(input));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via a eliminar:\n" + v);
        System.out.print("Confirma (y/n): ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }
        System.out.print("CONFIRMACIO FINAL (IRREVERSIBLE) y/n: ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }

        tramDAO.eliminarPerViaEsportiva(v.getIdVia());
        dao.eliminar(v.getIdVia());
        System.out.println("Via esportiva eliminada correctament.");
    }

    private static void eliminarClassica(Scanner sc) throws Exception {
        ViaClassicaDAO dao = new ViaClassicaDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.print("ID de la via classica (buit per sortir): ");
        String input = sc.nextLine();
        if (input.isBlank()) return;

        ViaClassica v = dao.cercarPerId(Integer.parseInt(input));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via a eliminar:\n" + v);
        System.out.print("Confirma (y/n): ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }
        System.out.print("CONFIRMACIO FINAL (IRREVERSIBLE) y/n: ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }

        tramDAO.eliminarPerViaClassica(v.getIdVia());
        dao.eliminar(v.getIdVia());
        System.out.println("Via classica eliminada correctament.");
    }

    private static void eliminarGel(Scanner sc) throws Exception {
        ViaGelDAO dao = new ViaGelDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.print("ID de la via gel (buit per sortir): ");
        String input = sc.nextLine();
        if (input.isBlank()) return;

        ViaGel v = dao.cercarPerId(Integer.parseInt(input));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via a eliminar:\n" + v);
        System.out.print("Confirma (y/n): ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }
        System.out.print("CONFIRMACIO FINAL (IRREVERSIBLE) y/n: ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }

        tramDAO.eliminarPerViaGel(v.getIdVia());
        dao.eliminar(v.getIdVia());
        System.out.println("Via gel eliminada correctament.");
    }
}
