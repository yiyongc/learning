import styled from 'styled-components';
import { NavLink } from 'react-router-dom';
import React from 'react';
import { Icon, Button } from 'semantic-ui-react';

export const NavMenuContainer = styled.div`
  border: 1px solid #e7e7e7;
  background-color: #f3f3f3;
  margin: 0;
  padding: 0;
  overflow: hidden;
`

export const StyledLink = styled(NavLink)`
  float: left;
  line-height: 2.5em;
  width: 80px;
  text-decoration: none;
  color: black;
  &:hover {
    background-color: lightgray;
    color: black;
  }
`

export const NavMenuOption = (props) => (
  <StyledLink {...props} exact activeStyle={{background:'darkgrey'}}/>
)

const CartIconContainer = styled.div`
  float: right;
  line-height: 2.5em;
  width: 50px
  &:hover {
    background: lightgray;
  }
`

const CountCircle = styled.div`
  border: 1px solid black;
  margin-top: -2px;
  background: #ffaa22;
  border-radius: 6px;
  text-shadow: none;
  color: black;
  font-size: 11px !important;
  font-weight: bold !important;
`

export const CartIcon = ({cartItems, proceedToCheckout}) => {
  const cartCount = cartItems.length;
  return (
    <CartIconContainer>
      <Icon.Group size='large'>
        <Icon name='shopping cart' color='black'/>
        {(cartCount) ? <Icon corner><CountCircle>{cartCount}</CountCircle></Icon> : ''}
      </Icon.Group>
      {(cartCount) ? <CartIconDropDown cartItems={cartItems} proceedToCheckout={proceedToCheckout}/> : ''}
    </CartIconContainer>
  )
}

export const CartIconDropDown = ({cartItems, proceedToCheckout}) => (
  <CartSummaryContainer>
    {cartItems.map((item, i) => {
      const { productName, quantity } = item;
      return <CartSummaryItem key={i} productName={productName} quantity={quantity} />
    })}
    <Button inverted color='blue' style={{ margin: '10px 0 10px 0'}} onClick={proceedToCheckout}>Check Out</Button>
  </CartSummaryContainer>
)

const CartSummaryContainer = styled.div`
  position: absolute;
  border: 1px solid #666;
  right: 15px;
  margin-top: 5px;
  background: #fff;
  display: none;
  z-index: 999;
  box-sizing: border-box;
  border-radius: 10px;
  ${CartIconContainer}:hover & {
    display: block
  }

  &::before{
    content: ' ';
    position: absolute;
    right: 10px;
    margin-top: -12px;
    border: 6px solid;
    border-color: transparent #666 #666 transparent;
  }

  &::after {
    content: ' ';
    position: absolute;
    right: 11px;
    top: -9px;
    border: 5px solid;
    border-color: transparent #fff #fff transparent;
  }
`

export const CartSummaryItem = ({ productName, quantity }) => (
  <SummaryItemContainer>
    <SummaryItemName>{productName}</SummaryItemName>
    <SummaryItemQuantity>x {quantity}</SummaryItemQuantity>
    <div style={{clear: 'both'}}/>
  </SummaryItemContainer>
)

const SummaryItemContainer = styled.div`
  padding: 10px;
  border-bottom: 1px solid #cccccc;
  margin-left: 7px;
  margin-right: 7px;
`

const SummaryItemName = styled.p`
  float: left;
  margin: 0;
  padding-right: 180px;
`

const SummaryItemQuantity = styled.p`
  float: right;
  margin: 0;
`
