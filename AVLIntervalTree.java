class AVLIntervalTree
{
	private Interval Value;
	private int height;
	private int Max;
	private int Min;
	private AVLIntervalTree left;
	private AVLIntervalTree right;
	private AVLIntervalTree parent;
	
	// Fastayrðing gagna:
	//    AVL tré er tilvísun á hlut af þessu tagi.
	//    Tóm tilvísun (null) stendur fyrir tómt tré.
	//    Ef tilvísunin er ekki tóm vísar hún á hlut
	//    sem stendur fyrir rót trésins.  Tréð uppfyllir
	//    annars vegar tvíleitartrjáaskilyrði:
	//       Öll gildi í vinstra undirtré eru alltaf (fyrir
	//       öll undirtré) <= rótargildi og öll gildi í
	//       hægra undirtré eru >= rótargildi.
	//    Og hins vegar AVL skilyrði:
	//       Tilviksbreytan height inniheldur hæð heildartrésins,
	//       mismunur hæða undirtrjáa er í mesta lagi 1.
	//    Þar sem hæð tóms trés er skilgreint sem 0.
	
	
	
	
	// Hlutir af tagi NodeHandler eru hlutir sem meðhöndla hnút í
	// tré á einhvern hátt.
	public interface NodeHandler
	{
		// Notkun: handler.handle(node);
		// Fyrir:  node er ekki null
		// Eftir:  Búið er að meðhöndla hnútinn node á viðeigandi
		//         hátt, hver sem sá háttur er.
		public void handle( AVLIntervalTree node );
	}
	
	private static int maxof3( int a, int b, int c ){
		int max = a;
		if( max < b ) max = b;
		if( max < c ) max = c;
		return max;		
		}
		
	private static int minof3( int a, int b, int c ){
		int min = a;
		if( min > b ) min = b;
		if( min > c ) min = c;
		return min;
		}
	
	private static void updateMax( AVLIntervalTree org ){
		int a, b;
		a = Integer.MIN_VALUE;
		b = a;
		if( org.left != null ) a = org.left.Max;
		if( org.right != null ) b = org.right.Max;
		org.Max = maxof3( a, b, org.Value.max() );
		}
		
	private static void updateMin( AVLIntervalTree org ){
		int a, b;
		a = Integer.MAX_VALUE;
		b = a;
		if( org.left != null ) a = org.left.Min;
		if( org.right != null ) b = org.right.Min;
		org.Min = minof3( a, b, org.Value.min() );
		}


		
	public static  String SearchInterval( AVLIntervalTree tre,Interval bil)
	{
		BST<Interval> skil = new BST<Interval>();
		findIntersecting(tre, bil, skil);
		return skil.toString();	
	}

	private static void findIntersecting( AVLIntervalTree tre, Interval bil, BST skil)
	{
		if(tre.Value.intersects(bil))
			skil.insert(tre.Value);
		if(tre.left!= null && tre.left.Max>= bil.min()&& tre.left.Min <= bil.max())
			tre.left.findIntersecting(tre.left, bil ,skil);
		if(tre.left!= null && tre.right.Max>= bil.min()&& tre.right.Min <= bil.max())
			tre.right.findIntersecting(tre.right, bil ,skil);
	}
	
	// Notkun: tree = rotateLL( tree );
	// Fyrir: tree hefur rót og vinstra barn.
	// Eftir: Búið er að snúa tree og uppfæra hæðir miðað við eftirfarandi mynd
	//			og skila tilvísun á nýju rótina.
	//          x            y
	//         / \          / \
	//        y   C    =>  A   x
	//       / \              / \
	//      A   B            B   C
	private static AVLIntervalTree rotateLL( AVLIntervalTree tree ){
		AVLIntervalTree x = tree, y = x.left;
		x.left = y.right;
		x.height = height(x.left,x.right);
		updateMax(x);
		updateMin(x);
		y.right = x;
		y.height = height(y.left,x);
		updateMax(y);
		updateMin(y);
		return y;
	}
		
	
	// Notkun: tree = rotateLR( tree );
	// Fyrir: tree hefur rót og vinstra barn sem hefur hægra barn.
	// Eftir: Búið er að snúa tree og uppfæra hæðir miðað við eftirfarandi mynd
	//			og skila tilvísun á nýju rótina.
	//          x                   z
	//         / \                 / \
	//        y   D              y     x
	//       / \         =>     / \   / \
	//      A   z              A   B C   D
	//         / \
	//        B   C
	private static AVLIntervalTree rotateLR( AVLIntervalTree tree ){
		AVLIntervalTree x = tree, y = x.left, z = y.right;
		x.left = z.right;
		x.height = height(x.left,x.right);
		updateMax(x);
		updateMin(x);
		y.right = z.left;
		y.height = height(y.left,y.right);
		updateMax(y);
		updateMin(y);
		z.right = x;
		z.left = y;
		z.height = height(y,x);
		updateMax(z);
		updateMin(z);
		return z;
	}
	
	// Notkun: tree = rotateRR( tree );
	// Fyrir: tree hefur rót og hægra barn.
	// Eftir: Búið er að snúa tree og uppfæra hæðir miðað við eftirfarandi mynd
	//			og skila tilvísun á nýju rótina.
	//          x              y
	//         / \            / \
	//        A   y    =>    x   C
	//           / \        / \
	//          B   C      A   B	
	private static AVLIntervalTree rotateRR( AVLIntervalTree tree ){
		AVLIntervalTree x = tree, y = x.right;
		x.right = y.left;
		x.height = height(x.left,x.right);
		updateMax(x);
		updateMin(x);
		y.left = x;
		y.height = height(x,y.right);
		updateMax(y);
		updateMin(y);
		return y;
	}
	
	// Notkun: tree = rotateRL( tree );
	// Fyrir: tree hefur rót og hægra barn sem hefur vinstra barn.
	// Eftir: Búið er að snúa tree og uppfæra hæðir miðað við eftirfarandi mynd
	//			og skila tilvísun á nýju rótina.
	//          x                   z
	//         / \                 / \
	//        A   y              x     y
	//           / \     =>     / \   / \
	//          z   D          A   B C   D
	//         / \
	//        B   C
	private static AVLIntervalTree rotateRL( AVLIntervalTree tree ){
		AVLIntervalTree x = tree, y = x.right, z = y.left;
		x.right = z.left;
		x.height = height(x.left,x.right);
		updateMax(x);
		updateMin(x);
		y.left = z.right;
		y.height = height(y.left,y.right);
		updateMax(y);
		updateMin(y);
		z.left = x;
		z.right = y;
		z.height = height(x,y);
		updateMax(z);
		updateMin(z);
		return z;
	}
	
	// Notkun: tree = AVLIntervalTree(I);
	// Fyrir:  
	// Eftir:  tree vísar á eins hnúts AVL tré með gildið I í rótinni.
	private AVLIntervalTree( Interval I )
	{
		Value = I;
		height = 1;
		Max = I.max();
		Min = I.min();
	}
	
	// Notkun: handleNodesInOrder(handler,tree);
	// Fyrir:  handler er ekki null
	// Eftir:  Búið er að meðhöndla alla hnúta í tree á þann hátt sem
	//         handler gerir, í in-order röð.
	public static void handleNodesInOrder( NodeHandler handler, AVLIntervalTree tree )
	{
		if( tree==null ) return;
		handleNodesInOrder(handler,tree.left);
		handler.handle(tree);
		handleNodesInOrder(handler,tree.right);
	}
	
	// Notkun: printTreeInOrder(tree);
	// Eftir:  Búið er að prenta strengina í tree á aðalúttak í
	//         in-order röð, einn í hverja línu.
	static void printTreeInOrder( AVLIntervalTree tree )
	{
		NodeHandler handler = 
			new NodeHandler()
			{
				public void handle( AVLIntervalTree tree )
				{
					System.out.println(""+tree.height+": "+tree.Value+ " max: "+tree.Max+ " min:"+tree.Min );
				}
			};
		handleNodesInOrder(handler,tree);
	}
	
	// Notkun: h = height(tree);
	// Eftir:  h er hæð AVL trésins tree
	static int height( AVLIntervalTree tree )
	{
		if( tree==null ) return 0;
		return tree.height;
	}
	
	// Notkun: h = height(left,right);
	// Eftir:  h er hæð trés með vinstri hluta left og hægri hluta right
	static int height( AVLIntervalTree left, AVLIntervalTree right )
	{
		int leftheight = height(left);
		int rightheight = height(right);
		if( leftheight > rightheight )
			return leftheight+1;
		else
			return rightheight+1;
	}
	
	// Notkun: f = find(tree,I);
	// Eftir:  f er satt ef I er til í tree
	public static boolean find( AVLIntervalTree tree, Interval I )
	{
		if( tree==null )
			return false;
		if( tree.Value.equals(I) )
			return true;
		if( I.compareTo(tree.Value) < 0 )
			return find(tree.left,I);
		else
			return find(tree.right,I);
	}
	
	// Notkun: s = min(tree);
	// Fyrir:  tree er ekki-tómt AVL tré
	// Eftir:  s er minnsti (fremsti) strengur í tree.
	static Interval min( AVLIntervalTree tree )
	{
		if( tree==null ) return null;
		if( tree.left==null ) return tree.Value;
		return min(tree.left);
	}
	
	// Notkun: s = max(tree);
	// Fyrir:  tree er ekki-tómt AVL tré
	// Eftir:  s er stærsti (aftasti) strengur í tree.
	static Interval max( AVLIntervalTree tree )
	{
		if( tree==null ) return null;
		if( tree.right==null ) return tree.Value;
		return max(tree.right);
	}
	
	// Notkun: tree = insert(org,I);
	// Fyrir:  
	// Eftir:  tree er nýja AVL tréð sem út kemur þegar hnúti með gildinu
	//         I er bætt í org tréð
	public static AVLIntervalTree insert( AVLIntervalTree org, Interval I )
	{
		
		if( org==null )
			return new AVLIntervalTree(I);
		
		// Uppfæri org.Max og org.Min
		if( I.max() > org.Max ) org.Max = I.max();
		if( I.min() < org.Min ) org.Min = I.min();
		
		if( I.compareTo(org.Value) < 0 )
		{
			org.left = insert(org.left,I);
			org.left.parent = org;
			
			// org hefur vinstra barn.
			if(height(org.left) > height(org.right) + 1){
				if(I.compareTo(org.left.Value) >= 0) // Vinstra barn org hefur hægra barn.
					org = rotateLR(org);
				else org = rotateLL(org);
			}
			org.height = height(org.left,org.right);
		}
		else if( I.compareTo(org.Value) > 0)
		{
			org.right = insert(org.right,I);
			org.right.parent = org;
			
			// org hefur hægra barn.
			if(height(org.right) > height(org.left) + 1){
				if(I.compareTo(org.right.Value) < 0) // Hægra barn org hefur vinstra barn.
					org = rotateRL(org);
				else org = rotateRR(org);
			}
			org.height = height(org.left,org.right);
		}
		return org;
	}
	
	
	// Notkun: tree = delete(org,I);
	// Fyrir:  
	// Eftir:  tree er nýja AVL tréð sem út kemur þegar hnúti með gildinu
	//         I er eytt í org trénu (ef einhver slíkur hnútur er til,
	//         annars er tree sama tré og org).
	public static AVLIntervalTree delete( AVLIntervalTree org, Interval I )
	{
		
		if( org==null ) return null;
		if( I.equals(org.Value) )
		{
			if( height(org.left) > height(org.right) )
			{
				org.Value = max(org.left);
				org.left = delete(org.left,org.Value);
				
				// Uppfæri org.Max
				updateMax(org);
				updateMin(org);
				
				if( height(org.left) + 1 < height(org.right) ){
					if( height(org.right.right) < height(org.right.left) ) org = rotateRL(org);
					else org = rotateRR(org);
				}
			
				org.height = height(org.left,org.right);
				return org;
			}
			else if( org.right != null )
			{
				org.Value = min(org.right);
				org.right = delete(org.right,org.Value);
				
				// Uppfæri org.Max
				updateMax(org);
				updateMin(org);
								
				if( height(org.right) + 1 < height(org.left) ){
					if( height(org.left.left) < height(org.left.right) ) org = rotateLR(org);
					else org = rotateLL(org);
				}
				
				org.height = height(org.left,org.right);

				return org;
			}
			else
				return null;
		}
		if( I.compareTo(org.Value) < 0 )
		{
			org.left = delete(org.left,I);
			// Uppfæri org.Max
			updateMax(org);
			updateMin(org);
			
			if( height(org.left) + 1 < height(org.right) ){
				if( height(org.right.right) < height(org.right.left) ) org = rotateRL(org);
				else org = rotateRR(org);
			}
			
			org.height = height(org.left,org.right);
		}
		else
		{
			org.right = delete(org.right,I);
			// Uppfæri org.Max
			updateMax(org);
			updateMin(org);
			
			if( height(org.right) + 1 < height(org.left) ){
				if( height(org.left.left) < height(org.left.right) ) org = rotateLR(org);
				else org = rotateLL(org);
			}
				
			org.height = height(org.left,org.right);
		}
		return org;
	}
	
	
	
	// Notkun: b = checkAVL( tree );
	// Fyrir: Ekkert
	// Eftir: b = true ef tree uppfyllir AVL skilyrði.
	//        b = false annars.
	public static boolean checkAVL( AVLIntervalTree tree ){
		if( tree == null ) return true;
		int d = height(tree.left) - height(tree.right);
		return d > -2 && d < 2 && checkAVL(tree.left) && checkAVL(tree.right);
	}
	
	// Notkun: b = checkBST( tree );
	// Fyrir: Ekkert
	// Eftir: b = true ef tree uppfyllir BST skilyrði.
	//        b = false annars.
	public static boolean checkBST( AVLIntervalTree tree ){
		if( tree == null ) return true;
		boolean c = tree.left == null || tree.left.Value.compareTo(tree.Value) <= 0;
		boolean d = tree.right == null || tree.right.Value.compareTo(tree.Value) >= 0;
		return c && d && checkBST( tree.left ) && checkBST( tree.right );
	}
	
	
	public static void main( String[] args ) throws Exception
	{
	
	Interval I1 = new Interval(3,7);
	Interval I2 = new Interval(3,5);
	Interval I3 = new Interval(3,6);
	Interval I4 = new Interval(2,7);
	Interval I5 = new Interval(4,5);
	Interval I6 = new Interval(3,9);
	Interval I7 = new Interval(6,10);
	
	AVLIntervalTree Tree = null;
	Tree = insert(Tree, I1);
	Tree = insert(Tree, I2);
	Tree = insert(Tree, I3);
	Tree = insert(Tree, I4);
	Tree = insert(Tree, I5);
	Tree = insert(Tree, I6);
	Tree = insert(Tree, I7);
	
	System.out.println("AVL: " + checkAVL(Tree));
	System.out.println("BST: " + checkBST(Tree));
	printTreeInOrder(Tree);
	
	Tree = delete(Tree, I1);
	Tree = delete(Tree, I2);
	
	
	System.out.println("AVL: " + checkAVL(Tree));
	System.out.println("BST: " + checkBST(Tree));
	printTreeInOrder(Tree);
	
	System.out.println(SearchInterval(Tree, new Interval(5,6)));	
	System.out.println(SearchInterval(Tree, new Interval(1,3)));	
	System.out.println(SearchInterval(Tree, new Interval(10,10)));	
	/*
		AVLIntervalTree tree = null;
		java.util.Scanner s = new java.util.Scanner(System.in);
		while( s.hasNextLine() )
		{
			// Búið er að lesa núll eða fleiri línur og þær eru
			// í AVL trénu tree.
			String line = s.nextLine();
			tree = insert(tree,line);
			if(!checkBST(tree)) {System.out.println("Not a BST"); break;}
			if(!checkAVL(tree)) {System.out.println("Not an AVL"); break;}
		}
		
		// Prentum allt tréð in-order:
		printTreeInOrder(tree);
		// Prentum það aftur á annan hátt:
		while( tree != null )
		{
			// Búið er að skrifa núll eða fleiri af þeim lesnu línum
			// sem voru fremstar í stafrófsröð, í stafrófsröð.
			// Hinar lesnu línurnar eru í tree.
			String line = min(tree);
			System.out.println(line);
			tree = delete(tree,line);
			if(!checkBST(tree)) {System.out.println("Not a BST"); break;}
			if(!checkAVL(tree)) {System.out.println("Not an AVL"); break;}
		}
			
		System.out.println(" AVL: " + checkAVL(tree) + "\n BST: " + checkBST(tree));
	*/	
	}
}
