import React from 'react';
import { ContentContainer } from '../App/styled/App';
import { CatalogPageTitle, ProductsContainer } from './styled/CatalogPage';
import ProductTile from '../ProductTile/ProductTileContainer';

const CatalogPage = ({productList}) => {
  return (
    <ContentContainer>
      <CatalogPageTitle/>
      <ProductsContainer>
        {productList.map((product, i) => {
          const { productName, productImg, productPrice, productId } = product;
          return <ProductTile key={productId}
                              productId={productId}
                              productName={productName}
                              productPrice={productPrice}
                              productImg={productImg} />
        })}
      </ProductsContainer>
    </ContentContainer>
  )
}

export default CatalogPage;
