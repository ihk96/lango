import { client } from "~/lib/api";
import type { Article } from "~/features/article/types";

export async function generateArticle(topic: string): Promise<Article> {
    return client<Article>("/articles/generate", { body: { topic } });
}
