package com.anyi.door.test_utils;

public class Model
{

    String question; // hold the question

    int current = NONE; // hold the answer picked by the user, initial is NONE(see below)

    public int getCurrent()
    {
        return current;
    }

    public static final int NONE = 1000; // No answer selected

    public static final int ANSWER_ONE_SELECTED = 0; // first answer selected

    public static final int ANSWER_TWO_SELECTED = 1; // second answer selected


    public Model(String question)
    {
        this.question = question;
    }


}
