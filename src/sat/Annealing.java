
package sat;

/**
 *
 * @author vitason
 */
public class Annealing {
    private final int LITERALS_COUNT = 4;
    private final double COOLDOWN_CONSTANT = 0.9;
    private double temperature;
    private final double temperatureFinal;
    private final int equilibrium;
    private SAT sat;
    private Loader loader;
    
    public Annealing() {
        this.temperature = 5;
        this.temperatureFinal = 0.1;    // set slightly above zero to avoid infinite limit convergence
        this.equilibrium = 10;
        this.loader = new Loader(this.LITERALS_COUNT);
        
        SAT.literals = this.loader.createLiteralsArrayHardcoded();
        this.sat = new SAT(false, 0, this.loader.createClausesArrayHardcoded());
    }
    
    public void anneal() {
        do {
            int iteration = 0;
            
            do {
                this.sat.changeState(this.temperature);
                
                iteration++;
            } while(iteration != this.equilibrium);
            
            this.cooldown();
            
            System.out.println("Temperature dropped to " + this.temperature);
        } while(this.temperature > this.temperatureFinal);
        
        this.sat.printBestState();
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
