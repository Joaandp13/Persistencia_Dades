package Controller.Vies;

import Model.DAO.Clases.ViaEsportivaDAO;
import Model.DAO.Clases.ViaClassicaDAO;
import Model.DAO.Clases.ViaGelDAO;
import Model.Objectes.ViaEsportiva;
import Model.Objectes.ViaClassica;
import Model.Objectes.ViaGel;
import java.util.List;
import java.util.Scanner;

public class llistarVies {

    // ── CRUD basic ────────────────────────────────────────────

    public static void llistarUna() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- LLISTAR VIA -----------");
        System.out.println("1. ESPORTIVA  2. CLASSICA  3. GEL");
        System.out.print("Tipus: ");
        try {
            int tipus = Integer.parseInt(sc.nextLine());
            System.out.print("ID de la via: ");
            int id = Integer.parseInt(sc.nextLine());

            switch (tipus) {
                case 1 -> {
                    ViaEsportiva v = new ViaEsportivaDAO().cercarPerId(id);
                    if (v == null) System.out.println("Via no trobada.");
                    else System.out.println(v);
                }
                case 2 -> {
                    ViaClassica v = new ViaClassicaDAO().cercarPerId(id);
                    if (v == null) System.out.println("Via no trobada.");
                    else System.out.println(v);
                }
                case 3 -> {
                    ViaGel v = new ViaGelDAO().cercarPerId(id);
                    if (v == null) System.out.println("Via no trobada.");
                    else System.out.println(v);
                }
                default -> System.out.println("Tipus no valid.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Has d'introduir un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static void llistarTotes() {
        System.out.println("----------- TOTES LES VIES -----------");
        try {
            System.out.println("--- ESPORTIVES ---");
            new ViaEsportivaDAO().llistarTotes().forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] " + v.getLlargadaTotal() + "m"
                    + " | " + v.getEstat()));

            System.out.println("--- CLASSIQUES ---");
            new ViaClassicaDAO().llistarTotes().forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] " + v.getLlargadaTotal() + "m"
                    + " | " + v.getEstat()));

            System.out.println("--- GEL ---");
            new ViaGelDAO().llistarTotes().forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] " + v.getLlargadaTotal() + "m"
                    + " | " + v.getEstat()));

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ── Consulta 1: vies disponibles (apte) d'una escola ─────

    public static void viesDisponiblesEscola() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- VIES DISPONIBLES D'UNA ESCOLA -----------");
        try {
            System.out.print("ID de l'escola: ");
            int idEscola = Integer.parseInt(sc.nextLine());

            System.out.println("--- ESPORTIVES ---");
            List<ViaEsportiva> esportives = new ViaEsportivaDAO().llistarPerEscola(idEscola);
            if (esportives.isEmpty()) System.out.println("  Cap via esportiva disponible.");
            else esportives.forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] " + v.getLlargadaTotal() + "m"
                    + " | Sector: " + v.getIdSector()));

            System.out.println("--- CLASSIQUES ---");
            List<ViaClassica> classiques = new ViaClassicaDAO().llistarPerEstat("apte");
            if (classiques.isEmpty()) System.out.println("  Cap via classica disponible.");
            else classiques.forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] " + v.getLlargadaTotal() + "m"));

            System.out.println("--- GEL ---");
            List<ViaGel> gels = new ViaGelDAO().llistarPerEstat("apte");
            if (gels.isEmpty()) System.out.println("  Cap via gel disponible.");
            else gels.forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] " + v.getLlargadaTotal() + "m"));

        } catch (NumberFormatException ex) {
            System.out.println("Has d'introduir un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ── Consulta 2: cercar per rang de dificultat ─────────────

    public static void cercarPerRangGrau() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- CERCAR VIES PER RANG DE DIFICULTAT -----------");
        try {
            System.out.print("Grau minim (ex: 6a): ");  String min = sc.nextLine();
            System.out.print("Grau maxim (ex: 7b+): "); String max = sc.nextLine();
            System.out.print("Tipus (esportiva / classica / gel / tots): "); String tipus = sc.nextLine();

            if (tipus.equals("esportiva") || tipus.equals("tots")) {
                System.out.println("--- ESPORTIVES ---");
                new ViaEsportivaDAO().cercarPerRangGrau(min, max).forEach(v ->
                    System.out.println("  " + v.getNom()
                        + " [" + v.getGrau() + "]"
                        + " | Sector: " + v.getIdSector()));
            }
            if (tipus.equals("classica") || tipus.equals("tots")) {
                System.out.println("--- CLASSIQUES ---");
                new ViaClassicaDAO().cercarPerRangGrau(min, max).forEach(v ->
                    System.out.println("  " + v.getNom()
                        + " [" + v.getGrau() + "]"
                        + " | Sector: " + v.getIdSector()));
            }
            if (tipus.equals("gel") || tipus.equals("tots")) {
                System.out.println("--- GEL ---");
                new ViaGelDAO().cercarPerRangGrau(min, max).forEach(v ->
                    System.out.println("  " + v.getNom()
                        + " [" + v.getGrau() + "]"
                        + " | Sector: " + v.getIdSector()));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ── Consulta 3: cercar per estat ──────────────────────────

    public static void cercarPerEstat() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- CERCAR VIES PER ESTAT -----------");
        try {
            System.out.print("Estat (apte / construccio / tancada): ");
            String estat = sc.nextLine();

            System.out.println("--- ESPORTIVES ---");
            new ViaEsportivaDAO().llistarPerEstat(estat).forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "]"
                    + " | Fi tancament: " + v.getDataFiNoApte()));

            System.out.println("--- CLASSIQUES ---");
            new ViaClassicaDAO().llistarPerEstat(estat).forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "]"
                    + " | Fi tancament: " + v.getDataFiNoApte()));

            System.out.println("--- GEL ---");
            new ViaGelDAO().llistarPerEstat(estat).forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "]"
                    + " | Fi tancament: " + v.getDataFiNoApte()));

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ── Consulta 4: vies recentment tornades a apte ───────────

    public static void viesRecentmentAptes() {
        System.out.println("----------- VIES RECENTMENT TORNADES A APTE -----------");
        try {
            List<ViaEsportiva> llista = new ViaEsportivaDAO().llistarRecentmentAptes();
            if (llista.isEmpty()) { System.out.println("Cap via recentment tornada a apte."); return; }
            for (ViaEsportiva v : llista)
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "]"
                    + " | Fi tancament: " + v.getDataFiNoApte());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // ── Consulta 5: vies mes llargues d'una escola ────────────

    public static void viesMesLlargues() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- VIES MES LLARGUES D'UNA ESCOLA -----------");
        try {
            System.out.print("ID de l'escola: ");
            int idEscola = Integer.parseInt(sc.nextLine());

            List<ViaEsportiva> llista = new ViaEsportivaDAO().llistarMesLlarguesPerEscola(idEscola);
            if (llista.isEmpty()) { System.out.println("Cap via trobada."); return; }
            for (ViaEsportiva v : llista)
                System.out.println("  " + v.getNom()
                    + " - " + v.getLlargadaTotal() + "m"
                    + " [" + v.getGrau() + "]");

        } catch (NumberFormatException ex) {
            System.out.println("Has d'introduir un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
