import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//Huzaifah Mirza 40136913
//COMP249 
//Assignment #3
//March 25, 2022

/**
 * @author Huzaifah Mirza
 *
 */
public class CSVDataMissing extends Exception {
	
	/**
	 * @param in input file 
	 * @param log error log
	 * @param attr missing attribute name
	 * @param line line number
	 * @throws IOException
	 */
	public CSVDataMissing(File in, File log, String attr, int line) throws IOException {
		PrintWriter logger = null;
		try {
			FileWriter fw = new FileWriter(log, true);
			BufferedWriter bw = new BufferedWriter(fw);
			logger = new PrintWriter(bw);
			System.out.println("Error: Input row cannot be parsed due to missing information");
			logger.println("Warning: File " + in.getName() + " line " + line
					+ " is not converted to html: missing data:" + attr);

		} catch (FileNotFoundException e) {
			System.out.println("exception file not found, error not logged.");
		} finally {
			logger.close();

		}

	}

}
