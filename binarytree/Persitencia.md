# MANEJO DE PERSISTENCIA

## ESTRUCTURACIÓN
Para el desarrollo del proyecto de árboles se decanto por el uso de un repositorio local con archivos JSON
Se decidio hacer una separación de responsabilidades en la que el repositorio solo guarda y envía una lista que se copia en memoria y que es la que se manipula en el transcurso del programa.

Al finalizar, la lista del archivo se sobreescribe con los cambios realizados durante la ejecución.
Esto para evitar consultar constantemente el archivo lo cual no es muy adecuado

Las clases involucradas, con métodos clave serán explicadas a continuación:

### BinaryTreeRepository
Inferfaz que contiene los "contratos" de los unicos dos métodos que realizarán los repositorios, los cuales son guardar la lista en el archivo  y enviar la lista almacenada en el archivo:
```java
package co.edu.uptc.infraestructure.persistence;

import java.util.List;

import co.edu.uptc.domain.model.BinaryTree;

public interface BinaryTreeRepository {
    void saveAll( List<BinaryTree> treeList);

    List<BinaryTree> findAll();

     
}
```
### JsonRepository
Clase que implementa los métodos de la inferfaz para el formato de archivo JSON, por ejemplo, a continuación el método de guardar la lisa:
```java
    @Override
    public void saveAll(List<BinaryTree> treeList) {
        if(treeList==null){
            treeList= new ArrayList<>();
        }
            try(FileWriter writer= new FileWriter(fileName)) {
                gson.toJson(treeList,writer);
            } catch (Exception e) {
                System.err.println("Error al escribir en el archivo json "+fileName+" :"+e.getMessage());
            }
        
    }
```
### BinaryTreeListService
Clase que maneja el CRUD de la lista de árboles,además es el encargado de recibir la lista que se envía desde el repositorio y de enviarsela de vuelta con los cambios al finalizar la ejecución

En el siguiente método la lista se instancia desde el constructor haciendo uso del método de "enviar la lista" del repositorio 
```java
    public BinaryTreeListService(BinaryTreeRepository repository) {
        this.repository = repository;
        this.treeList = repository.findAll();
    }
```
Y ahora el método para almacenar la lista:
```java
    public void sendListToRepository(){
        repository.saveAll(treeList);
    }
```

## PROCESO DE ALMACENAMIENTO
 Para que se comprenda un poco mejor se explicará paso a paso
- INICIALIZACIÓN PROGRAMA
- Se instancia un objeto de tipo BinaryTreeListService con atributo JsonRepository
- La lista que manipula el BinaryTreeListService se iguala a la enviada por el repositorio
- Se hacen los procesos de eliminación, lectura, etc... sobre la lista del BinaryTreeListService
- Se envía la lista del BinaryTreeListService de vuelta al repositorio con los cambios  y se sobreescribe el archivo
- CIERRE DE PROGRAMA
