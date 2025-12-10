export interface ApiResponse<T> {
    success: boolean;
    message: string | null;
    data: T | null;
}

export class ApiError extends Error {
    constructor(public message: string, public status: number) {
        super(message);
        this.name = 'ApiError';
    }
}

const BASE_URL = '/api';

interface RequestConfig extends Omit<RequestInit, 'body'> {
    body?: any;
}

export async function client<T>(endpoint: string, { body, ...customConfig }: RequestConfig = {}): Promise<T> {
    const headers = { 'Content-Type': 'application/json' };
    const config: RequestConfig = {
        method: body ? 'POST' : 'GET',
        ...customConfig,
        headers: {
            ...headers,
            ...customConfig.headers,
        },
        credentials: 'include',
    };

    if (body) {
        config.body = JSON.stringify(body);
    }

    const response = await fetch(`${BASE_URL}${endpoint}`, config);

    let data: ApiResponse<T>;
    try {
        data = await response.json();
    } catch (error) {
        throw new ApiError('Failed to parse response JSON', response.status);
    }

    if (!response.ok) {
        const errorMessage = data?.message || response.statusText || 'Something went wrong';
        throw new ApiError(errorMessage, response.status);
    }

    if (!data.success) {
        throw new ApiError(data.message || 'API request failed', response.status);
    }

    return data.data as T;
}
