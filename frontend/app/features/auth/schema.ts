import { z } from 'zod';

export const loginSchema = z.object({
    email: z.string().email('유효한 이메일을 입력해주세요.'),
    password: z.string().min(1, '비밀번호를 입력해주세요.'),
});

export const signupSchema = z.object({
    email: z.string().email('유효한 이메일을 입력해주세요.'),
    nickname: z.string().min(2, '닉네임은 2자 이상이어야 합니다.'),
    password: z.string().min(8, '비밀번호는 8자 이상이어야 합니다.')
    // relaxing regex to generic strong password or just min length as per user example
});

export type LoginInput = z.infer<typeof loginSchema>;
export type SignupInput = z.infer<typeof signupSchema>;
