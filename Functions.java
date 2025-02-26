package com.example.demo3;


import com.example.demo3.javataskclasses.EllipseShape;
import com.example.demo3.javataskclasses.LineShape;
import com.example.demo3.javataskclasses.PolylineShape;
import com.example.demo3.javataskclasses.RectangleShape;
import com.example.demo3.javataskclasses.Shapes;
import com.example.demo3.javataskclasses.TriangleShape;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Functions {
    public Functions() {
    }

    public static void addFigureDoubleClick(ChoiceBox<String> shapeChoiceBox, Pane drawingPane, ColorPicker rootColorPicker, double currentStrokeWidth) {
        drawingPane.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                double x = event.getX();
                double y = event.getY();
                String selectedShape = (String)shapeChoiceBox.getValue();
                Color color = (Color)rootColorPicker.getValue();
                switch (selectedShape) {
                    case "Line":
                        Shapes line = new LineShape(x, y, x + 150.0, y, color, currentStrokeWidth);
                        drawingPane.getChildren().add(line.draw());
                        break;
                    case "Ellipse":
                        Shapes ellipse = new EllipseShape(x, y, 80.0, 50.0, color, currentStrokeWidth);
                        drawingPane.getChildren().add(ellipse.draw());
                        break;
                    case "Triangle":
                        Shapes triangle = new TriangleShape(x, y, x + 50.0, y + 100.0, x - 50.0, y + 100.0, color, currentStrokeWidth);
                        drawingPane.getChildren().add(triangle.draw());
                        break;
                    case "Rectangle":
                        Shapes rectangle = new RectangleShape(x, y, 120.0, 80.0, color, currentStrokeWidth);
                        drawingPane.getChildren().add(rectangle.draw());
                        break;
                    case "Polyline":
                        Shapes polyline = new PolylineShape(new double[]{x, y, x + 50.0, y + 50.0, x + 100.0, y}, color, currentStrokeWidth);
                        drawingPane.getChildren().add(polyline.draw());
                }
            }

        });
    }

    public static void addClearButton(Button clearPaneButton, Pane drawingPane) {
        clearPaneButton.setOnMouseClicked((event) -> {
            drawingPane.getChildren().clear();
        });
    }
}
