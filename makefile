OBJ = ProcessData.java \
./parse/GTFParser.java ./parse/MarkerParser.java \
./util/RangeItem.java ./util/Ranges.java ./util/OverlappingRangeItem.java ./util/OverlappingRanges.java ./util/JSONHelper.java \
./feature/Genome.java ./feature/Sequence.java ./feature/Gene.java ./feature/Transcript.java ./feature/Exon.java ./feature/Range.java \
./result/Result.java 

Parser: $(OBJ)
	javac $(OBJ) -Xlint:unchecked
	java ProcessData "../data/GTF sample.csv"

clean:
	find . -type f -name \*.class -delete