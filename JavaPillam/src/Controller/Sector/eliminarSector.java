package Controller.Sector;

import Model.DAO.Clases.*;
import Model.DAO.Clases.Via.*;
import Model.Objectes.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class eliminarSector {

    private static final SectorDAO sectorDAO = new SectorDAO();
    private static final ViaEsportivaDAO viaEsportivaDAO = new ViaEsportivaDAO();
    private static final ViaClassicaDAO viaClassicaDAO = new ViaClassicaDAO();
    private static final ViaGelDAO viaGelDAO = new ViaGelDAO();
    private static final TramDAO tramDAO = new TramDAO();

    public static void eliminarSect() {
        Scanner sc = new Scanner(System.in);

        System.out.println("----------- ELIMINAR SECTOR -----------");

        try {
            while (true) {
                System.out.print("ID del sector a eliminar (buit per sortir): ");
                String input = sc.nextLine();
                if (input.isBlank()) return;

                Sector s = sectorDAO.cercarPerId(Integer.parseInt(input));
                if (s == null) {
                    System.out.println("Sector no trobat.");
                    continue;
                }

                System.out.println("Sector seleccionat:\n" + s);
                mostrarResum(s.getIdSector());

                System.out.print("\nEliminar sector i TOTES les dades associades? (y/n): ");
                if (!sc.nextLine().equalsIgnoreCase("y")) continue;

                System.out.print("CONFIRMACIÓ FINAL - AQUESTA ACCIÓ ÉS IRREVERSIBLE (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    eliminarEnCadena(s.getIdSector());
                    System.out.println("Sector i totes les dades associades eliminats correctament.");
                    return;
                }
            }

        } catch (NumberFormatException ex) {
            System.out.println("L'id ha de ser un número.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void mostrarResum(int idSector) throws SQLException {
        List<ViaEsportiva> esportives = viaEsportivaDAO.llistarPerSector(idSector);
        List<ViaClassica>  classiques = viaClassicaDAO.llistarPerSector(idSector);
        List<ViaGel>       gels       = viaGelDAO.llistarPerSector(idSector);

        int totalVies  = esportives.size() + classiques.size() + gels.size();
        int totalTrams = 0;

        for (ViaEsportiva v : esportives)
            totalTrams += tramDAO.llistarPerViaEsportiva(v.getIdVia()).size();
        for (ViaClassica v : classiques)
            totalTrams += tramDAO.llistarPerViaClassica(v.getIdVia()).size();
        for (ViaGel v : gels)
            totalTrams += tramDAO.llistarPerViaGel(v.getIdVia()).size();

        System.out.println("\nS'eliminaran:");
        System.out.println("  - " + totalVies + " via(es)");
        System.out.println("  - " + totalTrams + " tram(s)");
    }

    public static void eliminarEnCadena(int idSector) throws SQLException {
        for (ViaEsportiva v : viaEsportivaDAO.llistarPerSector(idSector)) {
            tramDAO.eliminarPerViaEsportiva(v.getIdVia());
            viaEsportivaDAO.eliminar(v.getIdVia());
        }
        for (ViaClassica v : viaClassicaDAO.llistarPerSector(idSector)) {
            tramDAO.eliminarPerViaClassica(v.getIdVia());
            viaClassicaDAO.eliminar(v.getIdVia());
        }
        for (ViaGel v : viaGelDAO.llistarPerSector(idSector)) {
            tramDAO.eliminarPerViaGel(v.getIdVia());
            viaGelDAO.eliminar(v.getIdVia());
        }
        sectorDAO.eliminar(idSector);
    }
}
