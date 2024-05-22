package org.Artemis.core.api;

import org.Artemis.core.database.AlmacenDeDatos;
import org.Artemis.core.user.User;

import java.util.Scanner;

public class MenuInicio {
    private AlmacenDeDatos almacenDeDatos;

    public MenuInicio(AlmacenDeDatos almacenDeDatos) {
        this.almacenDeDatos = almacenDeDatos;
        mostrarMenu();
    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            almacenDeDatos.limpiarPantalla();
            System.out.println("Bienvenido a Artemis, la plataforma de gestión de identidad descentralizada.\n\n");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    if (iniciarSesion(scanner)) {
                        MenuPrincipal menuPrincipal = new MenuPrincipal(almacenDeDatos);
                        return;
                    }
                    break;
                case "2":
                    registrarUsuario(scanner);
                    break;
                case "3":
                    System.out.println("Gracias por usar Artemis. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
    }

    private boolean iniciarSesion(Scanner scanner) {
        almacenDeDatos.limpiarPantalla();
        System.out.println("Iniciando sesión ...");

        System.out.print("Ingrese nombre de usuario: ");
        String username = scanner.nextLine().trim();

        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine().trim();

        User user = almacenDeDatos.iniciarSesion(username, password);
        if (user != null) {
            System.out.println("Inicio de sesión exitoso. Bienvenido " + user.getFirstName());
            almacenDeDatos.setUsuario(user);
            return true; // Login successful
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
            return false; // Login failed
        }
    }

    private void registrarUsuario(Scanner scanner) {
        almacenDeDatos.limpiarPantalla();
        System.out.println("Registrando usuario ...");

        boolean isRegistered = almacenDeDatos.registroLocal(scanner, almacenDeDatos);
        if (isRegistered) {
            System.out.println("Usuario registrado con éxito.");
        } else {
            System.out.println("No se pudo registrar al usuario. Por favor, intente de nuevo.");
        }
    }

    public static void main(String[] args) {
        // Configura la conexión a la base de datos
        AlmacenDeDatos almacenDeDatos = new AlmacenDeDatos("jdbc:your_database_url");

        // Inicia el menú de inicio
        new MenuInicio(almacenDeDatos);
    }
}
