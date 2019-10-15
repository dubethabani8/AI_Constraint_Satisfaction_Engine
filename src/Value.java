
public class Value<T> {
	T val = null;
	
	public Value(T val) {
		this.val = val;
	}
	
	public String toString() {
		return "val=" + this.val;
	}
}
