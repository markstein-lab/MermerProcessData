package parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import util.Ranges;

public class SkipParser {

	private static final String SPLIT_FILE_SEPARATOR = ",, ";
	
	Ranges<Long> skipMap;

	public SkipParser(String fileName) {
		BufferedReader fileReader = null;
		//Open file
		try {
			fileReader = new BufferedReader(new FileReader(new File(fileName)));
		} catch(FileNotFoundException e) {
			System.err.printf("Couldn't find skip(.sk) file: %s!\n", fileName);
			System.exit(-1);
		}

		try {
			parseFile(fileReader);
		} catch (IOException e) {
			System.out.println("Couldn't read skip(.sk) file!");
			System.err.println(e);
		}
	}

	private void parseFile(BufferedReader fileReader) throws IOException{
		skipMap = new Ranges<Long>();

		String line;
		String[] bits;
		long start, end;
		long lastEnd = 0;
		long offset = 0;
		while(fileReader.ready()) {
			line = fileReader.readLine();
			bits = line.split(SPLIT_FILE_SEPARATOR);
			start = Long.parseLong(bits[0]);
			end = Long.parseLong(bits[1]);
			skipMap.add(lastEnd, start, offset);	//Will have gaps in the groups of skipped, this is ok because results will never be there
			lastEnd = end;
			offset += end - start;
		}
		// System.out.println(skipMap);
	}

}