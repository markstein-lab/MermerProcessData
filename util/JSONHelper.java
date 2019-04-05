package util;

import java.util.Map;
import java.util.List;
import java.lang.StringBuilder;

public class JSONHelper {

	public static void startClass(StringBuilder b) {
		b.append("{");
	}

	public static void endClass(StringBuilder b) {
		b.delete(b.length() - 2, b.length());
		b.append("}");
	}		

	public static void add(StringBuilder b, String fieldName, String s) {
		b.append("\"" + fieldName + "\": \"" + s + "\", ");
	}

	public static void add(StringBuilder b, String fieldName, int i) {
		b.append("\"" + fieldName + "\": " + i + ", ");
	}

	public static void add(StringBuilder b, String fieldName, long l) {
		b.append("\"" + fieldName + "\": " + l + ", ");
	}

	public static void add(StringBuilder b, String fieldName, Object[] array) {
		b.append("\"" + fieldName + "\": [");
		if(array != null && array.length > 0){
			for(Object o: array) {
				if(o != null) {
					b.append(o.toString() + ", ");
				}
			}
			b.delete(b.length() - 2, b.length());
		}
		b.append("], ");
	}

	public static void add(StringBuilder b, String fieldName, List list) {
		if(list != null){
			add(b, fieldName, list.toArray());
		}
		else {
			Object[] n = {};
			add(b, fieldName, n);
		}
	}

	public static void add(StringBuilder b, String fieldName, Map map) {
		add(b, fieldName, map.values().toArray());
	}
}