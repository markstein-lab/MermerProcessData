package feature;

import util.JSONHelper;
import java.lang.StringBuilder;

public class Range {

	public static final String CDS = "CDS";
	public static final String UTR = "UTR";
	public static final String START_CODON = "startCodon";
	public static final String END_CODON = "endCodon";
	
	long start, end;
	String type;

	public Range(long start, long end, String type) {
		this.start = start;
		this.end = end;
		this.type = type;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		JSONHelper.startClass(sb);
		JSONHelper.add(sb, "start", start);
		JSONHelper.add(sb, "end", end);
		JSONHelper.add(sb, "type", type);
		JSONHelper.endClass(sb);
		return sb.toString();
	}
}