package parse;

import java.util.HashSet;
import java.util.Set;
import feature.Genome;
import util.OverlappingRanges;
import feature.Sequence;
import feature.Gene;
import feature.Transcript;
import feature.Exon;
import feature.Range;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class GTFParser {

	public static Genome parseGTFFile(String fileName) {
		return parseGTFFile(fileName, new Genome());
	}

	public static Genome parseGTFFile(String fileName, Genome genome) {
		BufferedReader fileReader = null;
		boolean sequencesExist = (genome.getSequenceCount() != 0);
		//Open file
		try {
			fileReader = new BufferedReader(new FileReader(new File(fileName)));
		} catch(FileNotFoundException e) {
			System.err.printf("Couldn't find GTF file: %s!\n", fileName);
			System.exit(-1);
		}

		try{
			String line, sequenceName, feature;
			long start, end;
			Sequence sequence;
			String[] cols;
			Set<String> unrecognizedSequences = new HashSet<String>();
			Gene g;
			Transcript t;
			Exon e;
			while(fileReader.ready()) {
				line = fileReader.readLine();
				cols = line.split("\t");
				if(cols.length != 9) {
					System.out.println("GTF file malformed line: " + line);
					continue;
				}
				sequenceName = cols[0];
				feature = cols[2];
				start = Long.parseLong(cols[3]);
				end = Long.parseLong(cols[4]);

				Map<String, String> attributes = parseAttributes(cols[8]);

				if(sequencesExist && !genome.hasSequence(sequenceName)) {
					unrecognizedSequences.add(sequenceName);
				} else {
					sequence = genome.getSequence(sequenceName);
					if(!sequencesExist && sequence == null) {
						sequence = new Sequence(sequenceName);
						genome.addSequence(sequence);
					}

					switch (feature) {
						case "gene" :
							// try {
							sequence.addGene(new Gene(
								attributes.get("gene_name"),
								start,
								end,
								attributes.get("gene_id")));
							// } catch (Exception e) {
							// 	System.out.printf("Gene Name: %s\n", attributes.get("gene_name"));
							// }
						break;
						case "transcript" :
							g = sequence.getGene(attributes.get("gene_id"));
							if(g == null) {
								System.out.printf("Unrecognized Gene: %s\n", attributes.get("gene_id"));
								break;
							}
							g.addTranscript(new Transcript(
									attributes.get("transcript_name"),
									start,
									end,
									attributes.get("transcript_id")));
						break;
						case "exon" :
							g = sequence.getGene(attributes.get("gene_id"));
							if(g == null) {
								System.out.printf("Unrecognized Gene: %s\n", attributes.get("gene_id"));
								break;
							}
							t = g.getTranscript(attributes.get("transcript_id"));
							if(t == null) {
								System.out.printf("Unrecognized Transcript: %s\n", attributes.get("transcript_id"));
								break;
							}
							t.addExon(new Exon(
										Integer.parseInt(attributes.get("exon_number")),
										start,
										end,
										attributes.get("exon_id"),
										Integer.parseInt(attributes.get("exon_version"))));
						break;
						case "CDS" :
						case "UTR" :
						case "start_codon" :
						case "stop_codon" :
							g = sequence.getGene(attributes.get("gene_id"));
							if(g == null) {
								System.out.printf("Unrecognized Gene: %s\n", attributes.get("gene_id"));
								break;
							}
							t = g.getTranscript(attributes.get("transcript_id"));
							if(t == null) {
								System.out.printf("Unrecognized Transcript: %s\n", attributes.get("transcript_id"));
								break;
							}
							if(attributes.get("exon_number") == null) {
								t.addRange(new Range(start, end, feature));
								break;
							}
							e = t.getExon(Integer.parseInt(attributes.get("exon_number")));
							if(e == null) {
								System.out.printf("Unrecognized Exon: %s\n", attributes.get("exon_number"));
								break;
							}
							e.addRange(new Range(start, end, feature));
						break;
						default:
						System.out.println("Unrecognized feature: " + feature);
					}
				}
			}
			if(unrecognizedSequences.size() > 0) {
				System.out.println("Unrecognized gtf sequences (Try adjusting the .mk regex):");
				for(String unrecognizedSequence: unrecognizedSequences) {
					System.out.print(unrecognizedSequence + ", ");
				}
			}
		} catch(IOException e) {
			System.err.println("Error while reading GTF file!");
			System.err.println(e.toString());

		}
		//Close file
		try {
			fileReader.close();
		} catch(IOException e) {
			System.err.println("Couldn't close GTF file!");
		}

		return genome;
	}

	private static Map<String, String> parseAttributes(String attributeString) {
		String[] attributeStrings = removeQuotes(attributeString).split(";");
		Map<String, String> attributes = new HashMap<String, String>();
		String[] pieces;
		for(String attribute: attributeStrings) {
			pieces = attribute.trim().split(" ");
			if(pieces.length != 2) {
				System.out.printf("Malformed attribute: [%s], on attribute String: [%s]\n", attribute, attributeString);
			} else {
				attributes.put(removeQuotes(pieces[0]), removeQuotes(pieces[1].substring(1, pieces[1].length() - 1)));
			}
		}
		return attributes;
	}

	private static String removeQuotes(String s) {
		return s.trim().replaceAll("^\"*|\"*$", "");	//Remove start/end quotes
	}
}