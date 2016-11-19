
package sat;

/**
 *
 * @author vitason
 */
public class Clause extends Formula {
    private final Pair[] subFormulas;
    
    public Clause(boolean satisfied, double weight, Pair[] subFormulas) {
        super(satisfied, weight);
        
        this.subFormulas = new Pair[subFormulas.length];
        
        for(int i = 0; i < this.subFormulas.length; i++) {
            this.subFormulas[i] = subFormulas[i];
        }
    }
    
    public void solve() {
        this.satisfied = false;
        
        for(Pair pos : this.subFormulas) {
            if(!(pos.isNegation() ^ SAT.literals[pos.getPosition()].isSatisfied())) {
                this.satisfied = true;
            }
        }
    }
}
