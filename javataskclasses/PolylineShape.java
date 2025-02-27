package com.example.demo3.javataskclasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;

public class PolylineShape extends Shapes {
    private double[] points;
    private Color color;
    private double strokeWidth;

    public PolylineShape(double[] points, Color color, double strokeWidth) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }
    @Override
    public Shape draw() {
        Polyline polyline = new Polyline(this.points);
        polyline.setFill(Color.TRANSPARENT);
        polyline.setStrokeWidth(this.strokeWidth);
        polyline.setStroke(this.color);
        this.setJavaFXShape(polyline);
        return polyline;
    }
}