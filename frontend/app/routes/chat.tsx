import { useEffect, useState, useRef } from 'react';
import { startSession } from '~/features/chat/api';
import { sendFeedback, translateText } from '~/features/support/api';
import { useChatStream } from '~/features/chat/hooks/useChatStream';
import type { ChatSession } from '~/features/chat/types';
import { Button } from '~/components/ui/button';
import { Input } from '~/components/ui/input';
import { Card } from '~/components/ui/card';
import { ScrollArea } from '~/components/ui/scroll-area';
import { Send, RefreshCw, Languages } from 'lucide-react';
import { toast } from "sonner";

export default function ChatPage() {
    const [session, setSession] = useState<ChatSession | null>(null);
    const [topic, setTopic] = useState("");
    const { messages, setMessages, sendMessage, streaming } = useChatStream(session?.sessionId || null);
    const [input, setInput] = useState("");
    const scrollRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        // Start session on mount or via a button
        startSession();
    }, []);

    const startSessionAction = async () => {
        try {
            const data = await startSession(topic || undefined);
            setSession(data);
            setMessages([{ role: 'assistant', content: data.initialMessage }]);
        } catch (e) {
            console.error(e);
        }
    };

    const handleSend = () => {
        if (!input.trim() || streaming) return;
        sendMessage(input);
        setInput("");
    };

    const handleFeedback = async () => {
        if (!input.trim()) return;
        try {
            const result = await sendFeedback(input, session?.scenario);
            toast.info(result, { duration: 5000 });
        } catch (e) {
            toast.error("피드백을 받지 못했습니다.");
        }
    };

    const handleTranslate = async (text: string) => {
        try {
            const result = await translateText(text);
            toast.success(result, { duration: 4000 });
        } catch (e) {
            toast.error("번역 실패");
        }
    }

    const handleKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            handleSend();
        }
    }

    // Auto scroll
    useEffect(() => {
        if (scrollRef.current) {
            scrollRef.current.scrollIntoView({ behavior: 'smooth', block: 'end' });
        }
    }, [messages, streaming]);

    return (
        <div className="flex h-screen flex-col bg-gray-100 p-4">
            <div className="flex-none mb-4 flex justify-between items-center">
                <h1 className="text-xl font-bold">Chat Learning</h1>
                <Button variant="outline" size="sm" onClick={startSessionAction}>
                    <RefreshCw className="mr-2 h-4 w-4" /> New Session
                </Button>
            </div>

            <Card className="flex-1 flex flex-col overflow-hidden">
                <ScrollArea className="flex-1 p-4">
                    {messages.map((m, i) => (
                        <div key={i} className={`flex mb-4 flex-col ${m.role === 'user' ? 'items-end' : 'items-start'}`}>
                            <div className={`max-w-[80%] p-3 rounded-lg ${m.role === 'user' ? 'bg-blue-500 text-white' : 'bg-gray-200 text-black'}`}>
                                {m.content}
                            </div>
                            {m.role === 'assistant' && m.content && (
                                <Button variant="ghost" size="sm" className="h-6 mt-1 text-xs text-gray-500" onClick={() => handleTranslate(m.content)}>
                                    <Languages className="h-3 w-3 mr-1" /> 번역
                                </Button>
                            )}
                        </div>
                    ))}
                    <div ref={scrollRef} />
                </ScrollArea>

                <div className="p-4 border-t flex gap-2">
                    <Input
                        value={input}
                        onChange={e => setInput(e.target.value)}
                        onKeyDown={handleKeyDown}
                        placeholder="Type your message..."
                        disabled={streaming}
                    />
                    <Button variant="secondary" onClick={handleFeedback} disabled={!input.trim() || streaming}>
                        Feedback
                    </Button>
                    <Button onClick={handleSend} disabled={streaming || !input.trim()}>
                        <Send className="h-4 w-4" />
                    </Button>
                </div>
            </Card>

            {session && (
                <div className="mt-2 text-sm text-gray-500 text-center">
                    Role: You are {session.userRole}, AI is {session.aiRole}. Scenario: {session.scenario}
                </div>
            )}
        </div>
    );
}
