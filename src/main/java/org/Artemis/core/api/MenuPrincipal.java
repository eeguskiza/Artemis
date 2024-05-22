package org.Artemis.core.api;

import org.Artemis.core.crypto.Name;
import org.Artemis.core.database.AlmacenDeDatos;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal {

    private AlmacenDeDatos almacenDeDatos;

    public MenuPrincipal(AlmacenDeDatos almacenDeDatos) {
        this.almacenDeDatos = almacenDeDatos;
        mostrarMenu();
    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            almacenDeDatos.limpiarPantalla();
            System.out.println("Hola " + almacenDeDatos.getUsuario().getFirstName());
            System.out.println("Seleccione una opción:");
            System.out.println("1. Mostrar Saldo");
            System.out.println("2. Enviar Crypto");
            System.out.println("3. Recibir Crypto");
            System.out.println("4. Historial de Transacciones");
            System.out.println("5. Editar Perfil");
            System.out.println("6. Cerrar Sesión");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    mostrarSaldo();
                    break;
                case "2":
                    opcion1();
                    break;
                case "3":
                    opcion2();
                    break;
                case "4":
                    opcion3();
                    break;
                case "5":
                    opcion4();
                    break;
                case "6":
                    cerrarSesion();
                    return; // Salir del bucle y finalizar el programa
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
    }

    private void mostrarSaldo() {
        almacenDeDatos.limpiarPantalla();
        System.out.println("Cargando saldo...");
        Map<Name, Double> balance = almacenDeDatos.getUsuario().calculateBalance(almacenDeDatos.getArtemis());

        System.out.println("Saldo del usuario:");

        double maxBalance = balance.values().stream().max(Double::compare).orElse(1.0);
        String[] colors = {
                "\u001B[31m", // Rojo
                "\u001B[32m", // Verde
                "\u001B[33m", // Amarillo
                "\u001B[34m", // Azul
                "\u001B[35m", // Magenta
                "\u001B[36m"  // Cian
        };
        String resetColor = "\u001B[0m";
        int colorIndex = 0;

        for (Map.Entry<Name, Double> entry : balance.entrySet()) {
            int barLength = (int) ((entry.getValue() / maxBalance) * 50); // Escalar a 50 caracteres de largo
            String bar = new String(new char[barLength]).replace('\0', '|');
            System.out.printf("%s%s: %s %s%.2f%s\n", colors[colorIndex], entry.getKey(), bar, resetColor, entry.getValue(), resetColor);
            colorIndex = (colorIndex + 1) % colors.length; // Cambiar al siguiente color
        }

        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }



    private void opcion1() {
        System.out.println("Enviar crypto:");
        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void opcion2() {
        System.out.println("Recibir crypto:");
        System.out.println("\nPresione Enter para mostrar su direccion de cartera...");
        new Scanner(System.in).nextLine();
        System.out.println("Su dirección de cartera es: " + almacenDeDatos.getUsuario().getPublicKey());
        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void opcion3() {
        System.out.println("Historial de transacciones:");
        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void opcion4() {
        System.out.println("Editar perfil:");
        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void cerrarSesion() {
        System.out.println("Cerrando sesión...");
        almacenDeDatos.cerrarSesion();
        //un wait para que se vea el mensaje
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sesión cerrada. Presione Enter para continuar...");
        new Scanner(System.in).nextLine();
        MenuInicio menuInicio = new MenuInicio(almacenDeDatos);
    }
}
