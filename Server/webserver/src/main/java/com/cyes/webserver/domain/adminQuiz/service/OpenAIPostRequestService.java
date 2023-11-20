package com.cyes.webserver.domain.adminQuiz.service;

import com.cyes.webserver.domain.adminQuiz.dto.Choices;
import com.cyes.webserver.domain.adminQuiz.dto.openAIDTO;
import com.cyes.webserver.domain.adminQuiz.dto.outNoCheckShortProblemDTO;
import com.cyes.webserver.domain.adminQuiz.entity.noCheckShortProblem;
import com.cyes.webserver.domain.adminQuiz.repository.noCheckProblemRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenAIPostRequestService {
    private final noCheckProblemRepository noCheckProblemRepository;
    private static final String OPENAI_API_KEY = "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";

        public List<String> sendPostword(String word) throws JsonProcessingException {

            log.info("무슨 단어 들어옴?" + word);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Type", "application/json;charset=UTF-8");
            headers.set("Content-Type", "application/json;charset=UTF-8");
//            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

            String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\",\"content\": \" " +
                  word + "와 관련된 단어만 20개를 뽑아줘" + "\"}] }";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            String apiUrl = "https://api.openai.com/v1/chat/completions";

            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            log.info(response.getBody());

            ObjectMapper objectMapper = new ObjectMapper();

            openAIDTO openaidto = null;


            if (response.getStatusCode().is2xxSuccessful()) {
                openaidto = objectMapper.readValue(response.getBody(), openAIDTO.class);
                log.info("openaidto = {}", openaidto);

                Choices[] choices = openaidto.getChoices();
                List<String> returnList = new ArrayList<>();
                for (Choices choice : choices) {

                    returnList.add(choice.getMessage().getContent());
                }
                return returnList;
            } else {
                // Handle error here
                return List.of("API Request Failed");
            }
        }

        @Transactional
    public String makeShortProblem(String searchWord, String word) throws JsonProcessingException {

        System.out.println("설명할거" + searchWord);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

        String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\",\"content\": \"" +
                searchWord + "에 대해 두줄로 설명해줘" + "\"}] }";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String apiUrl = "https://api.openai.com/v1/chat/completions";

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        openAIDTO openaidto = null;

        if (response.getStatusCode().is2xxSuccessful()) {
            openaidto = objectMapper.readValue(response.getBody(), openAIDTO.class);

            Choices[] choices = openaidto.getChoices();
            List<String> returnList = new ArrayList<>();

            for (Choices choice : choices) {
                returnList.add(choice.getMessage().getContent());

                log.info("말해줘" + choice.getMessage().getContent());

                noCheckShortProblem ncst = noCheckShortProblem.builder()
                        .question(choice.getMessage().getContent())
                        .category(word)
                        .build();


                System.out.println("뭐임마" + ncst.getQuestion());

                if (ncst != null) {

                    noCheckProblemRepository.save(ncst);
                } else {

                }

                noCheckShortProblem problem = noCheckProblemRepository.save(ncst);

                if(problem != null) return "Save Successful";

                log.info("성공"+ choice.getMessage().getContent());

            }
        } else {
            // Handle error here
            return "Save Failure";
        }
        return "Save Failure";
    }


    public Page<outNoCheckShortProblemDTO> outNoCheckProblems(Pageable pageable){

        Page<noCheckShortProblem> realProblem = noCheckProblemRepository.findAll(pageable);

        ArrayList<outNoCheckShortProblemDTO> list = new ArrayList<>();

        for(noCheckShortProblem ndto : realProblem){
            

            outNoCheckShortProblemDTO out = outNoCheckShortProblemDTO.builder()
                    .id(ndto.getId())
                    .question(ndto.getQuestion())
                    .category(ndto.getCategory()).build();

            list.add(out);

        }

        return new PageImpl<>(list, realProblem.getPageable(), realProblem.getTotalElements());
    }

    public String noCheckDelete(String id) {
            Optional<noCheckShortProblem> noCheckShortProblem = noCheckProblemRepository.findById(id);
        log.info("id: " + id);
        if (noCheckShortProblem.isPresent()) {

            noCheckProblemRepository.deleteById(id);
            return "삭제 완료";
        } else {
            return "존재하지 않는 객체입니다.";
        }
    }

}
