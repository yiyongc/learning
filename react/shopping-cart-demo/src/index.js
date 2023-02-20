import React from 'react';
import 'semantic-ui-css/semantic.min.css';
import ReactDOM from 'react-dom';
import registerServiceWorker from './registerServiceWorker';
import storeFactory from './store';
import routes from './routes';
import { Provider } from 'react-redux';

const initialState = {
  cartItems: [],
  productList: [{ productId: 1, productName: 'Banana', productPrice: '1', productImg: 'https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SL1500_.jpg' },
                { productId: 2, productName: 'Watermelon', productPrice: '2', productImg: 'http://www.chatelaine.com/wp-content/uploads/2014/05/Watermelon_HealthNews_may-660x660.jpg' }]
}

const store = storeFactory(initialState);

Window.React = React;
Window.store = store;

ReactDOM.render(
  <Provider store={store}>
    {routes}
  </Provider>, document.getElementById('root'));

registerServiceWorker();
