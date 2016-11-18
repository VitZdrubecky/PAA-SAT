
package sat;

/**
 *
 * @author vitason
 */
public class Clause extends Formula {
    private final int[] literalsPositions;
    
    public Clause(boolean satisfied, double weight) {
        super(satisfied, weight);
        
        this.literalsPositions = new int[3];
        
        for(int pos : this.literalsPositions) {
            
        }
    }
    
    public void solve() {
        this.setSatisfied(false);
        this.setWeight(0);
        
        for(int pos : this.literalsPositions) {
            if(SAT.literals[pos].isSatisfied()) {
                this.setSatisfied(true);
                this.setWeight(this.weight + SAT.literals[pos].getWeight());
            }
        }
    }
}
