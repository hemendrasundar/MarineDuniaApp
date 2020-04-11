package com.sun.marinedunia.Models;

public class QuestionModel {
    private String Question, OptionA, OptionB, OptionC, OptionD, CorrectAns;
    private int setno;

    public QuestionModel()
    {
        //for firebase
    }

    public QuestionModel(String question, String optionA, String optionB, String optionC, String optionD, String correctAns, int setno) {
        Question = question;
        OptionA = optionA;
        OptionB = optionB;
        OptionC = optionC;
        OptionD = optionD;
        CorrectAns = correctAns;
        this.setno = setno;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getOptionA() {
        return OptionA;
    }

    public void setOptionA(String optionA) {
        OptionA = optionA;
    }

    public String getOptionB() {
        return OptionB;
    }

    public void setOptionB(String optionB) {
        OptionB = optionB;
    }

    public String getOptionC() {
        return OptionC;
    }

    public void setOptionC(String optionC) {
        OptionC = optionC;
    }

    public String getOptionD() {
        return OptionD;
    }

    public void setOptionD(String optionD) {
        OptionD = optionD;
    }

    public String getCorrectAns() {
        return CorrectAns;
    }

    public void setCorrectAns(String correctAns) {
        CorrectAns = correctAns;
    }

    public int getSetno() {
        return setno;
    }

    public void setSetno(int setno) {
        this.setno = setno;
    }
}
