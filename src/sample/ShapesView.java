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

public class ShapesView implements PropertyChangeListener {
    ShapesModel model;
    GraphicsContext gc;
    int resX = 800;
    int resY = 600;
    int lineWidth = 2;
    int intersectionSize = 10;
    ShapesView(ShapesModel model, GraphicsContext gc){
        this.model=model;
        this.gc = gc;
        model.addPropertyChangeListener(this);
    }
    void drawShapes(){
        gc.setFill(Color.GRAY);
        gc.fillRect(0,0,resX, resY);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(lineWidth);
        //gc.strokeLine(40, 10, 10, 40);
        for (Shape shape: model.shapes){
            Vector2d lineBegin = shape.origin.add(shape.points.get(shape.points.size()-1).rotate(shape.rotation));
            for (Vector2d point: shape.points){
                Vector2d lineEnd = shape.origin.add(point.rotate(shape.rotation));
                gc.strokeLine(lineBegin.x, lineBegin.y, lineEnd.x, lineEnd.y);
                lineBegin = lineEnd;
            }
            gc.setFill(Color.BLUE);
            gc.fillOval(shape.origin.x-intersectionSize/2, shape.origin.y-intersectionSize/2, intersectionSize,intersectionSize);
            for (Shape shape1: model.shapes){
                if (shape1 == shape) continue;
                for (Vector2d interection:shape.getIntersections(shape1)){
                    gc.fillOval(interection.x-intersectionSize/2, interection.y-intersectionSize/2, intersectionSize,intersectionSize);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        new Thread(this::drawShapes).start();
    }
}
