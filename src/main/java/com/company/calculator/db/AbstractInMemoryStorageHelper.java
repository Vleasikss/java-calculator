package com.company.calculator.db;

import com.company.calculator.model.EntityId;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractInMemoryStorageHelper<T extends EntityId> {

    protected static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public AbstractInMemoryStorageHelper() {
        init();
    }

    protected abstract String storageFilename();

    protected abstract Class<T> clazz();

    public List<T> findAll() {
        try {
            String s = Files.readString(storageFilePath());
            return mapper.readValue(s, listClazz());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<T> findById(String id) {
        return findAll().stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public void save(T t) {
        List<T> ts = findAll();
        ts.add(t);
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ts);
            Files.writeString(storageFilePath(), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected CollectionType listClazz() {
        return mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz());
    }

    protected void init() {
        try {
            if (!Files.exists(storageFilePath()) || Files.readString(storageFilePath()).isEmpty()) {
                List<T> value = new ArrayList<>();
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
                Files.writeString(storageFilePath(), json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Path storageFilePath() {
        URL resource = getClass().getResource("/data");
        if (resource != null) {
            return Paths.get(Objects.requireNonNull(resource).getPath(), storageFilename());
        }

        String path = Objects.requireNonNull(getClass().getResource("/")).getPath();
        Path data = Path.of(path, "data");
        data.toFile().mkdir();
        return Paths.get(data.toAbsolutePath().toString(), storageFilename());
    }

}
