package com.tegogames.guesstheanimeplace;

public class QuestionsList {

    int imageQuestion;

    private  String option1,option2,option3,option4,answer;
    private  String userSelectedAnswer;
    private  String hint;

    public QuestionsList(int imageQuestion, String option1, String option2, String option3, String option4, String answer, String userSelectedAnswer, String hint) {
        this.imageQuestion = imageQuestion;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.userSelectedAnswer = userSelectedAnswer;
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getImageQuestion() {
        return imageQuestion;
    }

    public void setImageQuestion(int imageQuestion) {
        this.imageQuestion = imageQuestion;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUserSelectedAnswer() {
        return userSelectedAnswer;
    }

    public void setUserSelectedAnswer(String userSelectedAnswer) {
        this.userSelectedAnswer = userSelectedAnswer;
    }
}
