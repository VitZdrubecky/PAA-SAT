
package sat;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author vitason
 */
public class Logger {
    private ArrayList satisfiedArray;
    private ArrayList clausesArray;
    
    public Logger() {
        this.satisfiedArray = new ArrayList();
        this.clausesArray = new ArrayList();
    }
    
    public void saveState(boolean satisfied, int clauses) {
        this.satisfiedArray.add(satisfied);
        this.clausesArray.add(clauses);
    }
    
    public void writeData() {
        OutputStream satisfiedStream = null;
        OutputStream clausesStream = null;
        
        try {
            File satisfiedFile = new File("log/data_satisfied.txt");
            File clausesFile = new File("log/data_clauses.txt");
            
            // open the outpout streams without the need to append anything
            satisfiedStream = new FileOutputStream(satisfiedFile, false);
            clausesStream = new FileOutputStream(clausesFile, false);
            
            // set the default content strings
            String satisfiedData = "";
            String clausesData = "";
            
            // concatenate the values one by one
            for(int i = 0; i < this.satisfiedArray.size(); i++) {
                satisfiedData += Integer.toString(i + 1) + "," + this.satisfiedArray.get(i).toString() + "\n";
                clausesData += Integer.toString(i + 1) + "," + this.clausesArray.get(i).toString() + "\n";
            }
            
            // write the data
            satisfiedStream.write(satisfiedData.getBytes());
            clausesStream.write(clausesData.getBytes());

            // flush any buffered data immediately
            satisfiedStream.flush();
            clausesStream.flush();
            
            // close the streams
            satisfiedStream.close();
            clausesStream.close();
        } catch(IOException e) {
            System.out.println("An exception has been thrown during the state's logging process.");
            e.printStackTrace();
        } finally {
            // if something went wrong, close any open streams
            try {
                if (satisfiedStream != null) {
                    satisfiedStream.close();
                }
                
                if (clausesStream != null) {
                    clausesStream.close();
                }
            } catch(IOException e) {
                System.out.println("An exception has been thrown during the stream closing.");
                e.printStackTrace();
            }
        }
    }
    
    public void createPlot() {
        try{
            Runtime.getRuntime().exec("/usr/local/bin/gnuplot log/generate_satisfied_plot.txt");
            Runtime.getRuntime().exec("/usr/local/bin/gnuplot log/generate_clauses_plot.txt");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
