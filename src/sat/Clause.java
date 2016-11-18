
package sat;

/**
 *
 * @author vitason
 */
public class Clause extends Formula {
    private final Pair[] subFormulaPositions;
    
    public Clause(boolean satisfied, double weight) {
        super(satisfied, weight);
        
        this.subFormulaPositions = new Pair[4];
        
        for(Pair pos : this.subFormulaPositions) {
            
        }
    }
    
    public void solve() {
        this.satisfied = false;
        
        for(Pair pos : this.subFormulaPositions) {
            if(!(pos.isNegation() ^ SAT.literals[pos.getPosition()].isSatisfied())) {
                this.satisfied = true;
            }
        }
    }
}
