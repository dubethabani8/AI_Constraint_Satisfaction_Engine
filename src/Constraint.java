import java.util.ArrayList;

public class Constraint<T> {
	ArrayList<Variable<T>> tuple;
	
	public Constraint() {
		this.tuple = new ArrayList<Variable<T>>();
	}
	
	
	boolean contains(Variable<T> variable) { //checks if a variable is involved in this constraint
		if(this.tuple.contains(variable))
			return true;
	return false;
	}
	
	public ArrayList<Variable<T>> variablesInvolvedWith(Variable<T> variable) { //returns varaibles from tupple other than this one
		ArrayList<Variable<T>> temp = this.tuple;
		temp.remove(variable);
		return temp;
	}

	public boolean isViolated(ArrayList<Variable<T>> variables, Variable<T> variable){
		return false;
	}

}
