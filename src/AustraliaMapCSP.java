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
				if(var.value.val == variable.value.val) return true;
			}
			return false;
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
		this.variables.add(WA);
		this.variables.add(NT);
		this.variables.add(Q);
		this.variables.add(NSW);
		this.variables.add(V);
		this.variables.add(SA);
		this.variables.add(T);
		this.constraints = new ArrayList<Constraint<Color>>();
		this.constraints.add(new NotEqualConstraint(SA, WA));
		this.constraints.add(new NotEqualConstraint(SA, NT));
		this.constraints.add(new NotEqualConstraint(SA, Q));
		this.constraints.add(new NotEqualConstraint(SA, NSW));
		this.constraints.add(new NotEqualConstraint(SA, V));
		this.constraints.add(new NotEqualConstraint(WA, NT));
		this.constraints.add(new NotEqualConstraint(NT, Q));
		this.constraints.add(new NotEqualConstraint(Q, NSW));
		this.constraints.add(new NotEqualConstraint(NSW, V));
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
		System.out.println("result=" + result.toString());
	}
}
