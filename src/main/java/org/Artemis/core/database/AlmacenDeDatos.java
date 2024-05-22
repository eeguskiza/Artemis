package org.Artemis.core.database;

import org.Artemis.core.crypto.Block;
import org.Artemis.core.crypto.KeyPairGeneratorUtil;
import org.Artemis.core.crypto.Name;
import org.Artemis.core.crypto.Transaction;
import org.Artemis.core.user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlmacenDeDatos {
    // Logger for error handling
    public Logger logger = Logger.getLogger(AlmacenDeDatos.class.getName());
    // Connection to the database
    private static Connection conn;
    // User logged in
    private User usuario;

    //IMPORTANTE --> BLOCKCHAIN
    private ArrayList<Block> artemis;

    public AlmacenDeDatos(String dbURL) {
        logger.info("Reconstruyendo base de datos ...");
        usuario = new User();
        artemis = new ArrayList<>();
        crearBloqueGenesis();

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

    // Método para limpiar la pantalla
    public void limpiarPantalla() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    //get usuario
    public User getUsuario() {
        return usuario;
    }

    //set usuario
    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public ArrayList<Block> getArtemis() {
        return artemis;
    }

    public void setArtemis(ArrayList<Block> artemis) {
        this.artemis = artemis;
    }

    // Método para registro local
    public boolean registroLocal(Scanner scanner, AlmacenDeDatos almacenDeDatos) {
        System.out.println("Bienvenido al sistema de registro de usuarios de Artemis");

        while (true) {
            try {
                System.out.print("Ingrese nombre de usuario: ");
                String username = scanner.nextLine().trim();

                System.out.print("Ingrese contraseña: ");
                String password = scanner.nextLine().trim();

                System.out.print("Ingrese correo electrónico: ");
                String email = scanner.nextLine().trim();

                System.out.print("Ingrese nombre: ");
                String firstName = scanner.nextLine().trim();

                System.out.print("Ingrese apellido: ");
                String lastName = scanner.nextLine().trim();

                System.out.print("Ingrese rol: ");
                String role = scanner.nextLine().trim();

                // Generar par de claves
                KeyPairGeneratorUtil keyPairGenerator = new KeyPairGeneratorUtil();
                String publicKey = keyPairGenerator.getPublicKey();
                String privateKey = keyPairGenerator.getPrivateKey();

                // Crear un nuevo usuario
                User user = new User(0, username, AlmacenDeDatos.encode(password), email, firstName, lastName, role, publicKey, privateKey);

                // Registrar usuario en la base de datos
                boolean isRegistered = almacenDeDatos.registro(user);
                if (isRegistered) {
                    System.out.println("Usuario registrado con éxito.");
                    return true;
                } else {
                    System.out.println("No se pudo registrar al usuario. Por favor, intente de nuevo.");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Error al registrar el usuario: " + e.getMessage());
                return false;
            }
        }
    }

    // Método para registrar usuario en la base de datos
    public boolean registro(User user) {
        String sql = "INSERT INTO users (id, username, password, email, first_name, last_name, role, public_key, private_key) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, user.getId());             // id
            pstmt.setString(2, user.getUsername());    // username
            pstmt.setString(3, user.getPassword());    // password
            pstmt.setString(4, user.getEmail());       // email
            pstmt.setString(5, user.getFirstName());   // first_name
            pstmt.setString(6, user.getLastName());    // last_name
            pstmt.setString(7, user.getRole());        // role
            pstmt.setString(8, user.getPublicKey());   // public_key
            pstmt.setString(9, user.getPrivateKey());  // private_key

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



    // Método para iniciar sesión
    public User iniciarSesion(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, encode(password));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Crear el objeto User a partir de los datos recuperados
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(rs.getString("role"));
                user.setPublicKey(rs.getString("public_key"));
                user.setPrivateKey(rs.getString("private_key"));

                logger.info("Inicio de sesión exitoso para el usuario: " + username);
                return user; // Retornar el objeto User en caso de éxito
            } else {
                logger.warning("Inicio de sesión fallido para el usuario: " + username);
                return null;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al intentar iniciar sesión: ", e);
            return null;
        }
    }

    // Método para crear el bloque génesis
    public void crearBloqueGenesis() {
        Block genesisBlock = new Block(new Date());
        String key = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAItAYqsGUyo7YW0hYNw4nPQ9m8jdYWI7Q/zHZq8IMJYSDnFmnNfLW+6b91SHyDDtFSlo/3U8iqeWHpn2Nb4ZOqcCAwEAAQ==";
        //Injectar 10 transacciones a ERIK
        Transaction t1 = new Transaction("publicKey1", key, Name.BTC, 0.5);
        Transaction t2 = new Transaction("publicKey2", key, Name.ETH, 1.0);
        Transaction t3 = new Transaction("publicKey1", key, Name.ADA, 0.2);
        Transaction t4 = new Transaction("publicKey3", key, Name.BNB, 0.8);
        Transaction t5 = new Transaction("publicKey1", key, Name.SOL, 0.5);
        Transaction t6 = new Transaction("publicKey2", key, Name.ETH, 1.0);
        Transaction t7 = new Transaction("publicKey1", key, Name.BTC, 0.2);
        Transaction t8 = new Transaction("publicKey3", key, Name.WLD, 0.8);
        Transaction t9 = new Transaction("publicKey1", key, Name.XRP, 0.5);
        Transaction t10 = new Transaction("publicKey2", key, Name.SOL, 1.0);

        genesisBlock.setTransaction(t1);
        genesisBlock.setTransaction(t2);
        genesisBlock.setTransaction(t3);
        genesisBlock.setTransaction(t4);
        genesisBlock.setTransaction(t5);
        genesisBlock.setTransaction(t6);
        genesisBlock.setTransaction(t7);
        genesisBlock.setTransaction(t8);
        genesisBlock.setTransaction(t9);
        genesisBlock.setTransaction(t10);

        artemis.add(genesisBlock);
        logger.info("Bloque génesis creado con 10 transacciones a ERIK.");
    }
}
