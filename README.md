# 📚 Catalogo de la Biblioteca Escolar — JavaFX

CRUD de libros con persistencia en CSV  
Proyecto academico — UTEZ

---

# 📖 Descripcion

Sistema de escritorio desarrollado en **JavaFX** que permite gestionar
el catalogo de libros de una biblioteca escolar.

Permite realizar operaciones CRUD completas y almacenar la informacion
en un archivo **CSV local**, manteniendo los datos entre ejecuciones.

---

# ⚙️ Funcionalidades

✔ Registrar libros con validaciones completas  
✔ Consultar catalogo en tabla con busqueda en tiempo real  
✔ Editar libros existentes  
✔ Eliminar libros con confirmacion  
✔ Ver detalle completo de un libro  
✔ Exportar reporte del catalogo  
✔ Persistencia automatica en archivo CSV

---

# 🗂️ Estructura del proyecto

```text
src
└── main
    ├── java
    │   └── utez
    │       └── edu
    │           └── mx
    │               └── libreria
    │                   └── controllers
    │                       ├── PrincipalController.java
    │                       ├── FormularioController.java
    │                       └── DetalleController.java
    │
    └── resources
        └── utez
            └── edu
                └── mx
                    └── libreria
                        └── views
                            ├── principal.fxml
                            ├── formulario.fxml
                            ├── detalle.fxml
```

---

# 🧰 Requisitos previos

| Herramienta | Version |
|-------------|--------|
| JDK | 17 o superior |
| Maven | 3.8 o superior |
| IntelliJ IDEA | Recomendado |

---

# ▶️ Pasos de ejecucion

```bash
# 1. Clonar repositorio
git clone https://github.com/AlexRiveraExc/utez-2e-libreria-javafx-equipo01.git

# 2. Entrar al proyecto
cd utez-2e-libreria-javafx-equipo01

# 3. Ejecutar aplicacion
mvn clean javafx:run
```

Tambien puedes:

- Abrir el proyecto en **IntelliJ IDEA**
- Ejecutar directamente `MainApp.java`

La carpeta **data/** se crea automaticamente si no existe.

---

# 💾 Persistencia de datos

El sistema utiliza un archivo:

```
data/catalogo.csv
```

Este archivo almacena todos los libros registrados.

## Formato del archivo

```
ISBN;Titulo;Autor;Anio;Genero;Disponible
```

Ejemplo:

```
1991;El principito;Antoine de Saint-Exupery;1943;Ficcion;1
```

## Funcionamiento

- Al iniciar la aplicacion  
  → `LibroService.cargarDesdeArchivo()`

- Al registrar, editar o eliminar  
  → `LibroService.guardarEnArchivo()`

Las lineas que comienzan con:

```
#
```

son ignoradas como comentarios.

---

# 📊 Reporte exportado

Al presionar **"Exportar reporte"**, se genera:

```
reporte_catalogo.csv
```

Este archivo contiene:

- Fecha y hora de generacion
- Total de libros
- Columnas:

```
ISBN | Titulo | Autor | Anio | Genero | Disponible
```

- Resumen final:

```
Libros disponibles vs prestados
```

---

# 🔎 Validaciones del sistema

| Campo | Regla |
|------|-------|
| ISBN | No vacio y unico |
| Titulo | Minimo 3 caracteres |
| Autor | Minimo 3 caracteres |
| Anio | Numerico entre 1500 y año actual |
| Genero | No vacio |

---

# 👥 Equipo de desarrollo

| Integrante | Rama | Responsabilidad |
|------------|------|----------------|
| Alexis Tomas Rivera Rodriguez | alexis-rivera | Modelo, Servicio, MainApp, Persistencia |
| Kevin Morelos Torres | Kevin-Morelos | Controllers, FXML, CSS, README |

---

# 🏫 Institucion

**Universidad Tecnologica Emiliano Zapata (UTEZ)**  
Carrera: Desarrollo de Software Multiplataforma

---

# 📌 Notas adicionales

Este proyecto fue desarrollado como Tarea Integradora
para reforzar conceptos de:

- JavaFX
- Arquitectura MVC
- Persistencia en archivos CSV
- Manejo de eventos
- Validacion de datos