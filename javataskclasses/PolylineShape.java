package com.example.demo3.javataskclasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.TRANSPARENT;

public class PolylineShape extends Shapes {
    private List<Double> points;
    private Color color;
    private double strokeWidth;
    private boolean isFinalized = false;

    public PolylineShape() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK;
        this.strokeWidth = 1.0;
        Polyline polyline = new Polyline();
        setJavaFXShape(polyline);
    }

    @Override
    public Shape draw() {
        Polyline polyline = (Polyline) this.javafxShape;
        polyline.getPoints().clear();
        polyline.getPoints().addAll(points);
        polyline.setStroke(color);
        polyline.setStrokeWidth(strokeWidth);
        polyline.setFill(TRANSPARENT);
        return polyline;
    }

    public void addPoint(double x, double y) {
        if (!isFinalized) {
            points.add(x);
            points.add(y);
            draw();
        }
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        if (this.javafxShape != null) {
            this.javafxShape.setStroke(color);
        }
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        if (this.javafxShape != null) {
            this.javafxShape.setStrokeWidth(strokeWidth);
        }
    }

    @Override
    public void updateShape(double x, double y) {
        // Не требуется для полилинии
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
        points.clear();
        isFinalized = false;
    }

    public boolean isFinalized() {
        return isFinalized;
    }
}