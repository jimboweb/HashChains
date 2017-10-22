import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class HashChains {

    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private List<String> elems;
    // for hash function
    private int bucketCount;
    private int prime = 1000000007;
    private int multiplier = 263;
    
    
    
    
    private long lprime = 1000000007;
    private long lmult = 263;
    private long lbuck;
    private ArrayList<String>[] buckets;
    private static boolean naive = false;
    private static boolean debug = true;
    int queryCount;
    

    public static void main(String[] args) throws IOException {
        if(naive)
            new HashChains().processQueriesNaive();
        else
            new HashChains().processQueries();
            
    }

    public void processQueries() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        lbuck = (long)bucketCount;
        buckets  = new ArrayList[bucketCount];
        for(int i=0; i<buckets.length; i++){
            buckets[i] = new ArrayList<String>();
        }
        queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQuery(readQuery());
        }
        out.close();
    }
    
    private void processQuery(Query query) {
        int hash;
        switch (query.type) {
            case "add":
                hash = lHashFunc(query.s);
                 if (!buckets[hash].contains(query.s))
                    buckets[hash].add(0, query.s);
                 break;
           case "del":
               hash = lHashFunc(query.s);
                if (buckets[hash].contains(query.s))
                    buckets[hash].remove(query.s);
                break;
            case "find":
                hash = lHashFunc(query.s);
                writeSearchResult(buckets[hash].contains(query.s));
                break;
            case "check":
                if(!buckets[query.ind].isEmpty()){
                    for (String cur : buckets[query.ind])
                    out.print(cur + " ");
                }
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }
    
    private int lHashFunc(String input){
        long hs = 0;
        int l = input.length();
        for(int i=l - 1;i>=0;i--){
            hs = (((hs * lmult + (long)input.charAt(i)) % lprime) + lprime) % lprime;
        }
        hs = ((hs % lprime) + lprime) % lprime;
        hs = ((hs % lbuck) + lbuck) % lbuck;
        return (int)hs;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public HashChains() {
    }

    private int hashFunc(String s) {
        long hash = 0;
        for (int i = s.length() - 1; i >= 0; --i)
            hash = (hash * multiplier + s.charAt(i)) % prime;
        return (int)hash % bucketCount;
    }

    private Query readQuery() throws IOException {
        String type = in.next();
        if (!type.equals("check")) {
            String s = in.next();
            return new Query(type, s);
        } else {
            int ind = in.nextInt();
            return new Query(type, ind);
        }
    }

    private void writeSearchResult(boolean wasFound) {
        out.println(wasFound ? "yes" : "no");
        // Uncomment the following if you want to play with the program interactively.
        // out.flush();
    }

    private void processQueryNaive(Query query) {
        switch (query.type) {
            case "add":
                if (!elems.contains(query.s))
                    elems.add(0, query.s);
                break;
            case "del":
                if (elems.contains(query.s))
                    elems.remove(query.s);
                break;
            case "find":
                writeSearchResult(elems.contains(query.s));
                break;
            case "check":
                for (String cur : elems)
                    if (hashFunc(cur) == query.ind)
                        out.print(cur + " ");
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    public void processQueriesNaive() throws IOException {
        elems = new ArrayList<>();
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        int queryCount = in.nextInt();
        for (int i = 0; i < queryCount; ++i) {
            processQueryNaive(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
