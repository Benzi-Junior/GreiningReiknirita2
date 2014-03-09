//~ Við keyrum prufurorritið með skipuninni
//~ time java PrufuForrit < io/s1.in | diff -w io/s1.out - | wc -l

public class PrufuForrit {
    
    // Notkun: s = treeToString(T);
    // Fyrir: (ekkert)
    // Eftir: s er strengurinn "[]" ef T er null, annars er s strengurinn T.toString().
    private static String treeToString( AVLIntervalTree T ) {
        if (T==null) return "[]";
        else return T.toString();
    }
    
    public static void main(String[] args) {
        //~ Skanni sem les inn tölurnar
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        //~ tree==null sem þýðir að tree vísar í tómt tré.
        AVLIntervalTree tree = null;
        
        //~ F: Búið er að lesa inn núll línur af staðalinntaki.
        while (scanner.hasNext()) {
            //~ I: Búið er að lesa inn núll eða fleiri línur af staðalinntaki og gera viðeigandi aðgerðir með þær.
            String line = scanner.nextLine(); //tekur inntak fram að næsta enter
            String[] S = line.split(" "); //býr til fylki af orðunum í línunni (skipt eftir bilum)
            
            //~ Skoðum núna hvert fyrsta orðið í línunni er.
            switch (S[0]) {
                case  "+": assert(S.length==3); // "+ a b"
                            tree = AVLIntervalTree.insert( tree, new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) );
                            break;
                case  "-": assert(S.length==3); // "- a b"
                            tree = AVLIntervalTree.delete( tree, new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) );
                            break;
                case "?o": assert(S.length==3); // "?o a b"
                            System.out.println( treeToString( AVLIntervalTree.intersectInterval( tree , new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) ) ) );
                            break;
                case "?i": assert(S.length==3); // "?i a b"
                            System.out.println( treeToString( AVLIntervalTree.containInterval( tree , new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) ) ) );
                            break;
                case "?p": assert(S.length==2); // "?p a"
                            System.out.println( treeToString( AVLIntervalTree.containInteger( tree , Integer.parseInt(S[1]) ) ) );
                            break;
                default: System.out.println("Input line in wrong format. Exiting now."); System.exit(0); break;
            }
        }
        //~ E: Búið er að lesa inn allar línur af staðalinntaki og gera viðeigandi aðgerðir með þær.
    }
}