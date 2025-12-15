import {type Route} from "../../.react-router/types/app/routes/+types/exam";
import {
	answerGrammarTest, answerReadingTest,
	answerVocabularyTest, answerWritingTest, evaluate,
	generateTests,
	getGrammarTests, getReadingTests, getUsersLevel,
	getVocabularyTests, getWritingTests
} from "~/features/level/api";
import {Button} from "~/components/ui/button";
import {useEffect, useMemo, useState} from "react";
import {z} from "zod/v4";
import {GrammarTestSchema, ReadingTestSchema, VocabularyTestSchema, WritingTestSchema} from "~/features/level/types";
import {Input} from "~/components/ui/input";
import {ToggleGroup, ToggleGroupItem} from "~/components/ui/toggle-group";
import {Textarea} from "~/components/ui/textarea";

export async function loader({ request}: Route.LoaderArgs){

	await generateTests(request).catch(()=>{
		throw Error()
	})

}
export default function Exam() {
	const [step, setStep] = useState(0)

	return (
		<div>
			{
				step == 0 &&
				<div>
					학습을 시작하기 전에<br/>
					수준을 확인하기 위해 몇가지 문제를 풀어볼거에요.<br/>
					시간은 10분 정도 소요될 수 있습니다.
					<div>
						<Button onClick={()=>{setStep(1)}}>시작하기</Button>
					</div>
				</div>
			}
			{
				step == 1 &&
				<VocabularyTests onFinish={()=>{setStep(2)}} />
			}
			{
				step == 2 &&
				<GrammarTests onFinish={()=>{setStep(3)}} />
			}
			{
				step == 3 &&
				<ReadingTests onFinish={()=>{setStep(4)}} />
			}
			{
				step == 4 &&
				<WritingTests onFinish={()=>{setStep(5)}} />
			}
			{
				step == 5 &&
				<Evaluation />
			}
		</div>
	)
}

function Evaluation(){
	const [level, setLevel] = useState("")
	const [isReady, setIsReady] = useState(false)

	useEffect(() => {
		fetchEvaluation()
	}, []);

	async function fetchEvaluation(){
		const _level = await evaluate().then(res=>res.data)
		setLevel(_level)
		setIsReady(true)
	}

	return (
		<div>
			{
				!isReady &&
				<div>
					모든 시험이 끝났어요.<br/>
					제출한 답안을 평가하고 있습니다.<br/>
				</div>
			}
			{
				isReady &&
				<div>
					당신의 평가 결과는 {level}입니다!
				</div>
			}

		</div>
	)
}

function WritingTests(props: {
	onFinish?: ()=>void
}){
	const [step, setStep] = useState(0)
	const [isReady, setIsReady] = useState(false)
	const [questions, setQuestions] = useState<z.infer<typeof WritingTestSchema>[]>([])
	const currentQuestion = useMemo(()=>questions[step],[questions, step])
	const [answer, setAnswer] = useState("")
	useEffect(()=>{
		fetchQuestions()
	},[])
	async function fetchQuestions(){
		const _questions = await getWritingTests().then(res=>res.data)
		if(_questions.data.length == 0){
			setTimeout(fetchQuestions, 1000)
		} else {
			setQuestions(_questions.data)
			setIsReady(true)
		}
	}
	async function nextStep(){
		if(!answer){
			return;
		}
		answerWritingTest({questionId: step, answer: answer}).then(res=>{
			if(questions.length == step+1){
				props.onFinish?.()
			} else {
				setStep(curr=>curr+1)
				setAnswer("")
			}
		})
	}

	return (
		<div>
			{!isReady &&
				<div>
					문제를 출제하고 있습니다.
					<br/>
					잠시만 기다려주세요.
				</div>
			}

			{isReady &&
				<div>
					<div>작문 테스트</div>
					<div>{currentQuestion.question}</div>
					<div className={"flex gap-2"}>
						<Textarea onChange={(e)=>setAnswer(e.target.value)} value={answer}/>
					</div>
					<Button onClick={nextStep}>다음</Button>
				</div>
			}
		</div>
	)
}

function ReadingTests(props: {
	onFinish?: ()=>void
}){
	const [step, setStep] = useState(0)
	const [isReady, setIsReady] = useState(false)
	const [questions, setQuestions] = useState<z.infer<typeof ReadingTestSchema>[]>([])
	const currentQuestion = useMemo(()=>questions[step],[questions, step])
	const [answer, setAnswer] = useState("")
	useEffect(()=>{
		fetchQuestions()
	},[])
	async function fetchQuestions(){
		const _questions = await getReadingTests().then(res=>res.data)
		if(_questions.data.length == 0){
			setTimeout(fetchQuestions, 1000)
		} else {
			setQuestions(_questions.data)
			setIsReady(true)
		}
	}
	async function nextStep(){
		if(!answer){
			return;
		}
		answerReadingTest({questionId: step, answer: answer}).then(res=>{
			if(questions.length == step+1){
				props.onFinish?.()
			} else {
				setStep(curr=>curr+1)
				setAnswer("")
			}
		})
	}

	return (
		<div>
			{!isReady &&
				<div>
					문제를 출제하고 있습니다.
					<br/>
					잠시만 기다려주세요.
				</div>
			}

			{isReady &&
				<div>
					<div>독해 테스트</div>
					<div>다음 지문을 읽고 질문에 답변하세요.</div>
					<div>{currentQuestion.passageText}</div>
					<div>질문: {currentQuestion.question}</div>
					<div className={"flex gap-2"}>
						<Textarea onChange={(e)=>setAnswer(e.target.value)} value={answer}/>
					</div>
					<Button onClick={nextStep}>다음</Button>
				</div>
			}
		</div>
	)
}

function GrammarTests(props: {
	onFinish?: ()=>void
}) {
	const [step, setStep] = useState(0)
	const [isReady, setIsReady] = useState(false)
	const [questions, setQuestions] = useState<z.infer<typeof GrammarTestSchema>[]>([])
	const currentQuestion = useMemo(()=>questions[step],[questions, step])
	const [answer, setAnswer] = useState("")
	useEffect(()=>{
		fetchQuestions()
	},[])
	async function fetchQuestions(){
		const _questions = await getGrammarTests().then(res=>res.data)
		if(_questions.data.length == 0){
			setTimeout(fetchQuestions, 1000)
		} else {
			setQuestions(_questions.data)
			setIsReady(true)
		}
	}
	async function nextStep(){
		if(!answer){
			return;
		}
		answerGrammarTest({questionId: step, answer: answer}).then(res=>{
			if(questions.length == step+1){
				props.onFinish?.()
			} else {
				setStep(curr=>curr+1)
				setAnswer("")
			}
		})
	}

	return (
		<div>
			{!isReady &&
				<div>
					문제를 출제하고 있습니다.
					<br/>
					잠시만 기다려주세요.
				</div>
			}

			{isReady &&
				<div>
					<div>문법 테스트</div>
					<div>{currentQuestion.passageText}</div>
					<div className={"flex gap-2"}>
						<ToggleGroup type="single" onValueChange={(v)=>{setAnswer(v)}}>
							{
								currentQuestion.options.map(option=><ToggleGroupItem value={option}>{option}</ToggleGroupItem>)
							}
						</ToggleGroup>
					</div>
					<Button onClick={nextStep}>다음</Button>
				</div>
			}
		</div>
	)
}


function VocabularyTests(props: {
	onFinish?: ()=>void
}){
	const [questions, setQuestions] = useState<z.infer<typeof VocabularyTestSchema>[]>([])
	const [step, setStep] = useState(0)
	const currentQuestion = useMemo(()=>questions[step],[questions, step])
	const [isReady, setIsReady] = useState(false)
	const [answer, setAnswer] = useState("")

	useEffect(()=>{
		fetchQuestions()
	},[])

	async function fetchQuestions(){
		const _questions = await getVocabularyTests().then(res=>res.data)
		if(_questions.data.length == 0){
			setTimeout(fetchQuestions, 1000)
		} else {
			setQuestions(_questions.data)
			setIsReady(true)
		}
	}

	async function nextStep(){
		if(!answer){
			return;
		}
		answerVocabularyTest({questionId: step, answer: answer}).then(res=>{
			if(questions.length == step+1){
				props.onFinish?.()
			} else {
				setStep(curr=>curr+1)
				setAnswer("")
			}
		});

	}

	return (
		<div>
			{!isReady &&(
				<div>
					문제를 출제하고 있습니다.
					<br/>
					잠시만 기다려주세요.
				</div>
				)
			}
			{isReady &&
				<div>
					<div>어휘 테스트</div>
					<div>{currentQuestion.type == "fill" ? "다음 빈칸을 채울 단어를 적으세요." : "다음 단어의 뜻을 적으세요."}</div>
					<div>{currentQuestion.question}</div>
					<div className={"flex gap-2"}>
						답안: <Input value={answer} onChange={(e)=>setAnswer(e.target.value)}/>
					</div>
					<Button onClick={nextStep}>다음</Button>
				</div>
			}
		</div>
	)
}