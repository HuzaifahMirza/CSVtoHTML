
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//Huzaifah Mirza 40136913
//COMP249 
//Assignment #3
//March 25, 2022

/**
 * @author Huzaifah Mirza
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 * @throws CSVAttributeMissing
	 * @throws CSVDataMissing
	 */
	public static void main(String[] args) throws FileNotFoundException, CSVAttributeMissing, CSVDataMissing {
		Scanner in = null;
		Scanner in2 = null;
		Scanner input = new Scanner(System.in);
		try {
			in = new Scanner(new FileInputStream("covidStatistics.csv"), "UTF-8");
			System.out.println("covidStatistics.csv opened successfully.");

		}

		catch (IOException e) {
			System.out.println("Could not open input file 'covidStatistics.csv' for reading. "
					+ "Please check that the file exists and is readable. "
					+ "This program will terminate after closing any opened files.");

			System.exit(0);
		}

		try {
			in2 = new Scanner(new FileInputStream("doctorList.csv"), "UTF-8");
			System.out.println("doctorList.csv opened successfully.");

		}

		catch (IOException e) {
			System.out.println("Could not open input file 'doctorList.csv' for reading. "
					+ "Please check that the file exists and is readable. "
					+ "This program will terminate after closing any opened files.");
			System.exit(0);
		}
		File dlhtml = new File("doctorList.html");
		File cshtml = new File("covidStatistics.html");
		File xcept = new File("Exceptions.log");
		try {
			if (dlhtml.createNewFile() && cshtml.createNewFile() && xcept.createNewFile()) {
				System.out.println("doctorList.html, covidStatistics.html and Exception.log created.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		in.close();
		in2.close();
		System.out.println(
				"if you would like to convert these 2 csv files to html, please press enter. Otherwise type the csv filename you would like to convert.");
		String btn = input.nextLine();
		if (btn.isEmpty()) {

			File cs = new File("covidStatistics.csv");
			File dl = new File("doctorList.csv");
			CSVtoHTML(cs, cshtml, xcept);
			CSVtoHTML(dl, dlhtml, xcept);
		}

		else if (!(btn.isEmpty())) {
			try {
				File csv = new File(btn);
				if (csv.exists()) {
					int i = btn.lastIndexOf(".");
					String newfile = btn.substring(0, i) + ".html";
					System.out.println(newfile);
					File html = new File(newfile);
					CSVtoHTML(csv, html, xcept);

				}

				else {
					throw new FileNotFoundException();
				}
			} catch (FileNotFoundException e) {
				System.out.println("This file does not exist. program is ending, run program again to convert a file.");
				e.getMessage();
			}
		}

	}

	/**
	 * @param x   input file
	 * @param out outputfile
	 * @param log error log
	 * @throws CSVAttributeMissing   attribute missing from csv file
	 * @throws CSVDataMissing        data missing from csv file
	 * @throws FileNotFoundException
	 */
	public static void CSVtoHTML(File x, File out, File log)
			throws CSVAttributeMissing, CSVDataMissing, FileNotFoundException {
		boolean isComplete = false;
		Scanner input = new Scanner(System.in);
		try {
			PrintWriter output = new PrintWriter(out);
			Scanner in = new Scanner(new FileInputStream(x), "UTF-8");

			// metadata
			output.println("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<style>\r\n"
					+ "table {font-family: arial, sans-serif;border-collapse: collapse;}\r\n"
					+ "td, th {border: 1px solid #000000;text-align: left;padding: 8px;}\r\n"
					+ "tr:nth-child(even) {background-color: #dddddd;}\r\n" + "span{font-size: small}\r\n"
					+ "</style>\r\n" + "<body>\r\n" + "\r\n" + "<table>\n");
			// caption
			String line = in.nextLine();
			line = line.replaceAll(",", "");
			line = line.replace("?", "");
			output.println("<caption>" + line.substring(1) + "</caption>");

			// header tags
			String[] ln = in.nextLine().split(",");
			String[] headertags = ln;
			try {

				if (ln.length < 4) {
					output.close();
					in.close();
					throw new CSVAttributeMissing(x, out, log);

				}
				output.println("<tr>");

				for (int i = 0; i < ln.length; i++) {
					if (ln[i].isEmpty()) {
						output.close();
						in.close();
						throw new CSVAttributeMissing(x, out, log);

					}
					output.print("<th>");
					output.print(ln[i]);
					output.println("</th>");

				}
				output.println("</tr>");
			}

			catch (CSVAttributeMissing e) {
				System.exit(0);
			}

			// data entry

			while (in.hasNextLine()) {
				isComplete = true;
				int l = 1;
				line = in.nextLine();
				String[] lastln = line.split(" ");

				if (lastln[0].equals("Note:")) {
					line = line.replaceAll(",", "");
					output.println("</table>");
					output.println("<span>" + line + "</span>");
				} else {
					ln = line.split(",");

					try {
						for (int i = 0; i < ln.length; i++) {

							if (ln.length < 4) {
								isComplete = false;
								String attr = headertags[3];
								throw new CSVDataMissing(x, log, attr, l);

							} else if (ln[i].isEmpty()) {
								isComplete = false;
								String attr = headertags[i];

								throw new CSVDataMissing(x, log, attr, l);
							}

						}

						if (isComplete) {
							output.println("<tr>");
							for (int i = 0; i < ln.length; i++) {

								output.print("<td>");
								output.print(ln[i]);
								output.println("</td>");

							}
							output.println("</tr>");
							l++;
						}

					} catch (CSVDataMissing e) {
						continue;

					}

				}

			}
			output.println("</body>\r\n" + "</html>");
			System.out.println(out.getName() + " was created successfully.");

			in.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("press enter to open the html file");
		String btn = input.nextLine();
		String line;

		FileReader file = new FileReader(out);
		BufferedReader read = new BufferedReader(file);
		try {
			while ((line = read.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
