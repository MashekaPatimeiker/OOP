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
    private static final Map<String, Class<? extends Shapes>> pluginShapes = new HashMap<>();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Color.class, new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    public static void registerPlugin(ShapePlugin plugin) {
        // Сохраняем класс фигуры вместо самого плагина
        pluginShapes.put(plugin.getShapeName(), plugin.createShape().getClass());
    }

    public static void saveToFile(List<Shapes> shapes, File file) {
        try {
            List<Map<String, Object>> shapeData = shapes.stream()
                    .map(Shapes::toMap)
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

            List<Shapes> result = new ArrayList<>();
            List<String> missingPlugins = new ArrayList<>();

            for (Map<String, Object> data : shapeData) {
                String type = (String) data.get("type");
                try {
                    Shapes shape = createShapeFromType(type);
                    shape.fromMap(data);
                    result.add(shape);
                } catch (IllegalArgumentException e) {
                    if (isPluginShape(type)) {
                        missingPlugins.add(type);
                    } else {
                        throw e;
                    }
                }
            }

            if (!missingPlugins.isEmpty()) {
                throw new MissingPluginException("Missing plugins for shapes: " + String.join(", ", missingPlugins));
            }

            return result;
        } catch (IOException e) {
            showErrorAlert("Error loading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static boolean isPluginShape(String type) {
        return pluginShapes.containsKey(type);
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
        Class<? extends Shapes> shapeClass = pluginShapes.get(type);
        if (shapeClass != null) {
            try {
                return shapeClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create shape instance: " + type, e);
            }
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

    public static class MissingPluginException extends RuntimeException {
        public MissingPluginException(String message) {
            super(message);
        }
    }
}