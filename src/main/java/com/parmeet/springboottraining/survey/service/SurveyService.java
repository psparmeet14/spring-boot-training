package com.parmeet.springboottraining.survey.service;

import com.parmeet.springboottraining.survey.api.models.QuestionDTOV1;
import com.parmeet.springboottraining.survey.api.models.SurveyDTOV1;
import com.parmeet.springboottraining.survey.repository.SurveyRepository;
import com.parmeet.springboottraining.survey.service.mappers.QuestionMapper;
import com.parmeet.springboottraining.survey.service.mappers.SurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * This class represents a service for managing surveys in the system.
 */
@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    
    /**
     * Adds a new survey to the system.
     *
     * @param surveyDTOV1 the survey data transfer object containing the survey details
     * @return the ID of the newly added survey
     */
    @Transactional
    public int addNewSurvey(SurveyDTOV1 surveyDTOV1) {
        var survey = SurveyMapper.mapFromDTO(surveyDTOV1);
        var surveyId = surveyRepository.addNewSurvey(survey);
        surveyDTOV1.setQuestions(null);
        surveyDTOV1.getQuestions()
                .forEach(questionDTOV1 -> addNewSurveyQuestion(surveyId, questionDTOV1));
        return surveyId;
    }

    public int addNewSurveyQuestion(int surveyId, QuestionDTOV1 questionDTOV1) {
        var question = QuestionMapper.mapFromDTO(questionDTOV1);
        question.setSurveyId(surveyId);
        return surveyRepository.addNewSurveyQuestion(surveyId, question);
    }

    public List<SurveyDTOV1> retrieveAllSurveys() {
        return surveyRepository.retrieveAllSurveys()
                .orElse(List.of())
                .stream()
                .map(SurveyMapper::mapToDTO)
                .toList();
    }

    public Optional<SurveyDTOV1> retrieveSurveyById(int surveyId) {
        return surveyRepository.retrieveAllSurveys()
                .orElse(List.of())
                .stream()
                .filter(survey -> survey.getId() == surveyId)
                .map(SurveyMapper::mapToDTO)
                .findFirst();
    }

    public Optional<List<QuestionDTOV1>> retrieveAllSurveyQuestions(int surveyId) {
        var survey = retrieveSurveyById(surveyId);
        return survey.map(SurveyDTOV1::getQuestions);
    }

    public Optional<QuestionDTOV1> retrieveSpecificSurveyQuestion(int surveyId, int questionId) {
        return retrieveAllSurveyQuestions(surveyId)
                .orElse(List.of())
                .stream()
                .filter(question -> question.getId() == questionId)
                .findFirst();
    }

    public void updateSurveyQuestion(int surveyId, int questionId, QuestionDTOV1 questionDTOV1) {
        var question = QuestionMapper.mapFromDTO(questionDTOV1);
        question.setSurveyId(surveyId);
        surveyRepository.updateSurveyQuestion(surveyId, questionId, question);
    }

    public void deleteSurveyQuestion(int surveyId, int questionId) {
        surveyRepository.deleteSurveyQuestion(surveyId, questionId);
    }
}
