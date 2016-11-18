
package sat;

/**
 *
 * @author vitason
 */
public abstract class Formula {
    protected boolean satisfied;
    protected double weight;
    
    public Formula(boolean satisfied, double weight) {
        this.satisfied = satisfied;
        this.weight = weight;
    }
    
    public boolean isSatisfied() {
        return this.satisfied;
    }
    
    public double getWeight() {
        return this.weight;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
