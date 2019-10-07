import java.util.ArrayList;

public class BacktrackingSearchSolver<T> extends Solver<T> {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public Assignment<T> solve(CSP<T> csp){
		return backTrack(new Assignment<T>(), csp);
		
	}
	
	public Assignment<T> backTrack(Assignment<T> assignment, CSP<T> csp){
		Assignment<T> result = new Assignment<T>();
		if(assignment.isConsistent() && selectUnassignedVariable(csp) == null) //if complete return assignment
			return assignment;
		
		Variable<T> var = selectUnassignedVariable(csp);
		
		for(Value<T> val: orderDomainValues(var, assignment,csp)) {
			if(assignment.isConsistentAddition(assignment, val, var)) {
				System.out.println("Adding " + var.name + " with val: " + var.value.val);
				assignment.add(var);
				result = backTrack(assignment, csp);
				if(result != null) return result;
			}
			//remove var = val from assignment
			assignment.variables.remove(var);
			System.out.println("Removing " + var.name);
		}
		System.out.println("Failed, now backtracking or total fail");
		return null; //return failure
	}
	
	
	public Variable<T> selectUnassignedVariable(CSP<T> csp){
		
		ArrayList<Variable<T>> unassignedVars = new ArrayList<Variable<T>>();
		boolean allEqual = true;
		for(Variable<T> var: csp.variables) {
			if(var.value == null) unassignedVars.add(var);
		}
		if(unassignedVars.isEmpty()) return null;
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
	
	public ArrayList<Value<T>> orderDomainValues(Variable<T> var, Assignment<T> assignment, CSP<T> csp){
		return  var.domain.values;
	}
	
}
