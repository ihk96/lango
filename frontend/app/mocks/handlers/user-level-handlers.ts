import { http, HttpResponse } from 'msw'
import {getHost} from "~/mocks/utils";

const api = getHost() + '/api';

export const userLevelHandlers = [
    // Get user's level
    http.get(api + "/v1/users/levels", async () => {
        return HttpResponse.json({
            message: null,
            data: {
                id: "level-1",
                userId: "userId",
                level: "A1.2",
                createdAt: new Date().toISOString(),
                updatedAt: new Date().toISOString(),
            },
        });
    }),

    // Generate tests
    http.post(api + "/v1/users/levels/tests/generate", async () => {
        return HttpResponse.json();
    }),

    // Get Vocabulary tests (array)
    http.get(api + "/v1/users/levels/tests/questions/vocabulary", async () => {
        return HttpResponse.json({
            message: null,
            data: [
                { question: "Apple의 뜻은?", answer: "사과", level: "A1.1", type: "vocabulary" },
                { question: "Beautiful의 뜻은?", answer: "아름다운", level: "A1.2", type: "vocabulary" },
                { question: "Library의 뜻은?", answer: "도서관", level: "A2.1", type: "vocabulary" },
            ],
        });
    }),

    // Get Grammar test (single)
    http.get(api + "/v1/users/levels/tests/questions/grammar", async () => {
        return HttpResponse.json({
            message: null,
            data: [
	            {
		            passageText: "She ___ to school every day.",
		            answer: "goes",
		            level: "A2",
		            options: ["go", "goes", "going", "gone"],
	            },
	            {
		            passageText: "he ___ to school every day.",
		            answer: "goes",
		            level: "A2",
		            options: ["go", "goes", "going", "gone"],
	            },
            ],
        });
    }),

    // Get Reading test (single)
    http.get(api + "/v1/users/levels/tests/questions/reading", async () => {
        return HttpResponse.json({
            message: null,
            data: [
	            {
		            passageText: "Tom loves reading books about space. He often visits the library on weekends.",
		            question: "Tom은 주말에 어디에 자주 가나요?",
		            answer: "도서관",
		            level: "A2",
	            },
	            {
		            passageText: "Tom loves reading books about space. He often visits the library on weekends.",
		            question: "Tom은 주말에 어디에 자주 가나요?",
		            answer: "도서관",
		            level: "A3",
	            }
            ],
        });
    }),

    // Get Writing test (single)
    http.get(api + "/v1/users/levels/tests/questions/writing", async () => {
        return HttpResponse.json({
            message: null,
            data: [
	            {
		            question: "가장 좋아하는 음식에 대해 2~3문장으로 영어로 작성하세요.",
		            level: "A2",
	            },
	            {
		            question: "가장 좋아하는 음식에 대해 2~3문장으로 영어로 작성하세요.",
		            level: "B2",
	            }
            ],
        });
    }),

    // Answer: Vocabulary
    http.post(api + "/v1/users/levels/tests/answers/vocabulary", async ({ request }) => {
        // const body = await request.json();
        return HttpResponse.json();
    }),

    // Answer: Grammar
    http.post(api + "/v1/users/levels/tests/answers/grammar", async ({ request }) => {
        // const body = await request.json();
        return HttpResponse.json();
    }),

    // Answer: Reading
    http.post(api + "/v1/users/levels/tests/answers/reading", async ({ request }) => {
        // const body = await request.json();
        return HttpResponse.json();
    }),

    // Answer: Writing
    http.post(api + "/v1/users/levels/tests/answers/writing", async ({ request }) => {
        // const body = await request.json();
        return HttpResponse.json();
    }),

    // Evaluate tests → returns plain text level like "A2"
    http.post(api + "/v1/users/levels/tests/evaluate", async () => {
        return HttpResponse.text("A1.1");
    }),
];