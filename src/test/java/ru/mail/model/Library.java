package ru.mail.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Library {

    public String id;
    @JsonProperty("general_information")
    public GeneralInformation generalInformation;
    public String[] editions;
    public String genre;

    public static class GeneralInformation {
        public String title;
        public String[] authors;
    }
}
