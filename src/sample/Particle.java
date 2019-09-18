package sample;


public class Particle {
    int resX = 800;
    int resY = 600;
    static float radious = 5f;
    static float efficiency = 0.8f;
    Vector2d position;
    //speed is difference in coordinates between frames due to the retardedness of Vector2d implementation
    Vector2d velocity;
    Particle(){
        position = new Vector2d((float)(Math.random()*resX), (float)(Math.random()*resY));
        velocity = new Vector2d((float)(Math.random())-0.5f, (float)(Math.random())-0.5f);
    }
    Particle(Particle p){
        position = new Vector2d(p.position.x, p.position.y);
        velocity = new Vector2d(p.velocity.x, p.velocity.y);
    }
    Particle shift(Vector2d shift){
        Particle p = new Particle(this);
        p.position = p.position.add(shift);
        return p;
    }
    boolean bounce(Particle p){
        Vector2d radialVector = position.sub(p.position);
        if (radialVector.length()>2*radious){
            return false;
        }
        //position = position.add(velocity.scale(-1));
        //p.position = p.position.add(p.velocity.scale(-1));
        Vector2d radialDirection = radialVector.normalize();
        Vector2d parrrarelDirection = radialDirection.rotate((float)Math.PI/2);
        Vector2d v1Parrarel = parrrarelDirection.scale(velocity.dot(parrrarelDirection));
        Vector2d v2Parrarel = parrrarelDirection.scale(p.velocity.dot(parrrarelDirection));
        Vector2d v1Radial = radialDirection.scale(velocity.dot(radialDirection));
        Vector2d v2Radial = radialDirection.scale(p.velocity.dot(radialDirection));
        Vector2d v1RadialNew = v1Radial.add(v2Radial).sub((v1Radial.sub(v2Radial)).scale(efficiency)).scale(0.5f);
        Vector2d v2RadialNew = v2Radial.add(v1Radial).sub((v2Radial.sub(v1Radial)).scale(efficiency)).scale(0.5f);
        velocity=v1Parrarel.add(v1RadialNew);
        p.velocity = v2Parrarel.add(v2RadialNew);
        Vector2d overlapping = radialVector.sub(radialDirection.scale(2*radious));
        position = position.add(overlapping.scale(-0.51f));
        p.position = p.position.add(overlapping.scale(0.51f));
        return true;
    }
}
