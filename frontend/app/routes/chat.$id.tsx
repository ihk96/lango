import {type Route} from "../../.react-router/types/app/routes/+types/chat.$id";
import {getSession, getSessionMessages, sendMessage as sendChatMessage, startSession} from "~/features/chat/api";
import {useLoaderData} from "react-router";
import {useEffect, useState} from "react";
import {Spinner} from "~/components/ui/spinner";
import type {ChatMessage} from "~/features/chat/types";
import {Textarea} from "~/components/ui/textarea";
import {Button} from "~/components/ui/button";
import {cn} from "~/lib/utils";

export async function loader({request, params}: Route.LoaderArgs){

	const {data:session} = await getSession(params.id,request).then(res => res.data)
	const {data:messages} = await getSessionMessages(params.id,request).then(res => res.data)

	return {
		session,
		messages : [] as ChatMessage[],
	}
}

export default function chat$id(){
	const {session,messages : loaderMessages} = useLoaderData<typeof loader>()
	const [messages, setMessages] = useState<ChatMessage[]>(loaderMessages)
	const [isLoading,setIsLoading] = useState(false)
	const [userMessage,setUserMessage] = useState("")

	useEffect(() => {
		if(messages.length === 0 && !isLoading) start()
	}, []);

	async function start(){
		setIsLoading(true)
		const {data: message} = await startSession(session.sessionId).then(res => res.data)
		setMessages(prev=>[...prev,message])
		setIsLoading(false)
	}

	async function sendMessage(){
		setIsLoading(true)
		setMessages(prev=>[...prev,{content:userMessage,sender:"USER"}])
		const {data: message} = await sendChatMessage(session.sessionId, userMessage).then(res => res.data)
		setMessages(prev=>[...prev,message])
		setIsLoading(false)
		setUserMessage("")
	}

	return (
		<div>
			chat id
			<br/>
			제목 : {session.title}
			<br/>
			시나리오:
			<br/>
			{session.scenario}
			<br/><br/>
			<div className={"flex flex-col gap-2"}>
				{messages.map(((message,i) => (
					<div key={i} className={cn("flex", message.sender == "AI" ? "justify-end" : "justify-start")}>
						<div className={`bg-gray-200 rounded-lg px-4 py-2 max-w-[90%] ${message.sender === "USER" ? "text-right" : ""}`}>
							{message.content}
						</div>
					</div>
				)))
				}
			</div>
			{isLoading && <Spinner />}
			<Textarea value={userMessage} onChange={(e) => setUserMessage(e.target.value)} />
			<Button onClick={sendMessage}>보내기</Button>
		</div>
	)
}