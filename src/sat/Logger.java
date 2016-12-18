
package sat;

import java.io.*;

/**
 *
 * @author vitason
 */
public class Logger {
    public void writeState(int iteration, int satisfiedClauses) {
        OutputStream stream = null;
        
        try {
            File file = new File("log/data.txt");
            stream = new FileOutputStream(file, true);
            String content = Integer.toString(iteration) + "," + Integer.toString(satisfiedClauses) + "\n";
            
            stream.write(content.getBytes());

            stream.flush();
            stream.close();
        } catch(IOException e) {
            System.out.println("An exception has been thrown during the state's logging process.");
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch(IOException e) {
                System.out.println("An exception has been thrown during the stream closing.");
                e.printStackTrace();
            }
        }
    }
    
    public void createPlot() {
        try{
            Runtime.getRuntime().exec("/usr/local/bin/gnuplot log/generate.txt");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
