package co.edu.uptc.infraestructure.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.domain.repository.BinaryTreeRepository;

/**
 * Persistencia de prueba para el metodo de eliminar el arbol
 * se hace la prueba de crear tres arboles y guardarlos en arboles.json
 * luego se utiliza el exists(), delete() y findAll() en EliminarArbolService
 */

public class JsonBinaryTreeRepository implements BinaryTreeRepository {

    private static final String FILE_PATH = "arbolesPrueba/arboles.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonBinaryTreeRepository() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();

            BinarySearchTree a1 = new BinarySearchTree(); 
            a1.insert(50); 
            a1.insert(30); 
            a1.insert(70);
            BinarySearchTree a2 = new BinarySearchTree(); 
            a2.insert(20); 
            a2.insert(10); 
            a2.insert(30);
            BinarySearchTree a3 = new BinarySearchTree(); 
            a3.insert(100); 
            a3.insert(80); 
            a3.insert(120);

            save("Arbol 1", a1);
            save("Arbol 2", a2);
            save("Arbol 3", a3);
        }
    }

    private Map<String, BinarySearchTree> readData() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, BinarySearchTree>>() {}.getType();
            Map<String, BinarySearchTree> data = gson.fromJson(reader, type);
        
            if (data != null) {
                return data;
            }
            return new HashMap<>();
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private void writeData(Map<String, BinarySearchTree> data) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String name, BinarySearchTree tree) {
        Map<String, BinarySearchTree> data = readData();
        data.put(name, tree);
        writeData(data);
    }

    @Override
    public BinarySearchTree findByName(String name) {
        return readData().get(name);
    }

    @Override
    public List<String> findAll() {
        return new ArrayList<>(readData().keySet());
    }

    @Override
    public void delete(String name) {
        Map<String, BinarySearchTree> data = readData();
        data.remove(name);
        writeData(data);
    }

    @Override
    public boolean exists(String name) {
        return readData().containsKey(name);
    }
}