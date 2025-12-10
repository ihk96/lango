export interface Vocabulary {
    word: string;
    meaning: string;
}

export interface Article {
    title: string;
    content: string;
    vocabulary: Vocabulary[];
}
