package com.example.demo3.plugin;

import com.example.demo3.javataskclasses.Shapes;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TrapezoidShape {
    public static class Trapezoid extends Shapes {
        private double startX, startY;
        private double endX, endY;
        private double topWidthRatio = 0.7;
        private Polygon polygon;

        @Override
        protected String getShapeType() {
            return "Trapezoid";
        }

        @Override
        public Shape draw() {
            if (polygon == null) {
                polygon = new Polygon();
                polygon.setFill(getFillColor());
                polygon.setStroke(getStrokeColor());
                setJavaFXShape(polygon);
            }
            updatePolygonPoints();
            return polygon;
        }

        private void updatePolygonPoints() {
            if (polygon == null) return;

            double width = Math.abs(endX - startX);
            double height = Math.abs(endY - startY);
            double topWidth = width * topWidthRatio;
            double topOffset = (width - topWidth) / 2;

            double left = Math.min(startX, endX);
            double top = Math.min(startY, endY);

            polygon.getPoints().clear();
            polygon.getPoints().addAll(
                    left, top + height,
                    left + width, top + height,
                    left + width - topOffset, top,
                    left + topOffset, top
            );
        }

        @Override
        public void updateShape(double x, double y) {
            this.endX = x;
            this.endY = y;
            updatePolygonPoints();
        }

        @Override
        public void finalizeShape(double x, double y) {
            updateShape(x, y);
        }

        @Override
        public void setStart(double x, double y) {
            this.startX = x;
            this.startY = y;
            this.endX = x;
            this.endY = y;
        }

        @Override
        public void reset() {
            polygon = null;
        }

        @Override
        public void finishCreation() {
        }
    }
}