package Controller.Vies.TipusVies;

import Model.DAO.Clases.EscaladorDAO;
import Model.DAO.Clases.SectorDAO;
import Model.DAO.Clases.TramDAO;
import Model.DAO.Clases.Via.ViaEsportivaDAO;
import Model.Objectes.Escalador;
import Model.Objectes.Sector;
import Model.Objectes.Tram;
import Model.Objectes.ViaEsportiva;
import java.time.LocalDate;
import java.util.Scanner;

public class crearEsportiva {
    public static void crearesportiva() {
        Scanner sc = new Scanner(System.in);
        ViaEsportivaDAO viaDAO = new ViaEsportivaDAO();
        EscaladorDAO escaladorDAO = new EscaladorDAO();
        SectorDAO sectorDAO = new SectorDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.println("----------- CREAR VIA ESPORTIVA -----------");
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

            System.out.print("Llargada (5-30 m): ");
            int llargada = Integer.parseInt(sc.nextLine());

            System.out.print("Grau de dificultat (ex: 6a, 7b+, 9c+): ");
            String grau = sc.nextLine();

            System.out.print("Orientacio (N/NE/NO/SE/SO/E/O/S): ");
            String orientacio = sc.nextLine();
            if (orientacio.isBlank()) orientacio = null;

            System.out.print("Ancoratge (spits / parabolts / quimics): ");
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

            ViaEsportiva via = new ViaEsportiva(nom, idSector, llargada, grau, orientacio,
                estat, dataInici, dataFi, ancoratge, tipusRoca, idCreador, restriccions);
            viaDAO.inserir(via);

            // Crear l'unic tram automaticament (esportiva = sempre 1 tram)
            Tram tram = new Tram(null, via.getIdVia(), null, 1, llargada, grau);
            tramDAO.inserir(tram);

            System.out.println("Via esportiva creada correctament amb id: " + via.getIdVia());

        } catch (NumberFormatException ex) {
            System.out.println("Format de número incorrecte.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
