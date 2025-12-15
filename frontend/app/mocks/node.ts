import {setupServer} from "msw/node";
import {usersHandlers} from "~/mocks/handlers/users-handlers";
import {userLevelHandlers} from "~/mocks/handlers/user-level-handlers";

export const server = setupServer(
	...usersHandlers, ...userLevelHandlers
);
