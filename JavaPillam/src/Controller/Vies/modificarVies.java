package Controller.Vies;

import Model.DAO.Clases.Via.ViaClassicaDAO;
import Model.DAO.Clases.Via.ViaEsportivaDAO;
import Model.DAO.Clases.Via.ViaGelDAO;
import Model.Objectes.ViaClassica;
import Model.Objectes.ViaEsportiva;
import Model.Objectes.ViaGel;
import java.time.LocalDate;
import java.util.Scanner;

public class modificarVies {
    public static void modificarVie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------- MODIFICAR VIA -----------");
        System.out.println("1. ESPORTIVA  2. CLASSICA  3. GEL");
        System.out.print("Tipus: ");

        try {
            int tipus = Integer.parseInt(sc.nextLine());
            switch (tipus) {
                case 1 -> modificarEsportiva(sc);
                case 2 -> modificarClassica(sc);
                case 3 -> modificarGel(sc);
                default -> System.out.println("Tipus no valid.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Has d'introduir un numero.");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void modificarEsportiva(Scanner sc) throws Exception {
        ViaEsportivaDAO dao = new ViaEsportivaDAO();
        System.out.print("ID de la via esportiva: ");
        ViaEsportiva v = dao.cercarPerId(Integer.parseInt(sc.nextLine()));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via actual:\n" + v);
        System.out.println("(Deixa buit per no canviar)");

        System.out.print("Nou nom [" + v.getNom() + "]: ");
        String nom = sc.nextLine(); if (!nom.isBlank()) v.setNom(nom);

        System.out.print("Nou grau [" + v.getGrau() + "]: ");
        String grau = sc.nextLine(); if (!grau.isBlank()) v.setGrau(grau);

        System.out.print("Nova orientacio [" + v.getOrientacio() + "]: ");
        String ori = sc.nextLine(); if (!ori.isBlank()) v.setOrientacio(ori);

        System.out.print("Nou ancoratge (spits/parabolts/quimics) [" + v.getAncoratge() + "]: ");
        String anc = sc.nextLine(); if (!anc.isBlank()) v.setAncoratge(anc);

        System.out.print("Nou estat (apte/construccio/tancada) [" + v.getEstat() + "]: ");
        String estat = sc.nextLine();
        if (!estat.isBlank()) {
            if (!estat.equals("apte")) {
                System.out.print("Data inici no-apte (YYYY-MM-DD): ");
                v.setDataIniciNoApte(LocalDate.parse(sc.nextLine()));
                System.out.print("Data fi no-apte (YYYY-MM-DD): ");
                v.setDataFiNoApte(LocalDate.parse(sc.nextLine()));
            } else {
                v.setDataIniciNoApte(null);
                v.setDataFiNoApte(null);
            }
            v.setEstat(estat);
        }

        System.out.print("Noves restriccions [" + v.getRestriccions() + "]: ");
        String rest = sc.nextLine(); if (!rest.isBlank()) v.setRestriccions(rest);

        dao.modificar(v);
        System.out.println("Via esportiva modificada correctament.");
    }

    private static void modificarClassica(Scanner sc) throws Exception {
        ViaClassicaDAO dao = new ViaClassicaDAO();
        System.out.print("ID de la via classica: ");
        ViaClassica v = dao.cercarPerId(Integer.parseInt(sc.nextLine()));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via actual:\n" + v);
        System.out.println("(Deixa buit per no canviar)");

        System.out.print("Nou nom [" + v.getNom() + "]: ");
        String nom = sc.nextLine(); if (!nom.isBlank()) v.setNom(nom);

        System.out.print("Nou grau [" + v.getGrau() + "]: ");
        String grau = sc.nextLine(); if (!grau.isBlank()) v.setGrau(grau);

        System.out.print("Nova orientacio [" + v.getOrientacio() + "]: ");
        String ori = sc.nextLine(); if (!ori.isBlank()) v.setOrientacio(ori);

        System.out.print("Nou ancoratge [" + v.getAncoratge() + "]: ");
        String anc = sc.nextLine(); if (!anc.isBlank()) v.setAncoratge(anc);

        System.out.print("Nou estat (apte/construccio/tancada) [" + v.getEstat() + "]: ");
        String estat = sc.nextLine();
        if (!estat.isBlank()) {
            if (!estat.equals("apte")) {
                System.out.print("Data inici no-apte (YYYY-MM-DD): ");
                v.setDataIniciNoApte(LocalDate.parse(sc.nextLine()));
                System.out.print("Data fi no-apte (YYYY-MM-DD): ");
                v.setDataFiNoApte(LocalDate.parse(sc.nextLine()));
            } else {
                v.setDataIniciNoApte(null);
                v.setDataFiNoApte(null);
            }
            v.setEstat(estat);
        }

        System.out.print("Noves restriccions [" + v.getRestriccions() + "]: ");
        String rest = sc.nextLine(); if (!rest.isBlank()) v.setRestriccions(rest);

        dao.modificar(v);
        System.out.println("Via classica modificada correctament.");
    }

    private static void modificarGel(Scanner sc) throws Exception {
        ViaGelDAO dao = new ViaGelDAO();
        System.out.print("ID de la via gel: ");
        ViaGel v = dao.cercarPerId(Integer.parseInt(sc.nextLine()));
        if (v == null) { System.out.println("Via no trobada."); return; }

        System.out.println("Via actual:\n" + v);
        System.out.println("(Deixa buit per no canviar)");

        System.out.print("Nou nom [" + v.getNom() + "]: ");
        String nom = sc.nextLine(); if (!nom.isBlank()) v.setNom(nom);

        System.out.print("Nou grau (max 8b) [" + v.getGrau() + "]: ");
        String grau = sc.nextLine(); if (!grau.isBlank()) v.setGrau(grau);

        System.out.print("Nova orientacio [" + v.getOrientacio() + "]: ");
        String ori = sc.nextLine(); if (!ori.isBlank()) v.setOrientacio(ori);

        System.out.print("Nou ancoratge (friends/tascons/bagues/pitons/tricams/bigbros) [" + v.getAncoratge() + "]: ");
        String anc = sc.nextLine(); if (!anc.isBlank()) v.setAncoratge(anc);

        System.out.print("Nou estat (apte/construccio/tancada) [" + v.getEstat() + "]: ");
        String estat = sc.nextLine();
        if (!estat.isBlank()) {
            if (!estat.equals("apte")) {
                System.out.print("Data inici no-apte (YYYY-MM-DD): ");
                v.setDataIniciNoApte(LocalDate.parse(sc.nextLine()));
                System.out.print("Data fi no-apte (YYYY-MM-DD): ");
                v.setDataFiNoApte(LocalDate.parse(sc.nextLine()));
            } else {
                v.setDataIniciNoApte(null);
                v.setDataFiNoApte(null);
            }
            v.setEstat(estat);
        }

        System.out.print("Noves restriccions [" + v.getRestriccions() + "]: ");
        String rest = sc.nextLine(); if (!rest.isBlank()) v.setRestriccions(rest);

        dao.modificar(v);
        System.out.println("Via gel modificada correctament.");
    }
}
