import { combineReducers } from 'redux';
import { Actions } from '../utils/constants';

export const cartItems = (state=[], action) => {
  switch (action.type) {
    case Actions.ADD_TO_CART:
      return [
        ...state,
        action.payload
      ]
    case Actions.REMOVE_FROM_CART:
      return state.filter((item) => item.productId !== action.payload);
    default:
      return state;
  }
}

export const productList = (state=[], action) => {
  switch (action.type) {
    case Actions.FETCH_PRODUCT_LIST:
      return action.payload;
    default:
      return state;
  }
}

export default combineReducers({
  cartItems,
  productList
})
