
package sat;

/**
 *
 * @author vitason
 */
public abstract class Formula {
    protected boolean satisfied;
    protected int weight;
    
    public Formula(boolean satisfied, int weight) {
        this.satisfied = satisfied;
        this.weight = weight;
    }
    
    public boolean isSatisfied() {
        return this.satisfied;
    }
    
    public int getWeight() {
        return this.weight;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
