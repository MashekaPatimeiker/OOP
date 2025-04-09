package com.example.demo3.javataskclasses;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LineShape extends Shapes {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private Color color;
    private double strokeWidth;


    public LineShape() {
        this.color = Color.BLACK;
        this.strokeWidth = 1.0;
        Line line = new Line();
        setJavaFXShape(line);
        addHandlers();
    }
    @Override
    public void setColor(Color color) {
        this.color = color;
        if (this.javafxShape != null) {
            this.javafxShape.setStroke(color);
        }
    }
    @Override
    public Shape draw() {
        Line line = (Line) this.javafxShape;
        line.setStartX(this.startX);
        line.setStartY(this.startY);
        line.setEndX(this.endX);
        line.setEndY(this.endY);
        line.setStrokeWidth(this.strokeWidth);
        line.setStroke(this.color);
        return line;
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
        this.startX = x;
        this.startY = y;
        draw();
    }

    @Override
    public void reset() {

    }

    public void setEnd(double x, double y) {
        this.endX = x;
        this.endY = y;
        draw();
    }

    @Override
    public void updateShape(double x, double y) {
        setEnd(x, y); // Обновляем конечную точку линии
    }

    @Override
    public void finalizeShape(double x, double y) {
        setEnd(x, y); // Завершаем рисование, устанавливая конечную точку
    }

    private void addHandlers() {
        this.javafxShape.setOnMousePressed(event -> {
            event.consume(); // Предотвращаем всплытие события
            this.onMousePressed(event);
        });
        this.javafxShape.setOnMouseDragged(event -> {
            event.consume(); // Предотвращаем всплытие события
            this.onMouseDragged(event);
        });
        this.javafxShape.setOnMouseReleased(event -> {
            event.consume(); // Предотвращаем всплытие события
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
