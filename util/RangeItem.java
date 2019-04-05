package util;

class RangeItem<T> {
	
	long start, end;
	T item;

	public RangeItem(long start, long end, T item) {
		this.start = start;
		this.end = end;
		this.item = item;
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
		return String.format("[%,d, %s, %,d]", start, item, end);
		// ((item == null)?"NULL":item.toString())
	}
}