package sample;

public class Vector2d {
    float x;
    float y;
    Vector2d(float x, float y){
        this.x = x;
        this.y = y;
    }
    Vector2d(){
        this.x = 0;
        this.y = 0;
    }
    float length(){
        return (float)Math.sqrt(x*x+y*y);
    }
    Vector2d add(Vector2d b){
        return new Vector2d(this.x+b.x, this.y+b.y);
    }
    Vector2d sub(Vector2d b){
        return new Vector2d(this.x-b.x, this.y-b.y);
    }
    Vector2d scale(float scale){
        return new Vector2d(this.x*scale, this.y*scale);
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
    float dot(Vector2d v){
        return x*v.x+y*v.y;
    }
    Vector2d rotate(float angle){
        return new Vector2d((float)(x*Math.cos(angle)-y*Math.sin(angle)), (float)(y*Math.cos(angle)+x*Math.sin(angle)));
    }
    Vector2d normalize(){
        return this.scale(1/this.length());
    }
}
