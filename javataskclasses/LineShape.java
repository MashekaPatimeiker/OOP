package com.example.demo3.javataskclasses;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LineShape extends Shapes {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private final Color color;
    private double strokeWidth;

    public LineShape(double startX, double startY, double endX, double endY, Color color, double strokeWidth) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.strokeWidth = strokeWidth;
    }

    public Shape draw() {
        Line line = new Line(this.startX, this.startY, this.endX, this.endY);
        line.setFill(Color.TRANSPARENT);
        line.setStrokeWidth(this.strokeWidth);
        line.setStroke(this.color);
        this.setJavaFXShape(line);
        return line;
    }
}
