package br.com.quizcraft.question.builder;

import br.com.quizcraft.question.model.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OptionBuilder {

    public static List<Option> buildOption(List<Map<String, String>> options) {
        List<Option> optionsList = new ArrayList<>();

        options.forEach(option -> {
            optionsList.add(
                Option.builder()
                .option(option.get("option"))
                .text(option.get("text"))
                .build()
            );
        });

        return optionsList;
    }
}
