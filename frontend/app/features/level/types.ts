import {z} from "zod";

export interface Question {
    id: number;
    question: string;
}

export interface AssessmentResult {
    level: string;
    reason: string;
}


export const VocabularyTestSchema = z.object({
    question : z.string(),
    answer : z.string(),
    level : z.string(),
    type : z.enum(["fill","meaning"])
})

export const GrammarTestSchema = z.object({
    passageText : z.string(),
    answer : z.string(),
    level : z.string(),
    options : z.array(z.string())
})

export const ReadingTestSchema = z.object({
    passageText : z.string(),
    question : z.string(),
    answer : z.string(),
})

export const WritingTestSchema = z.object({
    question : z.string(),
    level : z.string(),
})