package com.walmartlabs;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ChuckNorrisTest {

    @Test
    public void test() throws IOException, InterruptedException {
        ChuckNorris chuckNorris = new ChuckNorris();
        List<String> results = chuckNorris.getJokes(10, "Rich", "Hickey");
        System.out.println("at end " + results);
        for (String joke : results) {
            System.out.println(joke);
        }
    }
}
