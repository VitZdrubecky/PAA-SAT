
package sat;

/**
 *
 * @author vitason
 */
public class Annealing {
    private final double COOLDOWN_CONSTANT = 0.9;
    private final double TEMPERATURE_FINAL = 0.1;   // set slightly above zero to avoid infinite limit convergence
    private final int EQUILIBRIUM = 10;
    private double temperature;
    private SAT sat;
    private Loader loader;
    public static final Logger logger = new Logger();
    
    public Annealing() {
        this.temperature = 5;
        this.loader = new Loader();
        
        SAT.literals = this.loader.createLiteralsArray();
        this.sat = new SAT(false, 0, this.loader.createClausesArray());
    }
    
    public void anneal() {
        do {
            int step = 0;
            
            do {
                this.sat.changeState(this.temperature);
                this.sat.logCurrentState();
                
                step++;
            } while(step != this.EQUILIBRIUM);
            
            this.cooldown();
            
            System.out.println("Temperature dropped to " + this.temperature);
        } while(this.temperature > this.TEMPERATURE_FINAL);
        
        this.sat.printBestState();
        logger.writeData();
        logger.createPlot();
    }
    
    private void cooldown() {
        this.temperature *= this.COOLDOWN_CONSTANT;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Annealing annealing = new Annealing();
        
        annealing.anneal();
    }
}
