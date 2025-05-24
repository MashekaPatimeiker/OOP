package com.example.demo3.plugin;

import com.example.demo3.javataskclasses.Shapes;
import javafx.scene.control.MenuItem;

public class TrapezoidPlugin implements ShapePlugin {
    @Override
    public String getShapeName() {
        return "Trapezoid";
    }

    @Override
    public Shapes createShape() {
        return new TrapezoidShape.Trapezoid();  // This works because Trapezoid extends Shapes
    }

    @Override
    public MenuItem getMenuItem() {
        return new MenuItem(getShapeName());
    }
}