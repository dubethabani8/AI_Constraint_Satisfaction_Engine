import java.util.ArrayList;

public class BacktrackingSearchSolver<T> extends Solver<T> {
	//final ArrayList<Constraint<T>> cons;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	
//	@Override
//	public Assignment<T> solve(CSP<T> csp){
//		Assignment<T> assignment = new Assignment<T>();
//		assignment.constraints = csp.constraints;
//		return backTrack(assignment, csp);		
//	}
//	
//	public Assignment<T> backTrack(Assignment<T> assignment, CSP<T> csp){
//		if(assignment.isConsistent() && assignment.variables.size() == csp.variables.size()) {
//			return assignment;
//		}
//		
//		Variable<T> var = selectUnassignedVariable(csp);
//		//mark assigned
//		csp.variables.remove(var);
//		
//		for(Value<T> value: orderDomainValues(var)) {
//			
//			if(assignment.isConsistentAddition(assignment, value, var)) {
//				var.value = value;
//				assignment.variables.add(var);
//				csp.variables.add(var);
//				Assignment<T> result = backTrack(assignment, csp);
//				if(result != null) return result;
//			}
//			assignment.variables.remove(var);
//			//Unmark removed var as unassigned in csp variables
//			Variable<T> temp = new Variable<T>();
//			for(Variable<T> variable: csp.variables) {
//				if(variable.name == var.name) temp = variable;
//			}
//			temp.value = null;
//			csp.variables.remove(temp);
//			csp.variables.add(temp);
//		}
//		return null;
//		
//	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public Assignment<T> solve(CSP<T> csp){
		System.out.println("!!!!START!!!!\n\n");
		Assignment<T> assignment = new Assignment<T>();
		assignment.constraints = csp.constraints;
		return backTrack(assignment, csp);		
	}
	
	public Assignment<T> backTrack(Assignment<T> assignment, CSP<T> csp){
		assignment.constraints = csp.constraints;
		if(assignment.isConsistent(csp) && (selectUnassignedVariable(csp) == null)) { //if complete return assignment
			System.out.println("No more anassigned and assignment complete");
			return assignment;
		}
		Variable<T> var = selectUnassignedVariable(csp);
		System.out.println("Selected unassigned variable " + var.name + " from " + csp.variables.toString());
		for(Value<T> val: orderDomainValues(var)) {
			System.out.println("Checking for consistency of addition " + var.name + "=" + val.val + " to " + assignment.variables.toString());
			if(assignment.isConsistentAddition(assignment, val, var, csp)) {
				var.value = val;
				assignment.variables.add(var);
				System.out.println("Consistent and added, now " + assignment.variables.toString());
				Assignment<T> result = backTrack(assignment, csp);
				
				if(result != null) {
					System.out.println("Returning Result\n" + result.toString());
					return result;
				}
				//else assignment.variables.remove(var);
			}
			//int i = assignment.variables.size()-1;
			assignment.variables.remove(var);
			System.out.println("Not consistent addition. Now " + assignment.variables.toString());
			
			//remove var = val from assignment
			//System.out.println("Removing " + var.name);	
		}

		
		//Unmark removed var as unassigned in csp variables
		Variable<T> temp = new Variable<T>();
		for(Variable<T> variable: csp.variables) {
			if(variable.name == var.name) temp = variable;
		}
		temp.value = null;
		csp.variables.remove(temp);
		csp.variables.add(temp);
		System.out.println("Failed, now backtracking or total fail");
		
		//csp.variables = assignment.variables;
		return null; //return failure
	}
	
	
	public Variable<T> selectUnassignedVariable(CSP<T> csp){
		ArrayList<Variable<T>> unassignedVars = new ArrayList<Variable<T>>();
		boolean allEqual = true;
		for(Variable<T> var: csp.variables) {
			if(var.value == null) unassignedVars.add(var);
		}
		if(unassignedVars.isEmpty()) return null;
		else {
			int domSize = unassignedVars.get(0).domain.size();
			for(Variable<T> var: csp.variables) {
				if(var.domain.size() != domSize) {
					allEqual = false;
					break;
				}
			}
			if(!allEqual) return csp.assignment.mrvHeuristic(unassignedVars);
			else return csp.assignment.degreeHeuristic(unassignedVars);
		}
		
	}
	
	public ArrayList<Value<T>> orderDomainValues(Variable<T> var){
		return  var.domain.values;
	}
	
}
