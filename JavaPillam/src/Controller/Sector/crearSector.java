package Controller.Sector;

import Model.DAO.Clases.EscolaDAO;
import Model.DAO.Clases.SectorDAO;
import Model.Objectes.Sector;
import java.util.Scanner;

public class crearSector {
    public static void crearSect() {
        Scanner sc = new Scanner(System.in);
        SectorDAO dao = new SectorDAO();
        EscolaDAO escolaDAO = new EscolaDAO();

        System.out.println("----------- CREAR SECTOR -----------");
        try {
            System.out.print("Nom del sector: ");
            String nom = sc.nextLine();

            System.out.print("ID de l'escola a la que pertany: ");
            String inputEscola = sc.nextLine();
            if (inputEscola.isBlank()) return;
            int idEscola = Integer.parseInt(inputEscola);

            if (escolaDAO.cercarPerId(idEscola) == null) {
                System.out.println("Escola no trobada.");
                return;
            }

            System.out.print("Latitud (ex: 41.5936): ");
            double latitud = Double.parseDouble(sc.nextLine());

            System.out.print("Longitud (ex: 1.8328): ");
            double longitud = Double.parseDouble(sc.nextLine());

            System.out.print("Aproximacio (com arribar, buit si no n'hi ha): ");
            String aproximacio = sc.nextLine();
            if (aproximacio.isBlank()) aproximacio = null;

            System.out.print("Popularitat (baixa / mitjana / alta): ");
            String popularitat = sc.nextLine();
            if (popularitat.isBlank()) popularitat = null;

            System.out.print("Restriccions (buit si no n'hi ha): ");
            String restriccions = sc.nextLine();
            if (restriccions.isBlank()) restriccions = null;

            System.out.print("Tipus de vies (gel / classica_esportiva): ");
            String tipusVies = sc.nextLine();

            Sector s = new Sector(nom, idEscola, latitud, longitud,
                                  aproximacio, popularitat, restriccions, tipusVies);
            dao.inserir(s);
            System.out.println("Sector creat correctament amb id: " + s.getIdSector());

        } catch (NumberFormatException ex) {
            System.out.println("Format de número incorrecte: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error en crear el sector: " + ex.getMessage());
        }
    }
}
