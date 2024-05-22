package org.Artemis.core.database;

import org.Artemis.core.crypto.Block;
import org.Artemis.core.crypto.KeyPairGeneratorUtil;
import org.Artemis.core.crypto.Name;
import org.Artemis.core.crypto.Transaction;
import org.Artemis.core.user.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;
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
        cargarBlockchain();
        //crearBloqueGenesis();

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
                User user = new User(username, AlmacenDeDatos.encode(password), email, firstName, lastName, role, publicKey, privateKey);

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

    public String obtenerClavePublica(String username) {
        String sql = "SELECT public_key FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("public_key");
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener la clave pública: ", e);
            return null;
        }
    }

    public User obtenerUsuarioPorUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
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
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener el usuario: ", e);
            return null;
        }
    }

    public Transaction enviar(Scanner scanner) {
        System.out.print("Ingrese el nombre de usuario al que desea enviar: ");
        String usernameReceptor = scanner.nextLine().trim();

        // Verificar si el usuario receptor existe
        User usuarioReceptor = obtenerUsuarioPorUsername(usernameReceptor);
        if (usuarioReceptor == null) {
            System.out.println("El usuario receptor no existe.");
            return null;
        }

        // Mostrar saldo del usuario actual
        Map<Name, Double> balance = getUsuario().calculateBalance(getArtemis());
        System.out.println("Su saldo actual es:");
        balance.forEach((moneda, cantidad) -> System.out.println(moneda + ": " + cantidad));

        System.out.print("Ingrese la moneda que desea enviar: ");
        String monedaStr = scanner.nextLine().trim().toUpperCase();

        // Verificar si la moneda es válida
        Name moneda;
        try {
            moneda = Name.valueOf(monedaStr);
        } catch (IllegalArgumentException e) {
            System.out.println("La moneda ingresada no es válida.");
            return null;
        }

        // Verificar si el usuario tiene suficiente saldo de esa moneda
        if (!balance.containsKey(moneda) || balance.get(moneda) <= 0) {
            System.out.println("No tiene suficiente saldo de la moneda seleccionada.");
            return null;
        }

        System.out.print("Ingrese la cantidad que desea enviar: ");
        double cantidad;
        try {
            cantidad = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Cantidad no válida.");
            return null;
        }

        if (cantidad <= 0 || cantidad > balance.get(moneda)) {
            System.out.println("No tiene suficiente saldo para enviar esa cantidad.");
            return null;
        }

        // Crear la transacción
        String publicKeyEmisor = getUsuario().getPublicKey();
        String publicKeyReceptor = usuarioReceptor.getPublicKey();
        Transaction transaccion = new Transaction(publicKeyEmisor, publicKeyReceptor, moneda, cantidad);

        // Agregar la transacción a la blockchain
        agregarTransaccionABlockchain(transaccion);

        System.out.println("Transacción creada y añadida a la blockchain.");

        return transaccion;
    }


    public void agregarTransaccionABlockchain(Transaction transaccion) {
        if (artemis.isEmpty() || artemis.get(artemis.size() - 1).getTransactions().length >= 10) {
            // Crear un nuevo bloque si no hay bloques o si el último bloque está lleno
            Block nuevoBloque = new Block(new Date());
            nuevoBloque.setTransaction(transaccion);
            artemis.add(nuevoBloque);
            logger.info("Nuevo bloque creado y transacción añadida.");
        } else {
            // Añadir la transacción al último bloque
            Block ultimoBloque = artemis.get(artemis.size() - 1);
            ultimoBloque.setTransaction(transaccion);
            logger.info("Transacción añadida al último bloque.");
        }
    }

    public void editarPerfil(Scanner scanner) {
        System.out.println("Editar perfil de usuario:");
        System.out.println("Deje el campo en blanco si no desea cambiarlo.");

        System.out.println("\n<----------------------------------------------->\n");
        System.out.print("Nombre de usuario actual: " + usuario.getUsername() + "\nNuevo nombre de usuario: ");
        String nuevoUsername = scanner.nextLine().trim();
        if (!nuevoUsername.isEmpty()) {
            usuario.setUsername(nuevoUsername);
        }

        System.out.println("\n<----------------------------------------------->\n");
        System.out.print("Nombre actual: " + usuario.getFirstName() + "\nNuevo nombre: ");
        String nuevoNombre = scanner.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            usuario.setFirstName(nuevoNombre);
        }

        System.out.println("\n<----------------------------------------------->\n");
        System.out.print("Apellido actual: " + usuario.getLastName() + "\nNuevo apellido: ");
        String nuevoApellido = scanner.nextLine().trim();
        if (!nuevoApellido.isEmpty()) {
            usuario.setLastName(nuevoApellido);
        }

        System.out.println("\n<----------------------------------------------->\n");
        System.out.print("Correo electrónico actual: " + usuario.getEmail() + "\nNuevo correo electrónico: ");
        String nuevoEmail = scanner.nextLine().trim();
        if (!nuevoEmail.isEmpty()) {
            usuario.setEmail(nuevoEmail);
        }

        System.out.println("\n<----------------------------------------------->\n");
        System.out.print("Contraseña actual: " + decode(usuario.getPassword()) + "\nNueva contraseña: ");
        String nuevaContrasena = scanner.nextLine().trim();
        if (!nuevaContrasena.isEmpty()) {
            usuario.setPassword(encode(nuevaContrasena));
        }

        // Actualizar el usuario en la base de datos
        String sql = "UPDATE users SET username = ?, first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getFirstName());
            pstmt.setString(3, usuario.getLastName());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getPassword());
            pstmt.setInt(6, usuario.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Perfil actualizado exitosamente.");
                System.out.println("Perfil actualizado exitosamente.");
            } else {
                logger.warning("No se pudo actualizar el perfil.");
                System.out.println("No se pudo actualizar el perfil.");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar el perfil: ", e);
            System.out.println("Error al actualizar el perfil: " + e.getMessage());
        }
    }

    //metodo cerrar sesion
    public void cerrarSesion() {
        usuario = null;
        logger.info("Sesión cerrada.");
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

    // Método para cargar la blockchain
    public void cargarBlockchain() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/blockchain.dat"))) {
            artemis = (ArrayList<Block>) in.readObject();
            logger.info("Blockchain cargada desde blockchain.dat");
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error al cargar la blockchain: ", e);
        }
    }

}
