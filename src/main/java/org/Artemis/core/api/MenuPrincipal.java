package org.Artemis.core.api;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.Artemis.core.crypto.Name;
import org.Artemis.core.crypto.Transaction;
import org.Artemis.core.database.AlmacenDeDatos;
import org.Artemis.core.database.QRCodeGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;

public class MenuPrincipal implements QRCodeGenerator {

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
        almacenDeDatos.limpiarPantalla();
        System.out.println("Enviar crypto:");
        Transaction transaccion = almacenDeDatos.enviar(new Scanner(System.in));
        if (transaccion != null) {
            System.out.println("Transacción realizada con éxito: " + transaccion);
        } else {
            System.out.println("Transacción fallida.");
        }
        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void opcion2() {
        almacenDeDatos.limpiarPantalla();
        System.out.println("Recibir crypto:");
        System.out.println("\nPresione Enter para mostrar su direccion de cartera...");
        new Scanner(System.in).nextLine();
        String publicKey = almacenDeDatos.getUsuario().getPublicKey();
        System.out.println("Su dirección de cartera es: " + publicKey);

        // Generar y guardar el QR Code
        try {
            String filePath = "src/main/resources/qrcode.png";
            generateQRCodeImage(publicKey, 200, 200, filePath);
            System.out.println("\nCódigo QR de su dirección de cartera guardado en: " + filePath);
        } catch (WriterException | IOException e) {
            System.out.println("Error al generar el código QR: " + e.getMessage());
        }

        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void opcion3() {
        almacenDeDatos.limpiarPantalla();
        System.out.println("Historial de transacciones:");
        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void opcion4() {
        almacenDeDatos.limpiarPantalla();
        System.out.println("Editar perfil:");
        almacenDeDatos.editarPerfil(new Scanner(System.in));
        System.out.println("\nPresione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    private void cerrarSesion() {
        almacenDeDatos.limpiarPantalla();
        System.out.println("Cerrando sesión...");
        almacenDeDatos.cerrarSesion();

        // Guardar la blockchain en un fichero binario
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/blockchain.dat"))) {
            out.writeObject(almacenDeDatos.getArtemis());
            System.out.println("Blockchain guardada en blockchain.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Un wait para que se vea el mensaje
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Sesión cerrada. Presione Enter para continuar...");
        new Scanner(System.in).nextLine();
        MenuInicio menuInicio = new MenuInicio(almacenDeDatos);
    }

    @Override
    public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
}
