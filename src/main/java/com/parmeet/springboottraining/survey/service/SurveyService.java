package com.parmeet.springboottraining.survey.service;

import com.parmeet.springboottraining.survey.api.models.QuestionDTO;
import com.parmeet.springboottraining.survey.api.models.SurveyDTO;
import com.parmeet.springboottraining.survey.repository.SurveyRepository;
import com.parmeet.springboottraining.survey.service.mappers.QuestionMapper;
import com.parmeet.springboottraining.survey.service.mappers.SurveyMapper;
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

    public List<QuestionDTO> retrieveAllSurveyQuestions(int surveyId) {
        var survey = retrieveSurveyById(surveyId);
        if (survey == null)
            return null;
        return survey.getQuestions();
    }

    public QuestionDTO retrieveSpecificSurveyQuestion(int surveyId, int questionId) {
        var questions = retrieveAllSurveyQuestions(surveyId);
        if (questions == null)
            return null;
        return questions.stream()
                .filter(question -> question.getId() == questionId)
                .findFirst().orElse(null);
    }


    public int addNewSurveyQuestion(int surveyId, QuestionDTO questionDTO) {
        var question = QuestionMapper.mapFromDTO(questionDTO);
        question.setSurveyId(surveyId);
        return surveyRepository.addNewSurveyQuestion(surveyId, question);
    }

    public void updateSurveyQuestion(int surveyId, int questionId, QuestionDTO questionDTO) {
        var question = QuestionMapper.mapFromDTO(questionDTO);
        question.setSurveyId(surveyId);
        surveyRepository.updateSurveyQuestion(surveyId, questionId, question);
    }

    public void deleteSurveyQuestion(int surveyId, int questionId) {
        surveyRepository.deleteSurveyQuestion(surveyId, questionId);
    }
}
