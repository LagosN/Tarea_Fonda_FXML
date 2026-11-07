package org.example.tarea_fonda_fxml.repository;
import org.example.tarea_fonda_fxml.model.Bebida;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class GsonBebidaDao<T extends Bebida> implements BebidaDao<T> {

    private Path archivo;
    private Type tipoLista;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public GsonBebidaDao(String nombre, Class<T> tClass){
        // implementar carpeta para datos
        this.archivo = Paths.get(nombre);
        this.tipoLista = TypeToken.getParameterized(List.class,tClass).getType();
    }


    @Override
    public List<T> listar() {
        if(Files.notExists(this.archivo) ){
            return new ArrayList<>();
        }
        try (Reader lector = Files.newBufferedReader(this.archivo, StandardCharsets.UTF_8 )){
            List<T> bebidas = gson.fromJson(lector, this.tipoLista);
            if (bebidas == null) {
                return new ArrayList<>();
            }
            List<T> validas = new ArrayList<>();
            for  (T bebida : bebidas) {
                if(bebida != null && bebida.getNombre()!= null){
                    validas.add(bebida);
                }
            }
            return validas;
        }catch(IOException e){
            throw new PersistenciaException(PersistenciaException.ALERTA_ESCRITURA, e);

        }

    }

    @Override
    public void cargar(List<T> listaBebidas) {
        try(Writer escritor = Files.newBufferedWriter(this.archivo, StandardCharsets.UTF_8)){
            this.gson.toJson(listaBebidas, this.tipoLista, escritor);
        }catch (IOException | JsonParseException e){
            throw new PersistenciaException(PersistenciaException.ALERTA_ESCRITURA, e);
        }


    }
}
