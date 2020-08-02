package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialController implements Initializable {

    @FXML
    private Spinner<Integer> SpinnerCadeiras;
    @FXML
    private Button button;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> SpinnerCadeirasF = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        SpinnerCadeiras.setValueFactory(SpinnerCadeirasF);
        button.setOnAction(e->{
            Stage stage = (Stage) button.getScene().getWindow();
            ProblemSingleton ps = ProblemSingleton.getInstance();
            ps.setNumcadeiras(SpinnerCadeiras.getValue());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = null;
            try {

                Controller controller = loader.getController();
                root = loader.load();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });
    }
}
