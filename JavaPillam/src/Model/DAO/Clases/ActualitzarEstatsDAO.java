package Model.DAO.Clases;

import Model.DAO.Clases.Via.ViaClassicaDAO;
import Model.DAO.Clases.Via.ViaEsportivaDAO;
import Model.DAO.Clases.Via.ViaGelDAO;
import Model.Objectes.ViaClassica;
import Model.Objectes.ViaEsportiva;
import Model.Objectes.ViaGel;
import java.time.LocalDate;
import java.util.List;

public class ActualitzarEstatsDAO {

    public static void comprovarIActualitzar() {
        int actualitzades = 0;

        try {
            LocalDate avui = LocalDate.now();

            // ── Via Esportiva ──
            ViaEsportivaDAO daoE = new ViaEsportivaDAO();
            List<ViaEsportiva> esportives = daoE.llistarPerEstat("construccio");
            esportives.addAll(daoE.llistarPerEstat("tancada"));
            for (ViaEsportiva v : esportives) {
                if (v.getDataFiNoApte() != null && !avui.isBefore(v.getDataFiNoApte())) {
                    daoE.actualitzarEstat(v.getIdVia(), "apte", null, null);
                    System.out.println("  [Esportiva] ID " + v.getIdVia()
                            + " - " + v.getNom()
                            + " | Fi tancament: " + v.getDataFiNoApte()
                            + " -> APTE");
                    actualitzades++;
                }
            }

            // ── Via Classica ──
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

            // ── Via Gel ──
            ViaGelDAO daoG = new ViaGelDAO();
            List<ViaGel> gels = daoG.llistarPerEstat("construccio");
            gels.addAll(daoG.llistarPerEstat("tancada"));
            for (ViaGel v : gels) {
                if (v.getDataFiNoApte() != null && !avui.isBefore(v.getDataFiNoApte())) {
                    daoG.actualitzarEstat(v.getIdVia(), "apte", null, null);
                    System.out.println("  [Gel]       ID " + v.getIdVia()
                            + " - " + v.getNom()
                            + " | Fi tancament: " + v.getDataFiNoApte()
                            + " -> APTE");
                    actualitzades++;
                }
            }

            if (actualitzades > 0)
                System.out.println("✓ " + actualitzades + " via(es) actualitzada(es) a APTE.\n");

        } catch (Exception e) {
            System.out.println("Avís: no s'han pogut comprovar els estats: " + e.getMessage());
        }
    }
}