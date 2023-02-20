import React, { Component } from 'react';
import { TileContainer, ProductImage, AddProductButton, RemoveProductButton, ProductDetails } from './styled/ProductTile';

class ProductTile extends Component {

  state = {
    isInCart: false
  }

  componentDidMount() {
    const { cartItems,productId } = this.props;
    const isProductInCart = cartItems.filter((item) => item.productId === productId).length;
    if (isProductInCart) {
      this.setState({
        isInCart: true
      })
    }
  }

  _handleAddToCart = () => {
    this.setState({
      isInCart: true
    })
    const { addProductToCart, productName, productPrice, productId } = this.props;
    addProductToCart({
      productId,
      productName,
      productPrice,
      quantity: 1
    });
  }

  _handleRemoveFromCart = () => {
    this.setState({
      isInCart: false
    })
    const { removeProductFromCart, productId } = this.props;
    removeProductFromCart(productId);
  }

  render() {
    const { productName, productPrice, productImg } = this.props;
    return (
      <TileContainer>
        <ProductImage productImg={productImg} productName={productName}/>
        <ProductDetails productName={productName} productPrice={productPrice} />
        {this.state.isInCart ? <RemoveProductButton onClick={this._handleRemoveFromCart} />
                             : <AddProductButton onClick={this._handleAddToCart}/> }
      </TileContainer>
    )
  }
}

export default ProductTile;
