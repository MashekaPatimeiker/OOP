package com.example.demo3.javataskclasses;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleShape extends Shapes {
    private double startX;
    private double startY;
    private double width;
    private double height;
    private Color color;
    private double strokeWidth;

    public RectangleShape() {
        this.color = Color.BLACK; // Default color
        this.strokeWidth = 1.0; // Default stroke width
        Rectangle rectangle = new Rectangle();
        setJavaFXShape(rectangle);
        addHandlers();
    }

    @Override
    public Shape draw() {
        Rectangle rectangle = (Rectangle) this.javafxShape;
        rectangle.setX(startX);
        rectangle.setY(startY);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStrokeWidth(strokeWidth);
        rectangle.setStroke(color);
        return rectangle;
    }

    public void setStart(double x, double y) {
        this.startX = x;
        this.startY = y;
        draw();
    }

    @Override
    public void reset() {

    }

    public void setDimensions(double width, double height) {
        this.width = width;
        this.height = height;
        draw();
    }

    private void addHandlers() {
        this.javafxShape.setOnMousePressed(this::onMousePressed);
        this.javafxShape.setOnMouseDragged(this::onMouseDragged);
    }

    private void onMousePressed(MouseEvent event) {
        setStart(event.getX(), event.getY());
    }

    private void onMouseDragged(MouseEvent event) {
        setDimensions(event.getX() - startX, event.getY() - startY);
    }

    @Override
    public void updateShape(double x, double y) {
        setDimensions(x - startX, y - startY);
    }

    @Override
    public void finalizeShape(double x, double y) {
        setDimensions(x - startX, y - startY);
    }
}
