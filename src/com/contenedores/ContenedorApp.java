package com.contenedores;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ContenedorApp extends Application {
    private ContenedorDAO dao = new ContenedorDAO();
    private TableView<ContenedorCilindrico> tableView;
    private ObservableList<ContenedorCilindrico> contenedores;

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(10);

        // Tabla para listar los contenedores
        tableView = new TableView<>();
        TableColumn<ContenedorCilindrico, Double> volumenColumn = new TableColumn<>("Volumen (L)");
        volumenColumn.setCellValueFactory(cellData -> cellData.getValue().volumenProperty().asObject());
        TableColumn<ContenedorCilindrico, Double> alturaColumn = new TableColumn<>("Altura (m)");
        alturaColumn.setCellValueFactory(cellData -> cellData.getValue().alturaProperty().asObject());
        TableColumn<ContenedorCilindrico, Double> radioColumn = new TableColumn<>("Radio (m)");
        radioColumn.setCellValueFactory(cellData -> cellData.getValue().radioProperty().asObject());
        tableView.getColumns().addAll(volumenColumn, alturaColumn, radioColumn);

        // Botón para cargar los contenedores
        Button cargarBtn = new Button("Cargar Contenedores");
        cargarBtn.setOnAction(e -> cargarContenedores());

        // Campo para agregar o modificar contenedores
        TextField volumenField = new TextField();
        volumenField.setPromptText("Nuevo volumen (3, 5, 10)");

        Button agregarBtn = new Button("Agregar Contenedor");
        agregarBtn.setOnAction(e -> {
            try {
                double volumen = Double.parseDouble(volumenField.getText());
                ContenedorCilindrico nuevoContenedor = new ContenedorCilindrico(0, volumen, 0, 0);
                dao.agregarContenedor(nuevoContenedor);
                cargarContenedores();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contenedor agregado.");
                alert.show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error al agregar el contenedor.").show();
            }
        });

        Button modificarBtn = new Button("Modificar Contenedor");
        modificarBtn.setOnAction(e -> {
            ContenedorCilindrico seleccionado = tableView.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                try {
                    double nuevoVolumen = Double.parseDouble(volumenField.getText());
                    dao.modificarContenedor(seleccionado.getId(), nuevoVolumen);
                    cargarContenedores();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contenedor modificado.");
                    alert.show();
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Error al modificar el contenedor.").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecciona un contenedor primero.").show();
            }
        });

        Button eliminarBtn = new Button("Eliminar Contenedor");
        eliminarBtn.setOnAction(e -> {
            ContenedorCilindrico seleccionado = tableView.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                dao.eliminarContenedor(seleccionado.getId());
                cargarContenedores();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Contenedor eliminado.");
                alert.show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Selecciona un contenedor primero.").show();
            }
        });

        layout.getChildren().addAll(new Label("Gestión de Contenedores"), tableView, cargarBtn,
                new Label("Volumen:"), volumenField, agregarBtn, modificarBtn, eliminarBtn);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestión de Contenedores");
        primaryStage.show();
    }

    private void cargarContenedores() {
        contenedores = FXCollections.observableArrayList(dao.obtenerTodos());
        tableView.setItems(contenedores);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
