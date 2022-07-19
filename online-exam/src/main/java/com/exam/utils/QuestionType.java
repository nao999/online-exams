package com.exam.utils;

/**
 * @ClassName : QuestionType
 * @Description :
 * @Author : y
 * @Date: 2021-07-25 10:21
 */


public class QuestionType {

    public static String getQuestionType(Integer questionType){
        String questionTypeName = "";
        switch (questionType){
            case 1:
                questionTypeName = "单选题";
                break;
            case 2:
                questionTypeName = "多选题";
                break;
            case 3:
                questionTypeName = "判断题";
                break;
            case 4:
                questionTypeName = "填空题";
                break;
            case 5:
                questionTypeName = "简答题";
                break;
            default:
                questionTypeName = "";

        }
        return questionTypeName;
    }
}
