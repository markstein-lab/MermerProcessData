package feature;

import java.util.List;
import util.JSONHelper;
import util.OverlappingRanges;
import java.util.Map;
import java.util.HashMap;



public class Sequence {

	private String sequenceName;
	private transient long indexStart;
	private transient long indexEnd;
	private Map<String, Gene> genes;
	private OverlappingRanges<Gene> geneRanges;

	public Sequence(String sequenceName, long indexStart, long indexEnd) {
		this.sequenceName = sequenceName;
		this.indexStart = indexStart;
		this.indexEnd = indexEnd;
		genes = new HashMap<String, Gene>();
		geneRanges = new OverlappingRanges<Gene>();
	}

	public Sequence(String sequenceName) {
		this(sequenceName, 0, Long.MAX_VALUE);
	}

	public Sequence(Sequence sequence) {
		this(sequence.sequenceName, sequence.indexStart, sequence.indexEnd);
	}

	public String getName() {
		return sequenceName;
	}

	public void addGene(Gene g) {
		genes.put(g.getId(), g);
		List<Gene> here = geneRanges.get(g.getStart());
		if(here != null && here.size() > 0) {
			System.out.printf("Collision between: \n%s\nand\n%s\n", here.get(0), g);
		}
		geneRanges.add(g.getStart(), g.getEnd(), g);
	}

	public Gene getGene(String geneId) {
		return genes.get(geneId);
	}
	
	public Gene getGene() {
	    	return (Gene) genes.values().toArray()[0];
	}

	public List<Gene> getGenesAt(long location) {
		return geneRanges.get(location);
	}

	public List<Gene> getGenesBefore(long location) {
		return geneRanges.getBefore(location);
	}

	public List<Gene> getGenesAfter(long location) {
		return geneRanges.getAfter(location);
	}

	public long getIndexStart() {
		return indexStart;
	}

	public long getIndexEnd() {
		return indexEnd;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		JSONHelper.startClass(sb);
		JSONHelper.add(sb, "sequenceName", sequenceName);
		JSONHelper.add(sb, "genes", genes);
		JSONHelper.endClass(sb);
		return sb.toString();
	}
}