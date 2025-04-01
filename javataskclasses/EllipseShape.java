package com.example.demo3.javataskclasses;

import javafx.scene.input.MouseEvent;
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
    private boolean isFirstClick = true; // Track the click state

    public EllipseShape() {
        this.color = Color.BLACK; // Default color
        this.strokeWidth = 1.0; // Default stroke width
        Ellipse ellipse = new Ellipse();
        setJavaFXShape(ellipse);
        addHandlers();
    }

    @Override
    public Shape draw() {
        Ellipse ellipse = (Ellipse) this.javafxShape;
        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        ellipse.setRadiusX(radiusX);
        ellipse.setRadiusY(radiusY);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStrokeWidth(strokeWidth);
        ellipse.setStroke(color);
        return ellipse;
    }

    public void setCenter(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        draw();
    }

    public void setRadii(double radiusX, double radiusY) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        draw();
    }

    private void addHandlers() {
        this.javafxShape.setOnMousePressed(this::onMousePressed);
    }

    private void onMousePressed(MouseEvent event) {
        if (isFirstClick) {
            setCenter(event.getX(), event.getY());
            isFirstClick = false; // Switch to second click
        } else {
            setRadii(Math.abs(event.getX() - centerX), Math.abs(event.getY() - centerY));
            isFirstClick = true; // Reset for the next ellipse
        }
    }

    @Override
    public void updateShape(double x, double y) {
        setRadii(Math.abs(x - centerX), Math.abs(y - centerY));
    }

    @Override
    public void finalizeShape(double x, double y) {
        setRadii(Math.abs(x - centerX), Math.abs(y - centerY));
    }

    @Override
    public void setStart(double x, double y) {
        setCenter(x, y);
    }

    @Override
    public void reset() {

    }
}
