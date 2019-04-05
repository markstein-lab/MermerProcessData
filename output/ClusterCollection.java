package output;

import feature.Gene;
import feature.Genome;
import feature.Sequence;
import util.JSONHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


// TODO: Currently cluster size is set to be always 1. Implement clustering

public class ClusterCollection {

    /**
     * Generates cluster objects from the parsed gtf object and the search results.
     * 
     * TODO: clusters based on window size, categorizing clusters based on where they were found (exon, cds, utr,...),
     * making a new cluster for every overlapping gene
     * 
     * @param genome
     * @param ind 
     * @param motif 
     * @return
     */
    public static List<Cluster> generateClusters(Genome genome, long[] ind, String motif) {
	
	Arrays.sort(ind);	// assuming they are not already sorted
	
	List<Cluster> clusters = new ArrayList<Cluster>();
	
	for(int i = 0; i < ind.length; i++) {
	    
	    long currInd = ind[i];
	    Cluster c = new Cluster(motif, currInd, motif.length());
	    Sequence chr = genome.getSequence(currInd);
	    
	    Gene g1, g2;
	    g1 = g2 = null;
	    
	    
	    try {
		List<Gene> overGns = chr.getGenesAt(currInd);	// all overlapping genes at index
	
		g1 = overGns.get(0);
		
		Sequence context = new Sequence(chr.getName());
		context.addGene(g1);
		
		//CATEGORIZE
		
		c.setContext(context, new boolean[6]);
		
		
	    } catch(IndexOutOfBoundsException e) {		// intragenic case
		
		List<Gene> overGns1 = chr.getGenesBefore(currInd);
		List<Gene> overGns2 = chr.getGenesAfter(currInd);
		
		if(overGns1 != null) g1 = overGns1.get(0);
		if(overGns2 != null) g2 = overGns2.get(0);
		
		long leftInd = (g1 != null) ? g1.getEnd() : 0;		// the left and right gene bounds (intragenic)
		long rightInd = (g2 != null) ? g2.getStart() : 0;
		
		int off1 = (int) (currInd - leftInd);
		int off2 = (int) (rightInd - currInd);
		
		Sequence context = new Sequence(chr.getName());
		context.addGene(g1);
		context.addGene(g2);
		
		c.setContext(context);
	    }
	    
	    clusters.add(c);
	}
	
	
	return clusters;
    }
   
    
    
    /**
     * Takes filter conditions defined as strings and filters the cluster list in-place
     * 
     * TODO: filter condtions which are not 'restrict'. Within a number of genes, etc.
     * Better way to specify what feature.
     * 
     * @param clusters
     * @param conditions
     */
    
    public static void filterClusters(List<Cluster> clusters, Map<String, String[]> conditions) {
	
	
	String[] featureRestricts = conditions.get("Restrict");
	
	for(String s: featureRestricts) {
	    
	    int feature = 0;
	    
	    if(s.equals("EXON")) feature = ClusterContext.EXON;
	    if(s.equals("INTRON")) feature = ClusterContext.INTRON;
	    if(s.equals("UTR5")) feature = ClusterContext.UTR5;
	    if(s.equals("UTR3")) feature = ClusterContext.UTR3;
	    if(s.equals("CDS")) feature = ClusterContext.CDS;
	    if(s.equals("ORF")) feature = ClusterContext.ORF;
		
	    featureFilter(clusters, feature);
	}
	
	
	String[] chromosomeIds = conditions.get("ChromosomeId");
	
	for(String id: chromosomeIds) {
	    chrIdRestrict(clusters, id);
	}
	
	
	String[] geneIds = conditions.get("GeneId");
	
	for(String id: geneIds) {
	    geneIdRestrict(clusters, id);
	}
    }
    
    

    private static void featureFilter(List<Cluster> clusters, int feature) {
	  clusters.removeIf(c -> !(c.context.filterFlags[feature]));	
    }
   
    private static void chrIdRestrict(List<Cluster> clusters, String id) {
	clusters.removeIf(c -> !(c.context.sequence.getName().equals(id)));
    }
    
    
    private static void geneIdRestrict(List<Cluster> clusters, String id) {

	clusters.removeIf(c -> (c.context instanceof IntergenicContext));
	clusters.removeIf(c -> !(c.context.sequence.getGene().getName().equals(id)));
  
    }
    
    
    
    
    public static String toStringResult(List<Cluster> clusters) {
	
	StringBuilder sb = new StringBuilder();
	JSONHelper.startClass(sb);
	JSONHelper.add(sb, "clusters", clusters.toArray());
	JSONHelper.endClass(sb);
	
	return sb.toString();
	
    }
    
}
