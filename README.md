# FERROTEC - Sistema de Gestión de Ferretería

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.4-brightgreen?style=for-the-badge&logo=springboot)
![H2 Database](https://img.shields.io/badge/Database-H2_In--Memory-blue?style=for-the-badge&logo=databricks)
![License](https://img.shields.io/badge/License-MIT-lightgrey?style=for-the-badge)

Aplicación web integral para la gestión comercial y control de existencias de una ferretería desarrollada con **Java 17** y **Spring Boot 3**. Implementa una arquitectura desacoplada con servicios REST transaccionales (ACID), base de datos embebida H2 y una interfaz interactiva servida directamente desde recursos estáticos.

---

## 📋 Tabla de Contenidos

1. [Características Principales](#-características-principales)
2. [Stack Tecnológico](#-stack-tecnológico)
3. [Arquitectura del Sistema](#-arquitectura-del-sistema)
4. [Estructura del Proyecto](#-estructura-del-proyecto)
5. [Documentación de la API REST](#-documentación-de-la-api-rest)
6. [Instalación y Ejecución](#-instalación-y-ejecución)
7. [Base de Datos Embebida (H2 Console)](#-base-de-datos-embebida-h2-console)
8. [Autores y Licencia](#-autores-y-licencia)

---

## 🚀 Características Principales

* **Control de Inventario (CRUD Completo):** Consulta, alta y actualización reactiva de productos, categorías, precios y niveles de stock.
* **Transacciones ACID con `@Transactional`:** Procesamiento de compras compuestas en carrito de compras con garantía de atomicidad y rollback automático ante stock insuficiente.
* **Persistencia Embebida:** Base de datos H2 en memoria con precarga de datos semilla (`data.sql`).
* **Frontend Integrado:** Interfaz moderna estilo retail servida por Tomcat embebido sin dependencias pesadas de frameworks front-end.
* **Manejo de Errores y Validaciones:** Respuestas HTTP semánticas (`200 OK`, `201 Created`, `400 Bad Request`, `404 Not Found`) con validaciones de esquema Jakarta Validation.

---

## 🛠️ Stack Tecnológico

* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.2.4
  * Spring Web (Controladores REST y servidor Apache Tomcat embebido)
  * Spring Data JPA (Capa ORM con Hibernate)
  * Spring Validation (Anotaciones de validación `@Valid`, `@NotNull`, `@Positive`)
* **Base de Datos:** H2 Database Engine (In-Memory)
* **Gestor de Dependencias:** Apache Maven
* **Cliente Web:** HTML5, CSS3 nativo, JavaScript (Fetch API) y FontAwesome

---

## 🏛️ Arquitectura del Sistema

El proyecto sigue una **Arquitectura en Capas (Layered Architecture)** para garantizar una estricta separación de responsabilidades:

```
[ Cliente Web / Navegador (Fetch API) ]
                    │
                    ▼
     [ Controller Layer (REST API) ]  <── @RestController, @CrossOrigin
                    │
                    ▼
       [ Service Layer (Negocio) ]    <── @Service, @Transactional
                    │
                    ▼
      [ Repository Layer (Spring Data) ] <── JpaRepository<Producto, Long>
                    │
                    ▼
      [ Database (H2 In-Memory) ]     <── Hibernate / SQL DDL
```

* **`Controller`:** Expone los endpoints HTTP, valida los payloads DTO y emite respuestas con códigos de estado semánticos.
* **`Service`:** Orquesta la lógica comercial y asegura transacciones atómicas mediante `@Transactional(rollbackFor = Exception.class)`.
* **`Repository`:** Abstrae la persistencia relacional delegando operaciones CRUD a Spring Data JPA.
* **`Entity / DTO`:** Mapea las tablas relacionales y desacopla la transferencia de datos cliente-servidor.

---

## 📁 Estructura del Proyecto

```text
ferreteria-springboot/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── ferreteria/
        │           ├── FerreteriaApplication.java
        │           ├── config/
        │           │   └── CorsConfig.java
        │           ├── controller/
        │           │   ├── ProductoController.java
        │           │   └── VentaController.java
        │           ├── dto/
        │           │   ├── ItemVentaDTO.java
        │           │   ├── VentaRequestDTO.java
        │           │   └── VentaResponseDTO.java
        │           ├── entity/
        │           │   └── Producto.java
        │           ├── repository/
        │           │   └── ProductoRepository.java
        │           └── service/
        │               ├── ProductoService.java
        │               └── VentaService.java
        └── resources/
            ├── application.properties
            ├── data.sql
            └── static/
                └── index.html
```

---

## 📡 Documentación de la API REST

### 1. Productos

| Método | Endpoint | Descripción | Código Exitoso |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/productos` | Obtiene el catálogo completo de productos. | `200 OK` |
| `POST` | `/api/productos` | Registra un nuevo producto en el catálogo. | `201 Created` |
| `PUT` | `/api/productos/{id}` | Actualiza datos (nombre, precio, stock) de un artículo. | `200 OK` |

#### Ejemplo de Cuerpo de Solicitud (Crear Producto):
```json
{
  "nombre": "Taladro Percutor 650W",
  "categoria": "Herramientas",
  "precio": 425.00,
  "stock": 6
}
```

---

### 2. Ventas y Carrito

| Método | Endpoint | Descripción | Código Exitoso |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/ventas/carrito` | Procesa la salida de inventario múltiple bajo una transacción atómica. | `200 OK` |

#### Ejemplo de Cuerpo de Solicitud (Venta en Lote):
```json
{
  "items": [
    { "id": 1, "cantidad": 2 },
    { "id": 3, "cantidad": 1 }
  ]
}
```

#### Respuesta de Éxito:
```json
{
  "mensaje": "Venta procesada con éxito",
  "articulosVendidos": 2,
  "totalPagar": 108.00
}
```

---

## ⚙️ Instalación y Ejecución

### Prerrequisitos
* **Java Development Kit (JDK):** Versión 17 o superior.
* **Apache Maven** (opcional si se ejecuta desde IDE).

### Pasos

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/Isra18K/-Tarea-04---Java-Spring-Boot.git
   cd -Tarea-04---Java-Spring-Boot
   ```

2. **Ejecutar la aplicación:**
   * **Con Maven CLI:**
     ```bash
     mvn spring-boot:run
     ```
   * **Desde Visual Studio Code:**
     Abre la clase `FerreteriaApplication.java` y haz clic en el botón superior **Run**.

3. **Acceder a la aplicación:**
   Abre tu navegador e ingresa a:
   ```text
   http://localhost:8080/
   ```

---

## 🗄️ Base de Datos Embebida (H2 Console)

La consola web de H2 está habilitada para auditar registros en caliente:

* **URL:** `http://localhost:8080/h2-console`
* **Driver Class:** `org.h2.Driver`
* **JDBC URL:** `jdbc:h2:mem:ferreteriadb`
* **User Name:** `sa`
* **Password:** *(dejar vacío)*

---

## 📄 Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.