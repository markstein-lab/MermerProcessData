package output;
import java.util.ArrayList;
import java.util.List;

import feature.Gene;
import feature.Genome;
import feature.Sequence;
import util.JSONHelper;

/**
 * A cluster is a span of DNA containing a group of matches of the searched sequence/motif.
 * Every cluster object will also contain contextual information (gene it is in, position in gene, etc.)
 */

public class Cluster {

    
    public ClusterContext context; 
    
    private String motif;	// the sequence that was matched
    private int length;		// span of cluster in bp
    public long index;	// absolute index of cluster
    
    private List<Integer> motifOffsets;	// relative index of matches within cluster
    					// (relative to index 0 of the cluster)
    
    
    
    public Cluster(String motif, long ind, int length) {
	this.motif = motif;
	this.index = ind;
	this.length = length;
	
	motifOffsets = new ArrayList<Integer>();
	motifOffsets.add(0);
    }
    
    
    public void setContext(Sequence seq) {
	context = new IntergenicContext(seq);
    }
    
    public void setContext(Sequence seq, boolean[] featureFlags) {
	context = new IntragenicContext(seq, featureFlags);
    }
    
    
    public void addMotif(int offset) {
	
	//add match
	//recompute length
    }
    
    
    public String toString() {
	
	StringBuilder sb = new StringBuilder();
	JSONHelper.startClass(sb);
	
	JSONHelper.add(sb, "motif", motif);
	JSONHelper.add(sb, "index", index);
	JSONHelper.add(sb, "length", length);
	JSONHelper.add(sb, "motifOffsets", motifOffsets);
	
	JSONHelper.add(sb, "clusterContext", context.toString());
	
	JSONHelper.endClass(sb);
	
	return sb.toString();
    }
}
