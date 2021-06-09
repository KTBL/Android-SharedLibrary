package de.ktbl.android.sharedlibrary.util;

import org.junit.Assert;
import org.junit.Test;

public class StringsShortenTest {

    @Test
    public void fullDescriptionTest() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 39; i++) {
            sb.append('A');
        }

        String shortened = Strings.shorten(sb.toString(), 40, true);
        Assert.assertEquals("The whole description should be shown.", sb.toString(),
                            shortened);
    }

    @Test
    public void partialDescriptionWithoutSpaceSearch() {
        String fullDescription = "Hello everyone here, have a nice weekend splitty split";
        String expectedDescription = "Hello everyone here, have a nice weekend...";
        String shortened = Strings.shorten(fullDescription, 40, true);
        Assert.assertEquals("Only a part of the description should be shown.",
                                 expectedDescription, shortened);

    }

    @Test
    public void partialDescriptionWithSpaceSearch() {
        String fullDescription = "Hello everyone here, have a nice week splitty split";
        String expectedDescription = "Hello everyone here, have a nice week...";
        String shortened = Strings.shorten(fullDescription, 40, true);
        Assert.assertEquals("Only a part of the description should be shown.",
                            expectedDescription, shortened);

    }

    @Test
    public void partialDescriptionWithoutSpaceSearchRemoveSign() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 39; i++) {
            sb.append('A');
        }
        String toShorten = sb.toString() + ". ";
        String expected = sb.toString() + "...";
        String shortened = Strings.shorten(toShorten, 40, true);
        Assert.assertEquals("Only a part of the description should be shown.",
                            expected, shortened);

    }

    @Test
    public void partialDescriptionWithoutSpaceSearchNoRemoveSign() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 19; i++) {
            sb.append('A');
        }
        String toShorten = sb.toString() + ". ";
        String expected = sb.toString() + ".";
        String shortened = Strings.shorten(toShorten, 20, false);
        Assert.assertEquals("Only a part of the description should be shown.",
                            expected, shortened);

    }

}