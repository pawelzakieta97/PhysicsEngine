package sample;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Simulation {
    PriorityQueue<Particle> particles;
    Simulation(int numberOfParticles){
        for (int i=0; i<numberOfParticles; i++){
            particles.add(new Particle());
        }
    }
}
