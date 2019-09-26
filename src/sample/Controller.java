package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Canvas img ;

    private GraphicsContext gc ;
    Model model;
    ShapesModel smodel;
    View view;
    ShapesView sview;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = img.getGraphicsContext2D();
        //model = new Model();
        smodel = new ShapesModel();
        //view = new View(model, gc);
        sview = new ShapesView(smodel, gc);
        //model.addPropertyChangeListener(view);
    }

}
