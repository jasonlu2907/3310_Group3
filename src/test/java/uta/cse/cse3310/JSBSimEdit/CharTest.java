package uta.cse.cse3310.JSBSimEdit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import uta.cse.cse3310.JSBSimEdit.utils.CharacterUtil;

/**
 * Test specific functions in CharacterUtil
 */

public class CharTest
{
    @Test
    public void Case()
    {
        assertTrue( CharacterUtil.isUpperCase('A') );
        assertTrue( CharacterUtil.isUpperCase('B') );
        assertTrue( CharacterUtil.isUpperCase('C') );
        assertTrue( CharacterUtil.isUpperCase('Z') );
        assertFalse( CharacterUtil.isUpperCase('a') );
    }
}
