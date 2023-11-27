package uta.cse.cse3310.JSBSimEdit.utils;

public class CharacterUtil {

   private CharacterUtil() {
   }

   /**
    * This function use to check if a character is uppercase or not.
    *
    * @param character the character to check.
    * @return true if the character is uppercase, false otherwise.
    */
   public static boolean isUpperCase(char character) {
      return character > 'A' - 1 && character < 'Z' + 1;
   }
}
