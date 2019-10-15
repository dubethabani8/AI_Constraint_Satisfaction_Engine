import java.util.ArrayList;
import java.util.Date;

/**
 * Constraint satisfaction problem for coloring the map of Australia,
 * from AIMA Section 6.1 and Fig 6.1.
 */
enum Color{
	RED, GREEN, BLUE
}

public class AustraliaMapCSP extends CSP<Color> {
	Domain<Color> universalDomain;
	
protected class NotEqualConstraint extends Constraint<Color>{
		protected NotEqualConstraint(Variable<Color> a, Variable<Color> b) {
			this.tuple.add(a);
			this.tuple.add(b);
		}
		
		@Override
		public boolean isViolated(ArrayList<Variable<Color>> variables, Variable<Color> variable) { //problem specific - overridden by child classes
			for(Variable<Color> var: variables) {
				if(var.value == null) return false;
				if( var.value.val == variable.value.val) return true;
			}
			return false;
		}
		
		@Override
		public ArrayList<Variable<Color>> variablesInvolvedWith(Variable<Color> variable) { //returns varaibles from tupple other than this one
			ArrayList<Variable<Color>> temp = this.tuple;
			temp.remove(variable);
			return temp;
		}
	}
	/**
	 * Construct and return a new instance of the CSP for coloring the map of
	 * Australia.
	 */
	public AustraliaMapCSP() {
		super();
		this.universalDomain = new Domain<Color>();
		this.universalDomain.values.add(new Value<Color>(Color.BLUE));
		this.universalDomain.values.add(new Value<Color>(Color.GREEN));
		this.universalDomain.values.add(new Value<Color>(Color.RED));
		// Don't share domains if doing constraint propagation
		Variable<Color> WA = new Variable<Color>("WA", this.universalDomain);
		Variable<Color> NT = new Variable<Color>("NT", this.universalDomain);
		Variable<Color> Q = new Variable<Color>("Q", this.universalDomain);
		Variable<Color> NSW = new Variable<Color>("NSW", this.universalDomain);
		Variable<Color> V = new Variable<Color>("V", this.universalDomain);
		Variable<Color> SA = new Variable<Color>("SA", this.universalDomain);
		Variable<Color> T = new Variable<Color>("T", this.universalDomain);
		this.variables = new ArrayList<Variable<Color>>();
		this.variables.add(SA);
		this.variables.add(WA);
		this.variables.add(NT);
		this.variables.add(Q);
		this.variables.add(NSW);
		this.variables.add(V);
		
		this.variables.add(T);
		this.constraints = new ArrayList<Constraint<Color>>();
		Constraint<Color> SA_WA = new NotEqualConstraint(SA, WA);
		Constraint<Color> SA_NT = new NotEqualConstraint(SA, NT);
		Constraint<Color> SA_Q = new NotEqualConstraint(SA, Q);
		Constraint<Color> SA_NSW = new NotEqualConstraint(SA, NSW);
		Constraint<Color> SA_V = new NotEqualConstraint(SA, V);
		Constraint<Color> WA_NT = new NotEqualConstraint(WA, NT);
		Constraint<Color> NT_Q = new NotEqualConstraint(NT, Q);
		Constraint<Color> Q_NSW = new NotEqualConstraint(Q, NSW);
		Constraint<Color> V_NSW = new NotEqualConstraint(V, NSW);
		this.constraints.add(SA_WA);
		this.constraints.add(SA_NT);
		this.constraints.add(SA_Q);
		this.constraints.add(SA_NSW);
		this.constraints.add(SA_V);
		this.constraints.add(WA_NT);
		this.constraints.add(NT_Q);
		this.constraints.add(Q_NSW);
		this.constraints.add(V_NSW);
	}

	public static void main(String[] args) {
	
		
		System.out.println("Australia Map Coloring Problem (AIMA 6.1.1)");
		CSP<Color> csp = new AustraliaMapCSP();
		System.out.println(csp);
		System.out.println("Backtracking search solver");
		Solver<Color> solver = new BacktrackingSearchSolver<Color>();
		long start = new Date().getTime();
		Assignment<Color> result = solver.solve(csp);
		long end = new Date().getTime();
		System.out.format("time: %.3f secs\n", (end-start)/1000.0);
		System.out.println("\nResult:\n" + result.toString());
	}
}
