package Controller.Escola;

import Model.DAO.Clases.EscolaDAO;
import Model.Objectes.Escola;
import java.util.Scanner;

public class modificarEscola {

    public static void modificarEsco() {
        Scanner sc = new Scanner(System.in);
        EscolaDAO dao = new EscolaDAO();

        System.out.println("----------- MODIFICAR ESCOLA -----------");

        try {
            System.out.print("ID de l'escola a modificar (buit per sortir): ");
            String input = sc.nextLine();
            if (input.isBlank()) return;

            Escola e = dao.cercarPerId(Integer.parseInt(input));
            if (e == null) {
                System.out.println("Escola no trobada.");
                return;
            }

            System.out.println("Escola actual:\n" + e);
            System.out.println("(Deixa buit per no canviar el camp)");

            System.out.print("Nou nom [" + e.getNom() + "]: ");
            String nom = sc.nextLine();
            if (!nom.isBlank()) e.setNom(nom);

            System.out.print("Nova població [" + e.getPoblacio() + "]: ");
            String poblacio = sc.nextLine();
            if (!poblacio.isBlank()) e.setPoblacio(poblacio);

            System.out.print("Nova aproximació [" + e.getAproximacio() + "]: ");
            String aproximacio = sc.nextLine();
            if (!aproximacio.isBlank()) e.setAproximacio(aproximacio);

            System.out.print("Nova popularitat (baixa/mitjana/alta) [" + e.getPopularitat() + "]: ");
            String popularitat = sc.nextLine();
            if (!popularitat.isBlank()) e.setPopularitat(popularitat);

            dao.modificar(e);
            System.out.println("Escola modificada correctament.");

        } catch (NumberFormatException ex) {
            System.out.println("L'id ha de ser un número.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
