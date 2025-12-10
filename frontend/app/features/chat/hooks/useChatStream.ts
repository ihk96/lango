import { useState, useCallback } from 'react';
import { fetchEventSource } from '@microsoft/fetch-event-source';
import type { Message } from '../types';

export function useChatStream(sessionId: number | null) {
    const [messages, setMessages] = useState<Message[]>([]);
    const [streaming, setStreaming] = useState(false);

    // Add initial message manually if needed, or handle it via props

    const sendMessage = useCallback(async (content: string) => {
        if (!sessionId) return;

        // Optimistic update
        setMessages(prev => [...prev, { role: 'user', content }]);
        setMessages(prev => [...prev, { role: 'assistant', content: '' }]);
        setStreaming(true);

        try {
            await fetchEventSource(`/api/chat/sessions/${sessionId}/messages/stream`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ content }),
                async onopen(response) {
                    if (response.ok && response.headers.get('content-type')?.includes('text/event-stream')) {
                        return; // everything's good
                    } else if (response.status >= 400 && response.status < 500 && response.status !== 429) {
                        // Client side error
                        throw new Error(`Failed to establish stream: ${response.statusText}`);
                    }
                },
                onmessage(msg) {
                    setMessages(prev => {
                        const newPrev = [...prev];
                        const lastMsg = newPrev[newPrev.length - 1];
                        if (lastMsg.role === 'assistant') {
                            // Append content. 
                            // Depending on backend, might need decoding.
                            // Assuming simple text data for now.
                            // Note: updates to 'lastMsg' mutate the defined object in array copy.
                            // It's safer to create new object.
                            newPrev[newPrev.length - 1] = {
                                ...lastMsg,
                                content: lastMsg.content + msg.data
                            };
                            return newPrev;
                        }
                        return prev;
                    });
                },
                onclose() {
                    setStreaming(false);
                },
                onerror(err) {
                    console.error("Stream error:", err);
                    setStreaming(false);
                    throw err; // rethrow to stop retrying
                }
            });
        } catch (err) {
            console.error(err);
            setStreaming(false);
        }
    }, [sessionId]);

    return { messages, setMessages, sendMessage, streaming };
}
