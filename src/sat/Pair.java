
package sat;

/**
 *
 * @author vitason
 */
public class Pair {
    private final int position;
    private final boolean negation;

    public Pair(int position, boolean negation) {
        this.position = position;
        this.negation = negation;
    }

    public int getPosition() {
        return position;
    }

    public boolean isNegation() {
        return negation;
    }
    
}
