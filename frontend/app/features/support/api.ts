import { client } from "~/lib/api";

export async function sendFeedback(message: string, context?: string): Promise<string> {
    return client<string>("/support/feedback", { body: { message, context } });
}

export async function translateText(text: string): Promise<string> {
    return client<string>("/support/translate", { body: { text } });
}
