
package sat;

/**
 *
 * @author vitason
 */
public class State extends Formula {
    private boolean[] values;
    
    public State(boolean satisfied, double weight) {
        super(satisfied, weight);
        
        this.values = new boolean[SAT.literals.length];
    }

    @Override
    public String toString() {
        String dump = "Is satisfied: " + this.satisfied + " with a weight: " + this.weight + " and literal values:";
        
        for(boolean value : this.values) {
            dump += " " + value;
        }
        
        return dump;
    }

    public boolean[] getValues() {
        return values;
    }

    public void setValues(boolean[] values) {
        this.values = values;
    }
}
