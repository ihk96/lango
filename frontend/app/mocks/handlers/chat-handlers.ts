import {http, HttpResponse} from "msw";
import {getHost} from "~/mocks/utils";

export const chatHandlers = [
	http.post(getHost()+'/v1/chat/sessions', async () => {
		return HttpResponse.json({
			message: null,
			data: {
				sessionId : "sessionId",
				scenario : "당신은 활기찬 동네 커피숍에 방문한 손님입니다. 오늘따라 특별한 커피를 마시고 싶어서, 메뉴에 없는 맞춤형 주문을 하려고 합니다. 바리스타에게 복잡한 요청을 정확하게 전달하고, 필요한 경우 바리스타의 추가 질문에 명확하게 답변해야 할 것입니다. 바리스타는 당신의 특별한 주문을 정확히 이해하고 준비하기 위해 몇 가지 세부 사항을 면밀히 확인할 것입니다. 예를 들어, 우유 종류, 시럽 추가 여부, 샷 추가, 또는 테이크아웃 여부 등 다양한 질문이 포함될 수 있습니다. 이 시나리오를 통해 다양한 커피 관련 표현과 정중하게 요청하고 응답하는 심층적인 대화 방법을 연습할 수 있습니다.",
				userRole : "손님",
				aiRole : "바리스타",
				title : "커피 주문 대화"
			}
		})
	}),

	http.post(getHost()+'/v1/chat/sessions/:sessionId/start', async () => {

		return HttpResponse.json({
			message: null,
			data: {
				content : "Hello",
				subContent: "안녕하세요."
			}
		})
	}),

	http.post(getHost()+'/v1/chat/sessions/:sessionId/messages', async () => {
		return HttpResponse.json({
			message: null,
			data: {
				content : "Response Response Response Response Response Response",
				subContent: "응답 응답 응답 응답 응답"
			}
		})
	})


]