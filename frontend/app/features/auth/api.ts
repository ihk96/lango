import type { LoginInput, SignupInput } from "~/features/auth/schema";
import type { AuthUser } from "~/features/auth/types";
import {type APIResponse, client, getDefaultOption} from "~/lib/APIClient";
import type {AxiosResponse} from "axios";

export async function login(input: LoginInput, request? : Request) {
    const options = getDefaultOption(request)
    const response: AxiosResponse<APIResponse<AuthUser>> = await client.post("/v1/auth/login", input, options);
    return response
}

export async function signup(input: SignupInput, request? : Request) {
    return client.post<APIResponse<AuthUser>>("/v1/auth/signup", input , getDefaultOption(request)  );
}

export async function logout(request? : Request): Promise<void> {
    await client.post<null>("/v1/auth/logout", getDefaultOption(request));
}
