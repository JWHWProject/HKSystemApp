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

    public static final int ANSWER_ONE_SELECTED = 1; // first answer selected

    public static final int ANSWER_TWO_SELECTED = 2; // second answer selected
    public static final int ANSWER_THREE_SELECTED = 3; // 3 answer selected
    public static final int ANSWER_FOUR_SELECTED = 4; // 4 answer selected
    public static final int ANSWER_FIVE_SELECTED = 5; // 5 answer selected


    public Model(String question)
    {
        this.question = question;
    }


}
