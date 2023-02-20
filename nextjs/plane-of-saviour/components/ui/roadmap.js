import styles from "./roadmap.module.css";

function RoadMap() {
  return (
    <div className={styles.roadMap}>
      <ul className={styles.listNoStyle}>
        <li className={styles.roadMapItem}>
          <div className={styles.roadMapNo}>
            25%
            <span>Ongoing</span>
          </div>
          <div className={styles.roadMapDesc}>
            Donation event - We understand that scarcity has caused a divide in
            the people with resources and the people without, and hence we plan
            to donate $10,000 each to 5 community-selected charities.
          </div>
        </li>
        <li className={styles.roadMapItem}>
          <div className={styles.roadMapNo}>
            50%<span>Ongoing</span>
          </div>
          <div className={styles.roadMapDesc}>
            Weapons Crate Launch - In order to prepare pilots for their
            expeditions, weaponization of planes will begin. All plane holders
            are entitled to get their hands on a free minted weapon.
          </div>
        </li>
        <li className={styles.roadMapItem}>
          <div className={styles.roadMapNo}>
            75%<span>Ongoing</span>
          </div>
          <div className={styles.roadMapDesc}>
            Flight Plans Begin - Get ready for take off, as game development
            will begin for exploration to scavenge Plutonium or to find the
            sacred artifacts.
          </div>
        </li>
        <li className={styles.roadMapItem}>
          <div className={styles.roadMapNo}>
            85%<span>Ongoing</span>
          </div>
          <div className={styles.roadMapDesc}>
            Pilot Gear - All Pilots will be able to deck themselves out and gear
            up for their expenditions with exclusive merchandise.
          </div>
        </li>
        <li className={styles.roadMapItem}>
          <div className={styles.roadMapNo}>
            100%<span>Ongoing</span>
          </div>
          <div className={styles.roadMapDesc}>
            Resources Eureka - Researchers have managed to incorporate
            $PLUTONIUM to be used as energy and currency on pilot expeditions.
          </div>
        </li>
      </ul>
    </div>
  );
}

export default RoadMap;
