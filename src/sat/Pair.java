
package sat;

/**
 * Represents one pair (literal and its negation) in a clause
 * 
 * @author Vit Zdrubecky
 */
public class Pair {
    private final int position;
    private final boolean negation;

    /**
     * Constructor
     * 
     * @param position Literal's position in array
     * @param negation Whether the literal is negated
     */
    public Pair(int position, boolean negation) {
        this.position = position;
        this.negation = negation;
    }

    /**
     * Returns the position
     * 
     * @return Position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Returns the negation flag
     * 
     * @return Negation
     */
    public boolean isNegation() {
        return negation;
    }
    
}
