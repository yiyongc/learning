import React from 'react';
import styled from 'styled-components';

export const CheckOutTitle = styled.div`
  text-decoration: underline;
  font-size: 2em;
  margin: 25px 0 50px 0;
`
const TableStyled = styled.table`
  width: 80%
  margin: 0 auto;
  font-size: 15px;
`

const ProductNameCell = styled.td`
  width: 60%
  text-align: left;
`

const TableCell = styled.td`
  width: 20%
`

const ItemRow = ({productName, quantity, productPrice}) => {
  return (
    <tr>
      <ProductNameCell>{productName}</ProductNameCell>
      <TableCell>x {quantity}</TableCell>
      <TableCell>$ {quantity*productPrice}</TableCell>
    </tr>
  )
}

const ProductHeaderCell = styled.th`
  width: 60%
  text-align: left;
`

const HeaderCell = styled.th`
  width: 20%
`

const TableHeader = () => (
  <thead >
    <tr style={{borderBottom: '1px solid gray'}}>
      <ProductHeaderCell>Product</ProductHeaderCell>
      <HeaderCell>Quantity</HeaderCell>
      <HeaderCell>Total Product Cost</HeaderCell>
    </tr>
    <tr><td colSpan="3"> <hr /> </td></tr>
  </thead>
)

const costCalculator = (accumulatedCost, currentProduct) => {
  const { productPrice, quantity } = currentProduct;
  const totalCostOfProduct = productPrice * quantity;
  return accumulatedCost + totalCostOfProduct;
}

const TotalCostRow = ({cartItems}) => {
  let totalCost = 0;
  if (cartItems.length) {
    totalCost = cartItems.reduce(costCalculator, 0);
  }
  return (
    <tr>
      <ProductHeaderCell colSpan='2'>Total Cart Cost:</ProductHeaderCell>
      <HeaderCell>$ {totalCost}</HeaderCell>
    </tr>
  );
}

export const CheckedOutProducts = ({cartItems}) => {
  return (
    <TableStyled>
      <TableHeader />
      <tbody>
        {cartItems.map((item, i) => (
          <ItemRow key={i} {...item} />
        ))}
        <tr><td colSpan="3"> <hr /> </td></tr>
        <TotalCostRow cartItems={cartItems} />
      </tbody>
    </TableStyled>
  )
}
