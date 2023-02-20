import React from 'react';
import { Switch, Route } from 'react-router-dom';
import HomePage from '../HomePage/HomePage';
import CatalogPage from '../CatalogPage/CatalogPageContainer';
import CheckOutPage from '../CheckOutPage/CheckOutPageContainer';
import { AppBodyContainer } from './styled/App';

const AppBody = () => {
  return (
    <AppBodyContainer>
      <Switch>
        <Route exact path='/' component={HomePage} />
        <Route exact path='/catalog' component={CatalogPage} />
        <Route exact path='/checkout' component={CheckOutPage} />
      </Switch>
    </AppBodyContainer>
  )
}

export default AppBody;
