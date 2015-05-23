package com.walmartlabs;

import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChuckNorrisTest {

    @Test
    public void test() throws IOException, InterruptedException {
        ChuckNorris chuckNorris = new ChuckNorris();
        List<String> results = chuckNorris.run();
        for (String joke : results) {
            System.out.println(joke);
        }
    }
}
