
package sat;

/**
 * A clause with all its subformulas
 * 
 * @author Vit Zdrubecky
 */
public class Clause extends Formula {
    private final Pair[] subFormulas;
    
    /**
     * Constructor
     * 
     * @param satisfied Initial satisfied flag
     * @param weight Default weight
     * @param subFormulas Array of pairs
     */
    public Clause(boolean satisfied, int weight, Pair[] subFormulas) {
        super(satisfied, weight);
        
        this.subFormulas = new Pair[subFormulas.length];
        
        for(int i = 0; i < this.subFormulas.length; i++) {
            this.subFormulas[i] = subFormulas[i];
        }
    }
    
    /**
     * Checks whether the clause is satisfied or not
     */
    public void solve() {
        this.satisfied = false;
        
        for(Pair pos : this.subFormulas) {
            // just one positive literal is sufficient for the clause to be satisfied
            if(pos.isNegation() ^ SAT.literals[pos.getPosition()].isSatisfied()) {
                this.satisfied = true;
                
                break;
            }
        }
    }
}
