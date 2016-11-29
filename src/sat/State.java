
package sat;

/**
 *
 * @author vitason
 */
public class State extends Formula {
    private boolean[] values;
    private int satisfiedClauses;
    
    public State(boolean satisfied, int weight) {
        super(satisfied, weight);
        
        this.values = new boolean[SAT.literals.length];
        this.satisfiedClauses = 0;
    }

    public int getSatisfiedClauses() {
        return satisfiedClauses;
    }

    public void setSatisfiedClauses(int satisfiedClauses) {
        this.satisfiedClauses = satisfiedClauses;
    }

    public boolean[] getValues() {
        return values;
    }

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
