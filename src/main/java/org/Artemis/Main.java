package org.Artemis;

import org.Artemis.core.database.AlmacenDeDatos;
import org.Artemis.core.user.User;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Scanner;

import static org.Artemis.core.database.AlmacenDeDatos.*;

public class Main {
    public static void main(String[] args) {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
            String dbURL = "jdbc:oracle:thin:@artemis_tpurgent?TNS_ADMIN=src/main/resources/Wallet_Artemis";
            AlmacenDeDatos almacenDeDatos = new AlmacenDeDatos(dbURL);
            printArtemisBanner();
            System.out.println("Bienvenido a Artemis, la plataforma de gestión de identidad descentralizada.\n\n");

            // Animación de carga con un "spinner"
            String[] spinner = new String[] { "-", "\\", "|", "/" };
            for (int i = 0; i < 100; i++) {
                String frame = spinner[i % spinner.length];
                System.out.print("\rCargando " + frame);
                Thread.sleep(40);  // Ajusta este valor si necesitas que la animación sea más rápida o más lenta
            }
            System.out.println("\rCargando completado ✓\n");

            almacenDeDatos.mostrarMenu(lineReader, terminal); // Pasar lineReader y terminal al método
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

