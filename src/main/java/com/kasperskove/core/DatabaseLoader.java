package com.kasperskove.core;

import com.kasperskove.course.Course;
import com.kasperskove.course.CourseRepository;
import com.kasperskove.review.Review;
import com.kasperskove.user.User;
import com.kasperskove.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private final CourseRepository courses;
    private final UserRepository users;

    @Autowired
    public DatabaseLoader(CourseRepository courses, UserRepository users) {
        this.courses = courses;
        this.users = users;
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

        List<User> students = Arrays.asList(
                new User("jacobproffer", "Jacob",  "Proffer", "password", new String[] {"ROLE_USER"}),
                new User("mlnorman", "Mike",  "Norman", "password", new String[] {"ROLE_USER"}),
                new User("k_freemansmith", "Karen",  "Freeman-Smith", "password", new String[] {"ROLE_USER"}),
                new User("seth_lk", "Seth",  "Kroger", "password", new String[] {"ROLE_USER"}),
                new User("mrstreetgrid", "Java",  "Vince", "password", new String[] {"ROLE_USER"}),
                new User("anthonymikhail", "Tony",  "Mikhail", "password", new String[] {"ROLE_USER"}),
                new User("boog690", "AJ",  "Teacher", "password", new String[] {"ROLE_USER"}),
                new User("faelor", "Erik",  "Faelor Shafer", "password", new String[] {"ROLE_USER"}),
                new User("christophernowack", "Christopher",  "Nowack", "password", new String[] {"ROLE_USER"}),
                new User("calebkleveter", "Caleb",  "Kleveter", "password", new String[] {"ROLE_USER"}),
                new User("richdonellan", "Rich",  "Donnellan", "password", new String[] {"ROLE_USER"}),
                new User("albertqerimi", "Albert",  "Qerimi", "password", new String[] {"ROLE_USER"})
        );

        users.save(students);
        users.save(new User("willrodgers", "Rodgers", "Will", "1234", new String [] {"ROLE_USER", "ROLE_ADMIN"}));

        List<Course> listOfCourses = new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i -> {
                    String template = templates[i % templates.length];
                    String buzzword = buzzwords[i % buzzwords.length];
                    String title = String.format(template, buzzword);
                    Course c = new Course(title, "https://example.com");
                    Review review = new Review((i % 5) + 1, String.format("More %s please!", buzzword));
                    review.setReviewer(students.get(i % students.size()));
                    c.addReview(review);
                    listOfCourses.add(c);
                });
        courses.save(listOfCourses);
    }
}
