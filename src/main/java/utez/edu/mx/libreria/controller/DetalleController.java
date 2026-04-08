package utez.edu.mx.libreria.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import utez.edu.mx.libreria.model.Libro;

/**
 * Controller de la pantalla de Detalle del libro.
 *
 * @author Integrante 2 (UI)
 */
public class DetalleController {

    @FXML private Label lblIsbn;
    @FXML private Label lblTitulo;
    @FXML private Label lblAutor;
    @FXML private Label lblAnio;
    @FXML private Label lblGenero;
    @FXML private Label lblDisponible;

    /** Rellena los labels con los datos del libro. */
    public void setLibro(Libro libro) {
        lblIsbn.setText(libro.getIsbn());
        lblTitulo.setText(libro.getTitulo());
        lblAutor.setText(libro.getAutor());
        lblAnio.setText(String.valueOf(libro.getAnio()));
        lblGenero.setText(libro.getGenero());
        lblDisponible.setText(
                libro.isDisponible() ? "✔  Disponible" : "✘  No disponible (prestado)");
    }

    @FXML
    private void onRegresar() {
        ((Stage) lblIsbn.getScene().getWindow()).close();
    }
}