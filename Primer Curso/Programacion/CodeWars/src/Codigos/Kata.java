package Codigos;

public class Kata {
    public static String reverseWords(final String original) {
        if (original == null || original.isEmpty()) {
            return original;
        }

        StringBuilder result = new StringBuilder();
        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);

            if (c == ' ') {
                result.append(currentWord.reverse());
                result.append(" ");
                currentWord.setLength(0);
            } else {
                currentWord.append(c);
            }
        }

        result.append(currentWord.reverse());

        return result.toString();
    }
}