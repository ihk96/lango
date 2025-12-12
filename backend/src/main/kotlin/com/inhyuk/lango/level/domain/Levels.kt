package com.inhyuk.lango.level.domain

class Levels {
    companion object {
        val A1_1 = CEFRLevel(
            level = "A1-1",
            vocabulary = CEFRVocabularyCount(300, 500),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "present simple (be, have, do)",
                    "singular/plural nouns",
                    "basic adjectives",
                    "simple questions (what, where, who)"
                ),
                forbidden = listOf(
                    "past tense",
                    "future tense",
                    "any complex sentences",
                    "phrasal verbs",
                    "idioms"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(20, 50),
                sentenceLengthRange = IntRange(3, 8),
                complexity = "단일 주제, 반복 구조, 일상 명사 중심"
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(5, 10),
                sentenceCountRange = IntRange(1, 2),
                errorTolerance = 30.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "매우 짧고 직접적. 한 번에 하나의 개념만.",
                feedbackDetail = "틀린 부분만 지적. 올바른 형태 즉시 제시.",
                exampleRequired = true
            ),
        )

        val A1_2 = CEFRLevel(
            level = "A1-2",
            vocabulary = CEFRVocabularyCount(600, 800),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "present simple + continuous",
                    "past simple (regular verbs)",
                    "basic prepositions (in, on, at)",
                    "can for ability",
                    "there is/are"
                ),
                forbidden = listOf(
                    "present perfect",
                    "passive voice",
                    "conditional sentences",
                    "relative clauses"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(50, 100),
                sentenceLengthRange = IntRange(5, 12),
                complexity = "2-3개 관련 문장. 시간 순서 명확."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(15, 25),
                sentenceCountRange = IntRange(2, 4),
                errorTolerance = 25.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "짧은 문장 연속. 질문은 yes/no 형태.",
                feedbackDetail = "패턴 제시. '이렇게 말할 수 있어요' 형식.",
                exampleRequired = true
            ),
        )

        val A1_3 = CEFRLevel(
            level = "A1-3",
            vocabulary = CEFRVocabularyCount(800, 1000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "will for future",
                    "going to",
                    "past simple (irregular verbs)",
                    "basic adverbs (often, never)",
                    "how much/many"
                ),
                forbidden = listOf(
                    "present perfect",
                    "used to",
                    "complex conjunctions",
                    "reported speech"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(80, 150),
                sentenceLengthRange = IntRange(6, 15),
                complexity = "짧은 단락. 시간/장소 표현 포함."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(30, 50),
                sentenceCountRange = IntRange(4, 6),
                errorTolerance = 20.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "단락 단위. 간단한 연결어 사용.",
                feedbackDetail = "왜 틀렸는지 간단히 설명.",
                exampleRequired = true
            ),
        )

        val A2_1 = CEFRLevel(
            level = "A2-1",
            vocabulary = CEFRVocabularyCount(1200, 1500),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "present perfect (basic)",
                    "comparatives and superlatives",
                    "should/must for advice",
                    "because/so/but",
                    "past continuous"
                ),
                forbidden = listOf(
                    "passive voice",
                    "conditionals (except zero)",
                    "wish/hope structures",
                    "advanced conjunctions"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(100, 200),
                sentenceLengthRange = IntRange(8, 18),
                complexity = "명확한 주제. 원인-결과 관계."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(50, 80),
                sentenceCountRange = IntRange(5, 8),
                errorTolerance = 18.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "설명 추가. '왜냐하면...' 사용.",
                feedbackDetail = "문법 규칙 간단히 언급.",
                exampleRequired = true
            ),
        )

        val A2_2 = CEFRLevel(
            level = "A2-2",
            vocabulary = CEFRVocabularyCount(1600, 2000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "present perfect vs past simple",
                    "will vs going to",
                    "first conditional",
                    "relative clauses (who, which, that)",
                    "have to/don't have to"
                ),
                forbidden = listOf(
                    "second/third conditional",
                    "passive voice",
                    "reported speech",
                    "wish clauses"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(150, 250),
                sentenceLengthRange = IntRange(10, 20),
                complexity = "여러 관점. 비교 포함."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(80, 120),
                sentenceCountRange = IntRange(7, 10),
                errorTolerance = 15.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "구조화된 답변. 서론-본론-결론.",
                feedbackDetail = "대안 표현 제시.",
                exampleRequired = true
            ),
        )

        val A2_3 = CEFRLevel(
            level = "A2-3",
            vocabulary = CEFRVocabularyCount(2000, 2500),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "all A2 grammar",
                    "used to",
                    "gerunds after verbs",
                    "too/enough",
                    "both/neither/either"
                ),
                forbidden = listOf(
                    "advanced conditionals",
                    "passive voice",
                    "complex participle clauses"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(200, 300),
                sentenceLengthRange = IntRange(12, 22),
                complexity = "개인 의견 포함. 찬반 구조."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(120, 150),
                sentenceCountRange = IntRange(9, 12),
                errorTolerance = 12.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "의견 묻기. 이유 설명 요구.",
                feedbackDetail = "더 자연스러운 표현 추천.",
                exampleRequired = false
            ),
        )

        val B1_1 = CEFRLevel(
            level = "B1-1",
            vocabulary = CEFRVocabularyCount(2400, 3000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "passive voice (present/past)",
                    "second conditional",
                    "present perfect continuous",
                    "defining relative clauses",
                    "reported speech (statements)"
                ),
                forbidden = listOf(
                    "third conditional",
                    "mixed conditionals",
                    "inversion",
                    "cleft sentences"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(250, 400),
                sentenceLengthRange = IntRange(15, 25),
                complexity = "추상적 개념 도입. 논리적 흐름."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(150, 200),
                sentenceCountRange = IntRange(10, 14),
                errorTolerance = 10.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "설명 확장. 예외 사항 언급.",
                feedbackDetail = "문법적 정확성과 자연스러움 구분.",
                exampleRequired = false
            ),
        )

        val B1_2 = CEFRLevel(
            level = "B1-2",
            vocabulary = CEFRVocabularyCount(2800, 3500),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "reported speech (questions)",
                    "past perfect",
                    "modals of deduction (must/might/can't)",
                    "non-defining relative clauses",
                    "wish + past simple"
                ),
                forbidden = listOf(
                    "advanced participle clauses",
                    "inversion for emphasis",
                    "subjunctive mood"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(350, 500),
                sentenceLengthRange = IntRange(15, 28),
                complexity = "다층적 논리. 암시적 의미."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(200, 250),
                sentenceCountRange = IntRange(12, 16),
                errorTolerance = 8.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "근거 기반 토론. 반론 제시.",
                feedbackDetail = "문맥에 따른 적절성 평가.",
                exampleRequired = false
            ),
        )

        val B1_3 = CEFRLevel(
            level = "B1-3",
            vocabulary = CEFRVocabularyCount(3200, 4000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "third conditional",
                    "all passive forms",
                    "participle clauses (present/past)",
                    "wish + past perfect",
                    "phrasal verbs (separable/inseparable)"
                ),
                forbidden = listOf(
                    "advanced inversion",
                    "subjunctive mood",
                    "archaic forms"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(400, 600),
                sentenceLengthRange = IntRange(18, 30),
                complexity = "복합 논증. 반어법 포함 가능."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(250, 300),
                sentenceCountRange = IntRange(14, 18),
                errorTolerance = 6.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "비판적 분석. 대안 제시.",
                feedbackDetail = "스타일과 톤 개선 제안.",
                exampleRequired = false
            ),
        )

        val B2_1 = CEFRLevel(
            level = "B2-1",
            vocabulary = CEFRVocabularyCount(4000, 5000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "mixed conditionals",
                    "inversion after negative adverbials",
                    "cleft sentences (it/what)",
                    "advanced passive structures",
                    "future perfect/continuous"
                ),
                forbidden = listOf(
                    "archaic grammar",
                    "highly specialized jargon"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(500, 700),
                sentenceLengthRange = IntRange(20, 35),
                complexity = "전문 분야 입문. 기술적 설명."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(300, 400),
                sentenceCountRange = IntRange(16, 22),
                errorTolerance = 5.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "깊이 있는 분석. 뉘앙스 구분.",
                feedbackDetail = "고급 어휘와 표현 제안.",
                exampleRequired = false
            ),
        )

        val B2_2 = CEFRLevel(
            level = "B2-2",
            vocabulary = CEFRVocabularyCount(4800, 6000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "all B2.1 + emphasis structures",
                    "subjunctive mood (formal)",
                    "ellipsis and substitution",
                    "advanced linking devices"
                ),
                forbidden = listOf(
                    "highly literary devices"
                )
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(600, 900),
                sentenceLengthRange = IntRange(20, 40),
                complexity = "학술 텍스트. 전문 용어 적정."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(400, 500),
                sentenceCountRange = IntRange(20, 28),
                errorTolerance = 4.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "전문적 토론. 반박과 양보.",
                feedbackDetail = "학술적/비즈니스 문체 구분.",
                exampleRequired = false
            ),
        )

        val B2_3 = CEFRLevel(
            level = "B2-3",
            vocabulary = CEFRVocabularyCount(5600, 7000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "all structures",
                    "advanced fronting",
                    "complex noun phrases",
                    "hedging language"
                ),
                forbidden = emptyList()
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(700, 1100),
                sentenceLengthRange = IntRange(22, 45),
                complexity = "복잡한 논리 구조. 함축적 의미."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(500, 600),
                sentenceCountRange = IntRange(24, 32),
                errorTolerance = 3.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "전문가 수준 대화. 정교한 논증.",
                feedbackDetail = "미묘한 의미 차이 설명.",
                exampleRequired = false
            ),
        )

        val C1_1 = CEFRLevel(
            level = "C1-1",
            vocabulary = CEFRVocabularyCount(7000, 9000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "모든 문법 자유자재",
                    "문체 변형"
                ),
                forbidden = emptyList()
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(800, 1200),
                sentenceLengthRange = IntRange(25, 50),
                complexity = "학술 논문 수준. 전문 분야."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(600, 800),
                sentenceCountRange = IntRange(28, 40),
                errorTolerance = 2.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "학술적 엄밀성. 다각적 분석.",
                feedbackDetail = "원어민 수준 정교함 추구.",
                exampleRequired = false
            ),
        )

        val C1_2 = CEFRLevel(
            level = "C1-2",
            vocabulary = CEFRVocabularyCount(8500, 11000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "모든 구조",
                    "수사적 기법"
                ),
                forbidden = emptyList()
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(1000, 1500),
                sentenceLengthRange = IntRange(25, 55),
                complexity = "전문 저널. 복잡한 이론."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(800, 1000),
                sentenceCountRange = IntRange(35, 50),
                errorTolerance = 1.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "전문가 간 토론. 비판적 평가.",
                feedbackDetail = "스타일과 목적의 일치성.",
                exampleRequired = false
            ),
        )

        val C1_3 = CEFRLevel(
            level = "C1-3",
            vocabulary = CEFRVocabularyCount(10000, 13000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "완벽한 구사",
                    "은유와 수사법"
                ),
                forbidden = emptyList()
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(1200, 2000),
                sentenceLengthRange = IntRange(30, 60),
                complexity = "문학 작품. 철학적 텍스트."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(1000, 1500),
                sentenceCountRange = IntRange(45, 70),
                errorTolerance = 0.5
            ),
            interaction = CEFRInteraction(
                responseStyle = "지적 담론. 창의적 해석.",
                feedbackDetail = "문화적·역사적 맥락 평가.",
                exampleRequired = false
            ),
        )

        val C2_1 = CEFRLevel(
            level = "C2-1",
            vocabulary = CEFRVocabularyCount(12000, 16000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "원어민 수준 완벽"
                ),
                forbidden = emptyList()
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(1500, 2500),
                sentenceLengthRange = IntRange(30, 70),
                complexity = "모든 장르와 스타일."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(1200, 1800),
                sentenceCountRange = IntRange(50, 90),
                errorTolerance = 0.3
            ),
            interaction = CEFRInteraction(
                responseStyle = "원어민 간 대화 수준.",
                feedbackDetail = "세밀한 뉘앙스와 함축.",
                exampleRequired = false
            ),
        )

        val C2_2 = CEFRLevel(
            level = "C2-2",
            vocabulary = CEFRVocabularyCount(15000, 20000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "방언, 고어 이해"
                ),
                forbidden = emptyList()
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(2000, 3000),
                sentenceLengthRange = IntRange(35, 80),
                complexity = "고전 문학. 역사적 문서."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(1500, 2500),
                sentenceCountRange = IntRange(70, 120),
                errorTolerance = 0.1
            ),
            interaction = CEFRInteraction(
                responseStyle = "문학적·예술적 표현.",
                feedbackDetail = "창의성과 독창성 평가.",
                exampleRequired = false
            ),
        )

        val C2_3 = CEFRLevel(
            level = "C2-3",
            vocabulary = CEFRVocabularyCount(18000, 25000),
            grammar = CEFRGrammar(
                allowed = listOf(
                    "완전한 마스터"
                ),
                forbidden = emptyList()
            ),
            reading = CEFRReading(
                wordCountRange = IntRange(2500, 5000),
                sentenceLengthRange = IntRange(40, 100),
                complexity = "모든 수준의 텍스트."
            ),
            writing = CEFRWriting(
                expectedLengthRange = IntRange(2000, 4000),
                sentenceCountRange = IntRange(100, 200),
                errorTolerance = 0.0
            ),
            interaction = CEFRInteraction(
                responseStyle = "장르별 완벽한 스타일 구사.",
                feedbackDetail = "번역가/작가 수준 평가.",
                exampleRequired = false
            ),
        )
    }

}




