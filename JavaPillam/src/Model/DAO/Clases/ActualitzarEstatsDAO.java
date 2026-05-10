// Defineix el paquet per a la lògica de manteniment d'estats.
package Model.DAO.Clases;

import Model.DAO.Clases.Via.ViaClassicaDAO;
import Model.DAO.Clases.Via.ViaEsportivaDAO;
import Model.DAO.Clases.Via.ViaGelDAO;
import Model.Objectes.ViaClassica;
import Model.Objectes.ViaEsportiva;
import Model.Objectes.ViaGel;
import java.time.LocalDate;
import java.util.List;

/**
 * ActualitzarEstatsDAO: Classe de servei encarregada de verificar temporalment les vies.
 * Si una via estava tancada o en construcció i la seva data de finalització ja ha arribat,
 * la passa automàticament a estat "apte".
 */
public class ActualitzarEstatsDAO {

    /**
     * Mètode estàtic que realitza la comprovació global a totes les categories de vies.
     * Es podria cridar en iniciar l'aplicació o mitjançant una tasca programada.
     */
    public static void comprovarIActualitzar() {
        int actualitzades = 0; // Comptador per fer un resum final per consola.

        try {
            // Obtenim la data actual del sistema per comparar-la.
            LocalDate avui = LocalDate.now();

            // ── 1. GESTIÓ DE VIES ESPORTIVES ──
            ViaEsportivaDAO daoE = new ViaEsportivaDAO();
            // Cerquem les vies que no estan disponibles actualment.
            List<ViaEsportiva> esportives = daoE.llistarPerEstat("construccio");
            esportives.addAll(daoE.llistarPerEstat("tancada"));

            for (ViaEsportiva v : esportives) {
                // Si la via té una data límit de "no apte" i aquesta data és avui o anterior:
                if (v.getDataFiNoApte() != null && !avui.isBefore(v.getDataFiNoApte())) {
                    // Actualitzem l'estat a la BDD i netegem les dates de restricció.
                    daoE.actualitzarEstat(v.getIdVia(), "apte", null, null);
                    System.out.println("  [Esportiva] ID " + v.getIdVia()
                            + " - " + v.getNom()
                            + " | Fi tancament: " + v.getDataFiNoApte()
                            + " -> APTE");
                    actualitzades++;
                }
            }

            // ── 2. GESTIÓ DE VIES CLÀSSIQUES ──
            ViaClassicaDAO daoC = new ViaClassicaDAO();
            List<ViaClassica> classiques = daoC.llistarPerEstat("construccio");
            classiques.addAll(daoC.llistarPerEstat("tancada"));

            for (ViaClassica v : classiques) {
                if (v.getDataFiNoApte() != null && !avui.isBefore(v.getDataFiNoApte())) {
                    daoC.actualitzarEstat(v.getIdVia(), "apte", null, null);
                    System.out.println("  [Classica]  ID " + v.getIdVia()
                            + " - " + v.getNom()
                            + " | Fi tancament: " + v.getDataFiNoApte()
                            + " -> APTE");
                    actualitzades++;
                }
            }

            // ── 3. GESTIÓ DE VIES DE GEL ──
            ViaGelDAO daoG = new ViaGelDAO();
            List<ViaGel> gels = daoG.llistarPerEstat("construccio");
            gels.addAll(daoG.llistarPerEstat("tancada"));

            for (ViaGel v : gels) {
                if (v.getDataFiNoApte() != null && !avui.isBefore(v.getDataFiNoApte())) {
                    daoG.actualitzarEstat(v.getIdVia(), "apte", null, null);
                    System.out.println("  [Gel]        ID " + v.getIdVia()
                            + " - " + v.getNom()
                            + " | Fi tancament: " + v.getDataFiNoApte()
                            + " -> APTE");
                    actualitzades++;
                }
            }

            // Mostrem un missatge de confirmació si s'han produït canvis.
            if (actualitzades > 0)
                System.out.println("✓ " + actualitzades + " via(es) actualitzada(es) a APTE.\n");

        } catch (Exception e) {
            // Captura qualsevol error (connexió, SQL, etc.) per no aturar l'execució principal.
            System.out.println("Avís: no s'han pogut comprovar els estats: " + e.getMessage());
        }
    }
}
