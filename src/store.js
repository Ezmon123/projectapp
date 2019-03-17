import {createStore, applyMiddleware, compose} from 'redux';
import thunk from 'redux-thunk';
import combinedReducer from "./reducers";

const initialState = {};
const middleware = [thunk];

let store;
if (window.navigator.userAgent.includes("Firefox")) {
    store = createStore(combinedReducer, initialState, compose(applyMiddleware(...middleware),
        window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()));
}else{
    store = createStore(combinedReducer, initialState, compose(applyMiddleware(...middleware)));
}

export default store;