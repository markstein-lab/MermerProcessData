package output;

import feature.Gene;
import feature.Sequence;


/**
 * Context of a cluster contains its surrounding genes and the feature it is in
 * 
 * TODO: remove 3 classes and combine to 1.
 */
public abstract class ClusterContext {	

    public Sequence sequence;	// container of context
    
    public boolean[] filterFlags;
    
    public static final int EXON = 0;
    public static final int INTRON = 1;
    public static final int UTR5 = 2;
    public static final int UTR3 = 3;
    public static final int CDS = 4;
    public static final int ORF = 5;
}
