package com.example.demo3.javataskclasses;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class PolygonShape extends Shapes {
    private double centerX;
    private double centerY;
    private double radius;
    private final int sides;
    private Color color;
    private double strokeWidth;
    private static int defaultSides = 5;

    public PolygonShape() {
        this.color = Color.BLACK;
        this.strokeWidth = 1.0;
        this.sides = defaultSides;
        Polygon polygon = new Polygon();
        setJavaFXShape(polygon);
        addHandlers();
    }

    public static void setDefaultSides(int sides) {
        defaultSides = Math.max(3, Math.min(20, sides));
    }

    @Override
    public Shape draw() {
        Polygon polygon = (Polygon) this.javafxShape;
        polygon.getPoints().clear();
        double initialAngle = -Math.PI / 2;

        for (int i = 0; i < sides; i++) {
            double angle = initialAngle + 2 * Math.PI * i / sides;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            polygon.getPoints().addAll(x, y);
        }

        polygon.setFill(this.getFillColor());
        polygon.setStrokeWidth(strokeWidth);
        polygon.setStroke(color);
        return polygon;
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

    @Override
    public void setStart(double x, double y) {
        this.centerX = x;
        this.centerY = y;
        this.radius = 0;
        draw();
    }

    @Override
    public void reset() {
        this.radius = 0;
        draw();
    }

    @Override
    public void updateShape(double x, double y) {
        this.radius = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        draw();
    }

    @Override
    public void finalizeShape(double x, double y) {
        this.radius = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
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
        this.javafxShape.setOnMouseReleased(event -> {
            event.consume();
            this.onMouseReleased(event);
        });
    }

    private void onMousePressed(MouseEvent event) {
        setStart(event.getX(), event.getY());
    }

    private void onMouseDragged(MouseEvent event) {
        updateShape(event.getX(), event.getY());
    }

    private void onMouseReleased(MouseEvent event) {
        finalizeShape(event.getX(), event.getY());
    }
}