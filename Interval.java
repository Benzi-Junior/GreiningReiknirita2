public class Interval implements Comparable<Interval>{

// Engin skilyrði
private final double left;
private final double right;

// Notkun: I = new Interval(a,b)
// Fyrir: Ekkert
// Eftir: I = [a, b]
public Interval(double a, double b){
	left = a;
	right = b;
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
public double max(){
	return right;
}

// Notkun: min = I.min();
// Fyrir: Ekkert
// Eftir: min eru neðri mörk I
public double min(){
	return left;
}

// Notkun: inni = I.contains(x)
// Fyrir: Ekkert
// Eftir: inni = true ef x er stak í I, inni = false annars
public boolean contains(double x){
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





/*

// Notkun: ollbil(x)
// Fyrir: Á staðalinntaki er sléttur fjöldi double talna
// Eftir: Fallið prentar út öll bilin á staðalinntaki sem innihalda x
public static void ollbil(double x){
	while(!StdIn.isEmpty()){
		double a = StdIn.readDouble();
		double b = StdIn.readDouble();
		Interval I = new Interval(a,b);
		if(I.contains(x)){
			System.out.println(I);
		}
	}
}
*/


public static void main(String[] args){
	Interval I = new Interval(Double.parseDouble(args[0]),Double.parseDouble(args[1]));
	Interval M = new Interval(0.0, 1.0);
	double inn = Double.parseDouble(args[2]);
	System.out.println("I er tomt: " + I.isEmpty());
	System.out.println("Nedri mork: " + I.min() + " , efri mork: " + I.max());
	System.out.println("I inniheldur " + inn + ": " + I.contains(inn));
	System.out.println("I sker M: " + I.intersects(M));
	System.out.println("I = " + I);
	System.out.println("I.compareTo(M): " + I.compareTo(M));
}}
