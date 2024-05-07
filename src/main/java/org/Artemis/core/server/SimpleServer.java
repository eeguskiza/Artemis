package org.Artemis.core.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5001);
            System.out.println("Esperando conexiones en el puerto 5000...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Conexión aceptada de " + clientSocket.getRemoteSocketAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine = in.readLine();
            System.out.println("Mensaje recibido del cliente: " + inputLine);

            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error al intentar escuchar en el puerto 5000.");
            System.exit(1);
        } finally {
            if (serverSocket != null) serverSocket.close();
        }
    }
}

