// Paquet que agrupa els controladors de gestió general de vies.
package Controller.Vies;

import Model.DAO.Clases.TramDAO;
import Model.DAO.Clases.Via.ViaClassicaDAO;
import Model.DAO.Clases.Via.ViaEsportivaDAO;
import Model.DAO.Clases.Via.ViaGelDAO;
import Model.Objectes.ViaClassica;
import Model.Objectes.ViaEsportiva;
import Model.Objectes.ViaGel;
import java.util.Scanner;

/**
 * Controlador encarregat d'eliminar vies de la base de dades.
 * Implementa una lògica de seguretat amb doble confirmació i eliminació
 * dels fills (trams) abans que els pares (vies).
 */
public class eliminarVies {

    /**
     * Menú principal per triar quin tipus de via es vol eliminar.
     * Redirigeix el flux segons la modalitat d'escalada.
     */
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

    /**
     * Gestiona l'eliminació d'una via esportiva i els seus trams.
     */
    private static void eliminarEsportiva(Scanner sc) throws Exception {
        ViaEsportivaDAO dao = new ViaEsportivaDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.print("ID de la via esportiva (buit per sortir): ");
        String input = sc.nextLine();
        if (input.isBlank()) return;

        // Cerca la via per assegurar-nos que existeix abans d'intentar l'eliminació.
        ViaEsportiva v = dao.cercarPerId(Integer.parseInt(input));
        if (v == null) { System.out.println("Via no trobada."); return; }

        // Mostra la informació de la via per evitar errors de l'usuari.
        System.out.println("Via a eliminar:\n" + v);
        
        // --- DOBLE CONFIRMACIÓ DE SEGURETAT ---
        System.out.print("Confirma (y/n): ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }
        
        System.out.print("CONFIRMACIO FINAL (IRREVERSIBLE) y/n: ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }

        // Primer eliminem els trams dependents per mantenir la integritat referencial.
        tramDAO.eliminarPerViaEsportiva(v.getIdVia());
        // Després eliminem la via.
        dao.eliminar(v.getIdVia());
        System.out.println("Via esportiva eliminada correctament.");
    }

    /**
     * Gestiona l'eliminació d'una via clàssica i els seus trams associats.
     */
    private static void eliminarClassica(Scanner sc) throws Exception {
        ViaClassicaDAO dao = new ViaClassicaDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.print("ID de la via classica (buit per sortir): ");
        String input = sc.nextLine();
        if (input.isBlank()) return;

        ViaClassica v = dao.cercarPerId(Integer.parseInt(input));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via a eliminar:\n" + v);
        
        // Procediment de seguretat.
        System.out.print("Confirma (y/n): ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }
        
        System.out.print("CONFIRMACIO FINAL (IRREVERSIBLE) y/n: ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }

        // Esborrat en cascada manual.
        tramDAO.eliminarPerViaClassica(v.getIdVia());
        dao.eliminar(v.getIdVia());
        System.out.println("Via classica eliminada correctament.");
    }

    /**
     * Gestiona l'eliminació d'una via de gel i els seus trams (llargs).
     */
    private static void eliminarGel(Scanner sc) throws Exception {
        ViaGelDAO dao = new ViaGelDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.print("ID de la via gel (buit per sortir): ");
        String input = sc.nextLine();
        if (input.isBlank()) return;

        ViaGel v = dao.cercarPerId(Integer.parseInt(input));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via a eliminar:\n" + v);
        
        // Procediment de seguretat.
        System.out.print("Confirma (y/n): ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }
        
        System.out.print("CONFIRMACIO FINAL (IRREVERSIBLE) y/n: ");
        if (!sc.nextLine().equalsIgnoreCase("y")) { System.out.println("Cancel·lat."); return; }

        // Esborrat en cascada manual de la via de gel.
        tramDAO.eliminarPerViaGel(v.getIdVia());
        dao.eliminar(v.getIdVia());
        System.out.println("Via gel eliminada correctament.");
    }
}
