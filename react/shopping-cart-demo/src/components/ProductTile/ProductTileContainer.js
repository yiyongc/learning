import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import { addToCart, removeFromCart } from '../../actions';
import ProductTile from './ProductTile';

const mapStateToProps = ({cartItems}) => ({
  cartItems
})

const mapDispatchToProps = (dispatch) => ({
  addProductToCart(product) {
    dispatch(addToCart(product));
  },
  removeProductFromCart(productId) {
    dispatch(removeFromCart(productId));
  }
})

const ProductTileContainer = connect(mapStateToProps, mapDispatchToProps)(ProductTile);

export default withRouter(ProductTileContainer);
