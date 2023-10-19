package com.example.inventario;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;


public class PantPrincipalView {

    //Componentes de al tabla para ver productos existentes
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


    //Componenetes de Crear Producto
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


    //componentes de actualizar producto
    @FXML
    private TextField productoIdBTextField;

    @FXML
    private TextField nombreBTextField;

    @FXML
    private TextField descripcionBTextField;

    @FXML
    private TextField precioBTextField;

    @FXML
    private TextField cantidadBTextField;

    @FXML
    private ChoiceBox ubicacionBChoiseBox;


    //Componenetes de actualizar cantidad de producto
    @FXML
    private TextField idProductoCTextField;

    @FXML
    private TextField cantidadCTextField;

    @FXML
    private RadioButton eliminarCRadioButton;

    @FXML
    private RadioButton anadirCRadioButton;


    //Uitlizado para eliminar producto
    @FXML
    private TextField idProductoDTextField;

    @FXML
    private Label incorrecto;




    //Inicializar la pantalla con los productos en la lista
    public void initialize() {

        reloadTabla();

        List<String> datos = ConexionBD.obtenerUbicaciones();
        ubicacionAChoiceBox.getItems().addAll(datos);
        ubicacionBChoiseBox.getItems().addAll(datos);

        anadirCRadioButton.setSelected(true);
        incorrecto.setVisible(false);

    }

    //Actualizar tabla cuando se realicen cambios
    public void reloadTablaAction(ActionEvent actionEvent){
        reloadTabla();
    }

    //Funcion que se llama cada vez que se quiera recargar la tabla
    public void reloadTabla(){
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
        incorrecto.setVisible(false);

        actualizarGrupo.setVisible(false);
        cantidadGrupo.setVisible(false);
        eliminarGrupo.setVisible(false);
        agregarGrupo.setVisible(true);

    }

    public void actualizarBotonAction(ActionEvent actionEvent){
        incorrecto.setVisible(false);

        agregarGrupo.setVisible(false);
        cantidadGrupo.setVisible(false);
        eliminarGrupo.setVisible(false);
        actualizarGrupo.setVisible(true);

    }


    public void cantidadBotonAction(ActionEvent actionEvent){
        incorrecto.setVisible(false);

        actualizarGrupo.setVisible(false);
        agregarGrupo.setVisible(false);
        eliminarGrupo.setVisible(false);
        cantidadGrupo.setVisible(true);

        anadirCRadioButton.setSelected(true);

    }


    public void eliminarBotonAction(ActionEvent actionEvent){
        incorrecto.setVisible(false);

        actualizarGrupo.setVisible(false);
        cantidadGrupo.setVisible(false);
        agregarGrupo.setVisible(false);
        eliminarGrupo.setVisible(true);

    }


    /*
    Funciones sobre loq ue se va a hacer al darle click a un boton de actualizar, eliminar,...
     */
    public void obtenerInfoAction(ActionEvent actionEvent){

        try{

            String id = productoIdBTextField.getText();

            Producto producto;

            producto = ConexionBD.getInfoProducto(id);

            assert producto != null;
            nombreBTextField.setText(producto.nombre);
            descripcionBTextField.setText(producto.descripcion);
            cantidadBTextField.setText(String.valueOf(producto.cantidad));
            precioBTextField.setText(String.valueOf(producto.precio));
            incorrecto.setVisible(false);



        }
        catch (Exception e){
            incorrecto.setVisible(true);
        }

    }


    public void agregarProductoAction(ActionEvent actionEvent){

        try{

            String nombre = nombreATextField.getText();
            String descripcion = descripcionATextField.getText();
            double precio = Double.parseDouble(precioATextField.getText());
            int cantidad = Integer.parseInt(cantidadATextField.getText());
            String ubi = (String) ubicacionAChoiceBox.getValue();

            ConexionBD.agregarProducto(nombre,descripcion, precio, cantidad, ubi);
            reloadTabla();
            incorrecto.setVisible(false);

        }
            catch (Exception e){
            incorrecto.setVisible(true);
        }
    }

    public void actualizarProductoAction(ActionEvent actionEvent){

        try{

            int id = Integer.parseInt(productoIdBTextField.getText());
            String nombre = nombreBTextField.getText();
            String descripcion = descripcionBTextField.getText();
            double precio = Double.parseDouble(precioBTextField.getText());
            int cantidad = Integer.parseInt(cantidadBTextField.getText());
            String ubi = (String) ubicacionBChoiseBox.getValue();

            ConexionBD.actualizarProducto(id,nombre,descripcion, precio, cantidad,ubi);
            reloadTabla();
            incorrecto.setVisible(false);

        }
                catch (Exception e){
            incorrecto.setVisible(true);
        }


}


    public void cantidadProductoAction(ActionEvent actionEvent){

        try{

            int id = Integer.parseInt(idProductoCTextField.getText());
            int cantidad = Integer.parseInt(cantidadCTextField.getText());

            if(anadirCRadioButton.isSelected()){

                ConexionBD.sumarCantidadProducto(id,cantidad);

            }
            else {

                ConexionBD.restarCantidadProducto(id,cantidad);

            }
            reloadTabla();
            incorrecto.setVisible(false);

        }
        catch (Exception e){
            incorrecto.setVisible(true);
        }


    }


    public void eliminarProductoAction(ActionEvent actionEvent){

        try{


            int id = Integer.parseInt(idProductoDTextField.getText());

            ConexionBD.eliminarProducto(id);

            reloadTabla();
            incorrecto.setVisible(false);


        }
        catch (Exception e){
            incorrecto.setVisible(true);
        }

    }


    /*
    Acciones al seleccioanar un radio Button
     */
    public void seleccionarAnadirAction(ActionEvent actionEvent){

        anadirCRadioButton.setSelected(true);
        eliminarCRadioButton.setSelected(false);


    }

    public void seleccionarEliminarAction(ActionEvent actionEvent){
        anadirCRadioButton.setSelected(false);
        eliminarCRadioButton.setSelected(true);

    }




    //Ir a la pantalla de reportes
    public void reportesAction(ActionEvent actionEvent){

        try
        {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("reportes-view.fxml")));
            Parent root = loader.load();
            Scene PantallaPrincipal = new Scene(root,900, 660);


            Stage currentStage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

            currentStage.hide();
            currentStage.setScene(PantallaPrincipal);
            currentStage.show();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //Cerrar sesión y regresar a pantalla de inicio de sesión
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
