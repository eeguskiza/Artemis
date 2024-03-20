# Estructura del Proyecto Artemis

Este repositorio contiene la implementación de una blockchain en Java, con la siguiente estructura de carpetas:

### `src`
Aquí se encuentra el código fuente del proyecto, organizado en subdirectorios según su propósito.

### `api`
- Interfaces y clases que definen los puntos de acceso públicos de la aplicación.
- Utilizado por otras aplicaciones o usuarios que interactúan con la blockchain.

### `core`
- La lógica de negocio principal de la aplicación blockchain.
    - `crypto`: Funcionalidades de criptografía como generación de claves, cifrado, y hashing.
    - `database`: Gestión de la base de datos y acceso a los datos (DAOs).
    - `network`: Manejo de la red, incluyendo clientes/servidores de sockets y protocolos de comunicación.
    - `util`: Herramientas y funciones de utilidad comunes.

### `resources`
- Recursos necesarios para la aplicación, como archivos de configuración y otros archivos estáticos.

## `/src/test/java`
- Pruebas automáticas, incluyendo pruebas unitarias y de integración para el código en `src/main/java`.

## `/.idea`
- Configuraciones específicas del IDE IntelliJ IDEA.

## `/target`
- Directorio de salida que contiene los resultados de la compilación y empaquetado del proyecto (generado por Maven).

Este archivo `README.md` debe ser actualizado si la estructura del proyecto cambia o si se agregan nuevas funcionalidades que requieran explicación.
