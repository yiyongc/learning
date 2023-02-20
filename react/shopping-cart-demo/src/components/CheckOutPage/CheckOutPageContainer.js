import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import CheckOutPage from './CheckOutPage';

const mapStateToProps = ({cartItems}) => ({
  cartItems
});

const CheckOutPageContainer = connect(mapStateToProps)(CheckOutPage);

export default withRouter(CheckOutPageContainer);
