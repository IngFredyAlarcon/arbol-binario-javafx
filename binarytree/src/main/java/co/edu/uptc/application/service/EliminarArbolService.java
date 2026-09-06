package co.edu.uptc.application.service;

import java.util.List;

import co.edu.uptc.domain.exception.NoTreeSelectedException;
import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.repository.BinaryTreeRepository;

public class EliminarArbolService {
   private BinaryTreeRepository repository;

    public EliminarArbolService(BinaryTreeRepository repository) {
        this.repository = repository;
    }

    public void eliminarArbol(String nombre) {
        if (nombre == null || !repository.exists(nombre)) {
            throw new NoTreeSelectedException();
        }

        if(!repository.exists(nombre)){
            throw new TreeNotFoundException(nombre);
        }
        repository.delete(nombre);
    }

    public List<String> obtenerNombresArboles() {
        return repository.findAll();
    }
}
