import { client } from "~/lib/api";
import type { LoginInput, SignupInput } from "~/features/auth/schema";
import type { AuthUser } from "~/features/auth/types";

export async function login(input: LoginInput): Promise<AuthUser> {
    return client<AuthUser>("/auth/login", { body: input });
}

export async function signup(input: SignupInput): Promise<AuthUser> {
    return client<AuthUser>("/auth/signup", { body: input });
}

export async function logout(): Promise<void> {
    await client<null>("/auth/logout", { method: "POST" });
}
