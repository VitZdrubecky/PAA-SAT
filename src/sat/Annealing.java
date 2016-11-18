
package sat;

/**
 *
 * @author vitason
 */
public class Annealing {
    private final double COOLDOWN_CONSTANT = 0.9;
    private double temperature;
    private double temperatureFinal;
    private int equilibrium;
    private SAT sat;
    
    public Annealing() {
        this.temperature = 10;
        this.temperatureFinal = 0;
        this.equilibrium = 5;
        
        Literal literal1 = new Literal(false, 5);
        Literal literal2 = new Literal(false, 4);
        Literal literal3 = new Literal(false, 6);
        Literal literal4 = new Literal(false, 1);
        
        SAT.literals = new Literal[]{literal1, literal2, literal3, literal4};
        this.sat = new SAT(false, 0);
    }
    
    public void anneal() {
        do {
            int iteration = 0;
            
            do {
                this.sat.changeState();
                this.sat.compareBestState();
                
                iteration++;
            } while(iteration != this.equilibrium);
            
            this.cooldown();
        } while(this.temperature != this.temperatureFinal);
    }
    
    private void cooldown() {
        this.temperature *= this.COOLDOWN_CONSTANT;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
