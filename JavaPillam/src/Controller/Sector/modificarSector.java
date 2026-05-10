package Controller.Sector;

import Model.DAO.Clases.SectorDAO;
import Model.Objectes.Sector;
import java.util.Scanner;

public class modificarSector {

    public static void modificarSect() {
        Scanner sc = new Scanner(System.in);
        SectorDAO dao = new SectorDAO();

        System.out.println("----------- MODIFICAR SECTOR -----------");

        try {
            System.out.print("ID del sector a modificar (buit per sortir): ");
            String input = sc.nextLine();
            if (input.isBlank()) return;

            Sector s = dao.cercarPerId(Integer.parseInt(input));
            if (s == null) {
                System.out.println("Sector no trobat.");
                return;
            }

            System.out.println("Sector actual:\n" + s);
            System.out.println("(Deixa buit per no canviar el camp)");

            System.out.print("Nou nom [" + s.getNom() + "]: ");
            String nom = sc.nextLine();
            if (!nom.isBlank()) s.setNom(nom);

            System.out.print("Nova aproximació [" + s.getAproximacio() + "]: ");
            String aprox = sc.nextLine();
            if (!aprox.isBlank()) s.setAproximacio(aprox);

            System.out.print("Nova popularitat (baixa/mitjana/alta) [" + s.getPopularitat() + "]: ");
            String pop = sc.nextLine();
            if (!pop.isBlank()) s.setPopularitat(pop);

            System.out.print("Noves restriccions [" + s.getRestriccions() + "]: ");
            String restr = sc.nextLine();
            if (!restr.isBlank()) s.setRestriccions(restr);

            // Atenció: canviar tipusVies pot deixar vies existents incoherents amb el sector
            System.out.print("Nou tipus de vies (gel/classica_esportiva) [" + s.getTipusVies() + "]: ");
            String tipus = sc.nextLine();
            if (!tipus.isBlank()) s.setTipusVies(tipus);

            System.out.print("Nova latitud [" + s.getLatitud() + "]: ");
            String lat = sc.nextLine();
            if (!lat.isBlank()) s.setLatitud(Double.parseDouble(lat));

            System.out.print("Nova longitud [" + s.getLongitud() + "]: ");
            String lon = sc.nextLine();
            if (!lon.isBlank()) s.setLongitud(Double.parseDouble(lon));

            dao.modificar(s);
            System.out.println("Sector modificat correctament.");

        } catch (NumberFormatException ex) {
            System.out.println("Format numèric invàlid.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
