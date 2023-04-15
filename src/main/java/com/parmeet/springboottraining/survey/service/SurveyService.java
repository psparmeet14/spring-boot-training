package com.parmeet.springboottraining.survey.service;

import com.parmeet.springboottraining.survey.api.mappers.SurveyMapper;
import com.parmeet.springboottraining.survey.api.models.SurveyDTO;
import com.parmeet.springboottraining.survey.repository.SurveyRepository;
import com.parmeet.springboottraining.survey.repository.models.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public List<SurveyDTO> retrieveAllSurveys() {
        return surveyRepository.retrieveAllSurveys().stream()
                .map(SurveyMapper::mapToDTO)
                .toList();
    }

    public SurveyDTO retrieveSurveyById(int surveyId) {
        Optional<SurveyDTO> optionalSurvey = surveyRepository.retrieveAllSurveys().stream()
                .filter(survey -> survey.getId() == surveyId)
                .map(SurveyMapper::mapToDTO)
                .findFirst();
        return optionalSurvey.orElse(null);
    }

    public List<Question> retrieveAllSurveyQuestions(int surveyId) {
        var survey = retrieveSurveyById(surveyId);
        if (survey == null)
            return null;
        return survey.questions();
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
        //question.setId(7);
        return surveyRepository.addNewSurveyQuestion(surveyId, question);
    }

    public void updateSurveyQuestion(int surveyId, int questionId, Question question) {
        surveyRepository.updateSurveyQuestion(surveyId, questionId, question);
    }

    public void deleteSurveyQuestion(int surveyId, int questionId) {
        surveyRepository.deleteSurveyQuestion(surveyId, questionId);
    }
}
