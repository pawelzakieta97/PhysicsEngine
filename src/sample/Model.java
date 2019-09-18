package sample;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Model {
    ArrayList<Particle> particles;
    TreeMap<Particle, Boolean> sortedX;
    TreeMap<Particle, Boolean> sortedY;
    float dt = (float) 0.01;
    float particleMass = 1f;
    int resX = 800;
    int resY = 600;
    float affectRange = 10;
    float gravity = 0.298f;
    int nThreads = 32;
    private PropertyChangeSupport propertyChangeSupport;
    public void addPropertyChangeListener(PropertyChangeListener l){
        propertyChangeSupport.addPropertyChangeListener(l);
    }
    Model(){
        propertyChangeSupport = new PropertyChangeSupport(this);
        Comparator<Particle> xComp = new Comparator<Particle>() {
            @Override
            public int compare(Particle p1, Particle p2) {
                if (p1==p2) return 0;
                if (p1.position.x - p2.position.x>0) return 1;
                else return -1;
            }
        };
        Comparator<Particle> yComp = new Comparator<Particle>() {
            @Override
            public int compare(Particle p1, Particle p2) {
                if (p1==p2) return 0;
                if (p1.position.y - p2.position.y>0) return 1;
                else return -1;
            }
        };
        particles = new ArrayList<Particle>();
        sortedX = new TreeMap<>(xComp);
        sortedY = new TreeMap<>(yComp);
        for (int i=0; i<2000; i++){
            Particle p= new Particle();
            particles.add(p);
            sortedX.put(p, true);
            sortedY.put(p, true);
        }
        ((Particle)particles.toArray()[0]).position = new Vector2d(100,100);
        ((Particle)particles.toArray()[1]).position = new Vector2d(199,100);
        ((Particle)particles.toArray()[0]).velocity = new Vector2d(1,0);
        ((Particle)particles.toArray()[1]).velocity = new Vector2d(-1, 0);

        new Thread(()->{
            while(true) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                update();
            }
        }).start();
    }
    void update(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
        CountDownLatch latch = new CountDownLatch(nThreads);
        int particlesPerThr = particles.size()/nThreads;
        for (int thr = 0; thr < nThreads; thr++) {
            List<Particle> subList = particles.subList(thr*particlesPerThr, (thr+1)*particlesPerThr);
            executor.submit(()->{for (Particle particle : subList) {
                Particle lower = particle.shift(new Vector2d(-affectRange, -affectRange));
                Particle upper = particle.shift(new Vector2d(affectRange, affectRange));
                SortedMap<Particle, Boolean> subx = sortedX.subMap(lower, upper);
                SortedMap<Particle, Boolean> suby = sortedY.subMap(lower, upper);
                ArrayList<Particle> particlesInRange = new ArrayList<>();
                for (Particle p : subx.keySet()) {
                    if (suby.containsKey(p)) {
                        particlesInRange.add(p);
                    }
                }
                Object[] particlesInRange2 = particles.toArray();
                Vector2d acceleration = new Vector2d(0, gravity);//.sub(particle.velocity.scale(0.01f));
                if (Math.abs(particle.position.x - resX / 2) < 30 && resY - particle.position.y < 100) {
                    acceleration = acceleration.add(new Vector2d(0, -1.9f));
                    //particle.velocity.x=0;
                }
                for (Object p2 : particlesInRange) {
                    if (p2 == particle) {
                        continue;
                    }
                    Vector2d temp = particle.position.sub(((Particle) p2).position);
                    float len = temp.length();
                    if (len < 1) {
                        continue;
                    }
                    temp = temp.scale(1 / temp.length() / temp.length() / particleMass);
                    //acceleration = acceleration.add(temp);
                    particle.bounce((Particle) p2);
                }
                particle.velocity = particle.velocity.add(acceleration);
            }
            latch.countDown();});

        }
        try {
            latch.await();
        } catch (InterruptedException e) {}
        executor.shutdown();
        for (Particle particle: particles) {
            sortedX.remove(particle);
            sortedY.remove(particle);
            particle.position = particle.position.add(particle.velocity);
            sortedX.put(particle, true);
            sortedY.put(particle, true);
            if (particle.position.x < 0) {
                particle.velocity.x = -particle.velocity.x;
                particle.position.x = 0;
            }
            if (particle.position.x > resX-10) {
                particle.velocity.x = -particle.velocity.x;
                particle.position.x = resX-10;
            }
            if (particle.position.y < 10) {
                particle.velocity.y = -particle.velocity.y;
                particle.position.y = 10;
            }
            if (particle.position.y > resY-10) {
                particle.velocity.y = -particle.velocity.y;
                particle.position.y = resY-10;
            }
        }
        propertyChangeSupport.firePropertyChange("particles", 0,1);
    }
}