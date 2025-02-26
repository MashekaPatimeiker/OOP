package com.example.demo3.javataskclasses;


import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class EllipseShape extends Shapes {
    private double centerX;
    private double centerY;
    private double radiusX;
    private double radiusY;
    private Color color;
    private double strokeWidth;

    public EllipseShape(double centerX, double centerY, double radiusX, double radiusY, Color color, double strokeWidth) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public Shape draw() {
        Ellipse ellipse = new Ellipse(this.centerX, this.centerY, this.radiusX, this.radiusY);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStrokeWidth(this.strokeWidth);
        ellipse.setStroke(this.color);
        this.setJavaFXShape(ellipse);
        return ellipse;
    }
}

