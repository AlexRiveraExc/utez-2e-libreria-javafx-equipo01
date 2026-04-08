# 📚 Catálogo de la Biblioteca Escolar — JavaFX

CRUD de libros con persistencia en CSV — UTEZ

---

## Descripción

Sistema de escritorio para una biblioteca escolar. Permite gestionar el catálogo
de libros (Alta, Consulta, Edición, Eliminación). La información se guarda en un
archivo .csv local para persistir entre ejecuciones.

### Funcionalidades
Registrar libros con validaciones completas
Consultar el catálogo en tabla con búsqueda en tiempo real
Editar y eliminar libros (con confirmación)
Ver detalle completo de un libro en pantalla dedicada
Exportar reporte del catálogo a reporte_catalogo.csv

---

## Estructura del proyecto
utez-2e-libreria-javafx-equipo01/
├── data/catalogo.csv
├── pom.xml
├── README.md
└── src/main/
├── java/
│   ├── module-info.java
│   └── utez/edu/mx/libreria/
│       ├── MainApp.java
│       ├── model/Libro.java
│       ├── service/LibroService.java
│       └── controller/
│           ├── PrincipalController.java
│           ├── FormularioController.java
│           └── DetalleController.java
└── resources/utez/edu/mx/libreria/views/
├── principal.fxml
├── formulario.fxml
├── detalle.fxml
└── styles.css

---

## Requisitos previos

| Herramienta | Versión |
|-------------|---------|
| JDK         | 17+     |
| Maven       | 3.8+    |

---

## Pasos de ejecución
bash
# 1. Clonar el repositorio
git clone https://github.com/TU_ORG/utez-2e-libreria-javafx-equipo01.git
cd utez-2e-libreria-javafx-equipo01

# 2. Ejecutar con Maven
mvn clean javafx:run

También se puede abrir en IntelliJ IDEA y ejecutar MainApp.java directamente.

La carpeta data/ se crea automáticamente si no existe.

---

## Persistencia

El archivo **data/catalogo.csv** almacena todos los libros.

**Formato de cada línea:**
ISBN;Título;Autor;Año;Género;Disponible(1=sí / 0=no)

Al **iniciar** la app → LibroService.cargarDesdeArchivo() lee el archivo.
Tras cada **Alta, Edición o Eliminación** → LibroService.guardarEnArchivo() lo sobreescribe.
Las líneas que comienzan con # son comentarios y se ignoran.

---

## Reporte exportado

Al presionar **"Exportar reporte"** se genera **reporte_catalogo.csv** con:
Encabezado con fecha y hora
Total de libros
Columnas: ISBN, Título, Autor, Año, Género, Disponible
Resumen al pie: disponibles vs prestados

---

## Validaciones

| Campo  | Regla |
|--------|-------|
| ISBN   | No vacío, único |
| Título | No vacío, mínimo 3 caracteres |
| Autor  | No vacío, mínimo 3 caracteres |
| Año    | Numérico, entre 1500 y año actual |
| Género | No vacío |

---

## Equipo

| Integrante                   | Rama          | Responsabilidad |
|------------------------------|---------------|-----------------|
| Alexis Tomas Rivera Rodriguez | alexis-rivera | Modelo, Servicio, MainApp, persistencia |
| Kevin Morelos Torres         | Kevin-Morelos | Controllers, FXML, CSS, README |