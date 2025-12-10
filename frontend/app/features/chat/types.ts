export interface Message {
    id?: string; // transient id for UI
    role: 'user' | 'assistant';
    content: string;
}

export interface ChatSession {
    sessionId: number;
    scenario: string;
    userRole: string;
    aiRole: string;
    initialMessage: string;
}

export interface FeedbackResponse {
    correction: string; // Or the full string as per doc
}

export interface TranslateResponse {
    translatedText: string;
}
