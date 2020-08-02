package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

public class Cliente implements Runnable  {


    public int id;
    public  int TempoBar;
    public   int TempoEmCasa;
    public ImageView image;
    private HBox casas;
    private HBox cadeiras;
    private TextFlow txflow;
    public Cliente(int n, int TempoEmCasa, int TempoNoBar, HBox casas, HBox cadeiras, TextFlow txflow) throws InterruptedException {
        this.id = n;
        this.TempoEmCasa =TempoEmCasa;
        this.TempoBar=TempoNoBar;
        this.txflow =txflow;
        this.casas =casas;
        this.cadeiras = cadeiras;

        this.image = this.initialImages();

        this.casas.getChildren().add(0,this.image);

        if(txflow.getChildren().toArray().length>3) {
            txflow.getChildren().remove(0);
            txflow.getChildren().add(new Text("Cliente "+this.id+" esta em  Casa\n"));
        }else {
            txflow.getChildren().add(new Text("Cliente "+this.id+" esta em Casa \n"));
        }

      //  System.out.println("cliente "+this.id+" quantidade de drinking "+this.drinking);



    }

    public ImageView getImage(){
        return this.image;
    }


    public  void cpu1Sec(){
        long Time1 = System.currentTimeMillis();
        int x =0;
        while ((System.currentTimeMillis() - Time1) < 1000 ){
            x++;
        }
    }
    public  void cpuTempoNoBar(){
        long Time1 = System.currentTimeMillis();
        int x =0;
        while ((System.currentTimeMillis() - Time1) <this.TempoBar* 1000 ){
            x++;
        }
    }

    public  void cpuTempoEmCasa(){
        long Time1 = System.currentTimeMillis();
        int x =0;
        while ((System.currentTimeMillis() - Time1) < this.TempoEmCasa*1000 ){
            x++;
        }
    }



    public  void CasaProBar(final int num_clientes_sentados ) {

        ImageView imageAux = this.initialImages();
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(this.image);
        Path path = new Path();
        double x =this.image.getX();
        MoveTo moveTo = new MoveTo(0, 56);
        LineTo line1 = new LineTo((this.cadeiras.getChildren().size()+1)*50, 56);
        LineTo line2 = new LineTo((this.cadeiras.getChildren().size()+1)*50, 200);
        path.getElements().add(moveTo);
        path.getElements().addAll(line1,line2);
        pathTransition.setPath(path);

        pathTransition.setOnFinished(e->{

                txflow.getChildren().add(new Text("Cliente "+this.id+" esta no Bar \n"));

        try {


                this.cadeiras.getChildren().add(0,imageAux);
                this.cadeiras.getChildren().remove(ProblemSingleton.getNumcadeiras());
               // this.cadeiras.getChildren().add(num_clientes_sentados,imageAux);

            this.casas.getChildren().remove(this.image);
            this.image = imageAux;



            ProblemSingleton.getMutex().release();
        }catch (Exception e1){

            e1.getMessage();
        }




        });


            pathTransition.play();




    }
    public  void BarPraCasa(final int num_clientes_emcasa){

        ImageView imageAux = this.initialImages();
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1000));
        pathTransition.setNode(this.image);
        Path path = new Path();
        double x =this.image.getX();
        MoveTo moveTo = new MoveTo(0, 100);
        LineTo line1 = new LineTo((this.cadeiras.getChildren().size())*-36, 100);
        LineTo line2 = new LineTo((this.cadeiras.getChildren().size())*-36, 0);
        path.getElements().add(moveTo);
        path.getElements().addAll(line1,line2);
        pathTransition.setPath(path);
        pathTransition.setOnFinished(e->{
          // ImageView imageAux = this.image;
            try {
                txflow.getChildren().add(new Text("Cliente "+this.id+" esta em Casa \n"));


                this.casas.getChildren().add(imageAux);
             if(this.cadeiras.getChildren().indexOf(this.image) >-1)
                this.cadeiras.getChildren().set( this.cadeiras.getChildren().indexOf(this.image),this.initialChair());
               // this.cadeiras.getChildren().remove(this.image);

                this.image =imageAux;
                ProblemSingleton.subDrinking();
                if (ProblemSingleton.getDrinking() == 0 ) {
                      int n = Math.min(ProblemSingleton.getNumcadeiras(),ProblemSingleton.getWaiting());
                      ProblemSingleton.subWaiting(n);
                    ProblemSingleton.addDrinking(n);
                   // ProblemSingleton.setBlockValue(n);
                    boolean mustwait = ProblemSingleton.getDrinking() == ProblemSingleton.getNumcadeiras();
                    ProblemSingleton.setMust_wait(mustwait);


                    ProblemSingleton.getBlock().release(n);

                }
                ProblemSingleton.getMutex().release();
            }catch (Exception e1){

                e1.getMessage();
            }


        });

            pathTransition.play();





    }

    public void AguardandoCadeira(){
        Platform.runLater(()->{
            try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                txflow.getChildren().add(new Text("Cliente "+this.id+" aguardando cadeira \n"));


        });
    }


    private  ImageView initialImages(){
        Image image = this.returnImageCharacter();
    return  this.returnImageView(image);
    }
    private ImageView initialChair(){
        Image image = this.returnImageChair();
        return  this.returnImageView(image);
    }
    private static ImageView returnImageView(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(32);
        imageView.setFitWidth(32);
        return imageView;
    }
    private  Image returnImageCharacter(){
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/sample/assets/character"+this.id+".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        return  image;
    }
    private static Image returnImageChair(){
        FileInputStream input = null;
        try {
            input = new FileInputStream("src/sample/assets/chair.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(input);
        return  image;
    }



    @Override
    public  void run() {
        while (true){
            try {

                this.cpuTempoEmCasa();
               ProblemSingleton.getMutex().acquire();

               if (ProblemSingleton.isMust_wait()) {

                   ProblemSingleton.addWaiting();
                    this.AguardandoCadeira();
                    ProblemSingleton.getMutex().release();
                    ProblemSingleton.getBlock().acquire();



                   System.out.println("drink value:"+ProblemSingleton.getDrinking());
                   System.out.println("waiting value:"+ProblemSingleton.getWaiting());


                } else {
                  // this.CasaProBar(ProblemSingleton.getDrinking());
                    ProblemSingleton.addDrinking();
                    boolean mustwait =  ProblemSingleton.getDrinking()==  ProblemSingleton.getNumcadeiras();
                    ProblemSingleton.setMust_wait(mustwait);
                   ProblemSingleton.getMutex().release();
                }
                ProblemSingleton.getMutex().acquire();
                this.CasaProBar(ProblemSingleton.getDrinking());




                this.cpuTempoNoBar();
               ProblemSingleton.getMutex().acquire();

                this.BarPraCasa(ProblemSingleton.getDrinking());


           //     ProblemSingleton.getMutex().release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    }

