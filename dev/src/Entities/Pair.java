package Entities;

import java.util.Objects;

public class Pair <F,S>{

	public F first;
	public S second;
	
	public Pair (F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F getFirst() {
		return first;
	}

	public void setFirst(F first) {
		this.first = first;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof Pair)){
			return false;
		}
		Pair toCompare = (Pair) obj;
		return first.equals(toCompare.getFirst()) && second.equals(toCompare.getSecond());
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}
}
