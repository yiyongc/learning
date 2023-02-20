import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import NavMenu from './NavMenu';

const mapStateToProps = ({cartItems}) => ({
  cartItems
})

const NavMenuContainer = connect(mapStateToProps)(NavMenu);

export default withRouter(NavMenuContainer);
