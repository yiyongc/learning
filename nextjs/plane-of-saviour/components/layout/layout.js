import MainNavigation from "./main-navigation";
import ScrollToTop from "../ui/scroll-to-top";

import styles from "./layout.module.css";

function Layout(props) {
  return (
    <>
      <MainNavigation />
      <div className={styles.filler}></div>
      <main>{props.children}</main>
      <ScrollToTop />
    </>
  );
}

export default Layout;
