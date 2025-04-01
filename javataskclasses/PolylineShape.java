package com.example.demo3.javataskclasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class PolylineShape extends Shapes {
    private List<Double> points;
    private Color color;
    private double strokeWidth;
    private String currentShapeType; // Хранит текущий тип фигуры

    public PolylineShape() {
        this.points = new ArrayList<>();
        this.color = Color.BLACK; // Default color
        this.strokeWidth = 1.0; // Default stroke width
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
        return polyline;
    }

    public void addPoint(double x, double y) {
        points.add(x);
        points.add(y);
        draw();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void updateShape(double x, double y) {
        // Not needed for polyline
    }

    @Override
    public void finalizeShape(double x, double y) {
        currentShapeType = "Polyline";
        // Add any additional logic for finalizing the polyline
    }


    @Override
    public void setStart(double x, double y) {
        addPoint(x, y);
    }

    @Override
    public void reset() {
        points.clear();
    }

    public void setCurrentShapeType(String shapeType) {
        this.currentShapeType = shapeType;
    }
}
