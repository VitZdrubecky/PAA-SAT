
package sat;

import java.util.concurrent.TimeUnit;

/**
 * The main class representing the annealing process
 * 
 * @author Vit Zdrubecky
 */
public class Annealing {
    private final double COOLDOWN_CONSTANT = 0.92;
    private final double TEMPERATURE_INITIAL = 500;
    private final double TEMPERATURE_FINAL = 1;   // set slightly above zero to avoid infinite limit convergence
    private final int EQUILIBRIUM = 150;
    private double temperature;
    private SAT sat;
    private Loader loader;
    public static final Logger logger = new Logger();
    
    /**
     * Constructor, initializing the data
     */
    public Annealing() {
        this.temperature = TEMPERATURE_INITIAL;
        this.loader = new Loader();
        
        SAT.literals = this.loader.createLiteralsArray();
        this.sat = new SAT(false, 0, this.loader.createClausesArray());
    }
    
    /**
     * Runs the annealing algorithm and measures its running time
     */
    public void anneal() {
        long endTime;
        long startTime = System.nanoTime();
        
        do {
            int step = 0;
            
            // repeat until the equilibrium is reached
            do {
                // try to change the state and log the result
                this.sat.changeState(this.temperature);
                this.sat.logCurrentState();
                
                step++;
            } while(step != this.EQUILIBRIUM);
            
            this.cooldown();
        } while(this.temperature > this.TEMPERATURE_FINAL);
        
        endTime = System.nanoTime();
        
        this.sat.printBestState();
        
        System.out.println("Total annealing time: " + TimeUnit.NANOSECONDS.toMillis(endTime - startTime) + " ms");
        
        logger.writeData();
        logger.createPlot();
    }
    
    /**
     * Drops the temperature by the predefined coefficient
     */
    private void cooldown() {
        this.temperature *= this.COOLDOWN_CONSTANT;
        
        System.out.println("Temperature dropped to " + this.temperature);
    }
    
    /**
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        Annealing annealing = new Annealing();
        
        annealing.anneal();
    }
}
