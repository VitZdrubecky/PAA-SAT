
package sat;

/**
 * A state with its defining parameters
 * 
 * @author Vit Zdrubecky
 */
public class State extends Formula {
    private boolean[] values;
    private int satisfiedClauses;
    
    /**
     * Constructor
     * 
     * @param satisfied Whether the state is satisfied
     * @param weight Total weight
     */
    public State(boolean satisfied, int weight) {
        super(satisfied, weight);
        
        this.values = new boolean[SAT.literals.length];
        this.satisfiedClauses = 0;
    }

    /**
     * Get the number of satisfied clauses
     * 
     * @return The required number
     */
    public int getSatisfiedClauses() {
        return satisfiedClauses;
    }

    /**
     * Sets the number of satisfied clauses
     * 
     * @param satisfiedClauses The number of clauses that are satisfied
     */
    public void setSatisfiedClauses(int satisfiedClauses) {
        this.satisfiedClauses = satisfiedClauses;
    }

    /**
     * Returns the literal values
     * 
     * @return Array of boolean values
     */
    public boolean[] getValues() {
        return values;
    }

    /**
     * Sets the literal values
     * 
     * @param values Array of boolean values
     */
    public void setValues(boolean[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        String dump = "is satisfied: " + this.satisfied + " with a number of satisfied clauses: " + this.satisfiedClauses + ", a weight: " + this.weight + " and a configuration:";
        
        for(boolean value : this.values) {
            dump += " " + value;
        }
        
        return dump;
    }
}
