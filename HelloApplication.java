package com.example.demo3;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private double currentStrokeWidth = 1.0;
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

    public HelloApplication() {
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane root = (Pane)fxmlLoader.load();
        Scene scene = new Scene(root, 600.0, 400.0);
        stage.setTitle("Figures");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        this.shapeChoiceBox.getItems().addAll(new String[]{"Line", "Ellipse", "Triangle", "Rectangle", "Polyline"});
        this.shapeChoiceBox.setValue("Line");
        this.depthScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.currentStrokeWidth = newValue.doubleValue();
            Functions.addFigureDoubleClick(this.shapeChoiceBox, this.drawingPane, this.rootColorPicker, this.currentStrokeWidth);
        });
        Functions.addFigureDoubleClick(this.shapeChoiceBox, this.drawingPane, this.rootColorPicker, this.currentStrokeWidth);
        Functions.addClearButton(this.clearPaneButton, this.drawingPane);
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }
}
