
package sat;

import static java.lang.Math.exp;
import java.util.Random;

/**
 *
 * @author vitason
 */
public class SAT extends Formula {
    public static final int LITERALS_COUNT = 10;
    public static Literal[] literals;
    private final Clause[] clauses;
    private State bestState;
    
    public SAT(boolean satisfied, int weight, Clause[] clauses) {
        super(satisfied, weight);
        
        this.clauses = clauses;
        this.bestState = new State(false, 0);
    }
    
    public boolean[] getValues() {
        boolean[] values = new boolean[LITERALS_COUNT];
        
        for(int i = 0; i < LITERALS_COUNT; i++) {
            values[i] = literals[i].isSatisfied();
        }
        
        return values;
    }
    
    public void changeState(double temperature) {
        // Generate the random literal's position
        Random generator = new Random();
        int literalPostition = generator.nextInt(LITERALS_COUNT);
        
        // Save the current values
        boolean originalSatisfied = this.satisfied;
        int originalWeight = this.weight;
        
        literals[literalPostition].switchValue();
        
        // Reset the metrics
        this.satisfied = true;
        this.weight = 0;
        
        // Calculate the satisfiability and the cost function
        for(Clause clause : this.clauses) {
            clause.solve();
            
            if(!clause.isSatisfied()) this.satisfied = false;
        }
        
        this.weight = this.calculateTotalWeight();
        
        this.printCurrentState();
        
        /* If the new state wins in comparison with the old one, the transition is made and we go straight
        * to the comparison with the best state so far. If it does not, there might still be a chance
        * to move further with some probability (but if that also fails, the previous state is restored).
        */
        if(this.compareCurrentState(originalSatisfied, originalWeight)) {
            this.saveBestState();
        }
        else {
            int delta = this.weight - originalWeight;
            double random = generator.nextDouble();
            
            if(random < exp(delta / temperature))
            {
                literals[literalPostition].switchValue();
                this.satisfied = originalSatisfied;
                this.weight = originalWeight;
            }
        }
    }
    
    private boolean compareCurrentState(boolean compareSatisfied, int compareWeight) {
        /* There are two possible cases when the current state is considered superior to the best one yet:
        * either the current formula is satisfied and the best one so far is not / has a lower weight,
        * or both the current and the best are not satisfied, but the current has a higher weight.
        */
        if((this.satisfied && (!compareSatisfied || this.weight > compareWeight)) ||
                (!this.satisfied && !compareSatisfied && this.weight > compareWeight)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public void saveBestState() {
        if(this.compareCurrentState(this.bestState.isSatisfied(), this.bestState.getWeight())) {
            this.bestState.setSatisfied(this.satisfied);
            this.bestState.setWeight(this.weight);
            this.bestState.setValues(this.getValues());
        }
    }

    private int calculateTotalWeight() {
        int newWeight = 0;
        
        for(Literal literal : literals) {
            if(literal.isSatisfied()) newWeight += literal.getWeight();
        }
        
        return newWeight;
    }
    
    public void printCurrentState() {
        String dump = "State is satisfied: " + this.satisfied + " with a weight: " + this.weight + " and a configuration:";
        
        for(Literal literal : literals) {
            dump += " " + literal.isSatisfied();
        }
        
        System.out.println(dump);
    }
    
    public void printBestState() {
        System.out.println("Best state => " + this.bestState);
    }
}
