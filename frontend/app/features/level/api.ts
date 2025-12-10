import { client } from "~/lib/api";
import type { Question, AssessmentResult } from "~/features/level/types";

export async function getInitialQuestions(): Promise<Question[]> {
    return client<Question[]>("/level/initial-questions");
}

export async function assessInitial(answers: string): Promise<AssessmentResult> {
    return client<AssessmentResult>("/level/assess-initial", { body: { answers } });
}
