package com.inhyuk.lango.level.prompt

import com.inhyuk.lango.level.domain.CEFRLevel

class LevelGuidePrompt {
    companion object {
        fun getChatSystemPrompt(level : CEFRLevel): String {

            return """
                <Level Guideline>
                    사용자는 CEFR ${level.level} 레벨 학습자입니다.
                    
                    # 어휘 제약사항
                    - 최대 ${level.vocabulary.max}개의 가장 일반적인 영어 단어만 사용하세요
                    - 특히 상위 빈도 ${level.vocabulary.commonWords}개 단어를 우선적으로 사용하세요
                    - 관용구, 전문 용어, 희귀한 단어는 피하세요

                    # 문법 규칙
                    ## 사용 가능한 문법:
                    ${level.grammar.allowed.map{"  - ${it}"}.joinToString("\n")} 
                    
                    ## 절대 사용 금지 문법:
                    ${level.grammar.forbidden.map{"  - ${it}"}.joinToString("\n")}
                    
                    # 문장 구조
                    - 문장당 ${level.reading.sentenceLengthRange.min}-${level.reading.sentenceLengthRange.max} 단어 사용
                    - 특징: ${level.reading.complexity}
                    
                    # 대화 방식
                    - 응답 스타일: ${level.interaction.responseStyle}
                                      
                    ## 중요 규칙
                    - 학습자의 레벨보다 어려운 표현을 쓰지 마세요
                    - 자연스러운 대화를 유지하되, 교육적 목적을 잊지 마세요
                </Level Guideline>
            """.trimIndent()
        }

        fun getArticleSystemPrompt(level: CEFRLevel, topic: String) : String{
            return """
                <Level Guideline>
                    사용자는 CEFR ${level.level} 레벨 학습자입니다.
                    # 주제
                    $topic

                    # 분량 규정
                    - 총 단어 수: ${level.reading.wordCountRange.min}-${level.reading.wordCountRange.max}단어
                    - 평균 문장 길이: ${level.reading.sentenceLengthRange.min}-${level.reading.sentenceLengthRange.max}단어
                    - 복잡도: ${level.reading.complexity}

                    # 어휘 제약
                    - 최대 어휘 범위: ${level.vocabulary.max}단어 이내
                    - 주로 상위 빈도 ${level.vocabulary.commonWords}단어 사용
                    - 피해야 할 표현: ${level.grammar.forbidden.joinToString(", ")}

                    # 문법 제약
                    ## 사용 가능:
                    ${level.grammar.allowed.map {"- ${it}"}.joinToString("\n")}

                    ## 절대 사용 금지:
                    ${level.grammar.forbidden.map {"- ${it}"}.joinToString("\n")}

                    # 내용 요구사항
                    - 학생과 성인 모두에게 적합한 주제
                    - ${if(level.level >= "B1.1") "일상과 전문적 상황 혼합" else "친숙한 일상 상황 중심"}
                    - 새로운 어휘는 문맥으로 의미를 유추할 수 있게 작성
                    - ${if(level.level >= "B2.1") "일부 전문/비즈니스 용어 포함 가능" else "일상적인 단어만 사용"}

                    # 출력 형식
                    1. 제목: 5-10단어
                    2. 본문: ${Math.ceil((level.reading.wordCountRange.min / 100).toDouble())}개 문단
                    3. 각 문단: ${level.reading.sentenceLengthRange.min * 3}-${level.reading.sentenceLengthRange.max * 5}단어

                    # 생성 후 자체 검증
                    작성 후 다음을 확인하세요:
                    - [ ] 금지된 문법 구조가 없는가?
                    - [ ] 어휘 범위를 초과하지 않았는가?
                    - [ ] 문장 길이가 적절한가?
                    - [ ] 주제가 레벨에 맞는가?
                </Level Guideline>
            """.trimIndent()
        }
    }
}

/*
1. **어휘 테스트** (5문제)
   - 그림을 보고 단어 맞추기
   - 난이도를 점진적으로 올리기

2. **문법 테스트** (5문제)
   - 빈칸 채우기
   - 시제/인칭 변화 확인

3. **짧은 작문** (30-50단어)
   - 주제: "자기소개" 또는 "어제 한 일"
   - 자유롭게 작성하도록 유도
 */