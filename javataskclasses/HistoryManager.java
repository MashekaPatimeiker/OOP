package com.example.demo3.javataskclasses;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class HistoryManager {
    private final Deque<List<Shapes>> undoStack = new ArrayDeque<>();
    private final Deque<List<Shapes>> redoStack = new ArrayDeque<>();
    private List<Shapes> currentState = new ArrayList<>();

    public void saveState(List<Shapes> shapes) {
        List<Shapes> filteredState = filterFinalizedShapes(shapes);

        undoStack.push(new ArrayList<>(currentState));
        currentState = new ArrayList<>(filteredState);
        redoStack.clear();
    }

    public List<Shapes> undo() {
        if (canUndo()) {
            redoStack.push(new ArrayList<>(currentState));
            currentState = undoStack.pop();
            return new ArrayList<>(currentState);
        }
        return currentState;
    }

    public List<Shapes> redo() {
        if (canRedo()) {
            undoStack.push(new ArrayList<>(currentState));
            currentState = redoStack.pop();
            return new ArrayList<>(currentState);
        }
        return currentState;
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
        currentState.clear();
    }

    private List<Shapes> filterFinalizedShapes(List<Shapes> shapes) {
        List<Shapes> result = new ArrayList<>();
        for (Shapes shape : shapes) {
            if (!(shape instanceof PolylineShape) || ((PolylineShape) shape).isFinalized()) {
                result.add(shape);
            }
        }
        return result;
    }
}