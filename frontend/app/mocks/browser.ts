import { setupWorker } from 'msw/browser';
import {usersHandlers} from "~/mocks/handlers/users-handlers";
import {userLevelHandlers} from "~/mocks/handlers/user-level-handlers";
import {chatHandlers} from "~/mocks/handlers/chat-handlers";

export const worker = setupWorker(
	...usersHandlers, ...userLevelHandlers, ...chatHandlers
);
