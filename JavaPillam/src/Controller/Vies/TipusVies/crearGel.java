// Paquet del controlador per a la gestió de la creació de vies de gel.
package Controller.Vies.TipusVies;

import Model.DAO.Clases.EscaladorDAO;
import Model.DAO.Clases.SectorDAO;
import Model.DAO.Clases.TramDAO;
import Model.DAO.Clases.Via.ViaGelDAO;
import Model.Objectes.Escalador;
import Model.Objectes.Sector;
import Model.Objectes.Tram;
import Model.Objectes.ViaGel;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Controlador que gestiona el procés de creació d'una cascada de gel.
 * Coordina la interacció per consola, les validacions de negoci i la persistència.
 */
public class crearGel {

    /**
     * Mètode que guia l'usuari en la inserció d'una via de gel.
     * Inclou la gestió de múltiples trams i el recàlcul automàtic de la llargada total.
     */
    public static void creargel() {
        Scanner sc = new Scanner(System.in);
        
        // Inicialització dels objectes d'accés a dades (DAOs).
        ViaGelDAO viaDAO = new ViaGelDAO();
        EscaladorDAO escaladorDAO = new EscaladorDAO();
        SectorDAO sectorDAO = new SectorDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.println("----------- CREAR VIA GEL -----------");
        
        try {
            // ── RECOLLIDA DE DADES GENERALS ──
            System.out.print("Nom de la via: ");
            String nom = sc.nextLine();

            System.out.print("ID del sector: ");
            int idSector = Integer.parseInt(sc.nextLine());

            // ── VALIDACIÓ DE TIPUS DE SECTOR ──
            // Verifiquem que el sector existeixi i que estigui catalogat com a sector de gel.
            Sector sector = sectorDAO.cercarPerId(idSector);
            if (sector == null) { 
                System.out.println("Sector no trobat."); 
                return; 
            }
            if (!sector.getTipusVies().equals("gel")) {
                System.out.println("Error: aquest sector nomes admet vies classica/esportiva."); 
                return;
            }

            System.out.print("Grau global (max 8b, ex: 6a, 7b): ");
            String grau = sc.nextLine();

            // ── ATRIBUTS TÈCNICS ──
            System.out.print("Orientacio (N/NE/NO/SE/SO/E/O/S, buit si es desconeix): ");
            String orientacio = sc.nextLine();
            if (orientacio.isBlank()) orientacio = null;

            System.out.print("Ancoratge (friends/tascons/bagues/pitons/tricams/bigbros): ");
            String ancoratge = sc.nextLine();

            System.out.print("Tipus de roca (conglomerat/granit/calcaria/arenisca/altres): ");
            String tipusRoca = sc.nextLine();

            // ── IDENTIFICACIÓ DEL CREADOR ──
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

            // ── ESTAT I RESTRICCIONS TEMPORALS ──
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

            // ── INSERCIÓ DE LA VIA (FASE 1) ──
            ViaGel via = new ViaGel(nom, idSector, grau, orientacio, estat,
                dataInici, dataFi, ancoratge, tipusRoca, idCreador, restriccions);
            viaDAO.inserir(via);

            // ── CREACIÓ DINÀMICA DE TRAMS (FASE 2) ──
            System.out.print("Quants trams te la via? ");
            int numTrams = Integer.parseInt(sc.nextLine());

            for (int i = 1; i <= numTrams; i++) {
                System.out.println("-- Tram L" + i + " --");
                System.out.print("  Llargada (15-30 m): ");
                int llargadaTram = Integer.parseInt(sc.nextLine());
                System.out.print("  Grau del tram (max 8b): ");
                String grauTram = sc.nextLine();

                // Creem el tram associant-lo a l'ID de la via de gel (id_via_gel).
                Tram tram = new Tram(null, null, via.getIdVia(), i, llargadaTram, grauTram);
                tramDAO.inserir(tram);
            }

            // ── ACTUALITZACIÓ FINAL ──
            // Cridem al DAO per sumar les llargades dels trams i actualitzar la via principal.
            viaDAO.actualitzarLlargadaTotal(via.getIdVia());
            System.out.println("Via gel creada amb id: " + via.getIdVia());

        } catch (NumberFormatException ex) {
            System.out.println("Format de número incorrecte.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
