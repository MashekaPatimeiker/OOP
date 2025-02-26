package com.example.demo3.javataskclasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleShape extends Shapes {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;
    private final Color color;
    private final double strokeWidth;

    public TriangleShape(double x1, double y1, double x2, double y2, double x3, double y3, Color color, double strokeWidth) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public Shape draw() {
        Polygon triangle = new Polygon(new double[]{this.x1, this.y1, this.x2, this.y2, this.x3, this.y3});
        triangle.setFill(Color.TRANSPARENT);
        triangle.setStrokeWidth(this.strokeWidth);
        triangle.setStroke(this.color);
        this.setJavaFXShape(triangle);
        return triangle;
    }
}
