import axios from "axios";
import {GET_ERRORS, GET_PROJECT, DELETE_PROJECT} from "./types";
import {GET_PROJECTS} from "./types";

// noinspection JSAnnotator
export const createProject = (project, history) => async dispatch => {
    try {
        // eslint-disable-next-line
        const res = await axios.post("/api/project", project);
        history.push("/dashboard");
        /**
         * Cleaning errors after succesfull creating of project
         */
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        })
    }
};

export const getProjects = () => async dispatch => {
    const res = await axios.get("/api/project/all");
    dispatch({
        type: GET_PROJECTS,
        payload: res.data
    });
};

export const getProject = (id, history) => async dispatch => {
    try {
        const res = await axios.get(`/api/project/${id}`);
        dispatch({
            type: GET_PROJECT,
            payload: res.data
        });
    } catch (err) {
        history.push("/dashboard");
    }
};

export const deleteProject = (id) => async dispatch => {
    if (window.confirm("Are you sure to delete project?")) {
        const res = await axios.delete(`/api/project/${id}`);
        dispatch({
            type: DELETE_PROJECT,
            payload: id
        })
    }
};