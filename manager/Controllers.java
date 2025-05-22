package com.example.demo3.manager;

import com.example.demo3.Functions;
import com.example.demo3.javataskclasses.Shapes;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;

import java.util.List;

public class Controllers {
    public static void addClearButton(Button clearPaneButton, Pane drawingPane, List<Shapes> shapesList) {
        clearPaneButton.setOnAction(event -> {
            for (Shapes shape : shapesList) {
                shape.clear();
            }

            drawingPane.getChildren().clear();
            shapesList.clear();
            Functions.getHistoryManager().clear();
            Functions.setCurrentShape(null);
            Functions.setIsDrawingPolyline(false);

            Functions.resetDrawingPaneHandlers(drawingPane);
            Functions.addFigureDoubleClick(drawingPane);
        });
    }

    public static void addUndoButton(Button undoButton, Pane drawingPane, List<Shapes> shapesList) {
        undoButton.setOnAction(event -> {
            if (Functions.getHistoryManager().canUndo()) {
                List<Shapes> previousState = Functions.getHistoryManager().undo();
                shapesList.clear();
                shapesList.addAll(previousState);
                Functions.redrawAllShapes(drawingPane);
            }
        });
    }

    public static void addRedoButton(Button redoButton, Pane drawingPane, List<Shapes> shapesList) {
        redoButton.setOnAction(event -> {
            if (Functions.getHistoryManager().canRedo()) {
                List<Shapes> nextState = Functions.getHistoryManager().redo();
                shapesList.clear();
                shapesList.addAll(nextState);
                Functions.redrawAllShapes(drawingPane);
            }
        });
    }
}
