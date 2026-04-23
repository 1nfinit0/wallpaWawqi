package com.wallpawawqi;

import com.sun.net.httpserver.*;
import com.wallpawawqi.Class.Platillo;
import com.wallpawawqi.services.JsonPersistenceService;

import java.io.InputStream;
import java.util.List;
import com.google.gson.Gson;

public class WallpaWawqi {
  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new java.net.InetSocketAddress(8080), 0);
    Gson gson = new Gson();
    server.createContext("/platillos", exchange -> {
      try {
        
        if ("OPTIONS".equals(exchange.getRequestMethod())) {
          exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
          exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
          exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
          exchange.sendResponseHeaders(204, -1);
          exchange.close();
          return;
        }

        
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if ("GET".equals(exchange.getRequestMethod())) {
          InputStream is = WallpaWawqi.class.getClassLoader().getResourceAsStream("platillos.json");
          String json = new String(is.readAllBytes());
          exchange.getResponseHeaders().add("Content-Type", "application/json");
          exchange.sendResponseHeaders(200, json.getBytes().length);
          exchange.getResponseBody().write(json.getBytes());
        }

        if ("POST".equals(exchange.getRequestMethod())) {
          String requestBody = new String(exchange.getRequestBody().readAllBytes());
          Platillo nuevoPlatillo = gson.fromJson(requestBody, Platillo.class);

          JsonPersistenceService<Platillo> service = new JsonPersistenceService<>();
          List<Platillo> platillos = service.load("src/main/resources/platillos.json", Platillo[].class);

          long nuevoId = service.add(
              "src/main/resources/platillos.json",
              platillos,
              nuevoPlatillo,
              Platillo::setId,
              Platillo::getId);

          String response = "{\"message\": \"Platillo guardado con id " + nuevoId + "\"}";
          exchange.getResponseHeaders().add("Content-Type", "application/json");
          exchange.sendResponseHeaders(200, response.getBytes().length);
          exchange.getResponseBody().write(response.getBytes());
        }

      } catch (Exception e) {
        e.printStackTrace();
        String error = "{\"error\": \"" + e.getMessage() + "\"}";
        exchange.sendResponseHeaders(500, error.length());
        exchange.getResponseBody().write(error.getBytes());
      } finally {
        exchange.close();
      }
    });

    server.start();

    System.out.println("Servidor en http://localhost:8080/platillos");
  }
}
