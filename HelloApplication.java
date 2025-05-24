package com.example.demo3;

import com.example.demo3.javataskclasses.myshapes.PolygonShape;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
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
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane root = (Pane) fxmlLoader.load();
        Scene scene = new Scene(root, 800, 550);
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
        Functions.initializeUIComponents(shapeChoiceBox, rootColorPicker, fillColorPicker, depthScrollBar);
        Functions.addFigureDoubleClick(this.drawingPane);
        Functions.addClearButton(this.clearPaneButton, this.drawingPane);
        Functions.addUndoButton(this.undoButton, this.drawingPane);
        Functions.addRedoButton(this.redoButton, this.drawingPane);
    }


    public static void main(String[] args) {
        launch();
    }
}