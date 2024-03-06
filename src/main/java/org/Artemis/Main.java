package org.Artemis;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;

        System.out.println("Welcome to Artemis Blockchain Interface!");
        System.out.print("Enter username: ");
        username = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.nextLine();

        // Aquí iría la lógica de autenticación
        if (authenticateUser(username, password)) {
            printArtemisBanner();
        } else {
            System.out.println("Authentication failed.");
        }
    }
    private static boolean authenticateUser(String username, String password) {
        // Aquí implementarías la verificación de las credenciales del usuario.
        // Este es solo un ejemplo, debes implementar tu propia lógica de autenticación.
        return "user".equals(username) && "0000".equals(password);
    }

    private static void printArtemisBanner() {
        String artemisBanner =
                "      ___           ___           ___           ___           ___                       ___     \n" +
                        "     /\\  \\         /\\  \\         /\\  \\         /\\  \\         /\\__\\          ___        /\\  \\    \n" +
                        "    /::\\  \\       /::\\  \\        \\:\\  \\       /::\\  \\       /::|  |        /\\  \\      /::\\  \\   \n" +
                        "   /:/\\:\\  \\     /:/\\:\\  \\        \\:\\  \\     /:/\\:\\  \\     /:|:|  |        \\:\\  \\    /:/\\ \\  \\  \n" +
                        "  /::\\~\\:\\  \\   /::\\~\\:\\  \\       /::\\  \\   /::\\~\\:\\  \\   /:/|:|__|__      /::\\__\\  _\\:\\~\\ \\  \\ \n" +
                        " /:/\\:\\ \\:\\__\\ /:/\\:\\ \\:\\__\\     /:/\\:\\__\\ /:/\\:\\ \\:\\__\\ /:/ |::::\\__\\  __/:/\\/__/ /\\ \\:\\ \\ \\__\\\n" +
                        " \\/__\\:\\/:/  / \\/_|::\\/:/  /    /:/  \\/__/ \\:\\~\\:\\ \\/__/ \\/__/~~/:/  / /\\/:/  /    \\:\\ \\:\\ \\/__/\n" +
                        "      \\::/  /     |:|::/  /    /:/  /       \\:\\ \\:\\__\\         /:/  /  \\::/__/      \\:\\ \\:\\__\\  \n" +
                        "      /:/  /      |:|\\/__/     \\/__/         \\:\\ \\/__/        /:/  /    \\:\\__\\       \\:\\/:/  /  \n" +
                        "     /:/  /       |:|  |                      \\:\\__\\         /:/  /      \\/__/        \\::/  /   \n" +
                        "     \\/__/         \\|__|                       \\/__/         \\/__/                     \\/__/    ";
        System.out.println(artemisBanner);
    }
}

