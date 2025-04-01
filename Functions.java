package com.example.demo3;

import com.example.demo3.javataskclasses.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.function.BiFunction;

public class Functions {
    private static final Map<String, BiFunction<Double, Double, Shapes>> shapeMap = new HashMap<>();
    private static final List<Shapes> shapesList = new ArrayList<>();

    static {
        shapeMap.put("Line", (x, y) -> new LineShape());
        shapeMap.put("Ellipse", (x, y) -> new EllipseShape());
        shapeMap.put("Triangle", (x, y) -> new TriangleShape());
        shapeMap.put("Rectangle", (x, y) -> new RectangleShape());
        shapeMap.put("Polyline", (x, y) -> new PolylineShape());
    }

    public Functions() {
    }

    public static void addFigureDoubleClick(ChoiceBox<String> shapeChoiceBox, Pane drawingPane, ColorPicker rootColorPicker, double currentStrokeWidth) {
        drawingPane.setOnMousePressed(event -> {
            double x = event.getX();
            double y = event.getY();
            String selectedShape = shapeChoiceBox.getValue();
            Color color = rootColorPicker.getValue();

            if (shapeMap.containsKey(selectedShape)) {
                Shapes shape = createShape(selectedShape, x, y, color, currentStrokeWidth);
                shapesList.add(shape); // Add shape to the list

                // Set the starting point for the shape
                shape.setStart(x, y);

                // Set event handlers based on the shape type
                if (shape instanceof PolylineShape) {
                    setupPolylineHandlers(drawingPane, (PolylineShape) shape);
                } else {
                    setupOtherShapeHandlers(drawingPane, shape);
                }
            }
        });
    }

    private static Shapes createShape(String selectedShape, double x, double y, Color color, double currentStrokeWidth) {
        Shapes shape = shapeMap.get(selectedShape).apply(x, y);
        shape.setColor(color);
        shape.setStrokeWidth(currentStrokeWidth);
        return shape;
    }

    private static void setupPolylineHandlers(Pane drawingPane, PolylineShape shape) {
        drawingPane.setOnMousePressed(e -> {
            shape.addPoint(e.getX(), e.getY());
            updateDrawingPane(drawingPane, shape);
        });

        drawingPane.setOnKeyPressed(e -> {
            if (Objects.requireNonNull(e.getCode()) == KeyCode.ENTER) { // Finalize the polyline when the Enter key is pressed
                shape.finalizeShape(0, 0); // Finalize without adding extra points
                resetDrawingPaneHandlers(drawingPane); // Reset handlers to allow other shapes
            }
        });

        drawingPane.setOnMouseReleased(e -> {
            // Optional: Add logic here if needed for mouse release
        });

        // Ensure the drawing pane is focused to capture key events
        drawingPane.requestFocus();
    }

    private static void setupOtherShapeHandlers(Pane drawingPane, Shapes shape) {
        drawingPane.setOnMouseDragged(e -> {
            shape.updateShape(e.getX(), e.getY());
            updateDrawingPane(drawingPane, shape);
        });

        drawingPane.setOnMouseReleased(e -> {
            shape.finalizeShape(e.getX(), e.getY());
        });
    }

    private static void updateDrawingPane(Pane drawingPane, Shapes shape) {
        drawingPane.getChildren().remove(shape.draw());
        drawingPane.getChildren().add(shape.draw());
    }

    private static void resetDrawingPaneHandlers(Pane drawingPane) {
        drawingPane.setOnMousePressed(null);
        drawingPane.setOnMouseDragged(null);
        drawingPane.setOnMouseReleased(null);
        drawingPane.setOnKeyPressed(null);
    }

    public static void addClearButton(Button clearPaneButton, Pane drawingPane) {
        clearPaneButton.setOnMouseClicked((event) -> {
            drawingPane.getChildren().clear();
            for (Shapes shape : shapesList) {
                shape.reset();
            }
            shapesList.clear();
        });
    }
}
