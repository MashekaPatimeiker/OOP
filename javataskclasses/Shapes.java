package com.example.demo3.javataskclasses;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Shapes {
    protected Shape javafxShape;
    private boolean resizing = false;
    private boolean moving = false;
    private double mouseX;
    private double mouseY;
    private double initialX;
    private double initialY;
    private double initialWidth;
    private double initialHeight;

    public Shapes() {
    }

    public abstract Shape draw();

    private void addHandlers() {
        if (this.javafxShape != null) {
            this.javafxShape.setOnMousePressed(this::onMousePressed);
            this.javafxShape.setOnMouseDragged(this::onMouseDragged);
            this.javafxShape.setOnMouseReleased((event) -> {
                this.resizing = false;
                this.moving = false;
            });
        }
    }

    private void onMousePressed(MouseEvent event) {
        this.mouseX = event.getSceneX();
        this.mouseY = event.getSceneY();
        this.initialX = this.javafxShape.getTranslateX();
        this.initialY = this.javafxShape.getTranslateY();
        this.initialWidth = this.javafxShape.getBoundsInLocal().getWidth();
        this.initialHeight = this.javafxShape.getBoundsInLocal().getHeight();

        // Проверяем, удерживается ли клавиша Shift
        if (event.isShiftDown()) {
            this.moving = true; // Разрешаем перемещение только если Shift удерживается
        } else if (this.isOnBorder(event)) {
            this.resizing = true;
        }
    }

    private void onMouseDragged(MouseEvent event) {
        if (this.resizing) {
            resize(event);
        } else if (this.moving) {
            double deltaX = event.getSceneX() - this.mouseX;
            double deltaY = event.getSceneY() - this.mouseY;
            this.javafxShape.setTranslateX(this.initialX + deltaX);
            this.javafxShape.setTranslateY(this.initialY + deltaY);
        }
    }

    private boolean isOnBorder(MouseEvent event) {
        double border = 10.0;
        double x = event.getX();
        double y = event.getY();
        return x <= border || x >= this.javafxShape.getBoundsInLocal().getWidth() - border || y <= border || y >= this.javafxShape.getBoundsInLocal().getHeight() - border;
    }

    protected void setJavaFXShape(Shape shape) {
        this.javafxShape = shape;
        this.addHandlers();
    }

    public void setColor(Color color) {
        if (this.javafxShape != null) {
            this.javafxShape.setFill(color);
        }
    }

    public void setStrokeWidth(double strokeWidth) {
        if (this.javafxShape != null) {
            this.javafxShape.setStrokeWidth(strokeWidth);
        }
    }

    private void resize(MouseEvent event) {
        double deltaX = event.getSceneX() - this.mouseX;
        double deltaY = event.getSceneY() - this.mouseY;

        double newWidth = Math.max(0, this.initialWidth + deltaX);
        double newHeight = Math.max(0, this.initialHeight + deltaY);

        this.javafxShape.setScaleX(newWidth / this.initialWidth);
        this.javafxShape.setScaleY(newHeight / this.initialHeight);
    }

    public abstract void updateShape(double x, double y);
    public abstract void finalizeShape(double x, double y);
    public abstract void setStart(double x, double y); // Добавленный метод

    public abstract void reset();
}
