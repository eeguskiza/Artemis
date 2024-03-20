package org.Artemis;

import org.Artemis.core.database.AlmacenDeDatos;
import org.Artemis.core.user.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlmacenDeDatos almacenDeDatos = new AlmacenDeDatos();

        System.out.println("Bienvenido al sistema de registro de usuarios de Artemis");

        // Continuar pidiendo datos hasta que se introduzcan correctamente.
        while (true) {
            try {
                System.out.print("Ingrese nombre de usuario: \n");
                String username = scanner.nextLine().trim();

                System.out.print("Ingrese contraseña: ");
                String password = scanner.nextLine().trim();

                System.out.print("Ingrese correo electrónico: ");
                String email = scanner.nextLine().trim();

                System.out.print("Ingrese nombre: ");
                String firstName = scanner.nextLine().trim();

                System.out.print("Ingrese apellido: ");
                String lastName = scanner.nextLine().trim();

                System.out.print("Ingrese rol: ");
                String role = scanner.nextLine().trim();

                // Crear un nuevo usuario
                User user = new User(username, AlmacenDeDatos.encode(password), email, firstName, lastName, role);

                // Registrar usuario en la base de datos
                boolean isRegistered = almacenDeDatos.registro(user);
                if (isRegistered) {
                    System.out.println("Usuario registrado con éxito.");
                } else {
                    System.out.println("No se pudo registrar al usuario. Por favor, intente de nuevo.");
                }

                // Si desea agregar una opción para terminar el bucle.
                System.out.print("¿Desea registrar otro usuario? (Sí/No): ");
                String respuesta = scanner.nextLine().trim();
                if (!respuesta.equalsIgnoreCase("Sí")) {
                    break; // Salir del bucle si la respuesta no es "Sí"
                }
            } catch (Exception e) {
                System.out.println("Error al registrar el usuario: " + e.getMessage());
            }
        }

        scanner.close();
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

