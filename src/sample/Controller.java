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
    View view;
    @FXML private void drawCanvas(ActionEvent event) {
        System.out.println(event);
        view.drawCanvas();
    }
    @FXML private void drawParticles(ActionEvent event) {
        System.out.println(event);
        view.drawParticles();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = img.getGraphicsContext2D();
        model = new Model();
        view = new View(model, gc);
        model.addPropertyChangeListener(view);
        gc.setFill(Color.BLACK);
        System.out.println("color set to black");
        gc.fillRect(50, 50, 100, 100);
        System.out.println("draw rectangle");
    }

}
