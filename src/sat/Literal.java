
package sat;

/**
 *
 * @author vitason
 */
public class Literal extends Formula {
    public Literal(boolean value, double weight) {
        super(value, weight);
    }

    public void switchValue() {
        this.satisfied ^= true;
    }
    
}
