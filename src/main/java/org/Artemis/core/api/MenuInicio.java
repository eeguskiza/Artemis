package org.Artemis.core.api;

import org.Artemis.core.user.User;

import java.util.Map;
import java.util.Scanner;

public class MenuInicio {

    public void mostrarMenu(User usuario) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            // Saludo al usuario
            System.out.println("\nHola, " + usuario.getUsername() + "!");

            // Muestra cada activo y su valor
            System.out.println("Tus activos actuales son:");
            for (Map.Entry<String, Double> activo : usuario.getActivos().entrySet()) {
                System.out.printf("%s: $%.2f%n", activo.getKey(), activo.getValue());
            }

            // Opciones del menú
            System.out.println("\n1. Enviar Crypto");
            System.out.println("2. Recibir Crypto");
            System.out.println("3. Editar Perfil");
            System.out.println("4. Mostrar Gráfico del Saldo");
            System.out.println("5. Salir");

            System.out.print("\nSelecciona una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    // Implementar lógica para enviar criptomonedas
                    System.out.println("Enviar Crypto (función no implementada)");
                    break;
                case "2":
                    // Implementar lógica para recibir criptomonedas
                    System.out.println("Recibir Crypto (función no implementada)");
                    break;
                case "3":
                    // Implementar lógica para editar el perfil
                    System.out.println("Editar Perfil (función no implementada)");
                    break;
                case "4":
                    mostrarGraficoSaldo(usuario);
                    break;
                case "5":
                    System.out.println("Saliendo...");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
            }
        }
    }


    private void mostrarGraficoSaldo(User usuario) {
        Map<String, Double> activos = usuario.getActivos();
        int longitudGrafico = 50; // Longitud total del gráfico
        double totalSaldo = activos.values().stream().mapToDouble(Double::doubleValue).sum(); // Suma total de todos los activos
        double escala = totalSaldo / longitudGrafico; // Escala para ajustar los activos al gráfico

        // Códigos ANSI para colores y reset
        String reset = "\u001B[0m";
        String[] colores = {"\u001B[41m", "\u001B[42m", "\u001B[43m", "\u001B[44m", "\u001B[45m"}; // Fondo de diferentes colores
        int colorIndex = 0;

        System.out.println("\nGráfico de Activos:");
        for (Map.Entry<String, Double> activo : activos.entrySet()) {
            int longitudSegmento = (int) (activo.getValue() / escala); // Calcula la longitud del segmento para el activo actual

            // Imprime el segmento del activo con el color correspondiente
            System.out.print(colores[colorIndex % colores.length]);
            for (int i = 0; i < longitudSegmento; i++) {
                System.out.print(" "); // Usamos espacio para representar el segmento para mejorar la visibilidad del color
            }

            colorIndex++; // Avanza al siguiente color para el próximo activo
        }
        System.out.println(reset + "| " + totalSaldo + "€"); // Resetea los colores y marca el fin del gráfico

        // Imprime la leyenda
        colorIndex = 0;
        for (String activo : activos.keySet()) {
            System.out.println("\n" + colores[colorIndex % colores.length] + " " + reset + " - " + activo);
            colorIndex++;
        }
    }



}
