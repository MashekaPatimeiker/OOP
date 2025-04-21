package com.example.demo3;

import com.example.demo3.javataskclasses.Shapes;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ArrayList;

public class HistoryManager {
    private final Deque<List<Shapes>> undoStack = new ArrayDeque<>();
    private final Deque<List<Shapes>> redoStack = new ArrayDeque<>();
    private List<Shapes> currentState = new ArrayList<>();

    public void saveState(List<Shapes> shapes) {
        // Сохраняем текущее состояние в стек undo
        undoStack.push(new ArrayList<>(currentState));
        // Обновляем текущее состояние
        currentState = new ArrayList<>(shapes);
        // Очищаем стек redo при новом действии
        redoStack.clear();
    }

    public List<Shapes> undo() {
        if (canUndo()) {
            // Сохраняем текущее состояние в стек redo
            redoStack.push(new ArrayList<>(currentState));
            // Восстанавливаем предыдущее состояние
            currentState = undoStack.pop();
            return new ArrayList<>(currentState);
        }
        return currentState;
    }

    public List<Shapes> redo() {
        if (canRedo()) {
            // Сохраняем текущее состояние в стек undo
            undoStack.push(new ArrayList<>(currentState));
            // Восстанавливаем отмененное состояние
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
}