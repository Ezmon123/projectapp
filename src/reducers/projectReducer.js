import {DELETE_PROJECT, GET_PROJECT, GET_PROJECTS} from "../actions/types";

const initialState = {
    projects: [],
    project: {}
};

//dlaczego wzracamy ...state?
export default function (state = initialState, action) {
    switch (action.type) {
        case GET_PROJECTS:
            /**
             * ...state means return copy of old state
             */
            return {
                ...state,
                projects: action.payload
            };
        case GET_PROJECT:
            return {
                ...state,
                project: action.payload
            };
        case DELETE_PROJECT:
            /**
             * filter method create new array with elements that satisfy
             * the condition
             */
            return{
                ...state,
                projects: state.projects.filter(
                    project => project.projectIdentifier !== action.payload)
            };
        default:
            return state;
    }
}