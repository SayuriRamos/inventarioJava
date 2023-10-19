package com.example.inventario;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Date;

public class ReportesView {



    @FXML
    private RadioButton movimientosRadioButton;

    @FXML
    private RadioButton entradaSRadioButton;

    //Taba de Movimientos
    @FXML
    private TableView<Movimientos> movimientosTableview;

    @FXML
    private TableColumn<Movimientos, Integer> idColumna;

    @FXML
    private TableColumn<Movimientos, String> productoColumna;

    @FXML
    private TableColumn<Movimientos, Date> fechaColumna;

    @FXML
    private TableColumn<Movimientos, String> tipoColumna;

    @FXML
    private TableColumn<Movimientos, Integer> cantidadColumna;

    @FXML
    private TableColumn<Movimientos, String> ubicacionColumna;


    //Tabla de Ventas
    @FXML
    private TableView<Ventas> entradasSTableView;

    @FXML
    private TableColumn<Ventas, String> mesColumna;

    @FXML
    private TableColumn<Ventas, Integer> entradasColumna;

    @FXML
    private TableColumn<Ventas, Integer> salidasColumna;



    public void initialize() {

        movimientosRadioButton.setSelected(true);
        movimientosReload();
        movimientosTableview.setVisible(true);

    }

    //Recargar la tabla movimientos
    public  void movimientosReload(){
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        productoColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fechaColumna.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tipoColumna.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cantidadColumna.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        ubicacionColumna.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

        ObservableList<Movimientos> data = ConexionBD.obtenerMovimeintos();
        movimientosTableview.setItems(data);



    }

    //ACCION AL SELECCIONAR EL RADIOBUTTON DE MOVIMIENTOS
    public void movAction(ActionEvent actionEvent) {

        movimientosTableview.setVisible(true);
        entradasSTableView.setVisible(false);


        movimientosRadioButton.setSelected(true);
        entradaSRadioButton.setSelected(false);

        movimientosReload();


    }

    //Accion al seleccionar el radiobutton de Salidas y entradas, se carga la tabla
    public void SalidaEntradaAction(ActionEvent actionEvent) {

        movimientosTableview.setVisible(false);
        entradasSTableView.setVisible(true);

        movimientosRadioButton.setSelected(false);
        entradaSRadioButton.setSelected(true);


        mesColumna.setCellValueFactory(new PropertyValueFactory<>("mes"));
        entradasColumna.setCellValueFactory(new PropertyValueFactory<>("entradasTotales"));
        salidasColumna.setCellValueFactory(new PropertyValueFactory<>("salidasTotales"));

        ObservableList<Ventas> data = ConexionBD.obtenerVentasTotales();
        entradasSTableView.setItems(data);


    }


    //Boton de regresar
    public void regresarAction(ActionEvent actionEvent){
        try
        {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("pantPrincipal-view.fxml")));
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


}
