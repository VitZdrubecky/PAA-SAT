
package sat;

/**
 * One literal, able to switch its value
 * 
 * @author Vit Zdrubecky
 */
public class Literal extends Formula {
    public Literal(boolean value, int weight) {
        super(value, weight);
    }

    /**
     * XOR the current value with "true" and therefore invert it
     */
    public void switchValue() {
        this.satisfied ^= true;
    }
    
}
