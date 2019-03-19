import {GET_ERRORS} from "./types";

export const cleanErrors = () => {
    return ({
            type: GET_ERRORS,
            payload: {}
        }
    )
};

// function addTodo(text) {
//     return {
//         type: "ADD_TODO",
//         text
//     }
// }