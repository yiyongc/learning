import React from 'react';
import { ContentContainer } from '../App/styled/App';
import { CheckOutTitle, CheckedOutProducts } from './styled/CheckOutPage';

const CheckOutPage = ({cartItems}) => {

  return (
    <ContentContainer>
      <CheckOutTitle>Checking Out Products</CheckOutTitle>
      <CheckedOutProducts cartItems={cartItems} />
    </ContentContainer>
  )
}

export default CheckOutPage;
