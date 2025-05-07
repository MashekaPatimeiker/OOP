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
    private boolean isFirstClick = true;

    public EllipseShape() {
        this.color = Color.BLACK;
        this.strokeWidth = 1.0;
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
        ellipse.setFill(this.getFillColor());
        ellipse.setStrokeWidth(strokeWidth);
        ellipse.setStroke(color);
        return ellipse;
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
        this.javafxShape.setOnMousePressed(event -> {
            event.consume();
            this.onMousePressed(event);
        });
    }

    private void onMousePressed(MouseEvent event) {
        if (isFirstClick) {
            setCenter(event.getX(), event.getY());
            isFirstClick = false;
        } else {
            setRadii(Math.abs(event.getX() - centerX), Math.abs(event.getY() - centerY));
            isFirstClick = true;
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
