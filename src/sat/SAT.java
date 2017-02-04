
package sat;

import static java.lang.Math.exp;
import java.util.Random;

/**
 * The core class solving the satisfiability problem
 * 
 * @author Vit Zdrubecky
 */
public class SAT extends Formula {

    public static final int LITERALS_COUNT = 1000;
    public static Literal[] literals;
    private final Clause[] clauses;
    private State bestState;
    private int satisfiedClauses;
    
    /**
     * Constructor
     * 
     * @param satisfied Sets the state's default satisfied status
     * @param weight Initial weight
     * @param clauses The array of clauses
     */
    public SAT(boolean satisfied, int weight, Clause[] clauses) {
        super(satisfied, weight);
        
        this.clauses = clauses;
        this.bestState = new State(false, 0);
        this.satisfiedClauses = 0;
    }
    
    /**
     * Returns the current values of literals
     * 
     * @return array of boolean values
     */
    public boolean[] getValues() {
        boolean[] values = new boolean[LITERALS_COUNT];
        
        for(int i = 0; i < LITERALS_COUNT; i++) {
            values[i] = literals[i].isSatisfied();
        }
        
        return values;
    }
    
    /**
     * Tries to move to a new state
     * 
     * @param temperature Current temperature value used during the transition to a worse state
     */
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
        * to the comparison with the best state so far. If it does not win, there might still be a chance
        * to move further with some probability (but if that also fails, the previous state is restored).
        */
        if(this.compareCurrentState(originalSatisfied, originalSatisfiedClauses, originalWeight)) {
            this.saveBestState();
        }
        else {
            int delta = this.weight - originalWeight;
            double random = generator.nextDouble();
            
            if(random > exp(delta / temperature))
            {
                literals[literalPostition].switchValue();
                this.satisfied = originalSatisfied;
                this.satisfiedClauses = originalSatisfiedClauses;
                this.weight = originalWeight;
                System.out.println("Don't move to the new state.");
            }
        }
    }
    
    /**
     * Compare the current state's parameters with the given ones
     * 
     * @param compareSatisfied Whether the compared state is satisfied
     * @param compareSatisfiedClauses The number of compared state's satisfied clauses
     * @param compareWeight The compared state's weight
     */
    private boolean compareCurrentState(boolean compareSatisfied, int compareSatisfiedClauses, int compareWeight) {
        /* There are two possible cases when the current state is considered superior to the compared one:
         * either the current formula is satisfied and the other one is not / has a lower total weight,
         * or both the current and the compared formulas are not satisfied - in that case a cascade of conditions is used to prefer one over the other
         */
        return (this.satisfied && (!compareSatisfied || this.weight > compareWeight)) ||
                (!this.satisfied && !compareSatisfied && (this.satisfiedClauses > compareSatisfiedClauses || (this.satisfiedClauses == compareSatisfiedClauses && this.weight > compareWeight)));
    }
    
    /**
     * Compares the current state with the best and possibly overwrite it
     */
    public void saveBestState() {
        if(this.compareCurrentState(this.bestState.isSatisfied(), this.bestState.getSatisfiedClauses(), this.bestState.getWeight())) {
            this.bestState.setSatisfied(this.satisfied);
            this.bestState.setSatisfiedClauses(this.satisfiedClauses);
            this.bestState.setWeight(this.weight);
            this.bestState.setValues(this.getValues());
        }
    }

    /**
     * Returns the new weight according to the current literal values
     */
    private int calculateTotalWeight() {
        int newWeight = 0;
        
        for(Literal literal : literals) {
            if(literal.isSatisfied()) newWeight += literal.getWeight();
        }
        
        return newWeight;
    }
    
    /**
     * Print the state's attributes to the output
     */
    public void printCurrentState() {
        String dump = "State is satisfied: " + this.satisfied + " with a number of satisfied clauses: "+ this.satisfiedClauses + ", a weight: " + this.weight + " and a configuration:";
        
        for(Literal literal : literals) {
            dump += " " + literal.isSatisfied();
        }
        
        System.out.println(dump);
    }
    
    /**
     * Prints the best state
     */
    public void printBestState() {
        System.out.println("Best state => " + this.bestState);
    }
    
    /**
     * Logs the current state
     */
    public void logCurrentState() {
        Annealing.logger.saveState(this.satisfied, this.satisfiedClauses);
    }
}
