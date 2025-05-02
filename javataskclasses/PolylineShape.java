package com.example.demo3.javataskclasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class PolylineShape extends Shapes {
    private final List<Line> lines = new ArrayList<>();
    private double lastX, lastY;
    private boolean isFirstPoint = true;
    private Color color;
    private double strokeWidth;
    private boolean isFinalized = false;

    public PolylineShape() {
        this.color = Color.BLACK;
        this.strokeWidth = 1.0;
    }

    @Override
    public Shape draw() {
        if (isFinalized || lines.isEmpty()) {
            return null;
        }
        return createPreviewLine(lastX, lastY);
    }

    public void addPoint(double x, double y) {
        if (isFinalized) return;

        if (isFirstPoint) {
            lastX = x;
            lastY = y;
            isFirstPoint = false;
        } else {
            Line newLine = createLine(lastX, lastY, x, y);
            lines.add(newLine);
            lastX = x;
            lastY = y;
        }
    }

    private Line createLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(color);
        line.setStrokeWidth(strokeWidth);
        return line;
    }

    public Shape createPreviewLine(double endX, double endY) {
        if (isFirstPoint || isFinalized) {
            return null;
        }
        return createLine(lastX, lastY, endX, endY);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        for (Line line : lines) {
            line.setStroke(color);
        }
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        for (Line line : lines) {
            line.setStrokeWidth(strokeWidth);
        }
    }

    @Override
    public void updateShape(double x, double y) {
    }

    @Override
    public void finalizeShape(double x, double y) {
        this.isFinalized = true;
    }

    @Override
    public void setStart(double x, double y) {
        addPoint(x, y);
    }

    @Override
    public void reset() {
        lines.clear();
        isFirstPoint = true;
        isFinalized = false;
    }

    public boolean isFinalized() {
        return isFinalized;
    }

    public List<Line> getLines() {
        return new ArrayList<>(lines);
    }

    public boolean hasLines() {
        return !lines.isEmpty();
    }
}