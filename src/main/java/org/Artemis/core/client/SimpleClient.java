package org.Artemis.core.client;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost"; // Puedes cambiar esto para conectar a un servidor remoto
        int portNumber = 5001;

        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Hola, servidor!");
            System.out.println("Mensaje enviado al servidor.");

            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor en " + hostName + ":" + portNumber);
            System.exit(1);
        }
    }
}

