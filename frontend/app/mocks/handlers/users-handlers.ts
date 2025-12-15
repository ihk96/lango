import { http, HttpResponse } from 'msw';
import {getHost} from "~/mocks/utils";

export const usersHandlers = [
	// Auth
	http.post(getHost()+'/api/v1/auth/login', async () => {
		return HttpResponse.json({
			message: null,
			data: {
				id: "userId",
				email: 'user@example.com',
				nickname: 'LangoUser',
			}
		});
	}),

	http.post(getHost()+'/api/v1/auth/signup', async () => {
		return HttpResponse.json({
			message: null,
			data: {
				id: "userId",
				email: 'user@example.com',
				nickname: 'LangoUser',
				currentLevel: null
			}
		});
	}),

	http.post(getHost()+'/api/v1/auth/logout', async () => {
		return HttpResponse.json({
			message: "success",
			data: null
		});
	}),
]