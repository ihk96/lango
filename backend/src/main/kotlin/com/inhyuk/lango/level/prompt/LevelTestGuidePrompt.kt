package com.inhyuk.lango.level.prompt

class LevelTestGuidePrompt {
    companion object{
        fun getVocaTestSystemPrompt(): String {
            return """
                <Level Test Generation Guideline>
                    문제 생성 규칙:
                    각 레벨별로 2문제씩 출제하여 어휘 범위를 파악 합니다.
                    문제 유형은 문장의 빈칸 채우기, 단어의 뜻 맞추기로 구성합니다.
                    * 빈칸 채우기의 경우 지문에 빈칸이 채워진 한국어 번역문을 포함합니다.
                        예시: I am a _______. (나는 학생입니다.)
                    * 뜻 맞추기의 경우 지문은 영어 단어 하나만 포함됩니다.
                    
                    **A1 레벨 어휘 (2문제)**
                    - 최빈도 500단어 내 (예: book, water, go, big)
                    - 간단한 정의로 제시
                    
                    **A2 레벨 어휘 (2문제)**
                    - 1000-2000단어 범위 (예: difficult, remember, decide)
                    - 문맥에서 의미 파악
                    
                    **B1 레벨 어휘 (2문제)**
                    - 2500-3500단어 범위 (예: achieve, concern, environment)
                    - 추상적 개념 포함
                    
                    **B2 레벨 어휘 (2문제)**
                    - 4000-6000단어 범위 (예: implement, deteriorate, coherent)
                    - 전문적 맥락
                    
                    **C1+ 레벨 어휘 (2문제)**
                    - 7000+단어 (예: meticulous, ubiquitous, juxtaposition)
                    - 학술/전문 용어
                    
                    위 예시에 있는 단어 및 문장 그대로를 문제로 출제하지 마세요.
                    각 문제는 레벨, 문제 유형, 지문, 정답으로 구성합니다.
                </Level Test Generation Guideline>
            """.trimIndent()
        }

        fun getGrammarTestSystemPrompt() : String{
            return """
                <Level Test Generation Guideline>
                    문제 생성 규칙:
                    각 레벨의 핵심 문법을 3문제씩 출제
                    아래는 각 단계별 문법 예시입니다.
                    **A1 문법 (3문제)**
                    1. be동사: "She ___ a teacher." (is/are/am)
                    2. 현재시제: "I ___ to school every day." (go/goes/going)
                    3. 단수/복수: "I have two ___." (book/books)

                    **A2 문법 (3문제)**
                    1. 현재완료: "I ___ never ___ to Japan." (have/been, has/gone)
                    2. 비교급: "This book is ___ than that one." (more interesting/interestinger)
                    3. 조건문: "If it ___ tomorrow, I will stay home." (rains/will rain)

                    **B1 문법 (3문제)**
                    1. 수동태: "The book ___ by many people." (is read/reads)
                    2. 간접화법: "She said that she ___ tired." (is/was)
                    3. 관계대명사: "The man ___ is wearing a hat is my father." (who/which)

                    **B2 문법 (3문제)**
                    1. 도치: "___ have I seen such a beautiful place." (Never/Not)
                    2. 혼합조건: "If I ___ rich, I would have bought it yesterday." (was/were/had been)
                    3. 강조구문: "___ was John who broke the window." (It/What)

                    **C1 문법 (3문제)**
                    1. 고급 구문: "Not only ___ he arrive late, but he also forgot the documents." (did/does)
                    2. 분사구문: "___ the work, he went home." (Finishing/Having finished)
                    3. 주관적 표현: "It is imperative that he ___ immediately." (leaves/leave)
                    
                    위 예시에 있는 단어 및 문장 그대로를 문제로 출제하지 마세요.
                    각 문제는 레벨, 지문, 선택지, 정답으로 구성합니다.
                    
                </Level Test Generation Guideline>
            """.trimIndent()
        }

        fun getReadingTestSystemPrompt() : String{
            return """
                <Level Test Generation Guideline>
                    문제 생성 규칙:
                    각 레벨마다 1개의 문제를 출제합니다.
                    아래는 각 단계별 문제 예시입니다.
                    **지문 1 (A1 수준)**: 30-50단어, 현재시제, 일상 주제, 한국어 질문, 한국어 정답
                    ```
                    My name is Tom. I am 10 years old. I go to school every day. 
                    I like math and English. My teacher is very kind.
                    ```
                    질문: "톰은 몇살입니까?"

                    **지문 2 (A2 수준)**: 80-120단어, 과거/현재완료, 경험 서술, 한국어 질문, 한국어 정답
                    ```
                    Last summer, I visited my grandmother in Busan. It was my first time there.
                    I have never seen such beautiful beaches before. We ate delicious seafood
                    and walked along the coast every evening...
                    ```
                    질문: "작가는 매일 저녁마다 무엇을 했습니까?"

                    **지문 3 (B1 수준)**: 150-200단어, 논리적 구조, 의견 포함, 영어로 질문, 한국어 정답
                    ```
                    Working from home has become increasingly popular. While it offers
                    flexibility and saves commuting time, some people find it difficult
                    to maintain work-life balance. In my opinion, the key is...
                    ```
                    질문: "What is the main advantage of working from home?"

                    **지문 4 (B2 수준)**: 250-350단어, 전문적 내용, 복잡한 논리, 영어로 질문, 한국어 정답
                    ```
                    Recent studies have shown that artificial intelligence is transforming
                    various industries. However, the implementation of such technology
                    raises several ethical concerns that must be addressed...
                    ```
                    질문: "What concern does the author mention about AI?"

                    **지문 5 (C1 수준)**: 400-500단어, 학술적, 추상적 개념, 영어로 질문, 한국어 정답
                    ```
                    The philosophical implications of quantum mechanics extend far beyond
                    the realm of physics. The uncertainty principle, for instance, challenges
                    our fundamental assumptions about causality and determinism...
                    ```
                    질문: "How does quantum mechanics challenge traditional philosophy?"
                    
                    위 예시에 있는 단어 및 문장 그대로를 문제로 출제하지 마세요.
                    각 문제는 레벨, 지문, 질문, 정답으로 구성합니다.
                    
                </Level Test Generation Guideline>
            """.trimIndent()
        }

        fun getWritingTestSystemPrompt(): String{
            return """
                <Level Test Generation Guideline>
                    학습자의 정확한 CEFR 레벨(A1.1~C2.3)을 파악하기 위한 작문 문제를 생성해야 합니다.
                    CEFR 레벨에 대한 설명은 아래 Level Description을 참고하세요.
                    <Level Description>
                        ${CEFRLevelPrompt.Description}
                    </Level Description>
                    
                    작문 테스트 (3문제)
    
                    **문제 1 (기초)**: "자기소개를 3-5문장으로 작성하세요."
                    - A1-A2 학습자 측정용
    
                    **문제 2 (중급)**: "당신의 고향에 대해 100단어 정도로 설명하세요."
                    - B1-B2 학습자 측정용
    
                    **문제 3 (고급)**: "기술 발전이 사회에 미치는 영향에 대한 의견을 200단어로 작성하세요."
                    - C1-C2 학습자 측정용
                </Level Test Generation Guideline>
            """.trimIndent()
        }
    }
}