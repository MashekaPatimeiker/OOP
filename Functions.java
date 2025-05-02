package com.example.demo3;

import com.example.demo3.javataskclasses.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import java.util.*;
import java.util.function.BiFunction;
import javafx.scene.input.MouseButton;
public class Functions {
    private static final Map<String, BiFunction<Double, Double, Shapes>> shapeMap = new HashMap<>();
    private static final List<Shapes> shapesList = new ArrayList<>();
    private static Shapes currentShape = null;
    private static boolean isDrawingPolyline = false;
    private static Shapes selectedShape = null;
    private static final HistoryManager historyManager = new HistoryManager();
    private static ChoiceBox<String> shapeChoiceBox;
    private static ColorPicker rootColorPicker;
    private static ColorPicker fillColorPicker;
    private static ScrollBar depthScrollBar;

    public static void initializeUIComponents(ChoiceBox<String> shapeCB, ColorPicker rootCP,
                                              ColorPicker fillCP, ScrollBar depthSB) {
        shapeChoiceBox = shapeCB;
        rootColorPicker = rootCP;
        fillColorPicker = fillCP;
        depthScrollBar = depthSB;
    }
    static {
        shapeMap.put("Line", (x, y) -> new LineShape());
        shapeMap.put("Ellipse", (x, y) -> new EllipseShape());
        shapeMap.put("Polygon", (x, y) -> new PolygonShape());
        shapeMap.put("Rectangle", (x, y) -> new RectangleShape());
        shapeMap.put("Polyline", (x, y) -> new PolylineShape());
    }

    public static void setSelectedShape(Shapes shape) {
        if (selectedShape != null) {
            selectedShape.setSelected(false);
        }
        selectedShape = shape;
        if (selectedShape != null) {
            selectedShape.setSelected(true);
        }
    }

    public static Shapes getSelectedShape() {
        return selectedShape;
    }

    public static void addFigureDoubleClick(ChoiceBox<String> shapeChoiceBox, Pane drawingPane,
                                            ColorPicker rootColorPicker, ColorPicker fillColorPicker,
                                            ScrollBar depthScrollBar) {
        drawingPane.setOnMousePressed(event -> {
            if (event.getTarget() instanceof Shape || isDrawingPolyline) {
                return;
            }

            String selectedShape = shapeChoiceBox.getValue();
            if (shapeMap.containsKey(selectedShape)) {
                currentShape = createShape(selectedShape, event.getX(), event.getY(),
                        rootColorPicker.getValue(), depthScrollBar.getValue());
                currentShape.setFillColor(fillColorPicker.getValue());
                shapesList.add(currentShape);
                currentShape.setStart(event.getX(), event.getY());

                if ("Polyline".equals(selectedShape)) {
                    isDrawingPolyline = true;
                    setupPolylineHandlers(drawingPane, (PolylineShape) currentShape);
                } else {
                    setupOtherShapeHandlers(drawingPane, currentShape);
                }

                saveCurrentState();
            }
        });
    }

    private static Shapes createShape(String shapeType, double x, double y,
                                      Color color, double strokeWidth) {
        Shapes shape = shapeMap.get(shapeType).apply(x, y);
        shape.setColor(color);
        shape.setStrokeWidth(strokeWidth);
        return shape;
    }

    private static void setupPolylineHandlers(Pane drawingPane, PolylineShape shape) {
        resetDrawingPaneHandlers(drawingPane);
        Line[] previewLine = {null};

        drawingPane.setOnMouseMoved(e -> {
            if (previewLine[0] != null) {
                drawingPane.getChildren().remove(previewLine[0]);
            }
            previewLine[0] = (Line) shape.createPreviewLine(e.getX(), e.getY());
            if (previewLine[0] != null) {
                drawingPane.getChildren().add(previewLine[0]);
            }
        });

        drawingPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                shape.addPoint(e.getX(), e.getY());
                redrawPolyline(drawingPane, shape);
            } else if (e.getButton() == MouseButton.SECONDARY) {
                if (shape.hasLines()) {
                    shape.finalizeShape(0, 0);
                    isDrawingPolyline = false;
                    currentShape = null;
                    if (previewLine[0] != null) {
                        drawingPane.getChildren().remove(previewLine[0]);
                        previewLine[0] = null;
                    }
                    resetDrawingPaneHandlers(drawingPane);
                    saveCurrentState();
                    addFigureDoubleClick(shapeChoiceBox, drawingPane, rootColorPicker, fillColorPicker, depthScrollBar);
                }
            }
        });

        drawingPane.requestFocus();
    }

    private static void redrawPolyline(Pane drawingPane, PolylineShape shape) {
        drawingPane.getChildren().removeIf(node ->
                node instanceof Line && shape.getLines().contains(node));
        for (Line line : shape.getLines()) {
            drawingPane.getChildren().add(line);
        }
    }

    private static void setupOtherShapeHandlers(Pane drawingPane, Shapes shape) {
        drawingPane.setOnMouseDragged(e -> {
            shape.updateShape(e.getX(), e.getY());
            updateDrawingPane(drawingPane, shape);
        });

        drawingPane.setOnMouseReleased(e -> {
            shape.finalizeShape(e.getX(), e.getY());
            if (shape instanceof PolygonShape) {
                ((PolygonShape) shape).finishCreation();
            }
            saveCurrentState();
        });
    }

    private static void updateDrawingPane(Pane drawingPane, Shapes shape) {
        drawingPane.getChildren().removeIf(node -> node == shape.draw());
        Shape drawnShape = shape.draw();
        drawingPane.getChildren().add(drawnShape);
    }

    private static void resetDrawingPaneHandlers(Pane drawingPane) {
        drawingPane.setOnMousePressed(null);
        drawingPane.setOnMouseDragged(null);
        drawingPane.setOnMouseReleased(null);
        drawingPane.setOnKeyPressed(null);
    }

    public static void addClearButton(Button clearPaneButton, Pane drawingPane) {
        clearPaneButton.setOnAction(event -> {
            drawingPane.getChildren().clear();
            shapesList.clear();
            historyManager.clear();
            currentShape = null;
            isDrawingPolyline = false;
            selectedShape = null;
        });
    }

    public static void addUndoButton(Button undoButton, Pane drawingPane) {
        undoButton.setOnAction(event -> {
            if (historyManager.canUndo()) {
                List<Shapes> previousState = historyManager.undo();
                shapesList.clear();
                shapesList.addAll(previousState);
                redrawAllShapes(drawingPane);
            }
        });
    }

    public static void addRedoButton(Button redoButton, Pane drawingPane) {
        redoButton.setOnAction(event -> {
            if (historyManager.canRedo()) {
                List<Shapes> nextState = historyManager.redo();
                shapesList.clear();
                shapesList.addAll(nextState);
                redrawAllShapes(drawingPane);
            }
        });
    }

    private static void saveCurrentState() {
        historyManager.saveState(new ArrayList<>(shapesList));
    }

    private static void redrawAllShapes(Pane drawingPane) {
        drawingPane.getChildren().clear();
        for (Shapes shape : shapesList) {
            if (shape instanceof PolylineShape) {
                PolylineShape polyline = (PolylineShape) shape;
                if (polyline.isFinalized()) {
                    for (Line line : polyline.getLines()) {
                        drawingPane.getChildren().add(line);
                    }
                }
            } else {
                Shape drawnShape = shape.draw();
                if (drawnShape != null) {
                    drawingPane.getChildren().add(drawnShape);
                }
            }
        }
    }
}