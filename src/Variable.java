
public class Variable<T> {
	String name;
	Value<T> value;
	Domain<T> domain;
	
	public Variable(){
		
	}
	
	public Variable(String name, Value<T> value) {
		this.name = name;
		this.value = value;
	}
	
	public Variable(String name, Domain<T> domain) {
		this.name = name;
		this.domain = domain;
	}
	
	public int getDegree() {
		return this.domain.size();
	}
	
	public String toString() {
		if(this.value != null)
			return this.name + "-" +  value.val;
		else return this.name + "-" +  "NONE";
	}
	
}
