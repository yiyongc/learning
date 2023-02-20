import { Actions } from './utils/constants';

export const addToCart = (product) =>
  ({
      type: Actions.ADD_TO_CART,
      payload: product
  })

export const removeFromCart = (productId) =>
  ({
    type: Actions.REMOVE_FROM_CART,
    payload: productId
  })
