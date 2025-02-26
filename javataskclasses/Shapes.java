package com.example.demo3.javataskclasses;


import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public abstract class Shapes {
    protected Shape javafxShape;
    private boolean resizing = false;
    private boolean moving = false;
    private double mouseX;
    private double mouseY;
    private double initialX;
    private double initialY;

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
        if (this.isOnBorder(event)) {
            this.resizing = true;
        } else {
            this.moving = true;
        }

    }

    private void onMouseDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - this.mouseX;
        double deltaY = event.getSceneY() - this.mouseY;
        this.javafxShape.setTranslateX(this.initialX + deltaX);
        this.javafxShape.setTranslateY(this.initialY + deltaY);
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
}
