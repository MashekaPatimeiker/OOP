package com.example.demo3.manager;

import com.example.demo3.javataskclasses.Shapes;
import com.example.demo3.javataskclasses.myshapes.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShapeSerializer {
    private static final ObjectMapper objectMapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static void saveToFile(List<Shapes> shapes, File file) {
        try {
            System.out.println("Saving shapes: " + shapes.size()); // Лог
            List<Map<String, Object>> shapeData = new ArrayList<>();
            for (Shapes shape : shapes) {
                Map<String, Object> map = shape.toMap();
                System.out.println("Shape data: " + map); // Лог каждой фигуры
                shapeData.add(map);
            }
            objectMapper.writeValue(file, shapeData);
        } catch (IOException e) {
            showErrorAlert("Error saving file: " + e.getMessage());
        }
    }

    public static List<Shapes> loadFromFile(File file) {
        try {
            List<Map<String, Object>> shapeData = objectMapper.readValue(
                    file,
                    new TypeReference<List<Map<String, Object>>>() {}
            );

            List<Shapes> shapes = new ArrayList<>();
            for (Map<String, Object> data : shapeData) {
                String type = (String) data.get("type");
                Shapes shape = createShapeFromType(type);
                shape.fromMap(data);
                shapes.add(shape);
            }
            return shapes;
        } catch (IOException e) {
            showErrorAlert("Error loading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static Shapes createShapeFromType(String type) {
        return switch (type) {
            case "Ellipse" -> new EllipseShape();
            case "Line" -> new LineShape();
            case "Polyline" -> new PolylineShape();
            case "Rectangle" -> new RectangleShape();
            case "Polygon" -> new PolygonShape();
            default -> throw new IllegalArgumentException("Unknown shape type: " + type);
        };
    }
    private static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}