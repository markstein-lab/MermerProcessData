package feature;

import util.JSONHelper;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class Exon {
	
	int exonNumber;
	long start, end;
	String exonId;
	int exonVersion;

	List<Range> ranges;

	public Exon(int exonNumber, long start, long end, String exonId, int exonVersion) {
		this.exonNumber = exonNumber;
		this.start = start;
		this.end = end;
		this.exonId = exonId;
		this.exonVersion = exonVersion;

		ranges = new ArrayList<Range>(1);
	}

	public int getNumber() {
		return exonNumber;
	}

	public void addRange(Range c) {
		ranges.add(c);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		JSONHelper.startClass(sb);
		JSONHelper.add(sb, "exonNumber", exonNumber);
		JSONHelper.add(sb, "start", start);
		JSONHelper.add(sb, "end", end);
		JSONHelper.add(sb, "exonId", exonId);
		JSONHelper.add(sb, "exonVersion", exonVersion);
		JSONHelper.add(sb, "ranges", ranges);
		JSONHelper.endClass(sb);
		return sb.toString();
	}
}