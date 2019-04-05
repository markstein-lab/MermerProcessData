package output;
import java.util.Map;

import feature.Gene;
import feature.Sequence;
import util.JSONHelper;


/**
 * Contains additional contextual information on a cluster for displaying it
 */

public class IntergenicContext extends ClusterContext{


    public IntergenicContext(Sequence context) {
	sequence = context;
	filterFlags = new boolean[6];	// all false for intergenic
    }
    

    
}
