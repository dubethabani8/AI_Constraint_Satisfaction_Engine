import java.util.ArrayList;

public class Assignment<T> {
	ArrayList<Variable<T>> variables;
	ArrayList<Constraint<T>> constraints;
	
	public Assignment() {
		this.variables = new ArrayList<Variable<T>>();
		this.constraints = new ArrayList<Constraint<T>>();
	}
	
	public void add(Variable<T> variable) {
		this.variables.add(variable);
	}
	
	public Variable<T> degreeHeuristic(ArrayList<Variable<T>> unassignedVars) { //returns variable with highest degree
		int max = Integer.MIN_VALUE;
		Variable<T> selected = new Variable<T>();
		for(Variable<T> variable: unassignedVars)
			if(variable.getDegree() > max ) {
				selected = variable;
				max = variable.getDegree();
			}
		return selected;
	}
	
	public Variable<T> mrvHeuristic(ArrayList<Variable<T>> unassignedVars){ //returns variable involved in least number of constraints
		Variable<T> selected = new Variable<T>();
		int min = Integer.MAX_VALUE;
		for(Variable<T> variable: unassignedVars) {
			int num = numOfOccurances(variable);
			if(num < min) {
				min = num;
				selected = variable;
			}
		}
		return selected;
	}
	
	public int numOfOccurances(Variable<T> variable) { //returns number of constraints the variable is involve in
		int num = 0;
		for(Constraint<T> constraint: this.constraints) {
			if(constraint.contains(variable)) num++;
		}
		return num;
	}
	
	public boolean isConsistent(){
		for(Variable<T> variable: this.variables) {
			for(Constraint<T> constraint: this.constraints) {
				ArrayList<Variable<T>> others = constraint.variablesInvolvedWith(variable);
				if(constraint.isViolated(others, variable)) return false;
			}
		}
		return true;
	}
	
	public boolean isConsistentAddition(Assignment<T> assignment, Value<T> val, Variable<T> var) {
		var.value = val;
		assignment.add(var);
		if(assignment.isConsistent()) return true;
		else return false;
		
	}
	
	public String toString() {
		String str = "";
		for(Variable<T> var: variables)
			str += var.name + " = " + var.value.val + /*" Domain: " + var.domain.size() +*/ "\n";
		return str;
	}
	
}
