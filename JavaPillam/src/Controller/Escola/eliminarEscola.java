package Controller.Escola;

import Model.DAO.Clases.*;
import Model.DAO.Clases.Via.*;
import Model.Objectes.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class eliminarEscola {

    // DAOs com a camps estàtics — es reutilitzen a mostrarResum i eliminarEnCadena
    private static final EscolaDAO escolaDAO         = new EscolaDAO();
    private static final SectorDAO sectorDAO         = new SectorDAO();
    private static final ViaEsportivaDAO viaEsportivaDAO = new ViaEsportivaDAO();
    private static final ViaClassicaDAO  viaClassicaDAO  = new ViaClassicaDAO();
    private static final ViaGelDAO       viaGelDAO       = new ViaGelDAO();
    private static final TramDAO tramDAO             = new TramDAO();

    public static void eliminarEsco() {
        Scanner sc = new Scanner(System.in);

        System.out.println("----------- ELIMINAR ESCOLA -----------");

        try {
            while (true) {
                System.out.print("ID de l'escola a eliminar (buit per sortir): ");
                String input = sc.nextLine();
                if (input.isBlank()) return;

                Escola e = escolaDAO.cercarPerId(Integer.parseInt(input));
                if (e == null) {
                    System.out.println("Escola no trobada.");
                    continue;
                }

                System.out.println("Escola seleccionada:\n" + e);
                mostrarResum(e.getIdEscola()); // mostra quant s'eliminarà

                System.out.print("\nEliminar escola i TOTES les dades associades? (y/n): ");
                if (!sc.nextLine().equalsIgnoreCase("y")) continue;

                System.out.print("CONFIRMACIÓ FINAL - AQUESTA ACCIÓ ÉS IRREVERSIBLE (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    // Eliminació: trams -> vies -> sectors -> escola
                    eliminarEnCadena(e.getIdEscola());
                    System.out.println("Escola i totes les dades associades eliminades correctament.");
                    return;
                }
            }

        } catch (NumberFormatException ex) {
            System.out.println("L'id ha de ser un número.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /** Compta i mostra el total de dades que s'eliminaran abans de confirmar. */
    private static void mostrarResum(int idEscola) throws SQLException {
        List<Sector> sectors = sectorDAO.llistarPerEscola(idEscola);
        int totalVies = 0;
        int totalTrams = 0;

        for (Sector s : sectors) {
            int idSector = s.getIdSector();
            List<ViaEsportiva> esportives = viaEsportivaDAO.llistarPerSector(idSector);
            List<ViaClassica>  classiques = viaClassicaDAO.llistarPerSector(idSector);
            List<ViaGel>       gels       = viaGelDAO.llistarPerSector(idSector);

            totalVies += esportives.size() + classiques.size() + gels.size();

            for (ViaEsportiva v : esportives)
                totalTrams += tramDAO.llistarPerViaEsportiva(v.getIdVia()).size();
            for (ViaClassica v : classiques)
                totalTrams += tramDAO.llistarPerViaClassica(v.getIdVia()).size();
            for (ViaGel v : gels)
                totalTrams += tramDAO.llistarPerViaGel(v.getIdVia()).size();
        }

        System.out.println("\nS'eliminaran:");
        System.out.println("  - " + sectors.size() + " sector(s)");
        System.out.println("  - " + totalVies + " via(es)");
        System.out.println("  - " + totalTrams + " tram(s)");
    }

    /** Elimina en cascada: trams -> vies -> sectors -> escola. */
    private static void eliminarEnCadena(int idEscola) throws SQLException {
        List<Sector> sectors = sectorDAO.llistarPerEscola(idEscola);

        for (Sector s : sectors) {
            eliminarSector.eliminarEnCadena(s.getIdSector());
        }

        escolaDAO.eliminar(idEscola);
    }
}
