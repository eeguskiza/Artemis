package org.Artemis.core.api;

import org.Artemis.core.crypto.Name;
import org.Artemis.core.database.AlmacenDeDatos;

import java.util.Map;

public class MenuPrincipal {
    public MenuPrincipal(AlmacenDeDatos almacenDeDatos) {
        almacenDeDatos.limpiarPantalla();
        System.out.printf("Hola " + almacenDeDatos.getUsuario().getFirstName());
        Map<Name, Double> balance = almacenDeDatos.getUsuario().calculateBalance(almacenDeDatos.getArtemis());
        System.out.println(balance);
    }
}
