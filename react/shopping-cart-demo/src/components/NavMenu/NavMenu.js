import React, { Component } from 'react';
import { NavMenuContainer, NavMenuOption, CartIcon } from './styled/NavMenu';
import PropTypes from 'prop-types';

class NavMenu extends Component {

  static contextTypes = {
    router: PropTypes.object.isRequired
  }

  proceedToCheckout = () => {
    this.context.router.history.push("/checkout");
  }

  render() {
    const { cartItems } = this.props;
    return (
      <NavMenuContainer>
        <NavMenuOption to='/'>Home</NavMenuOption>
        <NavMenuOption to="/catalog">Catalog</NavMenuOption>
        <CartIcon cartItems={cartItems} proceedToCheckout={this.proceedToCheckout} />
      </NavMenuContainer>
    )
  }

}

export default NavMenu;
