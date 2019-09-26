package sample;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ShapesModel {
    ArrayList<Shape> shapes;
    float dt = 1f;
    static Vector2d g = new Vector2d(0,0.01);
    private PropertyChangeSupport propertyChangeSupport;

    public void addPropertyChangeListener(PropertyChangeListener l){
        propertyChangeSupport.addPropertyChangeListener(l);
    }
    ShapesModel(){
        propertyChangeSupport = new PropertyChangeSupport(this);
        ArrayList<Vector2d> points = new ArrayList<>();
        points.add(new Vector2d(0,0));
        points.add(new Vector2d(100,0));
        points.add(new Vector2d(100,500));
        points.add(new Vector2d(700,500));
        points.add(new Vector2d(700,0));
        points.add(new Vector2d(800,0));
        points.add(new Vector2d(800,600));
        points.add(new Vector2d(0,600));
        Shape boundaries = new Shape(points);
        boundaries.setImmovable();
        shapes = new ArrayList<>();
        shapes.add(boundaries);
        Rectangle r1 = new Rectangle(300, 200, 100,100);
        Rectangle r2 = new Rectangle(600, 200, 100,100);
        Rectangle r3 = new Rectangle(200, 400, 100,100);
        Rectangle r4 = new Rectangle(601, 400, 100,100);
        r1.rotation = -Math.PI/4;
        r1.applyImpulse(r1.origin, new Vector2d(-10,10));
        //r2.applyImpulse(r2.origin, new Vector2d(-10,-10));
        //shapes.add(r1);
        shapes.add(r2);
        //shapes.add(r3);
        shapes.add(r4);
        System.out.println(boundaries);
        new Thread(()->{
            while (true){
                try {
                    //System.out.println(shape.velocity);
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                update();
            }
        }).start();
    }
    void update(){
        for (int i=0; i<shapes.size(); i++){
            Shape shape1 = shapes.get(i);

            for (int j=i+1; j<shapes.size(); j++){
                Shape shape2 = shapes.get(j);
                shape1.bounce(shape2);
            }

            shape1.move(shape1.velocity.scale(this.dt));
            shape1.rotate(shape1.angVel*this.dt);
            if (!shape1.immovable) shape1.velocity.addThis(g.scale(dt));
        }
        propertyChangeSupport.firePropertyChange("particles", 0,1);
    }
}
