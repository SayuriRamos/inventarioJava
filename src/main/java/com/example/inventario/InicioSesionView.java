package com.example.inventario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InicioSesionView {

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField contrasenaTextField;

    @FXML
    private Label incorrecto;


    /*
    Validar la contraseña y usuario
     */
    public void iniciarSesionAction(MouseEvent event)
    {
        String usuario = userNameTextField.getText();
        String contrasena = contrasenaTextField.getText();

        boolean autentificacionUsuario = ConexionBD.autentificarUsuario(usuario, contrasena);

        if (autentificacionUsuario) {

            incorrecto.setVisible(false);

            try
            {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("pantPrincipal-view.fxml")));
                Parent root = loader.load();
                Scene PantallaPrincipal = new Scene(root,900, 660);


                Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();

                currentStage.hide();
                currentStage.setScene(PantallaPrincipal);
                currentStage.show();
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

        } else {

            incorrecto.setVisible(true);

        }


    }
}

