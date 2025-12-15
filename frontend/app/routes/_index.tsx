import {type Route} from "../../.react-router/types/app/routes/+types/_index";
import {getUsersLevel} from "~/features/level/api";
import {redirect} from "react-router";

export async function loader({request} : Route.LoaderArgs){

	const level = await getUsersLevel(request)
		.then(res => res.data)
		.catch(()=>{
			throw redirect("/exam")
		})

}

export default function Index(){

	return (
		<div>
			index
		</div>
	)
}