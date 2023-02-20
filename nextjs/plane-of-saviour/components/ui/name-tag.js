import styles from "./name-tag.module.css";

function NameTag(props) {
  const styleName = props.nav ? styles.nametagnav : styles.nametag;

  return <div className={styleName}>{props.children}</div>;
}

export default NameTag;
