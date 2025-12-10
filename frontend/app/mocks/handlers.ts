import { http, HttpResponse } from 'msw';

export const handlers = [
    // Auth
    http.post('/api/auth/login', async () => {
        return HttpResponse.json({
            success: true,
            message: null,
            data: {
                id: 1,
                email: 'user@example.com',
                nickname: 'LangoUser',
                currentLevel: 'BEGINNER'
            }
        });
    }),

    http.post('/api/auth/signup', async () => {
        return HttpResponse.json({
            success: true,
            message: null,
            data: {
                id: 1,
                email: 'user@example.com',
                nickname: 'LangoUser',
                currentLevel: null
            }
        });
    }),

    http.post('/api/auth/logout', async () => {
        return HttpResponse.json({
            success: true,
            message: null,
            data: null
        });
    }),

    // Level Assessment
    http.get('/api/level/initial-questions', () => {
        return HttpResponse.json({
            success: true,
            message: null,
            data: [
                { id: 1, question: "Choose the correct verb: She ___ to school every day. (go / goes)" },
                { id: 2, question: "Write a sentence introducing yourself." },
                { id: 3, question: "What is your favorite food and why?" }
            ]
        });
    }),

    http.post('/api/level/assess-initial', async () => {
        // Simulate processing delay
        await new Promise(resolve => setTimeout(resolve, 1000));
        return HttpResponse.json({
            success: true,
            message: null,
            data: {
                level: 'INTERMEDIATE',
                reason: 'Demonstrated good vocabulary and grammar usage.'
            }
        });
    }),

    // Chat Feature
    http.post('/api/chat/sessions', async () => {
        return HttpResponse.json({
            success: true,
            message: null,
            data: {
                sessionId: 123,
                scenario: "Ordering coffee at a cafe",
                userRole: "Customer",
                aiRole: "Barista",
                initialMessage: "Hi there! What can I get for you today?"
            }
        });
    }),

    http.post('/api/chat/sessions/:sessionId/messages/stream', async () => {
        const stream = new ReadableStream({
            start(controller) {
                const encoder = new TextEncoder();
                const text = "Sure, I can help you with that. ";
                const chunks = text.split(" ");
                let i = 0;

                const interval = setInterval(() => {
                    if (i >= chunks.length) {
                        clearInterval(interval);
                        controller.close();
                        return;
                    }
                    const chunk = chunks[i++];
                    // SSE format: data: <content>\n\n
                    controller.enqueue(encoder.encode(`data: ${chunk} \n\n`));
                }, 100);
            }
        });

        return new HttpResponse(stream, {
            headers: {
                'Content-Type': 'text/event-stream',
            },
        });
    }),

    http.post('/api/support/translate', async () => {
        return HttpResponse.json({
            success: true,
            data: "Translated text example."
        });
    }),

    http.post('/api/support/feedback', async () => {
        return HttpResponse.json({
            success: true,
            data: "Feedback: Good sentence!"
        });
    }),

    // Article Feature
    http.post('/api/articles/generate', async () => {
        await new Promise(resolve => setTimeout(resolve, 1500));
        return HttpResponse.json({
            success: true,
            data: {
                title: "The Future of Space Travel",
                content: "Space travel involves traveling into outer space. It allows humans to explore the universe and discover new worlds.",
                vocabulary: [
                    { word: "Universe", meaning: "All existing matter and space considered as a whole" },
                    { word: "Explore", meaning: "Travel in or through an unfamiliar area" }
                ]
            }
        });
    }),

    // Health check
    http.get('/api/health', () => {
        return HttpResponse.json({
            success: true,
            message: 'OK',
            data: { status: 'healthy' }
        });
    }),
];
