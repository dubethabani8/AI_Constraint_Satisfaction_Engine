import java.util.ArrayList;

public class Domain<T> {
	ArrayList<Value<T>> values;
	
	public Domain() {
		this.values = new ArrayList<Value<T>>();
	}
	
	int size() { //returns number of values in domain
		return this.values.size();
	}
}
