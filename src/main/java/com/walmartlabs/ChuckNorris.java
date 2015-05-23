package com.walmartlabs;

import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class ChuckNorris{

    private static final String JOKE_URL = "http://api.icndb.com/jokes/random";

    private static final String BOARD_URL = "http://corporate.walmart.com/our-story/leadership/board-of-directors/";

    public List<String> run() throws IOException, InterruptedException {
        Elements elements = this.findDirectorElements();
        List<Director> directors = new ArrayList<>();
        for (Element element : elements) {
            directors.add(parseDirector(element));
        }
        return getJokes(directors);
    }
    /**
     * Makes `joke-count` requests to the ChuckNorris joke service in
     * parallel, and returns the jokes in order of length
     */
    public List<String> getJokes(List<Director> directors) throws IOException, InterruptedException {

        final ArrayBlockingQueue queue = new ArrayBlockingQueue(directors.size());
        Semaphore semaphore = new Semaphore(directors.size());

        for (Director director : directors) {
            AsyncHttpClient.BoundRequestBuilder requestBuilder = buildRequest(director);
            semaphore.acquire();
            requestBuilder.execute(new ResponseHandler(queue, semaphore));
        }

        semaphore.acquire(directors.size());

        ArrayList results = new ArrayList(directors.size());
        queue.drainTo(results);
        Collections.sort(results, new StringLengthComparer());

        return results;
    }

    /**
     * Builds a request object for the chuck norris joke service using first and last name
     */
    private AsyncHttpClient.BoundRequestBuilder buildRequest(Director director) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareGet(JOKE_URL);
        requestBuilder.addQueryParam("limitTo", "[nerdy]");
        requestBuilder.addQueryParam("escape", "javascript");
        requestBuilder.addQueryParam("firstName", director.firstName);
        requestBuilder.addQueryParam("lastName", director.lastName);
        return requestBuilder;
    }

    /**
     * Class that handles responses from the chuck norris joke service.
     * Responses will be parsed to joke strings and put onto the results
     * queue, at which point the semaphore will be released
     */
    private static class ResponseHandler extends AsyncCompletionHandler<Response> {
        private final BlockingQueue<String> results;
        private final Semaphore semaphore;

        private ResponseHandler(BlockingQueue<String> results, Semaphore semaphore) {
            this.results = results;
            this.semaphore = semaphore;
        }

        @Override
        public Response onCompleted(Response response) throws Exception {
            String joke = parseResponse(response);
            results.put(joke);
            semaphore.release();
            return response;
        }

        public String parseResponse(Response response) throws IOException {
            String body = response.getResponseBody();
            Gson gson = new Gson();
            JokeResponse jokeResponse = gson.fromJson(body, JokeResponse.class);
            String joke = jokeResponse.value.joke;
            if (joke == null) {
                throw new IllegalStateException("No Joke in response! " + body);
            }
            return joke;
        }

        public class JokeResponse {
            public JokeValue value;
        }

        public class JokeValue {
            public String joke;
        }
    }

    private static class StringLengthComparer implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if (o1.length() == o2.length())
                return 0;
            else if (o1.length() < o2.length())
                return -1;
            else
                return 1;
        }
    }

    public Elements findDirectorElements() throws IOException {
        Document document = Jsoup.parse(new URL(BOARD_URL), 5000);
        Elements links = document.select("h4.execName a");
        return links;
    }

    private Director parseDirector(Element element) {
        String content = element.html().trim();
        String[] names = content.split("\\s");
        return new Director(names[0], names[names.length - 1]);
    }

    private final class Director {
        public final String firstName;
        public final String lastName;
        public Director(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
