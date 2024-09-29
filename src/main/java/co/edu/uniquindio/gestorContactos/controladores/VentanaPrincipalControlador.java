package co.edu.uniquindio.gestorContactos.controladores;

import co.edu.uniquindio.gestorContactos.modelo.Contacto;
import co.edu.uniquindio.gestorContactos.modelo.ContactoPrincipal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPrincipalControlador implements Initializable {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtUrl;

    @FXML
    private DatePicker diaCumpleanos;
    @FXML
    private ComboBox<String> txtFiltro;

    @FXML
    private TableView<Contacto> tablaContactos;

    @FXML
    private TableColumn<Contacto, String> colNombre;

    @FXML
    private TableColumn<Contacto, String> colApellido;

    @FXML
    private TableColumn<Contacto, String> colTelefono;

    @FXML
    private TableColumn<Contacto, String> colCumpleanos;
    @FXML
    private TableColumn<Contacto, String> colCorreo;
    @FXML
    private TableColumn<Contacto, String> colPerfil;
    private Contacto contactoSeleccionado;

    private ObservableList<Contacto> contactosObservable;
    private final ContactoPrincipal contactoPrincipal; //Instancia de la clase NotaPrincipal

    public VentanaPrincipalControlador() {
        contactoPrincipal = new ContactoPrincipal();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Asignar las propiedades de la nota a las columnas de la tabla
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumeroTelefono()));
        colCumpleanos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().obtenerDiferencia()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        colPerfil.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUrl()));

        //Cargar categorias en el ComboBox
        txtFiltro.setItems( FXCollections.observableList(contactoPrincipal.listarOpciones()) );

        //Inicializar lista observable y cargar las notas
        contactosObservable = FXCollections.observableArrayList();
        cargarContactos();

        //Evento click en la tabla
        tablaContactos.setOnMouseClicked(e -> {
            //Obtener la nota seleccionada
            contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

            if(contactoSeleccionado != null){
                txtNombre.setText(contactoSeleccionado.getNombre());
                txtApellido.setText(contactoSeleccionado.getApellido());
                diaCumpleanos.setValue(contactoSeleccionado.getCumpleanos().toLocalDate());
                txtTelefono.setText(contactoSeleccionado.getNumeroTelefono());
                txtCorreo.setText(contactoSeleccionado.getCorreo());
                txtUrl.setText(contactoSeleccionado.getUrl());

            }

        });
    }
    public void anadirContacto(ActionEvent e){
        try {
            contactoPrincipal.agregarContacto(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtTelefono.getText(),
                    diaCumpleanos.getValue(),
                    txtCorreo.getText(),
                    txtUrl.getText()
            );

            limpiarCampos();
            actualizarContactos();
            mostrarAlerta("Contacto creado con éxito", Alert.AlertType.INFORMATION);
            tablaContactos.setItems( FXCollections.observableArrayList(contactoPrincipal.listarContactos()) );
        }catch (Exception ex){
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private void cargarContactos() {
        contactosObservable.setAll(contactoPrincipal.listarContactos());
        tablaContactos.setItems(contactosObservable);
    }
    private void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
    public void eliminarContacto(ActionEvent e){}
    public void editarContacto(ActionEvent e){
        if(contactoSeleccionado != null) {
            try {
                contactoPrincipal.editarContacto(
                        contactoSeleccionado.getId(),
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtTelefono.getText(),
                        txtUrl.getText(),
                        txtCorreo.getText(),
                        diaCumpleanos.getValue()

                );

                limpiarCampos();
                cargarContactos();
                mostrarAlerta("Contacto actualizado con éxito", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
            }
        }else{
            mostrarAlerta("Debe seleccionar un contacto de la lista de contactos", Alert.AlertType.WARNING);
        }
    }
    public void actualizarContactos() {
        contactosObservable.setAll(contactoPrincipal.listarContactos());
    }

    /**
     * Limpia los campos de texto del formulario
     */
    private void limpiarCampos(){
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtUrl.clear();
        diaCumpleanos.setValue(null);
    }
}
