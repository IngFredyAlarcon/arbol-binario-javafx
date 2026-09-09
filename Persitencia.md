# PERSISTENCIA Y MANEJO DE ARCHIVOS
## Clases y métodos
Para el funcionamiento de la persistencia se decanto por el uso de archivos Json por su facilidad para cargar y guardar objetos complejos mediante los métodos getters y setters, sin embargo mediante el uso de interfaces se permitió la posibilidad de cambiar el tipo de persistencia cuando se desee
A continuación un breve vistazo a las clases utilizadas para el desarrollo:
### BinaryTreeRepository
Es la interfaz de la persitencia que posee los "contratos" que deben seguir cualquier tipo de persistencia que se implemente, para este caso, únicamente el método de guardar y cargar el Map de los árboles como se verá a continuación: 
```java
package co.edu.uptc.infraestructure.persistence;
import java.util.Map;

import co.edu.uptc.domain.model.BinaryTree;
public interface BinaryTreeRepository {

    void saveList(Map<String, BinaryTree> trees);
    
}
```
### JsonRepository
Clase que implementa la interfaz BinaryTreeRepository para la persistencia en archivos Json
con los métodos de guardar y cargar:
- Método guardar Lista:
```java
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
```
- Método cargar lista:

### TreeManager
El treeManager se encarga del manejo del Map de árboles, el cual se inicializa con un repositorio.
Por lo tanto, el treeManager tiene como atributo en su constructor cualquier clase que implemente la interfaz del BinaryTreeRepository, es justo en esa instanciación del TreeManager donde se puede modificar a voluntad el tipo de persistencia que se usará:
- Constructor de la clase TreeManager con el atributo del repositorio:
```java

    public TreeManager(BinaryTreeRepository repository) {
        this.repository=repository;
        this.trees = new HashMap<>();
    }
```
- Instanciación del TreeManager especificando el tipo de persistencia utilizada:
```java
    treeManager = new TreeManager(new JsonRepository("BinaryTree.json"));
```
## Algoritmo de funcionamiento
- INICIALIZACIÓN DEL PROGRAMA
- Instanciación del TreeManager indicando la persistencia utilizada
- Carga de árboles desde el repositorio en un Map
- Map del repositorio al atributo Map del TreeManager 
- Operaciones de eliminar, insertar nodos etc..
- Guardar árboles localmente y en repositorio (Se hace al finalizar el programa y al presionar guardar)
- FINALIZACIÓN DEL PROGRAMA 

