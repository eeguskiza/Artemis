package org.Artemis.core.database;

import org.Artemis.core.user.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class AlmacenDeDatos {
    // Logger for error handling
    private static final Logger logger = Logger.getLogger(AlmacenDeDatos.class.getName());
    // Connection to the database
    private static Connection conn;
    // List of users
    private ArrayList<User> usuarios;

    public AlmacenDeDatos(String dbURL) {
        // No need to set the logger again, it's already initialized.
        logger.info("Reconstruyendo base de datos ...");
        usuarios = new ArrayList<>();


        try {
            conn = DriverManager.getConnection(dbURL, "ADMIN", "Oiogorta2024");
            logger.info("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se ha podido conectar a la Base de Datos.", e);
        }
    }
    //limpiar pantalla
    public void limpiarPantalla() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }

    }
    // Método para codificar la contraseña
    public static String encode(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    // Método para decodificar la contraseña
    public static String decode(String encodedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedPassword);
        return new String(decodedBytes);
    }
    //-------------MENU------------------
    public void mostrarMenu(LineReader lineReader, Terminal terminal) {
        while (true) {
            System.out.println("Bienvenido al sistema Artemis");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Ingrese su opción: ");

            String opcion = lineReader.readLine();

            switch (opcion) {
                case "1":
                    limpiarPantalla();
                    registroLocal(lineReader, this);
                    break;
                case "2":
                    limpiarPantalla();
                    System.out.print("Ingrese nombre de usuario: ");
                    String username = lineReader.readLine();

                    System.out.print("Ingrese contraseña: ");
                    String password = lineReader.readLine();

                    boolean inicioSesionExitoso = iniciarSesion(username, password);
                    if (inicioSesionExitoso) {
                        System.out.println("Inicio de sesión exitoso.");
                    } else {
                        System.out.println("Inicio de sesión fallido. Intente de nuevo.");
                    }
                    lineReader.readLine("Presione Enter para continuar...");
                    break;
                case "3":
                    System.out.println("Gracias por usar el sistema Artemis. Hasta luego.");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }


    //----------MANEJO DE BASE DE DATOS-----------
    public static void registroLocal(LineReader lineReader, AlmacenDeDatos almacenDeDatos) {
        System.out.println("Bienvenido al sistema de registro de usuarios de Artemis");

        // Continuar pidiendo datos hasta que se introduzcan correctamente.
        while (true) {
            try {
                System.out.print("Ingrese nombre de usuario: ");
                String username = lineReader.readLine().trim();

                System.out.print("Ingrese contraseña: ");
                String password = lineReader.readLine().trim();

                System.out.print("Ingrese correo electrónico: ");
                String email = lineReader.readLine().trim();

                System.out.print("Ingrese nombre: ");
                String firstName = lineReader.readLine().trim();

                System.out.print("Ingrese apellido: ");
                String lastName = lineReader.readLine().trim();

                System.out.print("Ingrese rol: ");
                String role = lineReader.readLine().trim();

                // Crear un nuevo usuario
                User user = new User(username, AlmacenDeDatos.encode(password), email, firstName, lastName, role);

                // Registrar usuario en la base de datos
                boolean isRegistered = almacenDeDatos.registro(user);
                if (isRegistered) {
                    System.out.println("Usuario registrado con éxito.");
                } else {
                    System.out.println("No se pudo registrar al usuario. Por favor, intente de nuevo.");
                }

                System.out.print("¿Desea registrar otro usuario? (Sí/No): ");
                String respuesta = lineReader.readLine().trim();
                if (!respuesta.equalsIgnoreCase("Sí")) {
                    break; // Salir del bucle si la respuesta no es "Sí"
                }
            } catch (Exception e) {
                System.out.println("Error al registrar el usuario: " + e.getMessage());
            }
        }
    }
    public boolean registro(User user) {
        String sql = "INSERT INTO users (id, username, password, email, first_name, last_name, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getFirstName());
            pstmt.setString(6, user.getLastName());
            pstmt.setString(7, user.getRole());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Usuario creado exitosamente.");
                return true;
            } else {
                logger.warning("No se pudo registrar el usuario.");
                return false;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al crear el usuario: ", e);
            return false;
        }
    }

    public boolean iniciarSesion(String username, String password) {
        String sql = "SELECT * FROM users WHERE USERNAME = ? AND PASSWORD = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, encode(password));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Crear el objeto User a partir de los datos recuperados
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(rs.getString("role"));

                logger.info("Inicio de sesión exitoso para el usuario: " + username);
                limpiarPantalla();
                return true;
            } else {
                logger.warning("Inicio de sesión fallido para el usuario: " + username);
                return false;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al intentar iniciar sesión: ", e);
            return false;
        }
    }



}
