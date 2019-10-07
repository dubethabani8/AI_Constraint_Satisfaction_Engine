import java.util.ArrayList;

public class CSP<T> {
	ArrayList<Variable<T>> variables;
	Assignment<T> assignment;
	ArrayList<Constraint<T>> constraints;
	
	public CSP(){
		this.assignment = new Assignment<T>();
	}
	
	int numOfVars() {
		return this.variables.size();
	}
	
	public Variable<T> removeFromDomain(Variable<T> variable, Value<T> val) {
		for(Variable<T> var: this.variables) {
			if(variable.name.equals(var.name)) {
				var.domain.values.remove(val);
				return variable;
			}
		}
		return variable;
	}
	
}