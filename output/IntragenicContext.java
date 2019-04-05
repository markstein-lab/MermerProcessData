package output;
import feature.Gene;
import feature.Sequence;
import util.JSONHelper;


/**
 * Contains additional contextual information on a cluster for displaying it
 */

public class IntragenicContext extends ClusterContext{

    
    
    public IntragenicContext(Sequence context, boolean[] flags) {
	sequence = context;
	
	// featureFlags = [exon, intron, 5'UTR, 3'UTR, CDS];
	filterFlags = flags;
    }
    
    
    public void setFilterFlags(boolean[] flags) {
	filterFlags = flags;
    }
}
