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
public class CSVAttributeMissing extends Exception {

	/**
	 * @param in input file
	 * @param out outputfile
	 * @param log error log
	 * @throws IOException
	 */
	public CSVAttributeMissing(File in, File out, File log) throws IOException {
		PrintWriter logger = null;
		try {
			FileWriter fw = new FileWriter(log, true);
			BufferedWriter bw = new BufferedWriter(fw);
			logger = new PrintWriter(bw);
			System.out.println("Attribute was not found, program was ended and newly created file deleted.");
			logger.println("Error: File " + in.getName() + ". Missing attribute. File not converted to html.");

		} catch (FileNotFoundException e) {
			System.out.println("exception file not found, error not logged.");
		} finally {

			logger.close();
			out.delete();
		}

	}
}
