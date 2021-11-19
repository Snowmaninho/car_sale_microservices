package com.snowman.offer_generator.generators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

// generate author
@Component
@Slf4j
public class AuthorGenerator {

    private List<String> authors;
    private Random random;

    {
        authors = createAuthorsList();
        random = new Random();
    }

    public List<String> createAuthorsList() {
        return Arrays.asList("Tom", "Jerry", "Emily", "Michael", "Andrew", "Sarah", "Elizabeth", "David", "Alexander",
                "James", "Victoria", "Jessica", "Jennifer", "John", "Kevin", "Jack", "Luke", "Kyle", "Alex", "Anthony",
                "Ashley", "Carl", "Clinton", "Derek", "Donald", "Douglas", "Felix", "Gareth", "Valentine", "Sylvester");
    }

    // take random author from list
    public String getRandomAuthor() {
        String result = authors.get(random.nextInt(authors.size()));
        log.info("IN getRandomAuthor - created author: " + result);
        return result;
    }
}
