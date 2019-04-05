package result;

import feature.Genome;
import feature.Sequence;
import feature.Gene;
import util.JSONHelper;
import java.util.List;

/*
One instance class in json form for each result is what is sent to the webpage to display the results of the search
*/
public class Result {
	
	String query;
	Sequence sequences;

	public Result(String query, Sequence sequences) {
		this.query = query;
		this.sequences = sequences;
	}

	// public static Result getResult(long index, Genome genome) {
		// Sequence sequences = genome.getSequence(index);
		// long location = index - sequences.getIndexStart();

		// Sequence resultSequence = new Sequence(sequences);
		// List<Gene> nearbyGenes
				
		//todo
		// return new Result("TODO: figure out which query this is", sequences);
	// }

	// public String toString() {
	// 	StringBuilder sb = new StringBuilder();
	// 	JSONHelper.startClass(sb);
	// 	JSONHelper.add(sb, "query", query);
	// 	JSONHelper.add(sb, "sequences", sequences);
	// 	JSONHelper.endClass(sb);
	// 	return sb.toString();
	// }
}