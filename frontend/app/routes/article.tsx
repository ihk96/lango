import { useState } from "react";
import { client } from "~/lib/api";
import type { Article } from "~/features/article/types";
import { Button } from "~/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "~/components/ui/card";
import { Input } from "~/components/ui/input";
import { ScrollArea } from "~/components/ui/scroll-area";
import { toast } from "sonner";
import { Separator } from "~/components/ui/separator";

export default function ArticlePage() {
    const [topic, setTopic] = useState("");
    const [article, setArticle] = useState<Article | null>(null);
    const [loading, setLoading] = useState(false);
    const [translatedContent, setTranslatedContent] = useState<string | null>(null);

    const handleGenerate = async () => {
        if (!topic.trim()) return;
        setLoading(true);
        setArticle(null);
        setTranslatedContent(null);
        try {
            const data = await client<Article>("/articles/generate", { body: { topic } });
            setArticle(data);
        } catch (e) {
            toast.error("아티클 생성 실패");
        } finally {
            setLoading(false);
        }
    };

    const handleTranslate = async () => {
        if (!article || translatedContent) return;
        try {
            const res = await client<string>("/support/translate", { body: { text: article.content } });
            setTranslatedContent(res);
        } catch (e) {
            toast.error("번역 실패");
        }
    };

    return (
        <div className="container mx-auto py-10 px-4 flex flex-col gap-6 max-w-4xl">
            <Card>
                <CardHeader>
                    <CardTitle>Article Generation</CardTitle>
                    <CardDescription>Enter a topic to generate an English article.</CardDescription>
                </CardHeader>
                <CardContent className="flex gap-4">
                    <Input
                        placeholder="e.g. Technology, Travel, Food"
                        value={topic}
                        onChange={e => setTopic(e.target.value)}
                    />
                    <Button onClick={handleGenerate} disabled={loading || !topic.trim()}>
                        {loading ? "Generating..." : "Generate"}
                    </Button>
                </CardContent>
            </Card>

            {article && (
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <Card className="md:col-span-2">
                        <CardHeader>
                            <CardTitle>{article.title}</CardTitle>
                            <div className="flex gap-2">
                                <Button variant="outline" size="sm" onClick={handleTranslate} disabled={!!translatedContent}>
                                    {translatedContent ? "Translated" : "Translate Article"}
                                </Button>
                            </div>
                        </CardHeader>
                        <CardContent className="space-y-4">
                            <div className="leading-relaxed text-lg">
                                {article.content}
                            </div>
                            {translatedContent && (
                                <>
                                    <Separator />
                                    <div className="leading-relaxed text-lg text-gray-700 bg-gray-50 p-4 rounded-md">
                                        {translatedContent}
                                    </div>
                                </>
                            )}
                        </CardContent>
                    </Card>

                    <Card>
                        <CardHeader>
                            <CardTitle>Vocabulary</CardTitle>
                        </CardHeader>
                        <CardContent>
                            <ScrollArea className="h-[400px]">
                                <ul className="space-y-4">
                                    {article.vocabulary.map((vocab, i) => (
                                        <li key={i} className="border-b pb-2 last:border-0">
                                            <div className="font-bold text-blue-600">{vocab.word}</div>
                                            <div className="text-sm text-gray-600">{vocab.meaning}</div>
                                        </li>
                                    ))}
                                </ul>
                            </ScrollArea>
                        </CardContent>
                    </Card>
                </div>
            )}
        </div>
    );
}
