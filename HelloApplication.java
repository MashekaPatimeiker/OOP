package com.example.demo3;

import com.example.demo3.javataskclasses.PolygonShape;
import com.example.demo3.javataskclasses.Shapes;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @FXML
    public ColorPicker fillColorPicker;
    @FXML
    public Button fillButton;
    @FXML
    private Button clearPaneButton;
    @FXML
    private ChoiceBox<String> shapeChoiceBox;
    @FXML
    private ScrollBar depthScrollBar;
    @FXML
    public ColorPicker rootColorPicker;
    @FXML
    private Pane drawingPane;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane root = (Pane) fxmlLoader.load();
        Scene scene = new Scene(root, 694, 464);
        stage.setTitle("Figures");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        this.shapeChoiceBox.getItems().addAll("Line", "Ellipse", "Polygon", "Rectangle", "Polyline");
        this.shapeChoiceBox.setValue("Line");
        this.depthScrollBar.setMin(1.0);
        this.depthScrollBar.setMax(20.0);
        this.depthScrollBar.setValue(1.0);

        // Добавляем обработчик выбора фигуры
        this.shapeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Polygon".equals(newValue)) {
                showPolygonSidesDialog();
            }
        });

        Functions.addFigureDoubleClick(this.shapeChoiceBox, this.drawingPane,
                this.rootColorPicker, this.fillColorPicker, this.depthScrollBar);
        Functions.addClearButton(this.clearPaneButton, this.drawingPane);
    }

    private void showPolygonSidesDialog() {
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Polygon Settings");
        dialog.setHeaderText("Enter number of sides");
        dialog.setContentText("Sides (3-20):");

        // Обработка результата диалога
        dialog.showAndWait().ifPresent(sidesStr -> {
            try {
                int sides = Integer.parseInt(sidesStr);
                if (sides < 3 || sides > 20) {
                    showAlert("Invalid input", "Number of sides must be between 3 and 20");
                } else {
                    PolygonShape.setDefaultSides(sides);
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid input", "Please enter a valid number");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}