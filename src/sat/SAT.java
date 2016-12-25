
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
    private int satisfiedClauses;
    
    public SAT(boolean satisfied, int weight, Clause[] clauses) {
        super(satisfied, weight);
        
        this.clauses = clauses;
        this.bestState = new State(false, 0);
        this.satisfiedClauses = 0;
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
        int originalSatisfiedClauses = this.satisfiedClauses;
        int originalWeight = this.weight;
        
        literals[literalPostition].switchValue();
        
        // Reset the metrics
        this.satisfied = false;
        this.weight = 0;
        this.satisfiedClauses = 0;
        
        // Calculate the satisfiability, firstly for all the clauses and then for the whole formula
        for(Clause clause : this.clauses) {
            clause.solve();
            
            if(clause.isSatisfied()) this.satisfiedClauses++;
        }
        
        if(this.satisfiedClauses == this.clauses.length) this.satisfied = true;
        
        // The cost function which serves as an additional metric in case two configurations satisfy the formula
        this.weight = this.calculateTotalWeight();
        
        this.printCurrentState();
        
        /* If the new state wins in comparison with the old one, the transition is made and we go straight
        * to the comparison with the best state so far. If it does not, there might still be a chance
        * to move further with some probability (but if that also fails, the previous state is restored).
        */
        if(this.compareCurrentState(originalSatisfied, originalSatisfiedClauses, originalWeight)) {
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
    
    private boolean compareCurrentState(boolean compareSatisfied, int compareSatisfiedClauses, int compareWeight) {
        /* There are two possible cases when the current state is considered superior to the compared one:
         * either the current formula is satisfied and the other one is not / has a lower number of satisfied clauses / has the same number of aforementioned clauses but a lower weight,
         * or both the current and the compared are not satisfied but all of the following conditions hold.
         */
        return (this.satisfied && (!compareSatisfied || this.satisfiedClauses > compareSatisfiedClauses || (this.satisfiedClauses == compareSatisfiedClauses && this.weight > compareWeight))) ||
                (!this.satisfied && !compareSatisfied && (this.satisfiedClauses > compareSatisfiedClauses || (this.satisfiedClauses == compareSatisfiedClauses && this.weight > compareWeight)));
    }
    
    public void saveBestState() {
        if(this.compareCurrentState(this.bestState.isSatisfied(), this.bestState.getSatisfiedClauses(), this.bestState.getWeight())) {
            this.bestState.setSatisfied(this.satisfied);
            this.bestState.setSatisfiedClauses(this.satisfiedClauses);
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
        String dump = "State is satisfied: " + this.satisfied + " with a number of satisfied clauses: "+ this.satisfiedClauses + ", a weight: " + this.weight + " and a configuration:";
        
        for(Literal literal : literals) {
            dump += " " + literal.isSatisfied();
        }
        
        System.out.println(dump);
    }
    
    public void printBestState() {
        System.out.println("Best state => " + this.bestState);
    }
    
    public void logCurrentState() {
        Annealing.logger.saveState(this.satisfied, this.satisfiedClauses);
    }
}
