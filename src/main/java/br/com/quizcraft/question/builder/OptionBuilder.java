package br.com.quizcraft.question.builder;

import br.com.quizcraft.question.model.Option;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OptionBuilder {

    public static List<Option> buildOption(Map<String, String> option) {
        return Collections.singletonList(Option.builder()
            .option(option.get("option"))
            .text(option.get("text"))
            .build());
    }
}
