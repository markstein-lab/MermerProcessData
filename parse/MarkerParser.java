package parse;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import feature.Genome;
import feature.Sequence;
import util.Ranges;

public class MarkerParser {
	
	private static final String MARKER_FILE_SEPARATOR = ",, ";


	public static Genome parseMarkerFile(String fileName) {
		BufferedReader fileReader = null;
		//Open file
		try {
			fileReader = new BufferedReader(new FileReader(new File(fileName)));
		} catch(FileNotFoundException e) {
			System.err.printf("Couldn't find marker file: %s!\n", fileName);
			System.exit(-1);
		}
		
		Genome genome = new Genome();

		String regex;
		String line;
		String[] cols;
		String sequenceName;
		long indexStart;
		long indexEnd;
		Sequence sequence;
		try {
			//Parse Regex
			regex = fileReader.readLine();
			while(fileReader.ready()) {
				line = fileReader.readLine();
				cols = line.split(MARKER_FILE_SEPARATOR);
				if(cols.length != 3) {
					System.out.printf("Incorrectly formatted marker line: %s\n", line);
				}
				sequenceName = cols[0].replaceFirst(regex, "");
				if(sequenceName.length() < cols[0].length())
					System.out.printf("Recognized chromosome: %s\n", sequenceName);
				indexStart = Long.parseLong(cols[1]);
				indexEnd = Long.parseLong(cols[2]);
				sequence = new Sequence(sequenceName, indexStart, indexEnd);
				genome.addSequence(sequence);
			}
		} catch (IOException e) {
			System.err.println("Couldnt read marker file!");
			System.err.println(e);
			System.exit(-1);
		}

		return genome;
	}
}