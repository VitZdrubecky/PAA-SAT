
package sat;

import java.util.Random;

/**
 *
 * @author vitason
 */
public class Loader {
    private final byte CLAUSES_TO_VARS_RATIO;
    private final double MINIMUM_WEIGHT;
    private final double MAXIMUM_WEIGHT;
    private final int literalsCount;
    private final Random generator;
    
    public Loader(int literalsCount) {
        this.CLAUSES_TO_VARS_RATIO = 3;
        this.MINIMUM_WEIGHT = 1;
        this.MAXIMUM_WEIGHT = 10;
        this.literalsCount = literalsCount;
        this.generator = new Random();
    }
    
    public Literal[] createLiteralsArray() {
        Literal[] literals = new Literal[this.literalsCount];
        
        System.out.print("Created literals: ");
        
        for(int i = 0; i < this.literalsCount; i++) {
            literals[i] = this.createLiteral();
            
            System.out.print("position: " + i + ", value: " + literals[i].isSatisfied() + ", weight: " + literals[i].getWeight() + " | ");
        }
        
        System.out.println();
        
        return literals;
    }
    
    public Literal[] createLiteralsArrayHardcoded() {
        Literal[] literals = new Literal[]{
            new Literal(true, 2),
            new Literal(false, 4),
            new Literal(false, 1),
            new Literal(true, 6)
        };
        
        return literals;
    }
    
    private Literal createLiteral() {
        boolean satisfied = this.generator.nextBoolean();
        double weight = this.MINIMUM_WEIGHT + (this.MAXIMUM_WEIGHT - this.MINIMUM_WEIGHT) * this.generator.nextDouble();
        
        Literal literal = new Literal(satisfied, weight);
        
        return literal;
    }
    
    public Clause[] createClausesArray() {
        int clausesCount = this.CLAUSES_TO_VARS_RATIO * this.literalsCount;
        Clause[] clauses = new Clause[clausesCount];
        
        for(int i = 0; i < clausesCount; i++) {
            // how many pairs are gonna be present in this clause (be careful of the result being a zero)
            int pairsCount = this.generator.nextInt(this.literalsCount - 1) + 1;
            Pair[] pairs = new Pair[pairsCount];
            
            for(Pair pair : pairs) {
                pair = this.createClausePair();
            }
            
            clauses[i] = new Clause(false, 0, pairs);
        }
        
        return clauses;
    }
    
    public Clause[] createClausesArrayHardcoded() {
        Clause[] clauses = new Clause[6];
        
        Pair[][] subFormulas = new Pair[6][];
        subFormulas[0] = new Pair[]{new Pair(0, false), new Pair(2, true), new Pair(3, false)};
        subFormulas[1] = new Pair[]{new Pair(0, true), new Pair(1 ,false), new Pair(2, true)};
        subFormulas[2] = new Pair[]{new Pair(2, false), new Pair(3, false)};
        subFormulas[3] = new Pair[]{new Pair(0, false), new Pair(1, false), new Pair(2, true), new Pair(3, true)};
        subFormulas[4] = new Pair[]{new Pair(1, true), new Pair(2, false)};
        subFormulas[5] = new Pair[]{new Pair(2, true), new Pair(3, true)};
        
        for(int i = 0; i < 6; i++) {
            clauses[i] = new Clause(false, 0, subFormulas[i]);
        }
        
        return clauses;
    }
    
    private Pair createClausePair() {
        int position = this.generator.nextInt(this.literalsCount);
        boolean negation = this.generator.nextBoolean();
        
        Pair pair = new Pair(position, negation);
        
        return pair;
    }
}
