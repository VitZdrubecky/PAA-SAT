
package sat;

import java.util.Random;

/**
 *
 * @author vitason
 */
public class SAT extends Formula { 
    public static Literal[] literals;
    private final int literalsCount;
    private final int clausesCount;
    private Clause[] clauses;
    private State bestState;
    
    public SAT(boolean satisfied, double weight) {
        super(satisfied, weight);
        
        this.literalsCount = 3;
        this.clausesCount = 3;
        this.clauses = new Clause[clausesCount];
        this.bestState = new State(false, 0);
    }
    
    public boolean[] getValues() {
        boolean[] values = new boolean[this.literalsCount];
        
        for(int i = 0; i < literals.length; i++) {
            values[i] = literals[i].isSatisfied();
        }
        
        return values;
    }
    
    public void changeState() {
        Random generator = new Random();
        int literalPostition = generator.nextInt(this.literalsCount);
        
        literals[literalPostition].switchValue();
        
        this.setSatisfied(true);
        this.setWeight(0);
        
        for(Clause clause : this.clauses) {
            clause.solve();
            
            if(!clause.isSatisfied()) this.setSatisfied(false);
            this.setWeight(this.weight + clause.getWeight());
        }
    }
    
    public void compareBestState() {
        /* There are two possible cases when it's necessary to set the new best state:
        * either the current formula is satisfied and the best one so far is not / has a lower weight,
        * or both the current and the best are not satisfied, but the current has a higher weight.
        */
        if((this.satisfied && (!this.bestState.isSatisfied() || this.weight > this.bestState.getWeight())) ||
                (!this.satisfied && !this.bestState.isSatisfied() && this.weight > this.bestState.getWeight())) {
            this.bestState.setSatisfied(this.satisfied);
            this.bestState.setWeight(this.weight);
            this.bestState.setValues(this.getValues());
        }
    }
}
