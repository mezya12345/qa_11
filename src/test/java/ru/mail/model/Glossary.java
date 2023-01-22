package ru.mail.model;

import com.google.gson.annotations.SerializedName;

public class Glossary {

    public String title;
    @SerializedName("gloss_div")
    public GlossDiv glossDiv;

    public static class GlossDiv {
        public String title;
        public Boolean flag;
    }
}
