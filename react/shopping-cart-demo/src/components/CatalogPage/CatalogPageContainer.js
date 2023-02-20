import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import CatalogPage from './CatalogPage';

const mapStateToProps = ({ productList }) => ({
  productList
})

const CatalogPageContainer = connect(mapStateToProps)(CatalogPage);

export default withRouter(CatalogPageContainer);
