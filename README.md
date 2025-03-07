Gestor de Rutas BICE
=============

[![License](https://img.shields.io/github/license/bennu/commons?label=License&logo=opensourceinitiative)](https://opensource.org/license/mit-0)
[![Supported JVM Versions](https://img.shields.io/badge/JVM-17--21-brightgreen.svg?label=JVM&logo=openjdk)](https://adoptium.net/es/temurin/releases/)

BICE Routes, an application to convert open API specifications to routes.txt (BICE specific file format).

Building
--------

Building requires a Java JDK and [Apache Maven](https://maven.apache.org/).
The required Java version is found in the `pom.xml` as the `maven.compiler.source` property.

From a command shell, run `mvn` without arguments to invoke the default Maven goal to run all tests and checks.

License
-------

This code is licensed under the [MIT License](https://opensource.org/license/mit).

Dependencies
------------

- quarkus 3.19.2
- bennu-commons 1.1.0
- lombok 1.18.36
- jackson 2.18.3