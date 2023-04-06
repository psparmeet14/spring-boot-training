package com.parmeet.springboottraining.survey.service;

import com.parmeet.springboottraining.survey.repository.models.Question;
import com.parmeet.springboottraining.survey.repository.models.Survey;
import com.parmeet.springboottraining.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;


    public List<Survey> retrieveAllSurveys() {
        return surveyRepository.retrieveAllSurveys();
    }

    public Survey retrieveSurveyById(int surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.retrieveAllSurveys().stream()
                .filter(survey -> survey.getId() == surveyId)
                .findFirst();
        return optionalSurvey.orElse(null);
    }

    public List<Question> retrieveAllSurveyQuestions(int surveyId) {
        var survey = retrieveSurveyById(surveyId);
        if (survey == null)
            return null;
        return survey.getQuestions();
    }

    public Question retrieveSpecificSurveyQuestion(int surveyId, int questionId) {
        var questions = retrieveAllSurveyQuestions(surveyId);
        if (questions == null)
            return null;
        return questions.stream()
                .filter(question -> question.getId() == questionId)
                .findFirst().orElse(null);
    }


    public int addNewSurveyQuestion(int surveyId, Question question) {
        return surveyRepository.addNewSurveyQuestion(surveyId, question);
    }

    public void updateSurveyQuestion(int surveyId, int questionId, Question question) {
        surveyRepository.updateSurveyQuestion(surveyId, questionId, question);
    }

    public void deleteSurveyQuestion(int surveyId, int questionId) {
        surveyRepository.deleteSurveyQuestion(surveyId, questionId);
    }
}
