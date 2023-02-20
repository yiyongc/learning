import { Ring, Sphere, Torus, useTexture } from "@react-three/drei";
import { useFrame } from "@react-three/fiber";
import { useRef } from "react";

function RingPlanet(props) {
  const moonTexture = useTexture({
    map: "textures/planet-2.jpeg",
    normalMap: "textures/normal.jpeg",
  });

  const ringTexture = useTexture({
    map: "textures/ring.png",
  });

  const radius = props.radius || 3;

  // Handle Planet Rotation
  const planetRef = useRef();
  const outerRingRef = useRef();
  const innerRingRef = useRef();
  useFrame(() => {
    planetRef.current.rotation.x += 0.002;
    planetRef.current.rotation.y += 0.001;
    outerRingRef.current.rotation.x -= 0.003;
    outerRingRef.current.rotation.y += 0.002;
    innerRingRef.current.rotation.x += 0.005;
    innerRingRef.current.rotation.y += 0.006;
  });

  return (
    <>
      <Ring
        ref={outerRingRef}
        position={props.position}
        args={[radius * 1.35, radius * 1.45, 100]}
      >
        <meshBasicMaterial attach="material" color="purple" />
      </Ring>
      <Ring
        ref={innerRingRef}
        position={props.position}
        args={[radius * 1.2, radius * 1.3, 100]}
      >
        <meshBasicMaterial attach="material" color="orange" />
      </Ring>
      <Sphere ref={planetRef} position={props.position} args={[radius, 32, 32]}>
        <meshStandardMaterial {...moonTexture} />
      </Sphere>
    </>
  );
}

export default RingPlanet;
