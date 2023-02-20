import Image from "next/image";
import styles from "./hero.module.css";

function Hero() {
  return (
    <section className={styles.hero}>
      <div className={styles.image}>
        <Image
          src="/images/site/yiyong.png"
          alt="An image showing Yi Yong"
          width={300}
          height={300}
        />
      </div>
      <h1>Hi, I&apos;m Yi Yong</h1>
      <p>
        I blog about development stuff and life. Mainly what I&apos;m learning
        or working on personally.
      </p>
    </section>
  );
}

export default Hero;
