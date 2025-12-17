export type ChatMessage = {
    content: string,
    subContent?: string,
    sender: "AI" | "USER"
}

export interface ChatSession {
    sessionId: string;
    scenario: string;
    userRole: string;
    aiRole: string;
    title: string;
}

export interface FeedbackResponse {
    correction: string; // Or the full string as per doc
}

export interface TranslateResponse {
    translatedText: string;
}
