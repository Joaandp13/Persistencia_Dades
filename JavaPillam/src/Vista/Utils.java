package Vista;

import java.util.Scanner;

public class Utils {
    private static final Scanner sc = new Scanner(System.in);

    public static void esperarEnter() {
        System.out.print("\nPrem ENTER per continuar...");
        sc.nextLine();
    }
}