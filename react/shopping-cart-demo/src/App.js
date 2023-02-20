import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import { AppContainer } from './components/App/styled/App';
import AppBody from './components/App/AppBody';
import NavMenu from './components/NavMenu/NavMenuContainer';

class App extends Component {
  render() {
    return (
      <AppContainer>
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <NavMenu/>
        <AppBody/>
      </AppContainer>
    );
  }
}

export default App;
