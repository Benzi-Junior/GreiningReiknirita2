public class Interval implements Comparable<Interval>{

// Engin skilyrði
private final int left;
private final int right;

// Notkun: I = new Interval(a,b)
// Fyrir: Ekkert
// Eftir: I = [a, b]
public Interval(int a, int b){
	if( a <= b ){
		left = a;
		right = b;
	}
	else{
		left = 0;
		right = 0;
	}
}

// Notkun: tomt = I.isEmpty()
// Fyrir: Ekkert
// Eftir: tomt = true ef I er tómamengið, tomt = false annars
public boolean isEmpty(){
	return right < left;
}

// Notkun: max = I.max();
// Fyrir: Ekkert
// Eftir: max eru efri mörk I
public int max(){
	return right;
}

// Notkun: min = I.min();
// Fyrir: Ekkert
// Eftir: min eru neðri mörk I
public int min(){
	return left;
}

// Notkun: inni = I.contains(x)
// Fyrir: Ekkert
// Eftir: inni = true ef x er stak í I, inni = false annars
public boolean contains(int x){
	return (left <= x) && (x <= right);
}


// Notkun: inni = I.contains(J)
// Fyrir: Ekkert
// Eftir: inni = true ef J er allt innihaldið í I, inni = false annars
public boolean contains(Interval  J){
	return J.isEmpty() || (left <= J.min()) && (J.max() <= right);
}

// Notkun: sker = I.intersects(J)
// Fyrir: Ekkert
// Eftir: sker = true ef sniðmengi I og M er ekki tómt,
//		  sker = false annars
public boolean intersects(Interval J){
	if((right < left) || J.isEmpty()) return false;
	return ((right >= J.left) || (right >= J.right)) && ((J.left >= left) || (J.right >= left));
}

// Notkun: s = I.toString()
// Fyrir: Ekkert
// Eftir: s = "[left, right]"
public String toString(){
	return "[" + left + ", " + right + "]";
}

// Notkun: c = I.compareTo(J)
// Fyrir: Ekkert
// Eftir: c > 0 ef I < J
//			c < 0 ef I > J
//			c = 0 ef I = J.
public int compareTo(Interval J){
	if( left < J.min() || (left == J.min() && right < J.max()) ){
		return -1;
	}
	if( left > J.min() || (left == J.min() && right > J.max()) ){
		return 1;
		}
	return 0;
}

// Notkun: b = I.equals(J)
// Fyrir: Ekkert
// Eftir: b = true þþaa I = J
public boolean equals(Interval J){
	return left == J.min() && right == J.max();
}

public static void main(String[] args){}

}
