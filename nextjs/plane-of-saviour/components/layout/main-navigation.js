import Link from "next/link";
import NameTag from "../ui/name-tag";

import styles from "./main-navigation.module.css";

function MainNavigation() {
  return (
    <header className={styles.navbar}>
      <Link href="/">
        <a>
          <NameTag nav={true}>Plane of Saviour</NameTag>
        </a>
      </Link>
      <nav>
        <ul>
          <li>
            <Link href="/about">About</Link>
          </li>
          <li>
            <a
              target="_blank"
              href="https://twitter.com/planeofsaviour"
              rel="noopener noreferrer"
            >
              Twitter
            </a>
          </li>
          <li>
            <a
              target="_blank"
              href="https://discord.gg/yyngfeMRbt"
              rel="noopener noreferrer"
            >
              Discord
            </a>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default MainNavigation;
