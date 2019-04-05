package util;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

public class Ranges<T> {
	
	List<RangeItem<T>> ranges;

	public Ranges(long start, long end) { //TODO compare linked list to array list, also optimise the shit out of this
		ranges = new ArrayList<RangeItem<T>>();
		ranges.add(new RangeItem<T>(start, end, null));
	}

	public Ranges() {
		this(0, Long.MAX_VALUE);
	}

	public void add(long start, long end, T item) {
		int si = getIndex(start);
		RangeItem ri = ranges.get(si);
		if(ri.item != null || end > ri.end) {
			System.out.printf("%s colliding with %s", item, ri.item);
			throw new IllegalStateException("Items in ranges cannot overlap!");
		}
		ranges.add(si + 1, new RangeItem<T>(start, end, item));
		if(ri.end - end > 1) {
			ranges.add(si + 2, new RangeItem<T>(end + 1, ri.end, null));
		}
		ri.end = start - 1;
		if(ri.size() <= 0) {
			ranges.remove(si);
		}
	}

	public int getIndex(long pos) {
		// System.out.println(this);
		// System.out.printf("Looking for: %,d\n", pos);
		//Binary search
		int min = 0;
		int max = ranges.size();
		int mid = (min + max) / 2;
		RangeItem<T> midItem = ranges.get(mid);
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
			midItem = ranges.get(mid);
		}

		return mid;
	}

	public T get(long pos) {
		return ranges.get(getIndex(pos)).item;
	}

	// //returns a list of size <= 3 that has the range item below, at, and above the position
	// public List<T> getNeighbors(long pos) {
	// 	List<T> r = new ArrayList<T>();
	// 	int i = getIndex(pos);
	// 	if(i - 1 >= 0) {
	// 		r.add(ranges.get(i - 1).item);
	// 	}
	// 	r.add(ranges.get(i).item);
	// 	if(i + 1 < ranges.size()) {
	// 		r.add(ranges.get(i + 1).item);
	// 	}
	// 	return r;
	// }

	public String toString() {
		StringBuilder b = new StringBuilder();
		for(RangeItem ri: ranges) {
			b.append(ri.toString());
		}
		return b.toString();
	}

}