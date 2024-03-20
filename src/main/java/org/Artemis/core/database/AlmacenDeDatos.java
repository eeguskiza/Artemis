package org.Artemis.core.database;

import org.Artemis.core.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlmacenDeDatos {
    // Logger for error handling
    private static final Logger logger = Logger.getLogger(AlmacenDeDatos.class.getName());
    // Connection to the database
    private static Connection conn;
    // List of users
    private ArrayList<User> usuarios;

    public AlmacenDeDatos() {
        // No need to set the logger again, it's already initialized.
        logger.info("Reconstruyendo base de datos ...");
        usuarios = new ArrayList<>();

        String dbURL = "jdbc:oracle:thin:@artemis_tpurgent?TNS_ADMIN=src/main/resources/Wallet_Artemis";
        try {
            conn = DriverManager.getConnection(dbURL, "ADMIN", "Oiogorta2024");
            logger.info("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se ha podido conectar a la Base de Datos.", e);
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

    //----------MANEJO DE BASE DE DATOS-----------
    public boolean registro(User user) {
        String sql = "INSERT INTO users (id, username, password, email, first_name, last_name, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, encode(user.getPassword()));
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
        String sql = "SELECT password FROM users WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = decode(rs.getString("password")); // Decodifica la contraseña almacenada
                if (password.equals(storedPassword)) { // Compara la contraseña ingresada con la almacenada
                    logger.info("Inicio de sesión exitoso para el usuario: " + username);
                    return true;
                }
            }

            logger.warning("Inicio de sesión fallido para el usuario: " + username);
            return false;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al intentar iniciar sesión: ", e);
            return false;
        }
    }

}
