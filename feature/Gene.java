package feature;

import util.JSONHelper;
import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;

public class Gene {
	
	private String geneName;
	private long start, end;
	private String geneId;

	private Map<String, Transcript> transcripts;

	public Gene(String geneName, long start, long end, String geneId) {
		this.geneName = geneName;
		this.start = start;
		this.end = end;
		this.geneId = geneId;
		transcripts = new HashMap<String, Transcript>();
	}

	public String getName() {
		return geneName;
	}

	public String getId() {
		return geneId;
	}

	public void addTranscript(Transcript t) {
		transcripts.put(t.getId(), t);
	}

	public Transcript getTranscript(String transcriptId) {
		return transcripts.get(transcriptId);
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		JSONHelper.startClass(sb);
		JSONHelper.add(sb, "geneName", geneName);
		JSONHelper.add(sb, "start", start);
		JSONHelper.add(sb, "end", end);
		JSONHelper.add(sb, "geneId", geneId);
		JSONHelper.add(sb, "transcripts", transcripts);
		JSONHelper.endClass(sb);
		return sb.toString();
	}
}