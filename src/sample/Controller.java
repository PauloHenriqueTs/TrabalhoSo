package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

public class Controller implements Initializable  {


  @FXML
    private Pane pane;
  @FXML
  private VBox vBox;
  @FXML
  private HBox mesas;
  @FXML
  private HBox cadeiras;
  @FXML
  private HBox casas;
  @FXML
  private Spinner<Integer> SpinnerEmCasa;
  @FXML
  private Spinner<Integer> SpinnerNoBar;
  @FXML
  private Button btn;
  @FXML
  private TextFlow txflow;


  private static ProblemSingleton problemSingleton;
  private int num_cadeiras;
  public int num_clientes =0;
  private int num_mesas;

  public void Criar(ActionEvent actionEvent) throws FileNotFoundException, InterruptedException {
    if(num_clientes <10){
      btn.setDisable(true);
      num_clientes++;
      int TempoEmCasa = SpinnerEmCasa.getValueFactory().getValue();
      int TempoNoBar = SpinnerNoBar.getValueFactory().getValue();
      int n = num_clientes;

      Cliente cliente = new Cliente(n,TempoEmCasa,TempoNoBar,casas,cadeiras,txflow);

     new Thread(cliente
      ).start();


      btn.setDisable(false);


    }
  }




  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {


    SpinnerValueFactory<Integer> SpinnerNoBarF = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
    SpinnerValueFactory<Integer> SpinnerEmCasaF = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100);
    SpinnerEmCasa.setValueFactory(SpinnerEmCasaF);
    SpinnerNoBar.setValueFactory(SpinnerNoBarF);

  problemSingleton = ProblemSingleton.getInstance();


  num_cadeiras = ProblemSingleton.getNumcadeiras();

    if(num_cadeiras %2 ==0){
      num_mesas= (int) num_cadeiras/2;
      for (int i = 0; i < num_mesas ; i++) {
        this.criaMesa();
      }
      for (int i = 0; i < num_cadeiras ; i++) {
        this.criaCadeiras();
      }
    }else {
      num_mesas=( (int) num_cadeiras/2 )+1;
      for (int i = 0; i < num_mesas ; i++) {
        this.criaMesa();
      }
      for (int i = 0; i < num_cadeiras ; i++) {
        this.criaCadeiras();
      }
    }





  }

  public void criaMesa()  {

    FileInputStream input = null;
    try {
      input = new FileInputStream("src/sample/assets/table.png");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Image image = new Image(input);
      ImageView imageView = new ImageView(image);
      imageView.setFitHeight(64);
      imageView.setFitWidth(64);
   mesas.getChildren().add(imageView);


  }

  public void criaCadeiras() {
    FileInputStream input = null;
    try {
      input = new FileInputStream("src/sample/assets/chair.png");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Image image = new Image(input);
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(32);
    imageView.setFitWidth(32);
    cadeiras.getChildren().add(imageView);

  }


}
