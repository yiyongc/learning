import React from 'react';
import styled from 'styled-components';
import { Button, Icon } from 'semantic-ui-react';

export const TileContainer = styled.div`
  display: inline-block;
  height: 280px;
  width: 200px;
  margin: 0 15px 10px 15px;
  vertical-align: top;
`
const ImageContainer = styled.div`
  height: 200px;
  width: 200px;
  background: grey;
  border: 1px solid black;
  box-sizing: border-box;
`
export const ProductImage = ({productImg, productName}) => {
  return (
    <ImageContainer>
      <img style={{ maxWidth: '100%', maxHeight: '100%' }} src={productImg} alt={productName}/>
    </ImageContainer>
  )
}

export const AddProductButton = ({onClick}) => (
  <Button onClick={onClick} style={{ width: '85px' }} animated='vertical'>
      <Button.Content hidden>Add To Cart</Button.Content>
      <Button.Content visible>
        <Icon name='shop' />
      </Button.Content>
    </Button>
)

export const RemoveProductButton = ({onClick}) => (
   <Button positive onClick={onClick} style={{ width: '85px' }}>Added</Button>
)

const ProductDetailsContainer = styled.div`
  width: 100%;
  line-height: 2.2em;
  height: 2.2em;
  margin-bottom: 10px;
`
const ProductNameStyled = styled.div`
  float: left;
  font-weight: bold;
`

const ProductPriceStyled = styled.div`
  float: right;
  font-style: italic;
`

export const ProductDetails = ({productName, productPrice}) => (
  <ProductDetailsContainer>
    <ProductNameStyled>{productName}</ProductNameStyled>
    <ProductPriceStyled>$ {productPrice}</ProductPriceStyled>
  </ProductDetailsContainer>
)
