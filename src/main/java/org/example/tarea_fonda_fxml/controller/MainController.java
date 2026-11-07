package org.example.tarea_fonda_fxml.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.example.tarea_fonda_fxml.model.Bebida;
import org.example.tarea_fonda_fxml.model.BebidaAlcoholica;
import org.example.tarea_fonda_fxml.model.BebidaSinAlcohol;
import org.example.tarea_fonda_fxml.repository.BebidaRepository;
import org.example.tarea_fonda_fxml.repository.PersistenciaException;

import java.text.ParseException;

public class MainController {
    private static final String BEBIDA_ALCOHOLICA = "Bebida Alcoholica";
    private static final String BEBIDA_SIN_ALCOHOL = "Bebida Sin Alcohol";

    // CONSTANTE PARA ALERTLABEL
    public final static String ALERTA_EXITO = "Registro exitoso";
    public final static String ALERTA_NO_VALIDO = "Dato no valido";
    public final static String ALERTA_ELIMINADO = "Registro eliminado";
    public final static String ALERTA_NO_SELECCIONADO = "Falta seleccion";
    public final static String ALERTA_LECTURA = "Error al leer archivo";
    public final static String ALERTA_ESCRITURA = "Error al escribir archivo";
    public final static String ALERTA_DEFAULT = "Sin Alerta";

    //Datos en común

    @FXML
    private ChoiceBox<String> tipoBebidaChoiceBox;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField volumenTextField;
    @FXML
    private TextField stockTextField;

    // Datos Bebida Alcoholica
    @FXML
    private TextField gradosAlcoholTextField;
    @FXML
    private CheckBox isCertificadaCheckBox;
    // Datos Bebida Sin Alcohol
    @FXML
    private TextField azucarTextField;


    //Datos Tabla
    @FXML private TableView<Bebida> bebidasTableView;
    @FXML private TableColumn<Bebida, String> tipoTableColumn;
    @FXML private TableColumn<Bebida, String> nombreTableColumn;
    @FXML private TableColumn<Bebida, String> volumenTableColumn;
    @FXML private TableColumn<Bebida, String> stockTableColumn;
    @FXML private TableColumn<Bebida, String> precioTableColumn;

    //Especiales
    @FXML private Button registrarButton;
    @FXML private Button limpiarButton;
    @FXML private Button eliminarButton;
    @FXML private Label mensajeUsuario;

    // Datos internos del controllador
    private final BebidaRepository repository = new BebidaRepository();
    private ObservableList<Bebida> bebidas = FXCollections.observableArrayList();

    // INTERIOR ALCOLICA // EXTERIOR SIN ALCOHOL
    @FXML
    private void initialize() {
        this.tipoBebidaChoiceBox.getItems().addAll(BEBIDA_ALCOHOLICA, BEBIDA_SIN_ALCOHOL);
        // Otra forma de agregar
        //this.tipoBebidaChoiceBox.getItems().add(BEBIDA_SIN_ALCOHOL)
        //this.tipoBebidaChoiceBox.getItems().add(BEBIDA_ALCOHOLICA)
        this.mensajeUsuario.setVisible(false);
        // Campos Bebida Alcoholica
        this.gradosAlcoholTextField.setDisable(true);
        this.isCertificadaCheckBox.setDisable(true);
        // Campos bebida sin alcohol
        this.azucarTextField.setDisable(true);


        this.configurarTableView();
        try{this.repository.cargar();
            this.actualizarTableview();
        }catch(Exception e)
        {this.setAlertaLabelMensaje(e.getMessage(), "Error al cargar inicial");}

        //TODO: Refactorizar
        bebidas.setAll(this.repository.getList());
    }

    private void configurarTableView(){
        this.tipoTableColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getTipo()));
        this.nombreTableColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getNombre()));
        this.volumenTableColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getVolumenMl())));
        this.stockTableColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(String.valueOf(celda.getValue().getStock())));
        this.precioTableColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(Bebida.pesos((int) Math.round(celda.getValue().calcularPrecio()))));
        this.bebidasTableView.setItems(this.bebidas);
    }

    private void actualizarTableview(){
        bebidas.setAll(repository.getList());
    }


    @FXML public void limpiarButton(){
        this.tipoBebidaChoiceBox.setValue("");
        this.nombreTextField.setText("");
        this.volumenTextField.setText("");
        this.stockTextField.setText("");
        this.setAlertaLabelMensaje(ALERTA_DEFAULT, "");
        // Campos interiores
        this.gradosAlcoholTextField.setDisable(true);
        this.isCertificadaCheckBox.setDisable(true);
        // Campos exteriores
        this.azucarTextField.setDisable(false);


    }
    @FXML public void OnSeleccionarTipo() {
        String tipo = this.tipoBebidaChoiceBox.getValue();
        switch (tipo) {

            case BEBIDA_ALCOHOLICA:
                this.tipoBebidaChoiceBox.setDisable(false);
                // Campos exteriores
                this.gradosAlcoholTextField.setDisable(false);
                this.isCertificadaCheckBox.setDisable(false);
                this.azucarTextField.setDisable(true);
                break;
            case BEBIDA_SIN_ALCOHOL:
                // Campos interiores
                this.tipoBebidaChoiceBox.setDisable(false);
                // Campos exteriores
                this.gradosAlcoholTextField.setDisable(true);
                this.isCertificadaCheckBox.setDisable(true);
                this.azucarTextField.setDisable(false);

                break;
            default:
                // Campos interiores
                this.gradosAlcoholTextField.setDisable(true);
                this.isCertificadaCheckBox.setDisable(true);

                // Campos exteriores
                this.azucarTextField.setDisable(true);



                //

        }
    }



    @FXML public void registrarButton() {
        Bebida bebida;
        try {
            //Se asignan las variables de los controladores generales
            String tipo = this.tipoBebidaChoiceBox.getValue();
            String nombre = this.nombreTextField.getText();

            int volumen = Integer.parseInt(this.volumenTextField.getText());
            int stock = Integer.parseInt(this.stockTextField.getText());
            switch (tipo) {
                case BEBIDA_ALCOHOLICA:

                    double gradosAlcohol = Double.parseDouble(this.gradosAlcoholTextField.getText());
                    if (gradosAlcohol <= 0.0) {
                        this.setAlertaLabelMensaje(ALERTA_NO_VALIDO, "Campo volumen debe ser mayor o igual a 0.0");
                        return;
                    }
                    boolean certificada = this.isCertificadaCheckBox.isSelected();

                    bebida = new BebidaAlcoholica(nombre, volumen, stock, gradosAlcohol, certificada);
                    if (certificada) {
                        ((BebidaAlcoholica) bebida).setVentaRestringida(true);
                    }
                    break;

                case BEBIDA_SIN_ALCOHOL:
                    int azucar = Integer.parseInt(this.azucarTextField.getText());
                    if (azucar <= 0) {
                        this.setAlertaLabelMensaje(ALERTA_NO_VALIDO, "Campo azúcar debe ser mayor o igual a 0");
                        return;
                    }

                    bebida = new BebidaSinAlcohol(nombre, volumen, stock, azucar);
                    break;
                default:
                    this.setAlertaLabelMensaje(ALERTA_NO_SELECCIONADO, "Campo Seleccionar Tipo de bebida:");

                    return;
            }
            try {
                this.repository.guardar(bebida);
                this.actualizarTableview();
            } catch (PersistenciaException e) {
                this.setAlertaLabelMensaje(e.getMessage(), "Error al guardar bebida");
                return;
            }

            this.setAlertaLabelMensaje(ALERTA_EXITO, "Bebida " + bebida.getNombre() + " agregada");


        } catch (IllegalArgumentException e) {
            this.setAlertaLabelMensaje(ALERTA_NO_VALIDO, e.getMessage());
        }
    }
    private void setAlertaLabelMensaje( String tipo,String mensaje){
        if (mensaje.trim().isBlank()){
            mensaje = tipo;
        }else{
            mensaje = tipo + " " + mensaje;
        }
        switch (tipo){
            case ALERTA_EXITO:
            case ALERTA_ELIMINADO:
                this.mensajeUsuario.setVisible(true);
                this.mensajeUsuario.setTextFill(Color.GREEN);

                break;

            case ALERTA_ESCRITURA:
            case ALERTA_NO_SELECCIONADO:
            case ALERTA_LECTURA:
            case ALERTA_NO_VALIDO:
                this.mensajeUsuario.setVisible(true);
                this.mensajeUsuario.setTextFill(Color.RED);

                break;
            default:
                this.mensajeUsuario.setVisible(false);



        }
        this.mensajeUsuario.setText(mensaje);

    }


    public void eliminarButton() {
        Bebida seleccionada = this.bebidasTableView.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            this.setAlertaLabelMensaje(ALERTA_NO_SELECCIONADO, "Seleccione un planta");
            return;
        } try{
            if(!this.repository.eliminar(seleccionada)){
                this.setAlertaLabelMensaje(ALERTA_NO_SELECCIONADO, "No se encuentra lo solicitado en el registro");
                this.actualizarTableview();
                return;
            }

        }catch (PersistenciaException e) {
            this.setAlertaLabelMensaje(e.getMessage(), "No se pueden guardar los cambios");
            return;
        }
        this.actualizarTableview();
        this.setAlertaLabelMensaje(ALERTA_EXITO, "Planta eliminada");
    }

}
