﻿GESTOR DE RUTAS
=============

# API de Gestión de Rutas
Este proyecto es un servicio REST para manejar rutas API. Permite comparar endpoints antiguos y nuevos, detectando discrepancias y generando reportes.

# Dependencias
- lombok 1.18.36
- jackson-core 2.18.2
- jackson-annotations 2.18.2
- commons-lang3 3.17.0
- commons-io 2.18.0

# Requisitos
- JDK 21
- Maven 3.8.1 o superior

# Licencias
Este código está licenciado bajo la Licencia MIT.

## Requisitos
Instalación: Para compilar y ejecutar el proyecto, sigue los siguientes pasos:

1. Clona el repositorio:
    ```sh
    git clone <URL_DEL_REPOSITORIO>
    cd <NOMBRE_DEL_PROYECTO>
    ```

2. Compila el proyecto:
    ```sh
    mvn clean install
    ```

3. Ejecuta el proyecto:
    ```sh
    mvn quarkus:dev
    ```

## Pruebas
Para ejecutar las pruebas, utiliza el siguiente comando:
```sh
mvn test


