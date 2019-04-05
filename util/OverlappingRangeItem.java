package util;

import java.util.ArrayList;
import java.util.List;

class OverlappingRangeItem<T> {
	
	long start, end;
	List<T> items;

	public OverlappingRangeItem(long start, long end) {
		this.start = start;
		this.end = end;
		items = new ArrayList<T>();
	}

	public void addItem(T item) {
		items.add(item);
	}

	public List<T> getItems() {
		return items;
	}

	public boolean inRange(long pos) {
		return (pos >= start && pos <= end);
	}

	public boolean isBelow(long pos) {
		return pos < start;
	}

	public long size() {
		return end - start;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		// for(T item: items) {
		// 	sb.append(item.toString());
		// 	sb.append(", ");
		// }
		// if(items.size() > 0) {
		// 	sb.delete(sb.length() - 2, sb.length());
		// }
		JSONHelper.add(sb, "items", items);
		return String.format("[%,d, %s%,d]", start, sb.toString(), end);
		// ((item == null)?"NULL":item.toString())
	}
}