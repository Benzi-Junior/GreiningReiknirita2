public class PrufuForritBjarni {
    public static void main(String[] args) {
        //~ private static Scanner scanner = new Scanner(new BufferedInputStream(System.in), charsetName);
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        //~ Dev purposes only
        int countFailures=0;
        
        //~ tree==null sem þýðir að tree vísar í tómt tré.
        AVLIntervalTree tree = null;
        
        //~ F: Búið er að lesa inn núll línur af staðalinntaki.
        while (scanner.hasNext()) {
            //~ I: Búið er að lesa inn núll eða fleiri línu af staðalinntaki og gera viðeigandi aðgerðir með þær.
            String line = scanner.nextLine(); //tekur inntak fram að næsta enter
            String[] S = line.split(" "); //býr til fylki af orðunum í línunni (skipt eftir bilum)
            
            //~ Skoðum núna hvert fyrsta orðið er í línunni.
            switch (S[0]) {
                case  "+": assert(S.length==3); // "+ a b"
                            tree = AVLIntervalTree.insert( tree, new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) );
                            break;
                case  "-": assert(S.length==3); // "- a b"
                            tree = AVLIntervalTree.delete( tree, new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) );
                            break;
                case "?o": assert(S.length==3); // "?o a b"
                            //~ System.out.println("?o");
                            System.out.println( AVLIntervalTree.containIntegererval( tree , new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) ) );
                            break;
                case "?i": assert(S.length==3); // "?i a b"
                            //~ System.out.println("?i");
                            System.out.println( AVLIntervalTree.intersectInterval( tree , new Interval( Integer.parseInt(S[1]) , Integer.parseInt(S[2]) ) ) );
                            break;
                case "?p": assert(S.length==2); // "?p a"
                            //~ System.out.println("?p");
                            System.out.println( AVLIntervalTree.containInteger( tree , Integer.parseInt(S[1]) ) );
                            break;
                default: countFailures++; break;
            }
        }
        //~ E: Búið er að lesa inn allar línur af staðalinntaki og gera viðeigandi aðgerðir með þær.
        
        System.out.println("Fjöldi lína sem ekki gekk að lesa rétt inn: "+countFailures);
    }
}