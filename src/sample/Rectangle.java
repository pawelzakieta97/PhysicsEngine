package sample;

import java.util.ArrayList;

public class Rectangle extends Shape {
    Rectangle(double x, double y, double width, double height){
        super(new ArrayList<>());
        ArrayList<Vector2d> points = new ArrayList<>();
        points.add(new Vector2d(x-width/2,y-height/2));
        points.add(new Vector2d(x+width/2,y-height/2));
        points.add(new Vector2d(x+width/2,y+height/2));
        points.add(new Vector2d(x-width/2,y+height/2));
        updateShape(points);
    }
}
