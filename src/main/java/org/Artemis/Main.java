package org.Artemis;

import org.Artemis.core.api.MenuInicio;
import org.Artemis.core.database.AlmacenDeDatos;
import org.Artemis.core.user.User;

import java.awt.*;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.Artemis.core.database.AlmacenDeDatos.*;
import org.Artemis.core.api.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String dbURL = "jdbc:oracle:thin:@artemis_tpurgent?TNS_ADMIN=src/main/resources/Wallet_Artemis";
            AlmacenDeDatos almacenDeDatos = new AlmacenDeDatos(dbURL);
            printArtemisBanner();
            System.out.println("Bienvenido a Artemis, la plataforma de gestión de identidad descentralizada.\n\n");

            // Animación de carga con un "spinner"
            String[] spinner = new String[] { "-", "\\", "|", "/" };
            for (int i = 0; i < 100; i++) {
                String frame = spinner[i % spinner.length];
                System.out.print("\rCargando " + frame);
                Thread.sleep(42); // Tiempo de espera entre frames
            }
            System.out.println("\rCargado completado ✓\n");
            MenuInicio menuInicio = new MenuInicio(almacenDeDatos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printArtemisBanner() {
        String artemisBanner = "\033[0;94m" + // Este código cambia el color del texto a azul
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
                "     \\/__/         \\|__|                       \\/__/         \\/__/                     \\/__/    \033[0m"; // Este código resetea el color
        System.out.println(artemisBanner);
    }
}