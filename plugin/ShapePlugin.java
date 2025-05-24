package com.example.demo3.plugin;

import com.example.demo3.javataskclasses.Shapes;
import javafx.scene.control.MenuItem;

public interface ShapePlugin {
    String getShapeName();
    Shapes createShape();
    MenuItem getMenuItem();
}