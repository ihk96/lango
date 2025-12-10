import { client } from "~/lib/api";
import type { ChatSession } from "~/features/chat/types";

export async function startSession(topic?: string): Promise<ChatSession> {
    return client<ChatSession>("/chat/sessions", { body: topic ? { topic } : undefined });
}
