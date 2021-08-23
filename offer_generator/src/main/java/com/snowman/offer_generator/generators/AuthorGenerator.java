package com.snowman.offer_generator.generators;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class AuthorGenerator {

    private List<String> authors;
    private Random random;

    {
        authors = createAuthorsList();
        random = new Random();
    }

    public List<String> createAuthorsList() {
        return Arrays.asList("Tom", "Jerry", "Emily", "Michael", "Andrew", "Sarah", "Elizabeth", "David", "Alexander",
                "James", "Victoria", "Jessica", "Jennifer", "John", "Kevin", "Jack", "Luke", "Kyle", "Alex");
    }

    public String getRandomAuthor() {
        return authors.get(random.nextInt(authors.size()));
    }
}
