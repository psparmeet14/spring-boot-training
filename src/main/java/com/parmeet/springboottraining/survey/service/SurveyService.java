package com.parmeet.springboottraining.survey.service;

import com.parmeet.springboottraining.survey.api.models.QuestionDTO;
import com.parmeet.springboottraining.survey.api.models.SurveyDTOV1;
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

    public Optional<List<QuestionDTO>> retrieveAllSurveyQuestions(int surveyId) {
        var survey = retrieveSurveyById(surveyId);
        return survey.map(SurveyDTOV1::getQuestions);
    }

    public Optional<QuestionDTO> retrieveSpecificSurveyQuestion(int surveyId, int questionId) {
        return retrieveAllSurveyQuestions(surveyId)
                .orElse(List.of())
                .stream()
                .filter(question -> question.getId() == questionId)
                .findFirst();
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
