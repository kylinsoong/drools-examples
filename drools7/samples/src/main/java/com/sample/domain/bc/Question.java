package com.sample.domain.bc;

public class Question {
    
    private QuestionType questionType;
    
    private String foodItem;

    public Question(QuestionType questionType) {
        super();
        this.questionType = questionType;
    }

    public Question(QuestionType questionType, String foodItem) {
        super();
        this.questionType = questionType;
        this.foodItem = foodItem;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

}
