import Auth0Lock from 'auth0-lock';
import Relay from 'react-relay/classic';
import CreateUser from '../mutations/CreateUser';
import SignInUser from '../mutations/SignInUser';

const authDomain = 'yiyongc.auth0.com';
const clientId = 'C798nuZsMO291PTQNxXqAnA2gUxPYAKQ'

class AuthService {

  constructor() {
    const options = {
      autoclose: true,
      auth: {
        responseType: 'token id_token',
        params: {scope: 'openid email'}
      }
    };

    this.lock = new Auth0Lock(clientId, authDomain, options);

    // this.showLock = this.showLock.bind(this);
    this.lock.on('authenticated', this.authProcess.bind(this));
  }

  authProcess = (authResult) => {
    console.log(authResult);
    const { email, exp } = authResult.idTokenPayload;
    const idToken = authResult.idToken;
    const authFields = {idToken, email, exp};
    console.log('AUTH PROCESS :: ', authFields);

    this.signInUser(authFields).then(
      success => success,
      rejected => {
        this.createUser({idToken, email, exp}).then(
          console.log('Sign in failed initially but user has now been created and logged in')
        )
      }
    )
  }

  showLock = () => {
    this.lock.show();
  }

  setToken = (authFields) => {
    const { idToken, exp } = authFields;
    localStorage.setItem('idToken', idToken);
    localStorage.setItem('exp', exp * 1000);
  }

  isCurrent = () => {
    const expString = localStorage.getItem('exp');
    if (!expString) {
      localStorage.removeItem('idToken');
      return false;
    }
    const now = new Date()
		const exp = new Date(parseInt(expString, 10)) //10 is radix parameter
    const isExpired = exp < now;
    if (isExpired) {
			this.logout()
			return false;
		} else {
			return true;
		}
  }

  getToken = () => {
    const idToken = localStorage.getItem('idToken');
    if (this.isCurrent() && idToken) {
      return idToken;
    } else {
      localStorage.removeItem('idToken');
      localStorage.removeItem('exp');
      return false;
    }
  }

  logout = () => {
    localStorage.removeItem('idToken');
    localStorage.removeItem('exp');
    window.location.reload();
  }

  createUser = (authFields) => {
    return new Promise((resolve, reject) => {
      Relay.Store.commitUpdate(
        new CreateUser({
          email: authFields.email,
          idToken: authFields.idToken
        }), {
          onSuccess: (response) => {
            this.signInUser(authFields);
            resolve(response);
          },
          onFailure: (response) => {
            console.log('CreateUser failed :: ', response);
            reject(response);
          }
        }
      )
    })
  }

  signInUser = (authFields) => {
    return new Promise((resolve, reject) => {
      Relay.Store.commitUpdate(
        new SignInUser({
          idToken: authFields.idToken
        }), {
          onSuccess: (response) => {
            this.setToken(authFields);
            resolve(response);
          },
          onFailure: (response) => {
            console.log("SignIn Failed :: ", response);
            reject(response);
          }
        }
      )
    })
  }
}

const auth = new AuthService();

export default auth;
