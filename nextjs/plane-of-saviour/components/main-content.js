import Image from "next/image";

import NameTag from "./ui/name-tag";
import styles from "./main-content.module.css";
import RoadMap from "./ui/roadmap";

function MainContent() {
  return (
    <div className={styles.content}>
      <div className={styles.bannerImage}>
        <Image
          src="/images/intro-banner.jpeg"
          alt="Plane of Saviour"
          width={1500}
          height={700}
        />
      </div>
      <section>
        <NameTag>📜 Lore</NameTag>
        <p>
          In Year 5099, the Jovian Planet Zaku was running out of their main
          Plutonium resource, a mineral used to harvest energy to sustain life
          and nature on every planet, and decided to start on their conquest
          against neighbouring planets. This conquest lasted over a hundred
          years and is commonly known as the Plutonium war. Millions have been
          massacred, and some life forms were led to extinction after Zaku had
          conquered them and stolen their Plutonium resources.
        </p>
        <p>
          Behind the Plutonium war, was an even greater scheme. Legends foretold
          that there are sacred artifacts, scattered across the far ends of the
          galaxy, which are capable of granting the wielder omnipotent powers
          should they choose to use it. The power to control the Universe,
          create life, or even control time would mean that Plutonium would be a
          thing of the past.
        </p>
        <p>
          Even with ample resources acquired from the Plutonium war, Zaku
          decided to wage war against the rest of the Cosmos for these
          artifacts, promising eternal resources to those who side with him. And
          war to those who don’t.
        </p>
        <p>
          As the news of this legend has spread far and wide, the different
          factions of pilots across different planets have begun their own
          expedition across the Universe.
        </p>
        <p>
          Plutonium for their planet? Revenge against Zaku? The hunt for the
          Sacred Artifacts? You decide.
        </p>
      </section>

      <section>
        <NameTag>🔧 Prepare for your Journey</NameTag>
        <p>
          In order to have a successful expedition, factions are encouraged to
          prepare their gear adequately.
        </p>
        <p>
          Feel free to browse through the top-tier planes which our Hangar
          provides. They will certainly be useful in your intergalactic
          lightspeed travels.
        </p>
        <Image
          src="/images/sample-nft.png"
          alt="Plane samples"
          width={1400}
          height={700}
        />
      </section>

      <section>
        <NameTag>🚀 Our selection</NameTag>
        <h1>The Manta</h1>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <h1>The Rhino</h1>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <h1>MK III</h1>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <h1>Off-the-catalog Specials</h1>
        <p>
          These are selections which are not up for display but certainly boasts
          great specs for any type of travels requirements. These planes are
          hard to come by and it would a luxury to even get your hands on one of
          them!
        </p>
      </section>

      <section>
        <h2>🏆 Roadmap</h2>
        <p>GET RICH AND RETIRE</p>
        <RoadMap />
      </section>
    </div>
  );
}

export default MainContent;
