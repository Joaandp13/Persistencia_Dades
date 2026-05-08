package Controller.Vies.TipusVies;

import Model.DAO.Clases.EscaladorDAO;
import Model.DAO.Clases.SectorDAO;
import Model.DAO.Clases.TramDAO;
import Model.DAO.Clases.Via.ViaClassicaDAO;
import Model.Objectes.Escalador;
import Model.Objectes.Sector;
import Model.Objectes.Tram;
import Model.Objectes.ViaClassica;
import java.time.LocalDate;
import java.util.Scanner;

public class crearClassica {
    public static void crearclassica() {
        Scanner sc = new Scanner(System.in);
        ViaClassicaDAO viaDAO = new ViaClassicaDAO();
        EscaladorDAO escaladorDAO = new EscaladorDAO();
        SectorDAO sectorDAO = new SectorDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.println("----------- CREAR VIA CLASSICA -----------");
        try {
            System.out.print("Nom de la via: ");
            String nom = sc.nextLine();

            System.out.print("ID del sector: ");
            int idSector = Integer.parseInt(sc.nextLine());

            Sector sector = sectorDAO.cercarPerId(idSector);
            if (sector == null) { System.out.println("Sector no trobat."); return; }
            if (!sector.getTipusVies().equals("classica_esportiva")) {
                System.out.println("Error: aquest sector nomes admet vies de gel."); return;
            }

            System.out.print("Grau global de la via (ex: 6c, 7b): ");
            String grau = sc.nextLine();

            System.out.print("Orientacio (N/NE/NO/SE/SO/E/O/S, buit si es desconeix): ");
            String orientacio = sc.nextLine();
            if (orientacio.isBlank()) orientacio = null;

            System.out.print("Ancoratge (friends/tascons/bagues/pitons/tricams/bigbros/spits/parabolts/quimics): ");
            String ancoratge = sc.nextLine();

            System.out.print("Tipus de roca (conglomerat/granit/calcaria/arenisca/altres): ");
            String tipusRoca = sc.nextLine();

            System.out.print("Nom del creador (buit si es desconeix): ");
            String nomCreador = sc.nextLine();
            Integer idCreador = null;
            if (!nomCreador.isBlank()) {
                Escalador creador = escaladorDAO.cercarPerNom(nomCreador);
                if (creador == null) {
                    System.out.println("Escalador no trobat. Cal donar-lo d'alta primer.");
                    return;
                }
                idCreador = creador.getIdEscalador();
            }

            System.out.print("Estat (apte / construccio / tancada): ");
            String estat = sc.nextLine();
            LocalDate dataInici = null, dataFi = null;
            if (!estat.equals("apte")) {
                System.out.print("Data inici no-apte (YYYY-MM-DD): ");
                dataInici = LocalDate.parse(sc.nextLine());
                System.out.print("Data fi no-apte (YYYY-MM-DD): ");
                dataFi = LocalDate.parse(sc.nextLine());
            }

            System.out.print("Restriccions (buit si no n'hi ha): ");
            String restriccions = sc.nextLine();
            if (restriccions.isBlank()) restriccions = null;

            // Crear la via amb llargada_total = null, s'actualitzara al afegir trams
            ViaClassica via = new ViaClassica(nom, idSector, grau, orientacio, estat,
                dataInici, dataFi, ancoratge, tipusRoca, idCreador, restriccions);
            viaDAO.inserir(via);

            // Afegir trams
            System.out.print("Quants trams (llargs) te la via? ");
            int numTrams = Integer.parseInt(sc.nextLine());

            for (int i = 1; i <= numTrams; i++) {
                System.out.println("-- Tram L" + i + " --");
                System.out.print("  Llargada (15-30 m): ");
                int llargadaTram = Integer.parseInt(sc.nextLine());
                System.out.print("  Grau del tram (max 8b): ");
                String grauTram = sc.nextLine();

                Tram tram = new Tram(via.getIdVia(), null, null, i, llargadaTram, grauTram);
                tramDAO.inserir(tram);
            }

            // Recalcular llargada_total sumant els trams inserits
            viaDAO.actualitzarLlargadaTotal(via.getIdVia());
            System.out.println("Via classica creada amb id: " + via.getIdVia());

        } catch (NumberFormatException ex) {
            System.out.println("Format de número incorrecte.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
