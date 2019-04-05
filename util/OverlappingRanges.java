package util;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

public class OverlappingRanges<T> {
	
	ArrayList<OverlappingRangeItem<T>> ranges;

	public OverlappingRanges(long start, long end) {
		ranges = new ArrayList<OverlappingRangeItem<T>>();
		ranges.add(new OverlappingRangeItem<T>(start, end));
	}

	public OverlappingRanges() {
		this(0, Long.MAX_VALUE);
	}

	public void add(long start, long end, T item) {
		int si = getIndex(start);
		int ei = getIndex(end);

		OverlappingRangeItem<T> sri = ranges.get(si);
		OverlappingRangeItem<T> eri = ranges.get(ei);
		// System.out.printf("si: %d, ei: %d\n", si, ei);
		if(sri.start != start) {
			// System.out.println("Chop front");
			OverlappingRangeItem<T> newSri = new OverlappingRangeItem<T>(start, sri.end);
			sri.end = start - 1;
			newSri.items.addAll(sri.items);
			si ++;
			ranges.add(si, newSri);
			ei ++;
			eri = ranges.get(ei);
		}
		if(eri.end != end) {
			// System.out.println("Chop back");
			OverlappingRangeItem<T> newEri = new OverlappingRangeItem<T>(eri.start, end);
			eri.start = end + 1;
			newEri.items.addAll(eri.items);
			ranges.add(ei, newEri);
			ei ++;
		}

		for(int i = si; i < ei; i ++) {
			ranges.get(i).items.add(item);
		}
	}

	public int getIndex(long pos) {
		// System.out.println(this);
		// System.out.printf("Looking for: %,d\n", pos);
		//Binary search
		int min = 0;
		int max = ranges.size();
		if(max == 0) {
			return 0;
		}
		int mid = (min + max) / 2;
		OverlappingRangeItem<T> midItem = ranges.get(mid);
		while(!midItem.inRange(pos)) {
			// System.out.printf("Pre %d, %d, %d\n", min, mid, max);
			if(max < min) {
				throw new IndexOutOfBoundsException("Position: " + pos + " is outside of Ranges range");
			}
			if(midItem.isBelow(pos)) {
				max = mid - 1;
			} else {
				min = mid + 1;
			}
			mid = (min + max) / 2;
			// System.out.printf("Post %d, %d, %d\n", min, mid, max);
			midItem = ranges.get(mid);
		}

		return mid;
	}

	public List<T> get(long pos) {
		return ranges.get(getIndex(pos)).items;
	}

	public List<T> getBefore(long pos) {
		int beforeIndex = getIndex(pos) - 1;
		if(beforeIndex < 0) {
			return null;
		}
		return ranges.get(beforeIndex).items;
	}

	public List<T> getAfter(long pos) {
		int afterIndex = getIndex(pos) + 1;
		if(afterIndex >= ranges.size()) {
			return null;
		}
		return ranges.get(afterIndex).items;
	}

	// // //returns a list of size <= 3 that has the range item below, at, and above the position
	// // public List<T> getNeighbors(long pos) {
	// // 	List<T> r = new ArrayList<T>();
	// // 	int i = getIndex(pos);
	// // 	if(i - 1 >= 0) {
	// // 		r.add(ranges.get(i - 1).item);
	// // 	}
	// // 	r.add(ranges.get(i).item);
	// // 	if(i + 1 < ranges.size()) {
	// // 		r.add(ranges.get(i + 1).item);
	// // 	}
	// // 	return r;
	// // }

	public String toString() {
		StringBuilder b = new StringBuilder();
		for(OverlappingRangeItem ri: ranges) {
			b.append(ri.toString());
		}
		return b.toString();
	}

}