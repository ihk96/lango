import {setupServer} from "msw/node";
import {usersHandlers} from "~/mocks/handlers/users-handlers";
import {userLevelHandlers} from "~/mocks/handlers/user-level-handlers";
import {chatHandlers} from "~/mocks/handlers/chat-handlers";

export const server = setupServer(
	...usersHandlers, ...userLevelHandlers, ...chatHandlers
);
