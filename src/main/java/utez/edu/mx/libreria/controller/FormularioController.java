package utez.edu.mx.libreria.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utez.edu.mx.libreria.MainApp;
import utez.edu.mx.libreria.model.Libro;
import utez.edu.mx.libreria.service.LibroService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller del formulario de Alta / Edición.
 * libroOriginal == null → modo Alta
 * libroOriginal != null → modo Edición
 *
 */
public class FormularioController implements Initializable {

    @FXML private Label          lblTituloPantalla;
    @FXML private TextField      txtIsbn;
    @FXML private TextField      txtTitulo;
    @FXML private TextField      txtAutor;
    @FXML private TextField      txtAnio;
    @FXML private ComboBox<String> cboGenero;
    @FXML private CheckBox       chkDisponible;
    @FXML private Label          lblError;
    @FXML private Button         btnGuardar;

    private LibroService         servicio;
    private Libro                libroOriginal;
    private PrincipalController  principal;

    private static final String[] GENEROS = {
            "Ciencia ficción", "Fantasía", "Historia", "Ciencias",
            "Literatura", "Filosofía", "Arte", "Tecnología",
            "Matemáticas", "Biografía", "Otros"
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicio = MainApp.getLibroService();
        cboGenero.getItems().addAll(GENEROS);
        cboGenero.getSelectionModel().selectFirst();
        lblError.setVisible(false);
    }

    /**
     * Inicializa el formulario.
     * Llamado desde PrincipalController después de cargar el FXML.
     */
    public void init(Libro libro, PrincipalController principal) {
        this.libroOriginal = libro;
        this.principal     = principal;

        if (libro == null) {
            lblTituloPantalla.setText("Registrar nuevo libro");
            chkDisponible.setSelected(true);
        } else {
            lblTituloPantalla.setText("Editar libro");
            txtIsbn.setText(libro.getIsbn());
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtAnio.setText(String.valueOf(libro.getAnio()));
            cboGenero.setValue(libro.getGenero());
            chkDisponible.setSelected(libro.isDisponible());
        }
    }

    @FXML
    private void onGuardar() {
        lblError.setVisible(false);
        try {
            Libro libro = construirLibro();
            if (libroOriginal == null) {
                servicio.agregarLibro(libro);
            } else {
                servicio.actualizarLibro(libroOriginal.getIsbn(), libro);
            }
            principal.cargarTabla();
            cerrar();
        } catch (IllegalArgumentException e) {
            lblError.setText("⚠ " + e.getMessage());
            lblError.setVisible(true);
        } catch (Exception e) {
            lblError.setText("Error inesperado: " + e.getMessage());
            lblError.setVisible(true);
        }
    }

    @FXML
    private void onCancelar() { cerrar(); }

    private Libro construirLibro() {
        String isbn    = txtIsbn.getText().trim();
        String titulo  = txtTitulo.getText().trim();
        String autor   = txtAutor.getText().trim();
        String anioStr = txtAnio.getText().trim();
        String genero  = cboGenero.getValue();
        boolean disp   = chkDisponible.isSelected();

        int anio;
        try {
            anio = Integer.parseInt(anioStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El año debe ser un número entero.");
        }
        return new Libro(isbn, titulo, autor, anio, genero, disp);
    }

    private void cerrar() {
        ((Stage) btnGuardar.getScene().getWindow()).close();
    }
}