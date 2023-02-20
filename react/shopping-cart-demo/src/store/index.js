import appReducer from './reducers';
import thunk from 'redux-thunk';
import { createStore, applyMiddleware } from 'redux';

const consoleMessages = store => next => action => {

  const result = next(action);
  const { cartItems } = store.getState();

  console.groupCollapsed(`dispatching action: ${action.type}`);
  console.log(`
                Store
    =============================
    Number of cart items: ${cartItems.length}
  `);
  console.groupEnd();

  return result;
}

export default (initialState={}) => {
  return applyMiddleware(thunk, consoleMessages)(createStore)(appReducer, initialState)
}
