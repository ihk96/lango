import {useEffect, useState} from "react";
import type {ChatSession} from "~/features/chat/types";
import {createSession} from "~/features/chat/api";
import {useNavigate} from "react-router";
import {Button} from "~/components/ui/button";


export default function ChatNew() {
	const [session,setSession] = useState<ChatSession>()
	const [isReady,setIsReady] = useState(true)
	const navigate = useNavigate()

	useEffect(() => {
		createSession().then(res=>{
			const session = res.data.data
			setSession(session)
			setIsReady(true)
		})
	}, []);

	return <div>
		{
			!isReady && <span>대화 시나리오 작성 중...</span>
		}
		{
			isReady && session && <div>
				<h3>대화 제목 : {session.title}</h3>
				<h3>대화 시나리오</h3>
				<p>{session.scenario}</p>
				<h3>당신의 역할: {session.userRole}</h3>
				<h3>대화 상대방의 역할: {session.aiRole}</h3>
				<Button onClick={() => navigate(`/chat/${session?.sessionId}`)}>시작하기</Button>
			</div>
		}
	</div>
}