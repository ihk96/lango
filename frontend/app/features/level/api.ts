import type { Question, AssessmentResult } from "~/features/level/types";
import {type APIResponse, client, getDefaultOption} from "~/lib/APIClient";
import type {AxiosResponse} from "axios";
import {z} from "zod/v4";

const getUsersLevelResponse = z.object({
    id: z.string(),
    userId: z.string(),
    level: z.string(),
    createdAt: z.string(),
    updatedAt: z.string()
})

export async function getUsersLevel(request? : Request) {
    let response : AxiosResponse<APIResponse<z.infer<typeof getUsersLevelResponse>>>
    response = await client.get("/v1/users/levels", getDefaultOption(request))
    return response
}

export async function generateTests(request? :Request){
    let response : AxiosResponse
    response = await client.post("/v1/users/levels/tests/generate", {}, getDefaultOption(request))
    return response
}

const getVocabularyTestsResponse = z.object({
    question : z.string(),
    answer : z.string(),
    level : z.string(),
    type : z.enum(["fill","meaning"])
})

export async function getVocabularyTests(request? :Request){
    let response : AxiosResponse<APIResponse<z.infer<typeof getVocabularyTestsResponse>[]>>
    response = await client.get("/v1/users/levels/tests/questions/vocabulary", getDefaultOption(request))
    return response
}

const getGrammarTestsResponse = z.object({
    passageText : z.string(),
    answer : z.string(),
    level : z.string(),
    options : z.array(z.string())
})
export async function getGrammarTests(request? :Request){
    let response : AxiosResponse<APIResponse<z.infer<typeof getGrammarTestsResponse>[]>>
    response = await client.get("/v1/users/levels/tests/questions/grammar", getDefaultOption(request))
    return response
}

const getReadingTestsResponse = z.object({
    passageText : z.string(),
    question : z.string(),
    answer : z.string(),
    level : z.string()
})
export async function getReadingTests(request? :Request){
    let response : AxiosResponse<APIResponse<z.infer<typeof getReadingTestsResponse>[]>>
    response = await client.get("/v1/users/levels/tests/questions/reading", getDefaultOption(request))
    return response
}

const getWritingTestsResponse = z.object({
    question : z.string(),
    level : z.string(),
})

export async function getWritingTests(request? :Request){
    let response : AxiosResponse<APIResponse<z.infer<typeof getWritingTestsResponse>[]>>
    response = await client.get("/v1/users/levels/tests/questions/writing", getDefaultOption(request))
    return response
}

export async function answerVocabularyTest(question: {questionId: number, answer: string}, request? :Request){
    let response : AxiosResponse
    response = await client.post("/v1/users/levels/tests/answers/vocabulary", question, getDefaultOption(request))
    return response
}

export async function answerGrammarTest(question: {questionId: number, answer: string}, request? :Request){
    let response : AxiosResponse
    response = await client.post("/v1/users/levels/tests/answers/grammar", question, getDefaultOption(request))
    return response
}
export async function answerReadingTest(question: {questionId: number, answer: string}, request? :Request){
    let response : AxiosResponse
    response = await client.post("/v1/users/levels/tests/answers/reading", question, getDefaultOption(request))
    return response
}
export async function answerWritingTest(question: {questionId: number, answer: string}, request? :Request){
    let response : AxiosResponse
    response = await client.post("/v1/users/levels/tests/answers/writing", question, getDefaultOption(request))
    return response
}
export async function evaluate(request?: Request){
    let response : AxiosResponse<APIResponse<string>>
    response = await client.post("/v1/users/levels/tests/evaluate", null, getDefaultOption(request))
    return response
}