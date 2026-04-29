package Controller.Escola;

import Model.DAO.Clases.EscolaDAO;
import Model.Objectes.Escola;
import java.util.Scanner;

public class crearEscola {
    public static void crearEsco() {
        Scanner sc = new Scanner(System.in);
        EscolaDAO dao = new EscolaDAO();
        System.out.println("----------- CREAR ESCOLA -----------");
        try {
            System.out.print("Nom: ");
            String nom = sc.nextLine();

            System.out.print("Poblacio: ");
            String poblacio = sc.nextLine();
            if (poblacio.isBlank()) poblacio = null;

            System.out.print("Aproximacio (com arribar): ");
            String aproximacio = sc.nextLine();
            if (aproximacio.isBlank()) aproximacio = null;

            System.out.print("Popularitat (baixa / mitjana / alta): ");
            String popularitat = sc.nextLine();
            if (popularitat.isBlank()) popularitat = null;

            Escola e = new Escola(0, nom, poblacio, aproximacio, popularitat);
            dao.inserir(e);
            System.out.println("Escola creada correctament amb id: " + e.getIdEscola());

        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error en crear l'escola: " + ex.getMessage());
        }
    }
}