package Controller.Escalador;

import Model.DAO.Clases.EscaladorDAO;
import Model.Objectes.Escalador;
import java.util.Scanner;

public class crearEscalador {

    public static void crearesc() {
        Scanner sc = new Scanner(System.in);
        EscaladorDAO dao = new EscaladorDAO();

        System.out.println("----------- CREAR ESCALADOR -----------");

        try {
            System.out.print("Nom: ");
            String nom = sc.nextLine();

            System.out.print("Àlies (deixa buit si no en té): ");
            String alias = sc.nextLine();
            if (alias.isBlank()) alias = null;

            System.out.print("Edat: ");
            int edat = sc.nextInt();
            sc.nextLine(); // limpia el buffer tras nextInt()

            System.out.print("Nivell màxim assolit (ex: 6a, 7b+, 9c+): ");
            String nivell = sc.nextLine();
            if (nivell.isBlank()) nivell = null;

            System.out.print("Nom de la via on ha assolit el nivell màxim: ");
            String viaMaxNivell = sc.nextLine();
            if (viaMaxNivell.isBlank()) viaMaxNivell = null;

            System.out.print("Estil preferit (esportiva / classica / gel): ");
            String estilPreferit = sc.nextLine();
            if (estilPreferit.isBlank()) estilPreferit = null;

            // Crea el objeto — los setters validarán los datos
            Escalador e = new Escalador(nom, alias, edat, nivell, viaMaxNivell, estilPreferit, null);

            // Inserta en la BDD
            dao.inserir(e);

            System.out.println("Escalador creat correctament amb id: " + e.getIdEscalador());

        } catch (IllegalArgumentException ex) {
            // Error de validación del setter (edat < 10, nivell inválido, etc.)
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            // Error de BDD u otro inesperado
            System.out.println("Error en crear l'escalador: " + ex.getMessage());
        }
    }
}