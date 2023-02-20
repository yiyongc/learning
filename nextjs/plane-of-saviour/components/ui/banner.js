import { Suspense } from "react";
import { Canvas } from "@react-three/fiber";
import { OrbitControls, Stars } from "@react-three/drei";
import { PCFShadowMap } from "three";

import Moon from "../threejs/moon";
import RingPlanet from "../threejs/ring-planet";

import styles from "./banner.module.css";

function Banner() {
  const onCanvasCreated = ({ gl }) => {
    gl.shadowMap.enabled = true;
    gl.shadowMap.type = PCFShadowMap;
    gl.gammaOutput = true;
    gl.toneMappingExposure = 1.0;
    gl.setPixelRatio(window.devicePixelRatio);
  };

  return (
    <div className={styles.container}>
      <Canvas
        onCreated={onCanvasCreated}
        orthographic
        camera={{ zoom: 5, position: [0, 0, 35] }}
        className={styles.canvas}
      >
        <ambientLight />
        <pointLight position={[0, 0, 0]} />
        <directionalLight intensity={4.16} />
        <Stars
          radius={200}
          depth={0}
          count={10000}
          factor={20}
          saturation={5}
          fade={true}
        />

        <Suspense fallback={null}>
          <Moon radius={8} position={[window.innerWidth * 0.06, -20, -20]} />
          <RingPlanet
            radius={11}
            position={[-window.innerWidth * 0.07, 22, 5]}
          />
          {/* <OrbitControls /> */}
        </Suspense>
      </Canvas>
      <span>Embark on your journey for Plutonium!</span>
    </div>
  );
}

export default Banner;
