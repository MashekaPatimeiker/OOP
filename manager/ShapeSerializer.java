package com.example.demo3.manager;

import com.example.demo3.Functions;
import com.example.demo3.javataskclasses.Shapes;
import com.example.demo3.javataskclasses.myshapes.*;
import com.example.demo3.plugin.ShapePlugin;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ShapeSerializer {
    private static final ObjectMapper objectMapper = createObjectMapper();
    private static final Map<String, ShapePlugin> pluginShapes = new HashMap<>();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    // Регистрируем плагин при его загрузке
    public static void registerPlugin(ShapePlugin plugin) {
        pluginShapes.put(plugin.getShapeName(), plugin);
    }

    public static void saveToFile(List<Shapes> shapes, File file) {
        try {
            System.out.println("Saving shapes: " + shapes.size());
            List<Map<String, Object>> shapeData = shapes.stream()
                    .map(Shapes::toMap)
                    .peek(map -> System.out.println("Shape data: " + map))
                    .collect(Collectors.toList());
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

            return shapeData.stream()
                    .map(data -> {
                        String type = (String) data.get("type");
                        Shapes shape = createShapeFromType(type);
                        shape.fromMap(data);
                        return shape;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            showErrorAlert("Error loading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static Shapes createShapeFromType(String type) {
        // Сначала проверяем базовые фигуры
        switch (type) {
            case "Ellipse": return new EllipseShape();
            case "Line": return new LineShape();
            case "Polyline": return new PolylineShape();
            case "Rectangle": return new RectangleShape();
            case "Polygon": return new PolygonShape();
        }

        // Затем проверяем зарегистрированные плагины
        ShapePlugin plugin = pluginShapes.get(type);
        if (plugin != null) {
            return plugin.createShape();
        }

        throw new IllegalArgumentException("Unknown shape type: " + type);
    }

    private static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}