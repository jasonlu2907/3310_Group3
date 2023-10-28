package uta.cse.cse3310.JSBSimEdit;
public class CharUtils {

   /* The function isUpper returns true if
 *     argument is an UpperCase letter
 *     */

   public static boolean isUpper (char C) {

      if (C > 'A'-1 && C < 'Z'+1 )
         return true;
      else
         return false;
   }

}
