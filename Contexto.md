# Proyecto: Gestor de Árboles Binarios

## Contexto del proyecto

Una institución educativa requiere una aplicación de escritorio que permita **crear, visualizar, consultar y modificar árboles binarios de búsqueda (ABB)**. La aplicación será utilizada como herramienta de apoyo para estudiar las operaciones fundamentales de estas estructuras de datos.

El sistema deberá permitir al usuario construir un árbol a partir de valores enteros, ejecutar operaciones sobre él y visualizar tanto su estructura como los resultados de los diferentes recorridos.

La aplicación será desarrollada en **Java**, administrada mediante **Maven**, utilizará **JavaFX con FXML** para la interfaz gráfica y seguirá una **arquitectura N-capas**. La información de los árboles deberá conservarse mediante **persistencia en archivos**, de manera que los datos puedan recuperarse al volver a ejecutar la aplicación.

### Alcance inicial

El sistema trabajará con **árboles binarios de búsqueda de valores enteros**. Cada árbol tendrá:

- Un identificador o nombre.
- Un nodo raíz.
- Cero o más nodos descendientes.
- Una colección de valores organizados según las reglas de un ABB.

Las operaciones principales estarán relacionadas con la estructura de datos estudiada en clase: inserción, búsqueda, eliminación, recorridos y consulta de propiedades del árbol.

---

# Requisitos funcionales

### RF01. Crear un árbol

El sistema deberá permitir al usuario crear un nuevo árbol binario de búsqueda e indicar un nombre o identificador para este.

### RF02. Insertar elementos

El sistema deberá permitir ingresar un valor entero al árbol seleccionado.

Al insertar un valor, el sistema deberá mantener las propiedades de un árbol binario de búsqueda:

- Los valores menores se ubican en el subárbol izquierdo.
- Los valores mayores se ubican en el subárbol derecho.
- El tratamiento de valores duplicados deberá estar definido por el sistema. Por ejemplo, **no permitir duplicados**.

### RF03. Buscar elementos

El usuario podrá ingresar un valor y solicitar su búsqueda dentro del árbol.

El sistema deberá indicar si el elemento existe y, cuando corresponda, proporcionar información sobre su ubicación dentro del árbol.

### RF04. Eliminar elementos

El usuario podrá seleccionar un valor para eliminarlo del árbol.

La operación deberá contemplar los casos correspondientes a:

1. Nodo hoja.
2. Nodo con un hijo.
3. Nodo con dos hijos.
4. Eliminación de la raíz.

### RF05. Recorrer el árbol

El sistema deberá permitir ejecutar y visualizar, como mínimo, los siguientes recorridos:

- **Preorden**
- **Inorden**
- **Postorden**

Opcionalmente podrá incorporarse el recorrido **por niveles**.

Los resultados deberán presentarse de manera comprensible para el usuario.

### RF06. Visualizar el árbol

La aplicación deberá representar gráficamente el árbol actual, mostrando:

- Los nodos.
- Sus relaciones padre-hijo.
- El valor almacenado en cada nodo.
- La raíz.

La representación deberá actualizarse después de operaciones que modifiquen el árbol.

### RF07. Consultar propiedades del árbol

El sistema deberá permitir consultar información del árbol, como:

- Número de nodos.
- Altura.
- Nivel máximo.
- Cantidad de hojas.
- Valor mínimo.
- Valor máximo.
- Si el árbol está vacío.

### RF08. Limpiar el árbol

El usuario podrá eliminar todos los elementos del árbol actual, dejando la estructura vacía.

La aplicación deberá solicitar confirmación antes de realizar esta operación.

### RF09. Guardar árboles

El sistema deberá permitir guardar la información de los árboles en archivos para conservar su estado después de cerrar la aplicación.

### RF10. Cargar árboles

Al iniciar la aplicación, o mediante una opción explícita, el usuario podrá cargar los árboles previamente almacenados en archivos.

### RF11. Seleccionar árbol

Si existen varios árboles almacenados, el sistema deberá permitir seleccionar cuál de ellos desea consultar o modificar el usuario.

### RF12. Validar entradas

El sistema deberá validar las entradas realizadas por el usuario.

Por ejemplo:

- No aceptar valores que no sean enteros.
- No permitir crear árboles sin identificador.
- Informar cuando se intente insertar un valor duplicado, si se adopta esta regla.
- Informar cuando se intente buscar o eliminar un valor inexistente.

### RF13. Informar resultados

Las operaciones deberán proporcionar retroalimentación al usuario mediante mensajes, indicadores o elementos visuales apropiados.

Por ejemplo:

> "El valor 45 fue insertado correctamente."

o

> "El valor 30 no se encuentra en el árbol."

---

# Requisitos no funcionales

### RNF01. Lenguaje de programación

La aplicación deberá desarrollarse utilizando **Java**.

Se deberán aplicar conceptos de programación orientada a objetos como:

- Encapsulamiento.
- Abstracción.
- Composición.
- Interfaces cuando sean apropiadas.
- Separación de responsabilidades.

### RNF02. Gestión del proyecto

El proyecto deberá utilizar **Maven** para:

- Gestionar dependencias.
- Administrar la estructura del proyecto.
- Ejecutar el ciclo de construcción.
- Facilitar la ejecución y distribución de la aplicación.

### RNF03. Interfaz gráfica

La interfaz deberá desarrollarse utilizando **JavaFX**.

Las vistas deberán definirse mediante **FXML**, separando la definición visual de la lógica de aplicación.

### RNF04. Controllers

Cada vista FXML deberá utilizar uno o más **controllers** responsables de gestionar los eventos e interacciones correspondientes.

Los controllers no deberán contener la implementación de las operaciones propias del árbol.

Por ejemplo, un controller podrá solicitar:

```java
treeService.insertar(50);
```

pero la lógica que determina dónde ubicar el nodo deberá permanecer en las clases correspondientes al dominio.

### RNF05. Arquitectura N-capas

La aplicación deberá organizarse utilizando una arquitectura por capas.

Una posible distribución sería:

```text
┌──────────────────────────────────────┐
│           Presentación               │
│       JavaFX + FXML + Controllers    │
├──────────────────────────────────────┤
│       Servicios / Aplicación         │
│   Casos de uso y reglas de negocio   │
├──────────────────────────────────────┤
│              Dominio                 │
│ ÁrbolBinario, Nodo, operaciones ABB  │
├──────────────────────────────────────┤
│           Persistencia               │
│       Lectura y escritura de         │
│              archivos                │
└──────────────────────────────────────┘
```

Esto permite que, por ejemplo, la implementación del árbol **no dependa de JavaFX**.

### RNF06. Persistencia

La información deberá almacenarse utilizando **archivos**, sin utilizar inicialmente una base de datos.

La implementación deberá encapsular las operaciones de lectura y escritura de manera que la lógica de negocio no tenga que conocer los detalles específicos del archivo utilizado.

### RNF07. Mantenibilidad

El código deberá estar organizado de forma que los componentes puedan modificarse o reemplazarse con el menor impacto posible sobre el resto de la aplicación.

Por ejemplo, debería ser posible cambiar posteriormente la persistencia en archivos por una base de datos sin modificar las clases que implementan las operaciones del árbol.

### RNF08. Reutilización

Las clases que representan y manipulan el árbol deberán ser independientes de la interfaz gráfica para permitir su reutilización en otros contextos, como pruebas unitarias o una futura aplicación web.

### RNF09. Manejo de errores

La aplicación deberá manejar adecuadamente situaciones excepcionales, evitando que errores de entrada o problemas de lectura/escritura provoquen el cierre inesperado del programa.

### RNF10. Usabilidad

La interfaz deberá ser clara y permitir que un usuario pueda identificar fácilmente:

- El árbol seleccionado.
- Las operaciones disponibles.
- El estado actual del árbol.
- Los resultados de las operaciones.
- Los mensajes de error o confirmación.

### RNF11. Pruebas

Las operaciones fundamentales del árbol deberán poder probarse de forma independiente de JavaFX.

Como mínimo, deberán existir pruebas para:

- Inserción.
- Búsqueda.
- Eliminación de hoja.
- Eliminación de nodo con un hijo.
- Eliminación de nodo con dos hijos.
- Eliminación de la raíz.
- Recorridos.
- Cálculo de altura.
- Árbol vacío.

---

# Restricciones técnicas

Para hacer más claro el ejercicio para los estudiantes, se establecen las siguientes restricciones:

| Elemento | Restricción |
|---|---|
| Lenguaje | Java |
| Construcción | Maven |
| Interfaz | JavaFX |
| Vistas | FXML |
| Control de vistas | Controllers |
| Persistencia | Archivos |
| Base de datos | No requerida |
| Arquitectura | N-capas |
| Estructura principal | Árbol binario de búsqueda |
| Datos | Enteros |
| Pruebas | Independientes de JavaFX |

## Estructura inicial sugerida

Sin entrar todavía en la implementación, el proyecto puede organizarse inicialmente de la siguiente manera:

```text
src/
├── main/
│   ├── java/
│   │   └── com.ejemplo.arboles/
│   │       ├── presentation/
│   │       │   └── controller/
│   │       │
│   │       ├── application/
│   │       │   └── service/
│   │       │
│   │       ├── domain/
│   │       │   ├── model/
│   │       │   └── repository/
│   │       │
│   │       └── infrastructure/
│   │           └── persistence/
│   │
│   └── resources/
│       └── fxml/
│           ├── MainView.fxml
│           ├── TreeView.fxml
│           └── ...
│
└── test/
    └── java/
        └── com.ejemplo.arboles/
```

# 1. Estructura de Domain

Vamos a trabajar con la siguiente estructura:

```text
src/main/java/
└── co/edu/uptc/binarytree/
    └── domain/
        ├── model/
        │   ├── BinarySearchTree.java
        │   └── Node.java
        │
        ├── exception/
        │   ├── DuplicateValueException.java
        │   └── ValueNotFoundException.java
        │
        └── repository/
            └── BinaryTreeRepository.java
```

Hay una decisión importante aquí: el repository es una abstracción del dominio, no la implementación de persistencia. La implementación que escriba/lea archivos la construiremos posteriormente en infrastructure.

# 2. Node

El nodo representa la unidad básica del árbol.

```java

package co.edu.uptc.domain.model;

public class Node {

    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
```

Por ahora mantendría Node deliberadamente sencillo.

No necesitamos que conozca:

JavaFX.
FXML.
Archivos.
Servicios.
Controllers.
Clases de infraestructura.

# 3. Excepciones del dominio

Como decidimos que no se permiten valores duplicados, podemos expresar esa regla mediante una excepción específica.

**DuplicateValueException.java**

```java
package co.edu.uptc.domain.exception;

public class DuplicateValueException extends RuntimeException {

    public DuplicateValueException(int value) {
        super("El valor " + value + " ya existe en el árbol.");
    }
}
```

Y para las operaciones que necesitan encontrar un elemento:

**ValueNotFoundException.java**

```java
package co.edu.uptc.domain.exception;

public class ValueNotFoundException extends RuntimeException {

    public ValueNotFoundException(int value) {
        super("El valor " + value + " no existe en el árbol.");
    }
}
```
Esto es mejor que lanzar un Exception genérico porque las reglas del dominio quedan expresadas explícitamente.

# 4. BinarySearchTree

Esta será la clase principal del dominio.

Inicialmente implementaremos:

- Insertar.
- Buscar.
- Eliminar.
- Preorden.
- Inorden.
- Postorden.
- Número de nodos.
- Altura.
- Número de hojas.
- Mínimo.
- Máximo.
- Saber si está vacío.
- Limpiar.

```java
package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    private Node root;

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node current, int value) {

        if (current == null) {
            return new Node(value);
        }

        if (value < current.getValue()) {
            current.setLeft(insertRecursive(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(insertRecursive(current.getRight(), value));
        } else {
            throw new DuplicateValueException(value);
        }

        return current;
    }

    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node current, int value) {

        if (current == null) {
            return false;
        }

        if (value == current.getValue()) {
            return true;
        }

        if (value < current.getValue()) {
            return containsRecursive(current.getLeft(), value);
        }

        return containsRecursive(current.getRight(), value);
    }

    public void delete(int value) {

        if (!contains(value)) {
            throw new ValueNotFoundException(value);
        }

        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node current, int value) {

        if (current == null) {
            return null;
        }

        if (value < current.getValue()) {
            current.setLeft(deleteRecursive(current.getLeft(), value));

        } else if (value > current.getValue()) {
            current.setRight(deleteRecursive(current.getRight(), value));

        } else {

            // Caso 1: nodo hoja
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }

            // Caso 2: solo hijo derecho
            if (current.getLeft() == null) {
                return current.getRight();
            }

            // Caso 2: solo hijo izquierdo
            if (current.getRight() == null) {
                return current.getLeft();
            }

            // Caso 3: dos hijos
            int successorValue = findMin(current.getRight());
            Node replacement = new Node(successorValue);

            replacement.setLeft(current.getLeft());
            replacement.setRight(
                    deleteRecursive(current.getRight(), successorValue)
            );

            return replacement;
        }

        return current;
    }

    public List<Integer> inOrder() {

        List<Integer> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(Node current, List<Integer> result) {

        if (current == null) {
            return;
        }

        inOrderRecursive(current.getLeft(), result);
        result.add(current.getValue());
        inOrderRecursive(current.getRight(), result);
    }

    public List<Integer> preOrder() {

        List<Integer> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(Node current, List<Integer> result) {

        if (current == null) {
            return;
        }

        result.add(current.getValue());
        preOrderRecursive(current.getLeft(), result);
        preOrderRecursive(current.getRight(), result);
    }

    public List<Integer> postOrder() {

        List<Integer> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(Node current, List<Integer> result) {

        if (current == null) {
            return;
        }

        postOrderRecursive(current.getLeft(), result);
        postOrderRecursive(current.getRight(), result);
        result.add(current.getValue());
    }

    public int size() {
        return countNodes(root);
    }

    private int countNodes(Node current) {

        if (current == null) {
            return 0;
        }

        return 1
                + countNodes(current.getLeft())
                + countNodes(current.getRight());
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(Node current) {

        if (current == null) {
            return -1;
        }

        return 1 + Math.max(
                heightRecursive(current.getLeft()),
                heightRecursive(current.getRight())
        );
    }

    public int countLeaves() {
        return countLeavesRecursive(root);
    }

    private int countLeavesRecursive(Node current) {

        if (current == null) {
            return 0;
        }

        if (current.getLeft() == null && current.getRight() == null) {
            return 1;
        }

        return countLeavesRecursive(current.getLeft())
                + countLeavesRecursive(current.getRight());
    }

    public int findMin() {

        if (isEmpty()) {
            throw new ValueNotFoundException(
                    Integer.MIN_VALUE
            );
        }

        return findMin(root);
    }

    private int findMin(Node current) {

        if (current.getLeft() == null) {
            return current.getValue();
        }

        return findMin(current.getLeft());
    }

    public int findMax() {

        if (isEmpty()) {
            throw new ValueNotFoundException(
                    Integer.MAX_VALUE
            );
        }

        return findMax(root);
    }

    private int findMax(Node current) {

        if (current.getRight() == null) {
            return current.getValue();
        }

        return findMax(current.getRight());
    }

    public void clear() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }
}
```

Una observación importante sobre **delete**:
Caso donde dos hijos reemplace el nodo por su sucesor inorden.

Es decir, para:
```
        50
       /  \
     30    70
          /  \
        60    80
```

al eliminar 50, utilizamos 60 como sucesor:
```
        60
       /  \
     30    70
              \
              80
```

# 5. Repository

En nuestra capa de dominio introducimos un repository que nos permite abstraer la persistencia de los árboles.

```java
package co.edu.uptc.domain.repository;

import co.edu.uptc.domain.model.BinarySearchTree;

import java.util.List;

public interface BinaryTreeRepository {

    void save(String name, BinarySearchTree tree);

    BinarySearchTree findByName(String name);

    List<String> findAll();

    void delete(String name);

    boolean exists(String name);
}
```
Esta interfaz no sabe que vamos a utilizar archivos.

Eso es intencional.

Más adelante tendremos algo parecido a:

```text
domain
   │
   │  BinaryTreeRepository
   │
   ▼
infrastructure
   │
   └── FileBinaryTreeRepository
```

Así la dependencia apunta hacia la abstracción.

La dependencia conceptual queda:
```text
BinarySearchTree
       │
       ├── Node
       │
       └── Domain Exceptions


BinaryTreeRepository
       │
       │  (contrato)
       ▼
  Infraestructura
```
**Y ninguna de estas clases conoce JavaFX.**