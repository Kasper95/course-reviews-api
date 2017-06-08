package com.kasperskove.core;

import com.kasperskove.course.Course;
import com.kasperskove.course.CourseRepository;
import com.kasperskove.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private final CourseRepository courses;

    @Autowired
    public DatabaseLoader(CourseRepository courses) {
        this.courses = courses;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String[] templates = {
            "Up and running with %s",
            "%s basics",
            "%s for beginners",
            "Intermediate %s",
            "Advanced %s"
        };

        String[] buzzwords = {
            "Spring REST data",
            "Java 9",
            "Scala",
            "Groovy",
            "Hibernate"
        };

        List<Course> listOfCourses = new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i -> {
                    String template = templates[i % templates.length];
                    String buzzword = buzzwords[i % buzzwords.length];
                    String title = String.format(template, buzzword);
                    Course c = new Course(title, "https://example.com");
                    c.addReview(new Review(i, String.format("More %s please!", buzzword)));
                    listOfCourses.add(c);
                });
        courses.save(listOfCourses);
    }
}
