import React from 'react';
import Relay from 'react-relay/classic';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventProvider from 'react-tap-event-plugin';
import NavDrawer from '../components/NavDrawer';
import { Header, Main } from '../styled/Template';

injectTapEventProvider();

class Template extends React.Component {
  render() {
    return (
      <MuiThemeProvider>
        <div>
          <NavDrawer auth={this.props.route.auth} authenticated={this.props.viewer.user}/>
          <Header>
            TicTacTuring
          </Header>
          <Main>
            {this.props.children}
          </Main>
        </div>
      </MuiThemeProvider>
    )
  }
}

export default Relay.createContainer(
  Template, {
    fragments: {
      viewer: () => Relay.QL`
        fragment on Viewer {
          user {
            id
          }
        }
      `
    }
  }
)
