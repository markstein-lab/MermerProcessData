package feature;

import java.util.ArrayList;
import java.util.List;
import util.JSONHelper;
import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;

public class Transcript {
	
	private String transcriptName;
	private long start, end;
	private String transcriptId;

	private Map<Integer, Exon> exons;
	private List<Range> ranges;

	public Transcript(String transcriptName, long start, long end, String transcriptId) {
		this.transcriptName = transcriptName;
		this.start = start;
		this.end = end;
		this.transcriptId = transcriptId;

		exons = new HashMap<Integer, Exon>();
		ranges = new ArrayList<Range>();
	}

	public String getId()  {
		return transcriptId;
	}

	public void addExon(Exon e) {
		exons.put(e.getNumber(), e);
	}

	public Exon getExon(int exonNumber) {
		return exons.get(exonNumber);
	}

	public void addRange(Range r) {
		ranges.add(r);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		JSONHelper.startClass(sb);
		JSONHelper.add(sb, "transcriptName", transcriptName);
		JSONHelper.add(sb, "start", start);
		JSONHelper.add(sb, "end", end);
		JSONHelper.add(sb, "transcriptId", transcriptId);
		JSONHelper.add(sb, "exons", exons);
		JSONHelper.add(sb, "ranges", ranges);
		JSONHelper.endClass(sb);
		return sb.toString();
	}
}