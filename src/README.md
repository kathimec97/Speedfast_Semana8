![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)
# Caso: SpeedFast - Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto
* **Nombre Completo:** Katherine del Carmen Avila Mecia
* **Sección:** 003A
* **Carrera:** Analista Programador Computacional
* **Bimestre:** 3
* **Sede:** Campus Virtual

---
## 📘 Sumativa N. 3 (Semana 8)

## Descripción del Proyecto.
Sistema desarrollado en Java Swing para registrar pedidos, gestionar repartidores y almacenar el historial de envíos mediante una base de datos MySQL

Este proyecto aplica el patrón de arquitectura Modelo-Vista-Controlador (MVC) junto con el patrón DAO, asegurando una separación limpia entre 
la interfaz gráfica, la lógica de negocio y la persistencia de datos.

### Clases principales

* **Punto de Entrada:**
    * `Main`: Inicia la aplicación llamando a la `VistaVentanaPrincipal`.

* **Modelo (Entidades):**
    * `Pedido`: Define los atributos del pedido, incluyendo su dirección, estado (`EstadoPedido`) y categoría (`TipoPedido`).
    * `Repartidor`: Representa al personal de entregas del sistema.
    * `Entrega`: Modelo transaccional que vincula un `Pedido` específico con un `Repartidor`, registrando la fecha y hora exacta de la asignación.

* **Controlador (Lógica de Negocio):**
    * `ControladorPedidos`, `ControladorRepartidor`, `ControladorEntregas`: Actúan como intermediarios, validando las entradas del usuario y solicitando 
    * las operaciones pertinentes a la capa DAO mediante métodos normales y sobrecargados.

* **Acceso a Datos (DAO):**
    * `ConexionBD`: Configura y establece la conexión con MySQL utilizando JDBC hacia la base de datos `speedfast_db`.
    * `PedidoDAOImpl`, `RepartidorDAOImpl`, `EntregaDAOImpl`: Implementan las interfaces DAO y centralizan todas las sentencias SQL.
    * (consultas, inserciones, actualizaciones y eliminaciones) utilizando `PreparedStatement`.

* **Vista (Interfaz Gráfica):**
    * `VistaVentanaPrincipal`: Pantalla dedicada al acceso principal mediante botones de navegación.
    * `VistaVentanaPedidos`: Pantalla dedicada a la captura, guardado y filtrado dinámico de pedidos.
    * `VistaVentanaRepartidores`: Pantalla dedicada a la captura, edición y visualización de los repartidores.
    * `VistaVentanaEntregas`: Interfaz para asignar pedidos pendientes a repartidores, visualizar el historial de entregas y aplicar filtros combinados.

### Características destacadas
* **Gestión de Entregas y Filtros Dinámicos**: Interfaz protegida con listas desplegables (`JComboBox`) que evita errores de tipeo y permite filtrar automáticamente el historial 
* mediante consultas SQL directas por Pedido o Repartidor.
* **Sobrecarga de Métodos**: Uso de POO en el controlador para manejar múltiples formas de cargar datos en las tablas (`cargarTabla`) dependiendo de si hay filtros activos
* **Persistencia Segura**: Prevención de inyección SQL mediante el uso estructurado de `PreparedStatement` en todas las transacciones de base de datos.
* **Actualización en Tiempo Real**: Visualización dinámica a través de tablas (`JTable`) que se refrescan al momento de realizar cualquier operación CRUD.

## Tecnologías utilizadas
* **Lenguaje:** Java
* **Interfaz Gráfica:** Java Swing (JFrame, JPanel, JComboBox, Layout managers)
* **Base de Datos:** MySQL
* **Conexión:** JDBC (Java Database Connectivity)

---
## Instrucciones de Ejecución
1. Descarga o clona el repositorio y abre el proyecto en tu IDE
2. Abre MySQL Workbench y ejecuta tu script SQL para crear la base de datos `speedfast_db` y las tablas correspondientes (`pedidos`, `repartidores`, `entregas`)
3. Abre el archivo `ConexionBD.java` y verifica que las credenciales coincidan con tu servidor local.
4. Asegúrate de que el conector de MySQL esté agregado a las dependencias en tu archivo `pom.xml`
5. Ejecuta la clase `Main.java` para iniciar la interfaz gráfica del menú principal

## 📁 Estructura del Proyecto

```plaintext
SpeedFast_Semana7
├── .idea
├── src
│   ├── main
│   │   ├── java
│   │   │   └── cl
│   │   │       └── duoc
│   │   │           ├── controlador
│   │   │           │   ├── ControladorEntregas.java
│   │   │           │   ├── ControladorPedidos.java
│   │   │           │   └── ControladorRepartidor.java
│   │   │           ├── dao
│   │   │           │   ├── impl
│   │   │           │   │   ├── EntregaDAOImpl.java
│   │   │           │   │   ├── PedidoDAOImpl.java
│   │   │           │   │   └── RepartidorDAOImpl.java
│   │   │           │   ├── EntregaDAO.java
│   │   │           │   ├── PedidoDAO.java
│   │   │           │   └── RepartidorDAO.java
│   │   │           ├── modelo
│   │   │           │   ├── Entrega.java
│   │   │           │   ├── EstadoPedido.java
│   │   │           │   ├── Pedido.java
│   │   │           │   ├── Repartidor.java
│   │   │           │   └── TipoPedido.java
│   │   │           ├── util
│   │   │           │   └── ConexionBD.java
│   │   │           └── vista
│   │   │               ├── VistaVentanaEntregas.java
│   │   │               ├── VistaVentanaPedidos.java
│   │   │               ├── VistaVentanaPrincipal.java
│   │   │               └── VistaVentanaRepartidores.java
│   │   │           └── Main.java
├── pom.xml
└── README.md