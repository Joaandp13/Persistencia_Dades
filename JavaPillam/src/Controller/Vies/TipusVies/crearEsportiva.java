// Paquet del controlador per a la creació de vies tipus Esportiva.
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

/**
 * Controlador encarregat de la lògica de creació de vies esportives via terminal.
 * A diferència de la clàssica, aquí la relació via-tram és 1:1 de forma automàtica.
 */
public class crearEsportiva {

    /**
     * Executa el diàleg interactiu per recollir les dades de la via esportiva.
     * Gestiona validacions de sector, cerca de creadors i automatització del primer tram.
     */
    public static void crearesportiva() {
        Scanner sc = new Scanner(System.in);
        
        // Inicialització de DAOs per a la persistència i consulta de dades.
        ViaEsportivaDAO viaDAO = new ViaEsportivaDAO();
        EscaladorDAO escaladorDAO = new EscaladorDAO();
        SectorDAO sectorDAO = new SectorDAO();
        TramDAO tramDAO = new TramDAO();

        System.out.println("----------- CREAR VIA ESPORTIVA -----------");
        
        try {
            // ── RECOLLIDA DE DADES BÀSIQUES ──
            System.out.print("Nom de la via: ");
            String nom = sc.nextLine();

            System.out.print("ID del sector: ");
            int idSector = Integer.parseInt(sc.nextLine());

            // ── VALIDACIÓ DEL SECTOR ──
            // Verifiquem si el sector existeix i si és apte per a roca (no gel).
            Sector sector = sectorDAO.cercarPerId(idSector);
            if (sector == null) { 
                System.out.println("Sector no trobat."); 
                return; 
            }
            if (!sector.getTipusVies().equals("classica_esportiva")) {
                System.out.println("Error: aquest sector nomes admet vies de gel."); 
                return;
            }

            // En esportiva, la llargada es demana directament perquè sol ser d'un sol tram.
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

            // ── GESTIÓ D'ESTATS I TEMPORALITAT ──
            System.out.print("Estat (apte / construccio / tancada): ");
            String estat = sc.nextLine();
            LocalDate dataInici = null, dataFi = null;
            
            // Si la via no és "apte", demanem les dates de vigència de l'estat.
            if (!estat.equals("apte")) {
                System.out.print("Data inici no-apte (YYYY-MM-DD): ");
                dataInici = LocalDate.parse(sc.nextLine());
                System.out.print("Data fi no-apte (YYYY-MM-DD): ");
                dataFi = LocalDate.parse(sc.nextLine());
            }

            System.out.print("Restriccions (buit si no n'hi ha): ");
            String restriccions = sc.nextLine();
            if (restriccions.isBlank()) restriccions = null;

            // ── INSERCIÓ DE LA VIA ──
            ViaEsportiva via = new ViaEsportiva(nom, idSector, llargada, grau, orientacio,
                estat, dataInici, dataFi, ancoratge, tipusRoca, idCreador, restriccions);
            viaDAO.inserir(via);

            // ── AUTOMATITZACIÓ DEL TRAM ──
            // Per definició, una via esportiva en aquesta app té un únic tram 
            // que hereta la llargada i el grau de la via.
            Tram tram = new Tram(null, via.getIdVia(), null, 1, llargada, grau);
            tramDAO.inserir(tram);

            System.out.println("Via esportiva creada correctament amb id: " + via.getIdVia());

        } catch (NumberFormatException ex) {
            // Error en la conversió de text a número (IDs, llargades).
            System.out.println("Format de número incorrecte.");
        } catch (IllegalArgumentException ex) {
            // Error en la validació de formats (dates o valors prohibits).
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            // Captura general d'errors de connexió o nuls inesperats.
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
