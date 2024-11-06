package com.hmydk.aigit.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants
 *
 * @author hmydk
 */
public class Constants {

    public static final String NO_FILE_SELECTED = "No file selected";
    public static final String GENERATING_COMMIT_MESSAGE = "Generating...";
    public static final String TASK_TITLE = "Generating commit message";

    public static final String[] languages = {"English", "Chinese", "Japanese", "Korean", "French", "Spanish",
            "German", "Russian", "Arabic", "Portuguese"};

    public static final String PROJECT_PROMPT_FILE_NAME = "commit-prompt.txt";
    public static final String PROJECT_PROMPT = "Project Prompt";
    public static final String CUSTOM_PROMPT = "Custom Prompt";

    public static String[] getAllPromptTypes() {
        return new String[]{PROJECT_PROMPT, CUSTOM_PROMPT};
    }

    public static final String[] LLM_CLIENTS = {"Gemini", "Ollama"};

    public static final Map<String, String[]> CLIENT_MODULES = new HashMap<>() {
        {
            put("Gemini", new String[]{"gemini-1.5-flash-latest", "gemini-1.5-flash"});
//            put("OpenAI", new String[] { "gpt-3.5-turbo", "gpt-4", "gpt-4-turbo" });
            put("Ollama", new String[]{"llama2", "mistral", "qwen2.5:14b"});
        }
    };

}
