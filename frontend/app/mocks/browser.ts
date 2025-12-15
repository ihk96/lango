import { setupWorker } from 'msw/browser';
import {usersHandlers} from "~/mocks/handlers/users-handlers";
import {userLevelHandlers} from "~/mocks/handlers/user-level-handlers";

export const worker = setupWorker(
	...usersHandlers, ...userLevelHandlers
);
