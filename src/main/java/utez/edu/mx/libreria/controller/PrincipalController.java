package utez.edu.mx.libreria.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utez.edu.mx.libreria.MainApp;
import utez.edu.mx.libreria.model.Libro;
import utez.edu.mx.libreria.service.LibroService;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller de la pantalla principal.
 * Gestiona la tabla y todos los botones de acción.
 *
 * @author Integrante 2 (UI)
 */
public class PrincipalController implements Initializable {

    @FXML private TableView<Libro>            tablaLibros;
    @FXML private TableColumn<Libro, String>  colIsbn;
    @FXML private TableColumn<Libro, String>  colTitulo;
    @FXML private TableColumn<Libro, String>  colAutor;
    @FXML private TableColumn<Libro, Integer> colAnio;
    @FXML private TableColumn<Libro, String>  colGenero;
    @FXML private TableColumn<Libro, String>  colDisponible;
    @FXML private Label     lblEstado;
    @FXML private TextField txtBuscar;

    private LibroService            servicio;
    private ObservableList<Libro>   listaObservable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicio        = MainApp.getLibroService();
        listaObservable = FXCollections.observableArrayList();
        configurarColumnas();
        cargarTabla();
        tablaLibros.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void configurarColumnas() {
        colIsbn.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getIsbn()));
        colTitulo.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getTitulo()));
        colAutor.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getAutor()));
        colAnio.setCellValueFactory(
                d -> new SimpleIntegerProperty(d.getValue().getAnio()).asObject());
        colGenero.setCellValueFactory(
                d -> new SimpleStringProperty(d.getValue().getGenero()));
        colDisponible.setCellValueFactory(
                d -> new SimpleStringProperty(
                        d.getValue().isDisponible() ? "✔ Sí" : "✘ No"));
    }

    /** Recarga la tabla desde el servicio (llamado también desde formulario). */
    public void cargarTabla() {
        listaObservable.setAll(servicio.obtenerTodos());
        tablaLibros.setItems(listaObservable);
        lblEstado.setText("Total de libros: " + listaObservable.size());
    }

    // ── Búsqueda en tiempo real ────────────────────────────────────────
    @FXML
    private void onBuscar() {
        String f = txtBuscar.getText().toLowerCase().trim();
        if (f.isBlank()) {
            listaObservable.setAll(servicio.obtenerTodos());
        } else {
            listaObservable.setAll(
                    servicio.obtenerTodos().stream()
                            .filter(l -> l.getTitulo().toLowerCase().contains(f)
                                    || l.getAutor().toLowerCase().contains(f)
                                    || l.getIsbn().toLowerCase().contains(f))
                            .toList());
        }
        tablaLibros.setItems(listaObservable);
        lblEstado.setText("Total de libros: " + listaObservable.size());
    }

    // ── Botones CRUD ───────────────────────────────────────────────────
    @FXML private void onNuevo()   { abrirFormulario(null); }

    @FXML
    private void onEditar() {
        Libro sel = tablaLibros.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alerta(Alert.AlertType.WARNING, "Sin selección",
                    "Selecciona un libro para editar."); return;
        }
        abrirFormulario(sel);
    }

    @FXML
    private void onEliminar() {
        Libro sel = tablaLibros.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alerta(Alert.AlertType.WARNING, "Sin selección",
                    "Selecciona un libro para eliminar."); return;
        }
        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle("Confirmar eliminación");
        conf.setHeaderText("¿Eliminar el libro?");
        conf.setContentText("\"" + sel.getTitulo() +
                "\" (ISBN: " + sel.getIsbn() + ") será eliminado permanentemente.");
        Optional<ButtonType> r = conf.showAndWait();
        if (r.isPresent() && r.get() == ButtonType.OK) {
            try {
                servicio.eliminarLibro(sel.getIsbn());
                cargarTabla();
                alerta(Alert.AlertType.INFORMATION,
                        "Eliminado", "El libro fue eliminado correctamente.");
            } catch (Exception e) {
                alerta(Alert.AlertType.ERROR,
                        "Error", "No se pudo eliminar: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onVerDetalle() {
        Libro sel = tablaLibros.getSelectionModel().getSelectedItem();
        if (sel == null) {
            alerta(Alert.AlertType.WARNING, "Sin selección",
                    "Selecciona un libro para ver su detalle."); return;
        }
        abrirDetalle(sel);
    }

    @FXML
    private void onExportarReporte() {
        try {
            String ruta = servicio.exportarReporte();
            alerta(Alert.AlertType.INFORMATION,
                    "Reporte exportado",
                    "El reporte fue guardado en:\n" + ruta);
        } catch (Exception e) {
            alerta(Alert.AlertType.ERROR, "Error al exportar", e.getMessage());
        }
    }

    // ── Abrir ventanas secundarias ─────────────────────────────────────
    private void abrirFormulario(Libro libro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/formulario.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 500, 420));
            stage.setTitle(libro == null ? "Nuevo libro" : "Editar libro");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            FormularioController ctrl = loader.getController();
            ctrl.init(libro, this);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            alerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir el formulario: " + e.getMessage());
        }
    }

    private void abrirDetalle(Libro libro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/detalle.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 480, 380));
            stage.setTitle("Detalle del libro");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            DetalleController ctrl = loader.getController();
            ctrl.setLibro(libro);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            alerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo abrir el detalle: " + e.getMessage());
        }
    }

    // ── Utilidad ───────────────────────────────────────────────────────
    private void alerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}