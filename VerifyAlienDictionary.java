import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


/**
 * LeetCode 953. Verifying an Alien Dictionary
 * https://leetcode.com/problems/verifying-an-alien-dictionary/
 */
public class VerifyAlienDictionary {


    /**
     * Return true if and only if the given words 
     * are sorted lexicographicaly in this alien language.
     * 
     * Solution accepted by LeetCode.
     */
    static boolean isAlienSorted1(String[] words, String order) {

        // **** ****
        int[] index = new int[26];
        for (int i = 0; i < order.length(); ++i)
            index[order.charAt(i) - 'a'] = i;

        // ???? ????
        // System.out.println("<<< index: " + Arrays.toString(index));

        // **** ****
        search: for (int i = 0; i < words.length - 1; ++i) {

            String word1 = words[i];
            String word2 = words[i+1];

            // ???? ????
            // System.out.println("<<< word1: " + word1);
            // System.out.println("<<< word2: " + word2);

            // Find the first difference word1[k] != word2[k].
            for (int k = 0; k < Math.min(word1.length(), word2.length()); ++k) {

                // ???? ????
                // System.out.println("<<< " + word1.charAt(k) + " != " + word2.charAt(k));

                // **** ****
                if (word1.charAt(k) != word2.charAt(k)) {

                    // ???? ????
                    // System.out.println("<<< " + index[word1.charAt(k) - 'a'] + " > " +
                    //                 index[word2.charAt(k) - 'a']);

                    // If they compare badly, it's not sorted.
                    if (index[word1.charAt(k) - 'a'] > index[word2.charAt(k) - 'a'])
                        return false;
                    continue search;
                }
            }

            // **** if we didn't find a first difference, the words are like ("app", "apple") ****
            if (word1.length() > word2.length())
                return false;
        }

        // **** words in ascending order ****
        return true;
    }


    /**
     * Return true if and only if the given words 
     * are sorted lexicographicaly in this alien language.
     * 
     * Solution accepted by LeetCode.
     */
    static boolean isAlienSorted2(String[] words, String order) {

        // **** ****
        if (words == null || words.length == 0 || order == null || order.length() == 0)
            return false;

        // **** ****
        Comparator<String> comparator = new Comparator<String>(){
            @Override
            public int compare(String str1, String str2) {
                for (int i = 0; i < Math.min(str1.length(), str2.length()); i++) {
                    char a = str1.charAt(i), b = str2.charAt(i);
                    if (a != b) {
                        if (order.indexOf(a) > order.indexOf(b)) return 1;
                        else return -1;
                    }
                }
                if (str1.length() > str2.length()) return 1;
                else if (str1.length() == str2.length()) return 0;
                else return -1;
            }
        };
        PriorityQueue<String> pq = new PriorityQueue<>(words.length, comparator);
        for (String s: words) pq.offer(s);
        int index = 0;

        // **** ****
        while (!pq.isEmpty()) {
            if (!pq.poll().equals(words[index++]))
                return false;
        }

        return true;
    }


    /**
     * Return true if and only if the given words 
     * are sorted lexicographicaly in this alien language.
     * 
     * !!!! My solution rejected by LeetCode !!!!
     */
    static boolean isAlienSorted(String[] words, String order) {

        // **** sanity checks ****
        if (words.length < 2)
            return true;

        // **** initialization ****
        char[] chars                    = order.toCharArray();
        HashMap<Character, Integer> hm  = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            // hm.put(chars[i], 0  + i);
            hm.put(chars[i], 97 + i);
        }

        // ???? ????
        System.out.println("<<< hm: " + hm.toString());

        // **** traverse array of words comparing adjacent words ****
        for (int i = 0; i < words.length - 1; i++) {

            // **** for ease of use ****
            char[] ca1 = words[i].toCharArray();
            char[] ca2 = words[i + 1].toCharArray();

            // ???? ????
            System.out.println("<<< ca1: " + Arrays.toString(ca1));
            System.out.println("<<< ca2: " + Arrays.toString(ca2));

            // **** compare adjacent words ****
            for (int j = 0; j < Math.min(ca1.length, ca2.length); j++) {

                // ???? ????
                if (hm.get(ca1[j]) <= hm.get(ca2[j]))
                    System.out.println("<<< j: " + j + " " + hm.get(ca1[j]) + " <= " + hm.get(ca2[j]));
                else 
                    System.out.println("<<< j: " + j + " " + hm.get(ca1[j]) + " > " + hm.get(ca2[j]));

                // **** compare characters ****
                if (hm.get(ca1[j]) > hm.get(ca2[j])) {

                    // ???? ????
                    System.out.println("<<< " + hm.get(ca1[j]) + " > " + hm.get(ca2[j]));

                    // **** ****
                    return false;
                }
            }

            // **** compare word length ****
            if (ca1.length > ca2.length)
                return false;
        }

        // **** words are in scending order ****
        return true;
    }


    /**
     * Test scaffolding.
     * 
     * !!! NOT PART OF THE SOLUTION !!!
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read alien words ****
        String[] words = br.readLine().trim().split(",");

        // **** read dictionary ****
        String order = br.readLine().trim();

        // **** close buffered reader ****
        br.close();


        // ???? ????
        System.out.println("main <<<  words: " + Arrays.toString(words));
        System.out.println("main <<<  order: " + order);
        System.out.println("main <<< words[0].compareTo(words[1]): " + words[0].compareTo(words[1]));
        for (int i = 0; i < Math.min(words[0].length(), words[1].length()); i++) {

            // ???? ????
            int v1      = words[0].charAt(i);
            int v2      = words[1].charAt(i);
            int diff    = v1 - v2;

            // ???? ????
            System.out.println("main <<<    i: " + i + " " + words[0].charAt(i) + "," + words[1].charAt(i));
            System.out.println("main <<< diff: " + diff);
        }


        // **** check and display result ****
        System.out.println("main <<<  isAlienSorted: " + isAlienSorted(words, order));

        // **** check and display result ****
        System.out.println("main <<< isAlienSorted1: " + isAlienSorted1(words, order));

        // **** check and display result ****
        System.out.println("main <<< isAlienSorted2: " + isAlienSorted2(words, order));

    }
}