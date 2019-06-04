package de.digiwill.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegexMatcherTest {

    @Test
    public void isValidEmailAddress_Success() {
        assertTrue(RegexMatcher.isValidEmailAddress("example@mail.com"));
        assertTrue(RegexMatcher.isValidEmailAddress("example@subdomain.mail.com"));
        assertTrue(RegexMatcher.isValidEmailAddress("example@mail.ch"));
        assertTrue(RegexMatcher.isValidEmailAddress("example.12_email@mail-server.com"));
        assertTrue(RegexMatcher.isValidEmailAddress("example@1.1.1.1"));
        assertTrue(RegexMatcher.isValidEmailAddress("12@mail.com"));
    }

    @Test
    public void isValidEmailAddress_Failure() {
        assertFalse(RegexMatcher.isValidEmailAddress("example at mail.com"));
        assertFalse(RegexMatcher.isValidEmailAddress("example@mail@mail.com"));
        assertFalse(RegexMatcher.isValidEmailAddress("@mail.com"));
        assertFalse(RegexMatcher.isValidEmailAddress("example@mail"));
        assertFalse(RegexMatcher.isValidEmailAddress("example@mail,com"));
        assertFalse(RegexMatcher.isValidEmailAddress("exam,ple@mail.com"));
        assertFalse(RegexMatcher.isValidEmailAddress(""));
    }

    @Test
    public void isValidMultipleEmailAddress_Success() {
        assertTrue(RegexMatcher.isValidMultipleEmailAddress("example@mail.com"));
        assertTrue(RegexMatcher.isValidMultipleEmailAddress("example@mail.com, example1@mail.com"));
        assertTrue(RegexMatcher.isValidMultipleEmailAddress("example@mail.com,example1@mail.com"));
    }

    @Test
    public void isValidMultipleEmailAddress_Failure() {
        assertFalse(RegexMatcher.isValidMultipleEmailAddress(""));
        assertFalse(RegexMatcher.isValidMultipleEmailAddress("example@mail.com, "));
        assertFalse(RegexMatcher.isValidMultipleEmailAddress("example@mail.com, example@mail.com example@mail.com"));
    }

    @Test
    public void isValidPassword_Success() {
        assertTrue(RegexMatcher.isValidPassword("Upp3ercase!"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1!"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1-"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1\""));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1?"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1."));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1,"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1#"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1@"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1$"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1§"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1%"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1&"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1/"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1("));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1)"));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1="));
        assertTrue(RegexMatcher.isValidPassword("Aabcde1-"));
        assertTrue(RegexMatcher.isValidPassword("!§$%&()Aa2"));
    }

    @Test
    public void isValidPassword_Failure() {
        assertFalse(RegexMatcher.isValidPassword("12345678"));
        assertFalse(RegexMatcher.isValidPassword("Aa1!"));
        assertFalse(RegexMatcher.isValidPassword("Aabcd1!"));
        assertFalse(RegexMatcher.isValidPassword("aabcde1!"));
        assertFalse(RegexMatcher.isValidPassword("Aabcde1a"));
        assertFalse(RegexMatcher.isValidPassword("AabcdeE!"));
        assertFalse(RegexMatcher.isValidPassword("abcedfgh"));
        assertFalse(RegexMatcher.isValidPassword("!\"§$%&()"));
    }

    @Test
    public void isIFTTTUrl_Success() {
        assertTrue(RegexMatcher.isIFTTTUrl("https://maker.ifttt.com/trigger/EventName/with/key/a1b2c3d4e5f6g7"));
        assertTrue(RegexMatcher.isIFTTTUrl("https://maker.ifttt.com/trigger/a/with/key/a1b2c3d4e5f6g7"));
    }

    @Test
    public void isIFTTTUrl_Failure() {
        assertFalse(RegexMatcher.isIFTTTUrl(""));
        assertFalse(RegexMatcher.isIFTTTUrl("example.com"));
        assertFalse(RegexMatcher.isIFTTTUrl("https://maker.ifttt.com/trigger"));
        assertFalse(RegexMatcher.isIFTTTUrl("https://maker.ifttt.com/trigger/with/key/a1b2c3d4e5f6g7"));
        assertFalse(RegexMatcher.isIFTTTUrl("https://maker.ifttt.com/trigger/Event/with/key/"));
    }
}