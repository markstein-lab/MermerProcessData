package feature;

import util.JSONHelper;
import java.util.Map;

import java.util.HashMap;
import util.Ranges;

public class Genome {

	private Map<String, Sequence> sequences;
	public Ranges<Sequence> sequenceRanges;

	public Genome() {
		sequences = new HashMap<String, Sequence>();
		sequenceRanges = new Ranges<Sequence>();
	}

	public void addSequence(Sequence s) {
		sequences.put(s.getName(), s);
		sequenceRanges.add(s.getIndexStart(), s.getIndexEnd(), s);
	}

	public Sequence getSequence(String sequenceName) {
		return sequences.get(sequenceName);
	}

	public Sequence getSequence(long index) {
		return sequenceRanges.get(index);
	}

	public boolean hasSequence(String sequenceName) {
		return sequences.containsKey(sequenceName);
	}

	public int getSequenceCount() {
		return sequences.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		JSONHelper.startClass(sb);
		JSONHelper.add(sb, "sequences", sequences);
		JSONHelper.endClass(sb);
		return sb.toString();
	}

}