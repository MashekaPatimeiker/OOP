package com.example.demo3.javataskclasses;

import com.example.demo3.Functions;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Shapes {
    protected Shape javafxShape;
    private Color fillColor = Color.TRANSPARENT;
    private boolean resizing = false;
    private boolean moving = false;
    private boolean isBeingCreated = true;
    private double mouseX;
    private double mouseY;
    private double initialX;
    private double initialY;
    private double initialWidth;
    private double initialHeight;

    public Shapes() {
    }

    public void setSelected(boolean selected) {
        if (this.javafxShape != null) {
            if (selected) {
                this.javafxShape.setStrokeWidth(this.javafxShape.getStrokeWidth() * 2);
            } else {
                this.javafxShape.setStrokeWidth(this.javafxShape.getStrokeWidth() / 2);
            }
        }
    }

    public void applyFill(Color fillColor) {
        if (this.javafxShape != null) {
            this.javafxShape.setFill(fillColor);
        }
    }

    public abstract Shape draw();

    public void setFillColor(Color color) {
        this.fillColor = color;
        if (this.javafxShape != null) {
            this.javafxShape.setFill(color);
        }
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    private void addHandlers() {
        this.javafxShape.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Shapes prevSelected = Functions.getSelectedShape();
                if (prevSelected != null) {
                    prevSelected.setSelected(false);
                }

                this.setSelected(true);
                Functions.setSelectedShape(this);
                event.consume();
            }
        });

        if (this.javafxShape != null) {
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
                this.resizing = false;
                this.moving = false;
                // После отпускания кнопки мыши считаем, что создание завершено
                this.isBeingCreated = false;
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

        if (event.isShiftDown()) {
            this.moving = true;
        } else if (this.isBeingCreated || this.isOnBorder(event)) {
            // Разрешаем ресайз только если фигура в процессе создания или если клик на границе
            this.resizing = true;
        }
    }

    private void onMouseDragged(MouseEvent event) {
        if (this.resizing && this.isBeingCreated) { // Ресайз только во время создания
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
        return x <= border || x >= this.javafxShape.getBoundsInLocal().getWidth() - border ||
                y <= border || y >= this.javafxShape.getBoundsInLocal().getHeight() - border;
    }

    protected void setJavaFXShape(Shape shape) {
        this.javafxShape = shape;
        this.addHandlers();
    }

    public void setColor(Color color) {
        if (this.javafxShape != null) {
            this.javafxShape.setStroke(color);
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

    public void finishCreation() {
        this.isBeingCreated = false;
    }

    public abstract void updateShape(double x, double y);
    public abstract void finalizeShape(double x, double y);
    public abstract void setStart(double x, double y);
    public abstract void reset();
}