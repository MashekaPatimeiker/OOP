package com.example.demo3.javataskclasses.myshapes;

import com.example.demo3.javataskclasses.Shapes;
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
        this.color = Color.BLACK;
        this.strokeWidth = 1.0;
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
        rectangle.setFill(this.getFillColor());
        rectangle.setStrokeWidth(strokeWidth);
        rectangle.setStroke(color);
        return rectangle;
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
        this.javafxShape.setOnMousePressed(event -> {
            event.consume();
            this.onMousePressed(event);
        });
        this.javafxShape.setOnMouseDragged(event -> {
            event.consume();
            this.onMouseDragged(event);
        });
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
