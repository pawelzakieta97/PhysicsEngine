package sample;

public class Vector2d {
    double x;
    double y;
    Vector2d(double x, double y){
        this.x = x;
        this.y = y;
    }
    Vector2d(){
        this.x = 0;
        this.y = 0;
    }
    double length(){
        return (double)Math.sqrt(x*x+y*y);
    }
    double angle(){return(double) Math.atan2(y,x);}
    Vector2d add(Vector2d b){
        return new Vector2d(this.x+b.x, this.y+b.y);
    }
    Vector2d sub(Vector2d b){
        return new Vector2d(this.x-b.x, this.y-b.y);
    }
    void addThis(Vector2d b){
        this.x+=b.x;
        this.y+=b.y;
    }
    void subThis(Vector2d b){
        this.x-=b.x;
        this.y-=b.y;
    }
    Vector2d scale(double scale){
        return new Vector2d(this.x*scale, this.y*scale);
    }
    void scaleThis(double scale){
        this.x*=scale;
        this.y*=scale;
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
    double dot(Vector2d v){
        return x*v.x+y*v.y;
    }
    Vector2d rotate(double angle){
        return new Vector2d((double)(x*Math.cos(angle)-y*Math.sin(angle)), (double)(y*Math.cos(angle)+x*Math.sin(angle)));
    }
    Vector2d normalize(){
        return this.scale(1/this.length());
    }
}
