package org.sergei;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Sergei Doroshenko on 04.08.2015.
 * Here is a couple of important hints to help you out:
 * - An anagram of the phrase is: "poultry outwits ants"
 * - The MD5 hash of the secret phrase is "4624d200580677270a54ccff86b9610e"
 * - The solition is: pastils turnout towy 4624d200580677270a54ccff86b9610e
 */
public class App00_challenge {
    public static final String PHRASE = "poultry outwits ants";
    public static final String HASH = "4624d200580677270a54ccff86b9610e";
    private static final String DICTIONARY_FILE = "wordlist.txt";
    // set to true to test runtime and # of letter inventories created
    private static final boolean TIMING = true;

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();

        // read dictionary into a set
        Scanner input = new Scanner(new File(DICTIONARY_FILE));
        Set<String> dictionary = new TreeSet<>();
        while (input.hasNextLine()) {
            dictionary.add(input.nextLine());
        }
        dictionary = Collections.unmodifiableSet(dictionary);   // read-only

        // create Anagrams object for, well, solving anagrams
        Anagrams solver = new Anagrams(dictionary);

        Set<String> words = solver.getWords(PHRASE);

        System.out.printf("Found %d worlds for anagrams in dictionary.\n", words.size());
        System.out.println(words);

        Set<List<String>> result = new HashSet<>();
        solver.permutation(new ArrayList<String>(), new ArrayList<>(words), new LetterInventory(PHRASE), result, 3);

        System.out.printf("Found %d anagrams.\n", result.size());

        for (List<String> anagram : result) {
            String temp = anagram.get(0) + " " + anagram.get(1) + " " + anagram.get(2);
            String hash = md5Hash(temp);
            if (hash.equals(HASH)) System.out.println("The solutions is: " + temp + ": " + hash);
        }

        long endTime = System.currentTimeMillis();
        // 12247 ms elapsed, 2594392 unique LetterInventory object(s) created
        if (TIMING) {
            long elapsed = endTime - startTime;
            int inventories = LetterInventory.getInstanceCount();
            System.out.println(elapsed + " ms elapsed, " + inventories + " unique LetterInventory object(s) created");
            LetterInventory.resetInstanceCount();
        }
    }

    public static String md5Hash(String message) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytesOfMessage = message.getBytes("UTF-8");
        byte[] thedigest = md.digest(bytesOfMessage);
        String md5 = new BigInteger(1,thedigest).toString(16);
        return md5;
    }
}
