import React from 'react';
import RaisedButton from 'material-ui/RaisedButton';

const AuthButton = (props) => {
  if (props.authenticated) {
    return (
      <RaisedButton label='Log Out' onTouchTap={props.auth.logout} fullWidth={true} secondary />
    )
  } else {
    return (
      <RaisedButton label='Log In / Sign Up' onTouchTap={props.auth.showLock} fullWidth={true} primary />
    )
  }
}

export default AuthButton;
