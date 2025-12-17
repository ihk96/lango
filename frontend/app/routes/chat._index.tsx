import {type Route} from "../../.react-router/types/app/routes/+types/chat._index";
import {getSessions} from "~/features/chat/api";
import {useLoaderData, useNavigate} from "react-router";
import {Button} from "~/components/ui/button";

export async function loader({request}: Route.LoaderArgs){

	const {data: sessions} = await getSessions(request).then(res => {
		console.log(res)
		return res.data
	})

	return {
		sessions
	}
}

export default function Chat_index() {
	const {sessions} = useLoaderData<typeof loader>()
	const navigate = useNavigate()

	return (
		<div>
			chat
			<Button onClick={() => navigate('/chat/new')}>새로운 대화</Button>
			<div className={"flex flex-col"}>
				{sessions.map(session => (
					<div key={session.sessionId}
					     onClick={() => navigate(`/chat/${session.sessionId}`)}
					>
						{session.title}
					</div>
				))}
			</div>
		</div>
	)
}