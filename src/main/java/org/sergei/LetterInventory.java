package org.sergei;

import java.util.Arrays;

/**
 * Created by Sergei Doroshenko on 04.08.2015.
 */
public class LetterInventory {
    private static int instanceCount;
    private char[] inventory;

    public LetterInventory() {
        instanceCount++;
    }

    public LetterInventory(char[] chars) {
        this();
        this.inventory = chars;
        Arrays.sort(this.inventory);
    }

    public LetterInventory(String s) {
        this();
        String temp = s.toLowerCase().replaceAll("[\\u0001-\\u003f]*", ""); // 'u0001'-'u003f'
        this.inventory = temp.toCharArray();
        Arrays.sort(this.inventory);
    }

    public static int getInstanceCount() {
        return instanceCount;
    }

    public static void resetInstanceCount() {
        instanceCount = 0;
    }

    public char[] getInventory() {
        return inventory;
    }

    public void setInventory(char[] inventory) {
        this.inventory = inventory;
    }

    public void add(LetterInventory li) {
        char[] newInventory = new char[this.inventory.length + li.getInventory().length];

        System.arraycopy(this.inventory, 0, newInventory, 0, this.inventory.length);

        System.arraycopy(li.getInventory(), 0, newInventory, this.inventory.length, li.getInventory().length);

        Arrays.sort(newInventory);

        this.inventory = newInventory;
    }

    public void add(String s) {
        add(new LetterInventory(s));
    }

    private char[] substractCharArrays(char[] arr1, char[] arr2) {

        if (arr1.length < arr2.length) {
            return null;
        }

        int substrCount = 0;
        char[] copy = new char[arr1.length]; // create copy of arr1
        System.arraycopy(arr1, 0, copy, 0, arr1.length);
        // replace all equals chars from arr2 in arr1 to empty char
        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j < copy.length; j++) {
                if (arr2[i] == copy[j]) {
                    copy[j] = '\u0000';
                    substrCount++; // counts replacements or matches
                    break;
                }
            }
        }

        if (substrCount < arr2.length) { // if matches is less than arr2.length, we couldn't subtract arrays
            return null;
        }

        // Create new array for result
        char[] newArr = new char[copy.length - substrCount];
        int index = 0;
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] != '\u0000') {
                newArr[index++] = copy[i];
            }
        }
        return newArr;
    }

    public LetterInventory substract(LetterInventory li) {
        return new LetterInventory(substractCharArrays(this.inventory, li.getInventory()));
    }

    public LetterInventory substract(String s) {
        return substract(new LetterInventory(s));
    }

    public boolean isEmpty() {
        return this.inventory.length == 0;
    }

    public int size() {
        return this.inventory.length;
    }

    public boolean contains(String s) {
        if (s.length() > this.inventory.length) {
            return false;
        }

        // Create new char array of inventory length
        char[] arr1 = new char[this.inventory.length];
        // Copy inventory to new char array
        System.arraycopy(this.inventory, 0, arr1, 0, this.inventory.length);
        // Get char array from word
        char[] arr2 = s.toLowerCase().toCharArray();

        return substractCharArrays(arr1, arr2) == null ? false : true;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.inventory);
    }
}
