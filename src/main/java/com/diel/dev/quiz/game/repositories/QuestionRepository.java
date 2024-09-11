package com.diel.dev.quiz.game.repositories;

import com.diel.dev.quiz.game.entities.Question;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            File file = new ClassPathResource("questions.csv").getFile();
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            String line;

            while ((line = buffer.readLine()) != null) {
                String[] question = line.split(",");
                int index = database.size();

                Question mapped = map(index, question);

                database.add(mapped);
            }

            reader.close();
            buffer.close();

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
