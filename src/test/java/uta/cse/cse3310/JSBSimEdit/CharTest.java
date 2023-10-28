package uta.cse.cse3310.JSBSimEdit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * Test specific functions in CharUtils
 */

public class CharTest
{
    @Test
    public void Case()
    {
        assertTrue( CharUtils.isUpper('A') );
        assertTrue( CharUtils.isUpper('B') );
        assertTrue( CharUtils.isUpper('C') );
        assertTrue( CharUtils.isUpper('Z') );
        assertFalse( CharUtils.isUpper('a') );
    }
}
