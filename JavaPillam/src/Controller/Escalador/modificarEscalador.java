package Controller.Escalador;

import Model.DAO.Clases.EscaladorDAO;
import Model.Objectes.Escalador;
import java.util.Scanner;

public class modificarEscalador {

    public static void modificarEsc() {
        Scanner sc = new Scanner(System.in);
        EscaladorDAO dao = new EscaladorDAO();

        System.out.println("----------- MODIFICAR ESCALADOR -----------");

        try {
            // Usem nextLine() + parseInt per consistència amb la resta de controllers
            // (nextInt() deixa el buffer brut i pot causar problemes)
            System.out.print("ID de l'escalador a modificar: ");
            String inputId = sc.nextLine();
            if (inputId.isBlank()) return;
            int id = Integer.parseInt(inputId);

            Escalador e = dao.cercarPerId(id);
            if (e == null) {
                System.out.println("Escalador no trobat.");
                return;
            }

            System.out.println("Escalador actual:\n" + e);
            System.out.println("(Deixa buit per no canviar el camp)");

            System.out.print("Nou nom [" + e.getNom() + "]: ");
            String nom = sc.nextLine();
            if (!nom.isBlank()) e.setNom(nom);

            System.out.print("Nou alias [" + e.getAlias() + "]: ");
            String alias = sc.nextLine();
            if (!alias.isBlank()) e.setAlias(alias);

            System.out.print("Nova edat [" + e.getEdat() + "]: ");
            String edatStr = sc.nextLine();
            if (!edatStr.isBlank()) e.setEdat(Integer.parseInt(edatStr));

            System.out.print("Nou nivell [" + e.getNivell() + "]: ");
            String nivell = sc.nextLine();
            if (!nivell.isBlank()) e.setNivell(nivell);

            System.out.print("Via nivell max [" + e.getViaMaxNivell() + "]: ");
            String via = sc.nextLine();
            if (!via.isBlank()) e.setViaMaxNivell(via);

            System.out.print("Estil preferit (esportiva/classica/gel) [" + e.getEstilPreferit() + "]: ");
            String estil = sc.nextLine();
            if (!estil.isBlank()) e.setEstilPreferit(estil);

            dao.modificar(e);
            System.out.println("Escalador modificat correctament.");

        } catch (NumberFormatException ex) {
            System.out.println("Format numèric invàlid.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
