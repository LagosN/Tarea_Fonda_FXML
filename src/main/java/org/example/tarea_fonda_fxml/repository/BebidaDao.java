package org.example.tarea_fonda_fxml.repository;


import org.example.tarea_fonda_fxml.model.Bebida;

import java.util.List;

public interface BebidaDao<T extends Bebida> {
    List<T> listar();
    void cargar(List<T> listaBebidas);
}
