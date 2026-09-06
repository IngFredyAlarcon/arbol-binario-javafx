package co.edu.uptc.infraestructure.persistence;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.edu.uptc.domain.model.BinaryTree;

public class JsonRepository implements BinaryTreeRepository {
    private String pathname;
    private Gson gson;
    
    public JsonRepository(String pathname) {
        String rutaDeEjecucion = System.getProperty("user.dir");
        
        File carpetaData = new File(rutaDeEjecucion, "data");
        
        // Si la carpeta "data" no existe junto al programa, la crea
        if (!carpetaData.exists()) {
            carpetaData.mkdirs(); 
        }
        
        // Une la carpeta "data" con el nombre del archivo
        File archivoFinal = new File(carpetaData, pathname);
        
        // Guardamos la ruta completa
        this.pathname = archivoFinal.getAbsolutePath();
        
        this.gson = new GsonBuilder()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create();
    }

    @Override
    public void saveList(Map<String, BinaryTree> trees) {
        if (trees == null) return;

        try (FileWriter writer = new FileWriter(pathname)) {
            gson.toJson(trees, writer);
            System.out.println("Archivo guardado con éxito en: " + pathname); 
        } catch (Exception e) {
            System.err.println("Error al escribir: " + e.getMessage());
        }
    }
}
