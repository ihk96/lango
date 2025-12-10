import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { client } from "~/lib/api";
import { Button } from "~/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle, CardDescription, CardFooter } from "~/components/ui/card";
import { Textarea } from "~/components/ui/textarea";
import { Label } from "~/components/ui/label";
import { toast } from "sonner";

interface Question {
    id: number;
    question: string;
}

interface AssessmentResult {
    level: string;
    reason: string;
}

export default function LevelAssessmentPage() {
    const navigate = useNavigate();
    const [questions, setQuestions] = useState<Question[]>([]);
    const [answers, setAnswers] = useState<Record<number, string>>({});
    const [loading, setLoading] = useState(true);
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        async function fetchQuestions() {
            try {
                const data = await client<Question[]>("/level/initial-questions");
                setQuestions(data);
            } catch (error) {
                toast.error("질문을 불러오는데 실패했습니다.");
            } finally {
                setLoading(false);
            }
        }
        fetchQuestions();
    }, []);

    const handleAnswerChange = (id: number, value: string) => {
        setAnswers((prev) => ({ ...prev, [id]: value }));
    };

    const handleSubmit = async () => {
        setSubmitting(true);
        // Format answers as a single string as per API example, or we could verify if API accepts JSON.
        // Doc says: "answers": "1. B, 2. A..."
        // I will format it nicely.
        const formattedAnswers = questions
            .map((q) => `${q.id}. ${answers[q.id] || '(No Answer)'}`)
            .join(", ");

        try {
            const result = await client<AssessmentResult>("/level/assess-initial", {
                body: { answers: formattedAnswers },
            });
            toast.success(`레벨 진단 완료: ${result.level}`);
            // Navigate to dashboard or show result
            navigate("/dashboard");
        } catch (error) {
            toast.error("제출에 실패했습니다.");
        } finally {
            setSubmitting(false);
        }
    };

    if (loading) return <div className="p-8 text-center">Loading questions...</div>;

    return (
        <div className="container mx-auto max-w-2xl py-10 px-4">
            <Card>
                <CardHeader>
                    <CardTitle>레벨 테스트</CardTitle>
                    <CardDescription>
                        정확한 레벨 측정을 위해 성실히 답변해주세요.
                    </CardDescription>
                </CardHeader>
                <CardContent className="space-y-6">
                    {questions.map((q) => (
                        <div key={q.id} className="space-y-2">
                            <Label htmlFor={`q-${q.id}`} className="text-base font-medium">
                                {q.id}. {q.question}
                            </Label>
                            <Textarea
                                id={`q-${q.id}`}
                                placeholder="답변을 작성해주세요..."
                                value={answers[q.id] || ""}
                                onChange={(e) => handleAnswerChange(q.id, e.target.value)}
                                className="min-h-[80px]"
                            />
                        </div>
                    ))}
                </CardContent>
                <CardFooter>
                    <Button onClick={handleSubmit} disabled={submitting} className="w-full">
                        {submitting ? "제출 중..." : "결과 제출하기"}
                    </Button>
                </CardFooter>
            </Card>
        </div>
    );
}
