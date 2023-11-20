package com.cyes.webserver.domain.stompSocket.service.result;

import com.cyes.webserver.domain.Answer.repository.AnswerRepository;
import com.cyes.webserver.domain.Answer.service.AnswerService;
import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.problem.dto.response.ProblemResponse;
import com.cyes.webserver.domain.rank.dto.GradingResult;
import com.cyes.webserver.domain.stompSocket.dto.GradingResultPresentResponse;
import com.cyes.webserver.domain.stompSocket.dto.ResultMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import com.cyes.webserver.domain.stompSocket.service.submit.SubmitService;
import com.cyes.webserver.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultService {
    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;
    private final SubmitService submitService;
    private final AnswerService answerService;

    /**
     * client에게 최종 순위를 보내주는 메서드
     * @param quizId (퀴즈id)
     * @param problemResponseList
     * @throws JsonProcessingException
     */
    public void sendResult(Long quizId, List<ProblemResponse> problemResponseList) throws JsonProcessingException {

        // 해당 퀴즈로 제출된 답안 List를 Redis에서 가져온다.
        List<SubmitRedis> list = submitService.getSubmitList(quizId.toString());

        //채점을 완료해서 순서를 매긴 값의 리스트 이다.
        List<GradingResult> gradingResultList = getGradingResultList(list, problemResponseList);

        //채점해서 졍렬한 결과를 상위 n명만 골라서 nickname, 맞은 갯수 넘겨준다.
        List<GradingResultPresentResponse> resultPresentResponseList = gradingPresent(gradingResultList, 3);


        //채점 결과를 담고있는 메시지양
        ResultMessage resultMessage = ResultMessage.builder()
                .quizId(quizId)
                .gradingResultPresentResponseList(resultPresentResponseList)
                .type(SessionMessage.MessageType.RESULT)
                .build();

        //Redis에 publish
        redisTemplate.convertAndSend(channelTopic.getTopic(), resultMessage);

        //Redis에 publish 한 뒤
        //AnswerList로 바꿔서 MongoDB에 flush
        answerService.saveAllSubmitRedis(list);
    }


    private List<GradingResult> getGradingResultList(List<SubmitRedis> answerList, List<ProblemResponse> problemList) {
        //key : memgerId, value : 채점 결과
        Map<Long, GradingResult> resultMap = new HashMap<>();

        for (int i = 0; i < answerList.size(); i++) {
            SubmitRedis answer = answerList.get(i);
            //제출 답안
            String submit = answer.getSubmitContent();

            //memberId
            Long memberId = answer.getMemberId();

            //문제 번호
            Integer problemNumber = answer.getProblemOrder();

            //문제의 정답
            String problemAnswer = problemList.get(problemNumber - 1).getAnswer();

            //채점결과가 존재하지 않다면
            if(!resultMap.containsKey(memberId)){
                resultMap.put(memberId, GradingResult.builder().memberId(memberId).build());
            }

            //제출 답안이랑 정답이랑 같으면
            if (submit.equals(problemAnswer)) {
                GradingResult result = resultMap.get(memberId);

                result.addCorrectCount();
                result.addDuringTime(answer.getDuringTime());

                resultMap.put(memberId, result);
            }
        }
        //전체 참여자를 순위를 매기고 정렬
        List<GradingResult> resultList = new ArrayList(resultMap.values());
        Collections.sort(resultList);


        System.out.println("resultList = " + resultList);

        return resultList;
    }

    private List<GradingResultPresentResponse> gradingPresent(List<GradingResult> gradingResultList, int num) {
        //최종 결과를 보여주는 것 갯수가 참여 멤버보다 크다면 모든 멤버 결과를 보여준다.
        if (num > gradingResultList.size()){num = gradingResultList.size();}

        //채점 결과를 원하는 만큼 자른다.
        List<GradingResult> slicedList = gradingResultList.subList(0, num);

        //member들의 id만 모아놓은 list
        List<Long> memberIdList = new ArrayList<>();
        for (GradingResult gradingResult : slicedList) {
            memberIdList.add(gradingResult.getMemberId());
        }

        //memberIdList로 그 Member들을 가져온다.
        List<Member> findAllMemberList = memberRepository.findAllById(memberIdList);

        //memberId를 key로 memberNickName을 value로 담는다.
        Map<Long, String> memberIdToNickName = new HashMap<>();
        for (Member member : findAllMemberList) {
            memberIdToNickName.put(member.getMemberId(), member.getMemberNickname());
        }

        List<GradingResultPresentResponse> resultPresentResponseList = changeGradingResultPresentResponses(slicedList, memberIdToNickName);

        return resultPresentResponseList;
    }

    private static List<GradingResultPresentResponse> changeGradingResultPresentResponses(List<GradingResult> slicedList, Map<Long, String> memberIdToNickName) {
        //return할 List를 선언한다.
        List<GradingResultPresentResponse> resultPresentResponseList = new ArrayList<>();

        //slicedList를 GradingResultPresentResponse로 바꿔서 반환한다.
        for (GradingResult gradingResult : slicedList) {
            GradingResultPresentResponse presentResponse = GradingResultPresentResponse
                    .builder()
                    .memberId(gradingResult.getMemberId())
                    .nickName(memberIdToNickName.get(gradingResult.getMemberId()))
                    .correctProblemCount(gradingResult.getCorrectProblemCount())
                    .build();
            resultPresentResponseList.add(presentResponse);
        }
        return resultPresentResponseList;
    }


}
