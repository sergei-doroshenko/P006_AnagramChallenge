package org.sergei;

import java.util.*;

/**
 * Created by Sergei Doroshenko on 04.08.2015.
 */
public class Anagrams {
    private Set<String> dictionary;

    public Anagrams(Set<String> dictionary) {
        if (dictionary == null) {
            throw new IllegalStateException("Dictionary is null");
        }
        this.dictionary = dictionary;
    }

    public Set<String> getWords(String phrase) {
        if (phrase == null) {
            throw new IllegalStateException("Phrase is null");
        }
        Set<String> words = new TreeSet<>();
        LetterInventory li = new LetterInventory(phrase);
        for (String word : dictionary) {
            if (li.contains(word) && (word.length() == 7 || word.length() == 4) ) {
                words.add(word);
            }
        }

        return words;
    }

    public Set<String> getWords(LetterInventory li, List<String> dictionary) {
        if (li == null || dictionary == null) {
            throw new IllegalStateException("Phrase is null");
        }

        Set<String> words = new TreeSet<>();

        for (String word : dictionary) {
            if ( li.contains(word) ) {
                words.add(word);
            }
        }

        return words;
    }

    public void permutation(List<String> chosen, List<String> choices, LetterInventory li, Set<List<String>> result, int max) {

        int n = choices.size();

        if (n == 0 || chosen.size() >= max || li.size() < 4) {
            if (li.isEmpty()) {
                result.add(chosen);
            }

        } else {
            for (int i = 0; i < n; i++) {

                List<String> newChosen = new ArrayList<>(chosen);
                LetterInventory newLi = new LetterInventory(li.getInventory());
                String word = choices.get(i);

                if (li.contains(word)) {
                    newChosen.add(word);
                    newLi = li.substract(word);
                }

                List<String> newChoices = new ArrayList<>(choices);
                newChoices.remove(i);
                newChoices = new ArrayList<>(getWords(newLi, newChoices));
                permutation(newChosen, newChoices, newLi, result, max);
            }
        }

    }
}
