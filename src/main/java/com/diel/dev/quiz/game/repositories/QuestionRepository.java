package com.diel.dev.quiz.game.repositories;

import com.diel.dev.quiz.game.entities.Question;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepository {
    private final List<Question> database = new ArrayList<>();

    public QuestionRepository() {
        initialize();
    }

    public List<Question> retrieve() {
        return new ArrayList<>(database);
    }

    private void initialize() {
        try {
            Resource resource = new ClassPathResource("questions.csv");
            InputStream stream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] question = line.split(",");
                int index = database.size();

                Question mapped = map(index, question);

                database.add(mapped);
            }

            stream.close();
            reader.close();


        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Question map(int index, String[] question) {
        String title = question[0];
        List<String> options = List.of(
                question[1],
                question[2],
                question[3],
                question[4]
        );
        int answer = Integer.parseInt(question[5].trim());

        return new Question(
                index,
                title,
                options,
                answer
        );
    }

}
