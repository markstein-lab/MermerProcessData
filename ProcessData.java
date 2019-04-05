import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import feature.Genome;
import feature.Sequence;
import output.*;
import parse.*;
import result.*;
import util.OverlappingRanges;




class ProcessData {

	public static void main(String[] args) {
	    
		if(args.length < 1) {
			System.out.println("Invalid arguments! Usage: java ProcessData [GTFFile.gtf] [QUERY.json]");
			System.exit(-1);
		}

		System.out.println("Parsing files...");
		Genome g = GTFParser.parseGTFFile(args[0]);
		System.out.println("Parsed");
		
		
		//TODO: deserialize query with gson
		
		String motif = "GAGAGA";			// Test inputs		
		long[] seqInd = {0, 100, 1000, 10000}; 
		
		List<Cluster> originalClusters = ClusterCollection.generateClusters(g, seqInd, motif);	// keep copy of original clusters
		
	
		
		Map<String, String[]> conditionMap = new HashMap<String, String[]>();		// Test filter conditions
		
		conditionMap.put("Restrict", new String[]{"EXON", "INTRON", "CDS", "URT5", "ORF"});
		conditionMap.put("ChromosomeId", new String[] {"3", "X"});
		conditionMap.put("GeneId", new String[] {"ABCB7"});
		
		
		List<Cluster> clusters = new ArrayList<Cluster>(originalClusters);
		ClusterCollection.filterClusters(clusters, conditionMap);
		
		
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter("Output1.json"));
			
			//TODO: use Gson on clusters
			
//			GsonBuilder builder = new GsonBuilder();
//			
//			builder.setExclusionStrategies(new ExclusionStrategy() {
//    			    @Override
//    			    public boolean shouldSkipClass(Class<?> c) {
//    				return c == List.class || c == OverlappingRanges.class;
//    			    }
//    
//    			    @Override
//    			    public boolean shouldSkipField(FieldAttributes arg0) {
//    				return false;
//    			    }
//    			});
//			
//			Gson gson = builder.create();
			
			output.write(new Gson().toJson(g));
			//output.write(g.toString());
			
			System.out.println(output);
			
			output.close();
			System.out.println("Output.json written");
		} catch (IOException e) {
			System.out.println("Couldn't write ouput file!");
		}
	}
}