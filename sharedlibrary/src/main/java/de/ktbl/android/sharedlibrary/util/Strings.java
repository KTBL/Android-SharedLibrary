package de.ktbl.android.sharedlibrary.util;

import java.util.regex.Pattern;

public class Strings {

    private Strings() {
        // No Constructor needed
    }


    private static final Pattern puncutationPattern = Pattern.compile("\\p{Punct}");

    /**
     * Shortens a string to a given length.
     * @param orig text to be shortened
     * @param len the target length of content to show
     * @param withDots whether three dots should be added to the end of the
     *                 shortened string or not.
     * @return the first {@code len} characters of {@code orig} if {@code withDots} is {@code false},
     *          else the first {@code len - 3} characters of {@code orig} with three dots at the end.
     */
    public static String shorten(String orig, int len, boolean withDots) {
        if (orig.length() > len) {
            String partial = orig.substring(0, len);
            // but we don't want to have a break within a word
            // therefore we have to get to the word before
            if (orig.charAt(len) != ' ') {
                int lastSpace = partial.lastIndexOf(' ');
                if (lastSpace != 39) {
                    partial = partial.substring(0, lastSpace);
                }
            }

            if (withDots) {
                // If we're going to add three dots at the end, we have to remove any other
                // dots, commas, and stuff first.
                String lastChar = partial.substring(partial.length() - 1);
                if(Strings.puncutationPattern.matcher(lastChar).matches()){
                    partial = partial.substring(0, partial.length() - 1);
                }
                partial += "...";
            }
            return partial;
        } else {
            return orig;
        }
    }

}
