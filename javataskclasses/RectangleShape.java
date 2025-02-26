package com.example.demo3.javataskclasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleShape extends Shapes {
    private double x;
    private double y;
    private double width;
    private double height;
    private final Color color;
    private double strokeWidth;

    public RectangleShape(double x, double y, double width, double height, Color color, double strokeWidth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public Shape draw() {
        Rectangle rectangle = new Rectangle(this.x, this.y, this.width, this.height);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(this.strokeWidth);
        rectangle.setStroke(this.color);
        this.setJavaFXShape(rectangle);
        return rectangle;
    }
}
