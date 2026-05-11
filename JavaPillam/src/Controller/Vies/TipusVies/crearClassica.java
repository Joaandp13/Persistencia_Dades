// Paquet que gestiona la lògica de control per a la creació de tipus específics de vies.
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

/**
 * Classe controladora que executa el flux de creació d'una via clàssica per consola.
 * Actua com a intermediari entre l'entrada d'usuari i la persistència a la base de dades.
 */
public class crearClassica {

    /**
     * Mètode principal que guia l'usuari a través de la recollida de dades.
     * Inclou validacions de negoci, gestió de nuls i càlcul de dades derivades (trams).
     */
    public static void crearclassica() {
        Scanner sc = new Scanner(System.in);
        
        // Instanciació de DAOs per interactuar amb les diferents taules de la BDD.
        ViaClassicaDAO viaDAO = new ViaClassicaDAO();
        EscaladorDAO escaladorDAO = new EscaladorDAO();
        SectorDAO sectorDAO = new SectorDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.println("----------- CREAR VIA CLASSICA -----------");
        
        try {
            // ── RECOLLIDA DE DADES BÀSIQUES ──
            System.out.print("Nom de la via: ");
            String nom = sc.nextLine();

            System.out.print("ID del sector: ");
            int idSector = Integer.parseInt(sc.nextLine());

            // ── VALIDACIÓ DEL SECTOR ──
            // Comprovem que el sector existeixi i que sigui compatible amb escalada en roca.
            Sector sector = sectorDAO.cercarPerId(idSector);
            if (sector == null) { 
                System.out.println("Sector no trobat."); 
                return; 
            }
            // Segons el model, si el sector només és de gel, no permetem crear-hi una clàssica.
            if (!sector.getTipusVies().equals("classica_esportiva")) {
                System.out.println("Error: aquest sector nomes admet vies de gel."); 
                return;
            }

            System.out.print("Grau global de la via (ex: 6c, 7b): ");
            String grau = sc.nextLine();

            // ── ATRIBUTS OPCIONALS ──
            System.out.print("Orientacio (N/NE/NO/SE/SO/E/O/S, buit si es desconeix): ");
            String orientacio = sc.nextLine();
            if (orientacio.isBlank()) orientacio = null; // Guardem null si no hi ha dades.

            System.out.print("Ancoratge (friends/tascons/bagues/pitons/tricams/bigbros/spits/parabolts/quimics): ");
            String ancoratge = sc.nextLine();

            System.out.print("Tipus de roca (conglomerat/granit/calcaria/arenisca/altres): ");
            String tipusRoca = sc.nextLine();

            // ── VINCULACIÓ AMB L'ESCALADOR (CREADOR) ──
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

            // ── GESTIÓ DE L'ESTAT I RESTRICCIONS ──
            System.out.print("Estat (apte / construccio / tancada): ");
            String estat = sc.nextLine();
            LocalDate dataInici = null, dataFi = null;
            
            // Si la via no és "apte", demanem el període de restricció.
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
            // Es crea l'objecte sense llargada_total (serà calculada sumant els trams després).
            ViaClassica via = new ViaClassica(nom, idSector, grau, orientacio, estat,
                dataInici, dataFi, ancoratge, tipusRoca, idCreador, restriccions);
            viaDAO.inserir(via);

            // ── INSERCIÓ DE TRAMS / LLARGAMENTS (FASE 2) ──
            System.out.print("Quants trams (llargs) te la via? ");
            int numTrams = Integer.parseInt(sc.nextLine());

            for (int i = 1; i <= numTrams; i++) {
                System.out.println("-- Tram L" + i + " --");
                System.out.print("  Llargada (15-30 m): ");
                int llargadaTram = Integer.parseInt(sc.nextLine());
                System.out.print("  Grau del tram (max 8b): ");
                String grauTram = sc.nextLine();

                // Creem el tram vinculat a l'ID de la via clàssica acabada de generar.
                Tram tram = new Tram(via.getIdVia(), null, null, i, llargadaTram, grauTram);
                tramDAO.inserir(tram);
            }

            // ── FINALITZACIÓ I ACTUALITZACIÓ DE DADES DERIVADES ──
            // Executem el mètode del DAO que suma les llargades de tots els trams inserits.
            viaDAO.actualitzarLlargadaTotal(via.getIdVia());
            System.out.println("Via classica creada amb id: " + via.getIdVia());

        } catch (NumberFormatException ex) {
            // Error quan s'espera un número (ID, llargada) i s'introdueix text.
            System.out.println("Format de número incorrecte.");
        } catch (IllegalArgumentException ex) {
            // Error de dades que no compleixen els requisits del constructor.
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            // Captura de qualsevol altre error inesperat (SQL, NullPointer, etc.).
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
