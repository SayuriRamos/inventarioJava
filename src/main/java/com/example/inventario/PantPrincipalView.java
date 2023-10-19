package com.example.inventario;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;


public class PantPrincipalView {

    @FXML
    private TableView<Producto> tableViewInventario;

    @FXML
    private TableColumn<Producto, String> idColumna;

    @FXML
    private TableColumn<Producto, String> nombreColumna;

    @FXML
    private TableColumn<Producto, String> descripcionColumna;

    @FXML
    private TableColumn<Producto, String> precioColumna;

    @FXML
    private TableColumn<Producto, String> cantidadColumna;

    @FXML
    private TableColumn<Producto, String> ubicacionColumna;


    //Nombres de grupos de objetos para agregar, actualizar,...
    @FXML
    private Group agregarGrupo;

    @FXML
    private Group actualizarGrupo;

    @FXML
    private Group cantidadGrupo;

    @FXML
    private Group eliminarGrupo;


    @FXML
    private TextField nombreATextField;

    @FXML
    private TextField descripcionATextField;

    @FXML
    private TextField precioATextField;

    @FXML
    private TextField cantidadATextField;

    @FXML
    private ChoiceBox ubicacionAChoiceBox;






    //Inicializar la pantalla con los productos en la lista
    public void initialize() {
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        precioColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        ubicacionColumna.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        List<String> datos = ConexionBD.obtenerUbicaciones();
        ubicacionAChoiceBox.getItems().addAll(datos);


        ObservableList<Producto> data = ConexionBD.obtenerProductos();
        tableViewInventario.setItems(data);


    }

    //Actualizar tabla cuando se realicen cambios
    public void reloadTablaAction(ActionEvent actionEvent){
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionColumna.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        precioColumna.setCellValueFactory(new PropertyValueFactory<>("precio"));
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        ubicacionColumna.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        ObservableList<Producto> data = ConexionBD.obtenerProductos();
        tableViewInventario.setItems(data);
    }


    //Esconder y poner visible grupos dependiendo qué botón se escogió
    public void agregarBotonAction(ActionEvent actionEvent){
        actualizarGrupo.setVisible(false);
        cantidadGrupo.setVisible(false);
        eliminarGrupo.setVisible(false);
        agregarGrupo.setVisible(true);

        List<String> datos = ConexionBD.obtenerUbicaciones();
        ubicacionAChoiceBox.getItems().addAll(datos);

    }

    public void actualizarBotonAction(ActionEvent actionEvent){
        agregarGrupo.setVisible(false);
        cantidadGrupo.setVisible(false);
        eliminarGrupo.setVisible(false);
        actualizarGrupo.setVisible(true);

    }

    public void cantidadBotonAction(ActionEvent actionEvent){
        actualizarGrupo.setVisible(false);
        agregarGrupo.setVisible(false);
        eliminarGrupo.setVisible(false);
        cantidadGrupo.setVisible(true);

    }


    public void eliminarBotonAction(ActionEvent actionEvent){
        actualizarGrupo.setVisible(false);
        cantidadGrupo.setVisible(false);
        agregarGrupo.setVisible(false);
        eliminarGrupo.setVisible(true);

    }

    public void agregarProductoAction(ActionEvent actionEvent){

        String nombre = nombreATextField.getText();
        String descripcion = descripcionATextField.getText();
        double precio = Double.parseDouble(precioATextField.getText());
        int cantidad = Integer.parseInt(cantidadATextField.getText());
        String ubi = (String) ubicacionAChoiceBox.getValue();

        ConexionBD.agregarProducto(nombre,descripcion, precio, cantidad, ubi);
    }

    public void actualizarProductoAction(ActionEvent actionEvent){

    }


    public void cantidadProductoAction(ActionEvent actionEvent){

    }

    public void eliminarProductoAction(ActionEvent actionEvent){

    }





    public void cerrarSesionAction(MouseEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("iniciosesion-view.fxml")));
            Parent root = loader.load();
            Scene InicioSesion = new Scene(root,900, 660);


            Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();

            currentStage.hide();
            currentStage.setScene(InicioSesion);
            currentStage.show();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
