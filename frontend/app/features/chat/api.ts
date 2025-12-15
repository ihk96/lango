import type { ChatSession } from "~/features/chat/types";
import {type APIResponse, client, getDefaultOption} from "~/lib/APIClient";
import {z} from "zod/v4";

export async function createSession(topic?: string, request? : Request){
    const options = getDefaultOption(request)

    return client.post<APIResponse<ChatSession>>("/v1/chat/sessions?topic="+topic, {}, options);
}

const ScenarioResponse = z.object({
    sessionId: z.string(),
    title: z.string(),
    scenario: z.string(),
    userRole: z.string(),
    aiRole: z.string(),
})

export async function startSession(sessionId: string, request? : Request){
    return client.post<APIResponse<z.infer<typeof ScenarioResponse>>>("/v1/chat/sessions/"+sessionId+"/start", {}, getDefaultOption(request));
}

const ChatMessageResponse = z.object({
    content: z.string(),
    subContent: z.string(),
})

export async function sendMessage(sessionId: string, message: string, request? : Request){
    return client.post<APIResponse<z.infer<typeof ChatMessageResponse>>>("/v1/chat/sessions/"+sessionId+"/messages", {content: message}, getDefaultOption(request));
}