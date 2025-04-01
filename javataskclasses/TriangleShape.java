package com.example.demo3.javataskclasses;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleShape extends Shapes {
    private double startX;
    private double startY;
    private double width;
    private double height;
    private Color color;
    private final double strokeWidth;

    public TriangleShape() {
        this.color = Color.BLACK; // Default color
        this.strokeWidth = 1.0; // Default stroke width
        Polygon triangle = new Polygon();
        setJavaFXShape(triangle);
        addHandlers();
    }

    @Override
    public Shape draw() {
        Polygon triangle = (Polygon) this.javafxShape;
        triangle.getPoints().clear();

        // Рассчитываем координаты треугольника на основе startX, startY, width и height
        double x1 = startX; // Левый нижний угол
        double y1 = startY + height; // Нижний угол
        double x2 = startX + width; // Правый нижний угол
        double y2 = startY + height; // Нижний угол
        double x3 = startX + width / 2;
        double y3 = startY;

        triangle.getPoints().addAll(
                x1, y1,
                x2, y2,
                x3, y3
        );

        triangle.setFill(Color.TRANSPARENT);
        triangle.setStrokeWidth(this.strokeWidth);
        triangle.setStroke(this.color);
        return triangle;
    }

    @Override
    public void setStart(double x, double y) {
        this.startX = x;
        this.startY = y;
        this.width = 0;
        this.height = 0;
        draw();
    }

    @Override
    public void reset() {

    }

    @Override
    public void updateShape(double x, double y) {
        // Обновляем ширину и высоту треугольника
        this.width = Math.abs(x - startX);
        this.height = Math.abs(y - startY);
        draw();
    }

    @Override
    public void finalizeShape(double x, double y) {
        // Завершаем рисование треугольника
        this.width = Math.abs(x - startX);
        this.height = Math.abs(y - startY);
        draw();
    }

    private void addHandlers() {
        this.javafxShape.setOnMousePressed(this::onMousePressed);
        this.javafxShape.setOnMouseDragged(this::onMouseDragged);
        this.javafxShape.setOnMouseReleased(this::onMouseReleased);
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
