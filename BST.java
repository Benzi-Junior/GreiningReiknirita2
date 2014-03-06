public class BST<E extends Comparable<E>>{
	
	private class Node<F>{
		
		// Fastayrðing
		public F val;
		public Node<F> left, right;
		
		// Notkun: N = new Node(val, left, right) 
		// Fyrir: 
		// Eftir: N er Node hlutur með N.val = val, N.left = left og N.right = right
		public Node(F val, Node<F> left, Node<F> right){
			this.val = val;
			this.left = left;
			this.right = right;
			}
		}
	
	// Fastayrðing
	private Node<E> rot;
	
	// Notkun: T = new BST()
	// Fyrir: Ekkert
	// Eftir: T er tómur BST hlutur.
	public BST(){
		this.rot = new Node<E>(null, null, null);
	}
	
	// Notkun: T.insert(val)
	// Fyrir: Ekkert
	// Eftir: T inniheldur nú hnút með gildið val.
	public void insert(E val){
		if(rot.val == null){
			Node<E> L = new Node<E>(null, null, null);
			Node<E> R = new Node<E>(null, null, null);
			Node<E> N = new Node<E>(val, L, R);
			rot = N;
			}
		else if(rot.val.compareTo(val) < 0){
			BST<E> S = new BST<E>();
			S.rot = rot.left;
			S.insert(val);
			rot.left = S.rot;
			}
		else if(rot.val.compareTo(val) > 0){
			BST<E> S = new BST<E>();
			S.rot = rot.right;
			S.insert(val);
			rot.right = S.rot;
			}
		}	
		
	// Notkun: b = T.isEmpty()
	// Fyrir: Ekkert
	// Eftir: b = true þþaa T hefur enga hnúta.
	public boolean isEmpty(){
		return rot.val == null;
		}
	
	// Notkun: b = T.contains()
	// Fyrir: Ekkert
	// Eftir: b = true þþaa T hefur hnút með gildið val.
	public boolean contains(E val){
		if(rot.val == null) return false;
		if(rot.val.compareTo(val) < 0){
			BST<E> S = new BST<E>();
			S.rot = rot.left;
			return S.contains(val); 
			}
		if(rot.val.compareTo(val) > 0){
			BST<E> S = new BST<E>();
			S.rot = rot.right;
			return S.contains(val);
			}
		return true;
		}
	
	// Notkun: s = T.toString()
	// Fyrir: Ekkert
	// Eftir: s er strengur sem inniheldur strengina sem geymdir eru í T í stafrófsröð.
	public String toString(){
		if(rot.val == null) return "";
		BST<E> R = new BST<E>(), L = new BST<E>();
		R.rot = rot.right;
		L.rot = rot.left;
		return R.toString() + "\n" + rot.val + "\n" + L.toString();
		}
			
	// Notkun: main()
	// Fyrir: Ekkert
	// Eftir: 
	public static void main(String[] args){
		BST<Interval> T = new BST<Interval>();
		Interval I = new Interval(5,2);
		Interval J = new Interval(1,5);
		Interval K = new Interval(2,2);
		Interval L = new Interval(5,0);
		Interval M = new Interval(-1,22);
		T.insert(I);
		T.insert(J);
		T.insert(K);
		T.insert(L);
		T.insert(M);
		System.out.println(T);
		}
} 
