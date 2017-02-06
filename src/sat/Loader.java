
package sat;

import java.util.Random;

/**
 * Loads the literals and clauses used for computations (either hardcoded or randomly generated)
 * 
 * @author Vit Zdrubecky
 */
public class Loader {
    private final byte CLAUSES_TO_VARS_RATIO = 3;
    private final int MAXIMUM_WEIGHT = 100;
    private final int MAXIMUM_CLAUSE_SIZE = SAT.LITERALS_COUNT / 5;
    private final Random generator;
    
    /**
     * Constructor
     */
    public Loader() {
        this.generator = new Random();
    }
    
    /**
     * Creates the literals one by one using the dedicated method
     * 
     * @return Array of literals
     */
    public Literal[] createLiteralsArray() {
        Literal[] literals = new Literal[SAT.LITERALS_COUNT];
        
        System.out.print("Created literals => ");
        
        for(int i = 0; i < SAT.LITERALS_COUNT; i++) {
            literals[i] = this.createLiteral();
            
            System.out.print("position: " + i + ", value: " + literals[i].isSatisfied() + ", weight: " + literals[i].getWeight() + " | ");
        }
        
        System.out.println();
        
        return literals;
    }
    
    /**
     * Default testing array of four literals, used as a measure of the initial program's functions
     * 
     * @return Array of literals
     */
    public Literal[] createLiteralsArrayHardcoded() {
        Literal[] literals = new Literal[]{
            new Literal(true, 2),
            new Literal(false, 4),
            new Literal(false, 1),
            new Literal(true, 6)
        };
        
        return literals;
    }
    
    /**
     * Randomly creates one literal object
     * 
     * @return Literal
     */
    private Literal createLiteral() {
        boolean satisfied = this.generator.nextBoolean();
        int weight = this.generator.nextInt(this.MAXIMUM_WEIGHT - 1) + 1;
        
        Literal literal = new Literal(satisfied, weight);
        
        return literal;
    }
    
    /**
     * Creates an array of clauses based on the arrays of pairs
     * 
     * @return Array of clauses
     */
    public Clause[] createClausesArray() {
        int clausesCount = this.CLAUSES_TO_VARS_RATIO * SAT.LITERALS_COUNT;
        Clause[] clauses = new Clause[clausesCount];
        
        System.out.print("Generated formula => ");
        
        for(int i = 0; i < clausesCount; i++) {
            // how many pairs are gonna be present in this clause (be careful of the result being a zero)
            int pairsCount = this.generator.nextInt(this.MAXIMUM_CLAUSE_SIZE - 1) + 1;
            Pair[] pairs = new Pair[pairsCount];
            boolean[] positionsFilled = new boolean[SAT.LITERALS_COUNT];
            
            System.out.print("(");
                    
            for(int j = 0; j < pairsCount; j++) {
                pairs[j] = this.createClausePair(positionsFilled);
                
                System.out.print("x" + (pairs[j].getPosition() + 1));
                if(pairs[j].isNegation()) System.out.print("'");
                if(j < pairsCount - 1) System.out.print("+");
            }
            
            System.out.print(")");
            
            clauses[i] = new Clause(false, 0, pairs);
        }
        
        System.out.println();
        
        return clauses;
    }
    
    /**
     * Default hardcoded clauses for testing purposes
     * 
     * @return Array of clauses
     */
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
    
    /**
     * Creates one pair for a clause
     * 
     * @param positionsFilled An array of currently filled literal positions so that it can't be created again
     * 
     * @return A pair of a literal's position and negation parameters
     */
    private Pair createClausePair(boolean[] positionsFilled) {
        // the literals in a clause have to be unique, so keep on generating random positions until an empty one is found
        int position;
        
        do {
            position = this.generator.nextInt(SAT.LITERALS_COUNT);
        } while(positionsFilled[position]);
        
        // the free spot has been found - mark it as occupied
        positionsFilled[position] = true;
        
        boolean negation = this.generator.nextBoolean();
        
        Pair pair = new Pair(position, negation);
        
        return pair;
    }
}
