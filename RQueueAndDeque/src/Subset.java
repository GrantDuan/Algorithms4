public class Subset {

    /**
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        // String[] words = StdIn.readStrings();
        // for (String word : words)
        // rq.enqueue(word);

        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            rq.enqueue(word);
        }

        int k = Integer.parseInt(args[0]);

        System.out.println("% echo ");

        for (String str : rq) {
            if (k > 0) {
                System.out.println(str);
                k--;
            } else
                break;
        }

    }

}
