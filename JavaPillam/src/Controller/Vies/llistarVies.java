// Paquet destinat a la lògica de consulta i llistat de les vies.
package Controller.Vies;

import Model.DAO.Clases.Via.*;
import Model.Objectes.*;
import Vista.Utils;

import java.util.List;
import java.util.Scanner;

/**
 * Controlador de llistats i consultes de vies.
 * Centralitza totes les operacions de visualització de dades segons diferents criteris.
 */
public class llistarVies {

    // ── CRUD BÀSIC: LLISTATS GENERALS ────────────────────────────────────────────

    /**
     * Permet consultar una única via específica introduint el seu ID i tipus.
     */
    public static void llistarUna() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- LLISTAR VIA -----------");
        System.out.println("1. ESPORTIVA  2. CLASSICA  3. GEL");
        System.out.print("Tipus: ");
        try {
            int tipus = Integer.parseInt(sc.nextLine());
            System.out.print("ID de la via: ");
            int id = Integer.parseInt(sc.nextLine());

            // Segons el tipus, instanciem el DAO corresponent per fer la cerca.
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
        Utils.esperarEnter(); // Pausa la consola per permetre la lectura.
    }

    /**
     * Mostra un resum resumit de totes les vies de totes les categories a la BDD.
     */
    public static void llistarTotes() {
        System.out.println("----------- TOTES LES VIES -----------");
        try {
            // Llistat d'Esportives: Llargada sempre definida.
            System.out.println("--- ESPORTIVES ---");
            new ViaEsportivaDAO().llistarTotes().forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] " + v.getLlargadaTotal() + "m"
                    + " | " + v.getEstat()));

            // Llistat de Clàssiques: Gestiona el cas de llargada encara no calculada (pendent).
            System.out.println("--- CLASSIQUES ---");
            new ViaClassicaDAO().llistarTotes().forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] "
                    + (v.getLlargadaTotal() != null ? v.getLlargadaTotal() + "m" : "pendent")
                    + " | " + v.getEstat()));

            // Llistat de Gel.
            System.out.println("--- GEL ---");
            new ViaGelDAO().llistarTotes().forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom()
                    + " [" + v.getGrau() + "] "
                    + (v.getLlargadaTotal() != null ? v.getLlargadaTotal() + "m" : "pendent")
                    + " | " + v.getEstat()));

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    // ── CONSULTES DE NEGOCI (FILTRATGE) ──────────────────────────────────────────

    /**
     * Consulta 1: Filtra les vies d'una escola que tenen estat 'apte'.
     */
    public static void viesDisponiblesEscola() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- VIES DISPONIBLES D'UNA ESCOLA -----------");
        try {
            System.out.print("ID de l'escola: ");
            int idEscola = Integer.parseInt(sc.nextLine());

            System.out.println("--- ESPORTIVES ---");
            List<ViaEsportiva> esportives = new ViaEsportivaDAO().llistarAptesPerEscola(idEscola);
            if (esportives.isEmpty()) System.out.println("  Cap via esportiva disponible.");
            else esportives.forEach(v -> System.out.println("  [" + v.getIdVia() + "] " + v.getNom() + "..."));

            // Es repeteix la lògica per Clàssica i Gel...
            // ... (codi repetitiu omès per brevetat en el comentari)
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    /**
     * Consulta 2: Filtra les vies segons un rang de dificultat (ex: de 6a a 7b).
     */
    public static void cercarPerRangGrau() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- CERCAR VIES PER RANG DE DIFICULTAT -----------");
        try {
            System.out.print("Grau minim (ex: 6a): ");  String min = sc.nextLine();
            System.out.print("Grau maxim (ex: 7b+): "); String max = sc.nextLine();
            System.out.print("Tipus (esportiva / classica / gel / tots): "); String tipus = sc.nextLine();

            // Executa la cerca segons el criteri de tipus escollit per l'usuari.
            if (tipus.equals("esportiva") || tipus.equals("tots")) {
                System.out.println("--- ESPORTIVES ---");
                new ViaEsportivaDAO().llistarPerRangGrau(min, max).forEach(v ->
                    System.out.println("  " + v.getNom() + " [" + v.getGrau() + "]"));
            }
            // (Mateixa lògica per la resta de tipus)
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    /**
     * Consulta 3: Filtra vies per estat (apte, tancada, en construcció).
     */
    public static void cercarPerEstat() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- CERCAR VIES PER ESTAT -----------");
        try {
            System.out.print("Estat (apte / construccio / tancada): ");
            String estat = sc.nextLine();

            // Mostra també la data de finalització del tancament si n'hi ha.
            new ViaEsportivaDAO().llistarPerEstat(estat).forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom() + " | Fi tancament: " + v.getDataFiNoApte()));
            // ... (resta de categories)
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    /**
     * Consulta 4: Mostra vies que han passat a estat 'apte' recentment.
     */
    public static void viesRecentmentAptes() {
        System.out.println("----------- VIES RECENTMENT TORNADES A APTE -----------");
        try {
            // Útil per als escaladors que volen saber quines vies s'acaben de reobrir.
            new ViaEsportivaDAO().llistarRecentmentAptes().forEach(v ->
                System.out.println("  [" + v.getIdVia() + "] " + v.getNom() + "..."));
            // ... (resta de categories)
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }

    /**
     * Consulta 5: Mostra les vies amb major longitud total d'una escola concreta.
     */
    public static void viesMesLlargues() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- VIES MES LLARGUES D'UNA ESCOLA -----------");
        try {
            System.out.print("ID de l'escola: ");
            int idEscola = Integer.parseInt(sc.nextLine());

            // Ordenades normalment de major a menor des del DAO.
            new ViaEsportivaDAO().llistarMesLlarguesPerEscola(idEscola).forEach(v ->
                System.out.println("  " + v.getNom() + " - " + v.getLlargadaTotal() + "m"));
            // ... (resta de categories)
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        Utils.esperarEnter();
    }
}
