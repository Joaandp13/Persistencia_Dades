package Controller.Escalador;


import Model.DAO.Clases.EscaladorDAO;
import Model.Objectes.Escalador;
import java.util.Scanner;

public class eliminarEscalador {
    public static void eliminarEsc(){
        Scanner sc = new  Scanner(System.in);
        EscaladorDAO dao = new EscaladorDAO();
        boolean eliminar = false;
        System.out.println("----------- ELIMINAR ESCALADOR -----------");
        try {
            while (!eliminar) {
                System.out.println("Introdueix l'id del escalador a eliminar: (buit per sortir)");
                String input = sc.nextLine();
                if (input.isBlank()) break;

                int id = Integer.parseInt(input);
                Escalador e = dao.cercarPerId(id);

                if (e == null) {
                    System.out.println("Escalador no trobat.");
                } else {
                    System.out.println("Dades del escalador:\n" + e);
                    System.out.print("Eliminar escalador? (y/n): ");
                    if (sc.nextLine().equalsIgnoreCase("y")) {
                        System.out.print("CONFIRMACIÓ D'ELIMINACIO (IRREVERSIBLE) Y/N: ");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            dao.eliminar(id);
                            System.out.println("Escalador eliminat correctament.");
                            eliminar = true;
                        }
                    }
                }
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Dada incorrecta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
