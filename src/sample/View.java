package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    Model model;
    GraphicsContext gc;
    int resX = 800;
    int resY = 600;
    View(Model model, GraphicsContext gc){
        this.model=model;
        this.gc = gc;
    }
    void drawCanvas() {
        gc.setFill(Color.BLUE);
        gc.fillRect(10,10,100,100);
    }
    void drawParticles(){
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,resX, resY);
        gc.setFill(Color.BLUE);
        for (Particle particle: model.particles){
            gc.fillOval(particle.position.x, particle.position.y,10,10);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        new Thread(this::drawParticles).start();
    }
}
