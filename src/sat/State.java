
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

    public boolean[] getValues() {
        return values;
    }

    public void setValues(boolean[] values) {
        this.values = values;
    }
}
