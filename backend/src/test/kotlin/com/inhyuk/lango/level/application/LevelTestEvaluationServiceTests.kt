package com.inhyuk.lango.level.application

import com.inhyuk.lango.ModelConfigForTest
import com.inhyuk.lango.level.domain.GrammarTestAnswer
import com.inhyuk.lango.level.domain.LevelTestAnswer
import com.inhyuk.lango.level.domain.VocabularyTestQuestion
import com.inhyuk.lango.level.domain.GrammarTestQuestion
import com.inhyuk.lango.level.domain.ReadingTestAnswer
import com.inhyuk.lango.level.domain.ReadingTestQuestion
import com.inhyuk.lango.level.domain.VocabularyTestAnswer
import com.inhyuk.lango.level.domain.WritingTestAnswer
import com.inhyuk.lango.level.domain.WritingTestQuestion
import io.kotest.core.spec.style.BehaviorSpec

class LevelTestEvaluationServiceTests : BehaviorSpec({
    val chatModel = ModelConfigForTest.chatModel

    val levelTestFactory = LevelTestEvaluationService(
        chatModel
    )

    given("Level test evaluation service") {


        `when`("evaluating answer") {
            val response = levelTestFactory.evaluate(getTestAnswer())
            then("it should return CEFR level") {
                print(response)
            }
        }
    }




})


fun getTestAnswer() : LevelTestAnswer{
    val vocabularyTestQuestions = listOf(
        VocabularyTestQuestion(
            question = "I like to ______ books. (나는 책을 읽는 것을 좋아합니다.)",
            answer = "read",
            level = "A1.1",
            type = "fill"
        ),
        VocabularyTestQuestion(
            question = "friend",
            answer = "친구",
            level = "A1.1",
            type = "meaning"
        ),
        VocabularyTestQuestion(
            question = "It is _______ to learn a new language. (새로운 언어를 배우는 것은 흥미롭습니다.)",
            answer = "interesting",
            level = "A2.1",
            type = "fill"
        ),
        VocabularyTestQuestion(
            question = "prepare",
            answer = "준비하다",
            level = "A2.1",
            type = "meaning"
        ),
        VocabularyTestQuestion(
            question = "We need to find a _______ to this problem. (우리는 이 문제에 대한 해결책을 찾아야 합니다.)",
            answer = "solution",
            level = "B1.1",
            type = "fill"
        ),
        VocabularyTestQuestion(
            question = "depend",
            answer = "의존하다, ~에 달려있다",
            level = "B1.1",
            type = "meaning"
        ),
        VocabularyTestQuestion(
            question = "The government decided to _______ a new policy to boost the economy. (정부는 경제를 활성화하기 위해 새로운 정책을 시행하기로 결정했습니다.)",
            answer = "implement",
            level = "B2.1",
            type = "fill"
        ),
        VocabularyTestQuestion(
            question = "crucial",
            answer = "결정적인, 매우 중요한",
            level = "B2.1",
            type = "meaning"
        ),
        VocabularyTestQuestion(
            question = "The architect tried to _______ the natural beauty of the landscape into the building's design. (건축가는 풍경의 자연미를 건물 디자인에 통합하려고 노력했습니다.)",
            answer = "integrate",
            level = "C1.1",
            type = "fill"
        ),
        VocabularyTestQuestion(
            question = "ubiquitous",
            answer = "어디에나 있는, 편재하는",
            level = "C1.1",
            type = "meaning"
        )
    )
    val vocabularyAnswers = mutableListOf(
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[0],
            answer = "read",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[1],
            answer = "friend",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[2],
            answer = "interesting",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[3],
            answer = "준비하다",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[4],
            answer = "solution",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[5],
            answer = "의존하다",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[6],
            answer = "",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[7],
            answer = "치명적인",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[8],
            answer = "",
        ),
        VocabularyTestAnswer(
            question = vocabularyTestQuestions[9],
            answer = "",
        ),
    )
    val grammarTestQuestions = listOf(
        GrammarTestQuestion(
            passageText = "My sister ___ a doctor.",
            answer = "is",
            level = "A1.1",
            options = listOf("is", "are", "am")
        ),
        GrammarTestQuestion(
            passageText = "Birds ___ in the sky.",
            answer = "fly",
            level = "A1.1",
            options = listOf("fly", "flies", "flying")
        ),
        GrammarTestQuestion(
            passageText = "There are many colorful ___ in the garden.",
            answer = "flowers",
            level = "A1.1",
            options = listOf("flower", "flowers", "flower's")
        ),
        GrammarTestQuestion(
            passageText = "She ___ never ___ a famous person.",
            answer = "has/met",
            level = "A2.1",
            options = listOf("has/met", "have/meet", "had/meet")
        ),
        GrammarTestQuestion(
            passageText = "The blue car is ___ than the red one.",
            answer = "faster",
            level = "A2.1",
            options = listOf("faster", "more fast", "fastest")
        ),
        GrammarTestQuestion(
            passageText = "If you ___ for help, I will assist you.",
            answer = "ask",
            level = "A2.2",
            options = listOf("ask", "will ask", "asked")
        ),
        GrammarTestQuestion(
            passageText = "The letter ___ by my mother last week.",
            answer = "was written",
            level = "B1.1",
            options = listOf("was written", "wrote", "is writing")
        ),
        GrammarTestQuestion(
            passageText = "He mentioned that he ___ to go to the party.",
            answer = "was not able",
            level = "B1.1",
            options = listOf("is not able", "was not able", "will not be able")
        ),
        GrammarTestQuestion(
            passageText = "I know a woman ___ teaches English.",
            answer = "who",
            level = "B1.1",
            options = listOf("who", "which", "what")
        ),
        GrammarTestQuestion(
            passageText = "Seldom ___ he complain about anything.",
            answer = "does",
            level = "B2.1",
            options = listOf("does", "he does", "did he")
        ),
        GrammarTestQuestion(
            passageText = "If I ___ your advice, I would have avoided that mistake.",
            answer = "had followed",
            level = "B2.1",
            options = listOf("had followed", "followed", "follow")
        ),
        GrammarTestQuestion(
            passageText = "___ was her dedication that impressed everyone.",
            answer = "It",
            level = "B2.1",
            options = listOf("It", "That", "What")
        ),
        GrammarTestQuestion(
            passageText = "Not only ___ she design the website, but she also coded it.",
            answer = "did",
            level = "C1.1",
            options = listOf("did", "does", "has")
        ),
        GrammarTestQuestion(
            passageText = "___ the report, he submitted it to his manager.",
            answer = "Having completed",
            level = "C1.1",
            options = listOf("Completing", "Having completed", "Completed")
        ),
        GrammarTestQuestion(
            passageText = "It is essential that every student ___ the safety guidelines.",
            answer = "follow",
            level = "C1.1",
            options = listOf("follows", "follow", "followed")
        )
    )

    val grammarTestAnswers = mutableListOf(
        GrammarTestAnswer(
            question = grammarTestQuestions[0],
            answer = "is"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[1],
            answer = "fly"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[2],
            answer = "flowers"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[3],
            answer = "has/met"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[4],
            answer = "faster"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[5],
            answer = "ask"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[6],
            answer = "was written"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[7],
            answer = "is not able"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[8],
            answer = "who"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[9],
            answer = "does"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[10],
            answer = "followed"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[11],
            answer = "It"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[12],
            answer = "did"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[13],
            answer = "Completing"
        ),
        GrammarTestAnswer(
            question = grammarTestQuestions[14],
            answer = "follow"
        ),
    )

    val readingTestQuestions = listOf(
        ReadingTestQuestion(
            passageText = "My name is Lily. I am 9 years old. Every morning, I eat breakfast with my family. Today, I am studying English at school. Yesterday, I went to the park with my dog, Max. We played fetch and had a good time.",
            question = "릴리는 어제 무엇을 했습니까?",
            answer = "그녀는 개와 함께 공원에 갔습니다.",
            level = "A1.1"
        ),
        ReadingTestQuestion(
            passageText = "Last month, I traveled to Jeju Island with my family. It was my first time visiting there, and I have always wanted to see its beautiful volcanic landscapes. We explored Hallasan Mountain, which is the highest peak in South Korea, and enjoyed fresh seafood. The weather was perfect most days, but if it rained, we visited indoor museums. I bought many souvenirs for my friends. I believe Jeju Island is more beautiful than any other place I have visited in Korea.",
            question = "작가가 가장 아름답다고 생각하는 한국의 장소는 어디입니까?",
            answer = "제주도",
            level = "A1.1"
        ),
        ReadingTestQuestion(
            passageText = "Online learning has significantly transformed education, offering unprecedented flexibility and accessibility. Students can attend classes from anywhere in the world, which is a major advantage for those with geographical limitations or busy schedules. Furthermore, digital resources often provide a richer, more interactive learning experience compared to traditional textbooks. However, this mode of education also presents challenges. Some students struggle with self-discipline and motivation without the direct supervision of an instructor. Moreover, the lack of face-to-face interaction can lead to feelings of isolation and hinder the development of social skills. If I were designing an ideal education system, I would incorporate both online and in-person elements to maximize benefits while minimizing drawbacks. Many educators have been continuously adapting their methods, and the impact of these changes is still being evaluated. While online platforms have improved dramatically, it's clear that human connection still plays a vital role in the learning process. The quality of online courses has also been greatly improved over the last decade.",
            question = "According to the passage, what is a potential drawback of online learning for some students?",
            answer = "일부 학생들은 직접적인 감독 없이 자기 훈련과 동기 부여에 어려움을 겪을 수 있으며, 대면 상호작용의 부족으로 고립감을 느낄 수 있습니다",
            level = "B1.1"
        ),
        ReadingTestQuestion(
            passageText = "Genetic engineering, a revolutionary field, holds immense promise for treating diseases and enhancing human capabilities. Through techniques like CRISPR-Cas9, scientists can precisely modify DNA, correcting genetic defects that cause conditions such as cystic fibrosis or Huntington's disease. Moreover, it offers the potential to create crops resistant to pests and droughts, thereby addressing global food security challenges. However, the ethical landscape surrounding genetic engineering is fraught with complexities that demand careful consideration. What truly concerns many is the prospect of 'designer babies,' where genetic traits are selected not for medical necessity but for non-medical enhancements like intelligence or physical appearance. Seldom do we encounter a technology with such profound implications for human identity and societal equity. If we were to pursue such enhancements without robust ethical guidelines, societal divisions could be exacerbated, creating a biological 'haves' and 'have-nots.' It is crucial that public discourse informs policy decisions to prevent misuse. Not only could this technology alter individuals, but it also has the potential to reshape the human gene pool for future generations, the long-term consequences of which are largely unknown. The scientific community has been grappling with these moral dilemmas for decades, and only through transparent dialogue can a balanced path forward be forged. The potential benefits are undeniable, yet the risks, particularly those related to unforeseen ecological impacts or unintended human consequences, must be thoroughly assessed and mitigated. It was the rapid advancement of this technology that brought these ethical questions to the forefront",
            question = "What is the primary ethical concern highlighted in the passage regarding genetic engineering?",
            answer = "주요 윤리적 우려는 '맞춤형 아기'의 가능성과 비의료적 개선 사항을 선택할 가능성이며, 이는 사회적 분열을 악화시키고 인간 유전자 풀을 재편할 수 있습니다",
            level = "B2.1",
        ),
        ReadingTestQuestion(
            passageText = "The enigma of consciousness remains one of the most profound challenges confronting both philosophy and neuroscience. Despite centuries of inquiry, a universally accepted definition or definitive explanation of how subjective experience arises from physical processes continues to elude us. From a philosophical standpoint, the 'hard problem' of consciousness, as articulated by David Chalmers, distinguishes the question of why and how physical states give rise to qualitative, subjective experiences (qualia) from the 'easy problems' of explaining cognitive functions such as memory and attention. This distinction underscores the difficulty in bridging the explanatory gap between the objective, measurable properties of the brain and the subjective, phenomenal character of conscious awareness. Neuroscientific investigations, employing advanced imaging techniques and sophisticated experimental paradigms, have made considerable strides in identifying neural correlates of consciousness (NCCs) – the minimal set of neuronal events sufficient for a specific conscious percept. However, even if we were to meticulously map every neural firing and synaptic connection associated with a particular experience, it arguably would not, in itself, explain *why* these physical events are accompanied by subjective feeling. What is at stake is not merely a scientific puzzle but a fundamental question about the nature of reality and our place within it. Various theories attempt to tackle this colossal challenge. Integrated Information Theory (IIT), for instance, proposes that consciousness corresponds to the capacity of a system to integrate information, with the degree of consciousness being proportional to the amount of integrated information. Conversely, global neuronal workspace theory suggests that consciousness emerges when information becomes globally available to multiple brain systems. While these frameworks offer compelling insights and generate testable hypotheses, they often grapple with conceptual ambiguities and empirical limitations. The ultimate reconciliation of subjective experience with objective scientific explanation may necessitate a paradigm shift, perhaps even a re-evaluation of our most basic assumptions about matter, mind, and the very fabric of existence. The relentless pursuit of understanding consciousness is not simply an academic exercise; it reflects a deep human imperative to comprehend the most intimate aspect of our being, one that shapes our perception of the world and our interactions within it. It is precisely this intricate interplay between the material and the experiential that makes the subject so endlessly fascinating and frustratingly complex, demanding an interdisciplinary approach that transcends traditional academic boundaries.",
            question = "What is the 'hard problem' of consciousness as described in the passage?",
            answer = "의식의 '어려운 문제'는 물리적 상태가 왜, 어떻게 질적이고 주관적인 경험(감각질)을 일으키는지 설명하는 과제를 의미하며, 인지 기능과 관련된 더 쉬운 문제들과 구별됩니다",
            level = "C1.1",
        )
    )
    val readingTestAnswers = mutableListOf(
        ReadingTestAnswer(
            question = readingTestQuestions[0],
            answer = "그녀의 개, Max와 함께 공원에 갔습니다."
        ),
        ReadingTestAnswer(
            question = readingTestQuestions[1],
            answer = "제주도"
        ),
        ReadingTestAnswer(
            question = readingTestQuestions[2],
            answer = ""
        ),
        ReadingTestAnswer(
            question = readingTestQuestions[3],
            answer = ""
        ),
        ReadingTestAnswer(
            question = readingTestQuestions[4],
            answer = ""
        )
    )

    val writingTestQuestions = mutableListOf(
        WritingTestQuestion(
            question = "자기소개를 3-5문장으로 작성하세요.",
            level = "A1",
        ),
        WritingTestQuestion(
            question = "당신의 고향에 대해 100단어 정도로 설명하세요.",
            level = "B1",
        ),
        WritingTestQuestion(
            question = "기술 발전이 사회에 미치는 영향에 대한 의견을 200단어로 작성하세요.",
            level = "C1",
        ),
    )
    val writingTestAnswers = mutableListOf(
        WritingTestAnswer(
            question = writingTestQuestions[0],
            answer = "My name is Inhyuk kim. I am 30 years old. I live in South Korea. I working as a software engineer."
        ),
        WritingTestAnswer(
            question = writingTestQuestions[1],
            answer = "My hometown is Daejeon in South Korea. I born and grew up in there. It is very silent place. quite small"
        ),
        WritingTestAnswer(
            question = writingTestQuestions[2],
            answer = ""
        ),
    )

    val testAnswers = LevelTestAnswer(
        vocabulary = vocabularyAnswers,
        grammar = grammarTestAnswers,
        reading = readingTestAnswers,
        writing = writingTestAnswers
    )
    return testAnswers
}