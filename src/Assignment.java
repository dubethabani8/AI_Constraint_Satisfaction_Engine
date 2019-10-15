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
	

	public boolean isConsistent(CSP<T> csp){
	for(Variable<T> variable: csp.variables) {
		Variable<T> tempV = variable;
		for(Constraint<T> constraint: csp.constraints) {
			Constraint<T> tempC = constraint;
			System.out.println("CHECKING CONSTRAINT: " + tempC.toString());
			ArrayList<Variable<T>> others = new ArrayList<Variable<T>>();
			if(tempC.contains(tempV)) others = tempC.variablesInvolvedWith(tempV);
			else continue;
			System.out.println(tempV.name + " is involved with " + others.toString());
			//assign vals and check for violations
			ArrayList<Variable<T>> others2 = new ArrayList<Variable<T>>();
			
			for(Variable<T> var: csp.variables) {
				Variable<T> varTemp = var;
				for(Variable<T> var2: others) {
					Variable<T> var2Temp = var2;
					if(varTemp.name.equals(var2Temp.name)) {
						var2Temp.value = varTemp.value;
						others2.add(var2Temp);
					}
				}
			}
			
			if(tempC.isViolated(others2, tempV)) return false;
		}
	}
	return true;
}
	
	
//	public boolean isConsistent(){
//		for(Variable<T> variable: this.variables) {
//			Variable<T> tempV = variable;
//			for(Constraint<T> constraint: this.constraints) {
//				Constraint<T> tempC = constraint;
//				System.out.println("CHECKING CONSTRAINT: " + tempC.toString());
//				ArrayList<Variable<T>> others = new ArrayList<Variable<T>>();
//				if(tempC.contains(tempV)) others = tempC.variablesInvolvedWith(tempV);
//				else continue;
//				System.out.println(tempV.name + " is involved with " + others.toString());
//				//assign vals and check for violations
//				ArrayList<Variable<T>> others2 = new ArrayList<Variable<T>>();
//				
//				for(Variable<T> var: this.variables) {
//					Variable<T> varTemp = var;
//					for(Variable<T> var2: others) {
//						Variable<T> var2Temp = var2;
//						if(varTemp.name.equals(var2Temp.name)) {
//							var2Temp.value = varTemp.value;
//							others2.add(var2Temp);
//						}
//					}
//				}
//				if(tempC.isViolated(others2, tempV)) return false;
//			}
//		}
//		return true;
//	}
	
	public boolean isConsistentAddition(Assignment<T> ass, Value<T> val, Variable<T> var, CSP<T> csp) {
		var.value = val;
		csp.variables.add(var);
		if(ass.isConsistent(csp)) {
			
			csp.variables.remove(var);
			return true;
		}
		else{
	
			csp.variables.remove(var);
			return false;
		}
		
	}
	
	
	public String toString() {
		String str = "";
		for(Variable<T> var: variables)
			str += var.name + " = " + var.value.val + /*" Domain: " + var.domain.size() +*/ "\n";
		return str;
	}
	
}
