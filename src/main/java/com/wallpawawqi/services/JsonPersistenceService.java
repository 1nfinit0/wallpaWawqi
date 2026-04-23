package com.wallpawawqi.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class JsonPersistenceService<T> {
    private final Gson gson = new Gson();

    public List<T> load(String filePath, Class<T[]> clazz) throws IOException {
        if (!Files.exists(Path.of(filePath))) {
            return new ArrayList<>();
        }
        String contenido = Files.readString(Path.of(filePath));
        if (contenido.isBlank()) {
            return new ArrayList<>();
        }
        T[] array = gson.fromJson(contenido, clazz);
        return new ArrayList<>(Arrays.asList(array));
    }

    public void save(String filePath, List<T> data) throws IOException {
        String json = gson.toJson(data);
        Files.writeString(Path.of(filePath), json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public long add(String filePath, List<T> data, T nuevo, IdSetter<T> idSetter, IdGetter<T> idGetter) throws IOException {
        // calcular nuevo id
        long nuevoId = data.stream().mapToLong(idGetter::getId).max().orElse(0) + 1;
        idSetter.setId(nuevo, nuevoId);

        // agregar y guardar
        data.add(nuevo);
        save(filePath, data);

        return nuevoId;
    }
    public interface IdSetter<T> {
        void setId(T obj, long id);
    }
    public interface IdGetter<T> {
        long getId(T obj);
    }
}
