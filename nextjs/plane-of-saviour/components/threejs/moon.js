import { Sphere, useTexture } from "@react-three/drei";
import { useFrame } from "@react-three/fiber";
import { useRef } from "react";

function Moon(props) {
  const moonTexture = useTexture({
    map: "textures/moon.jpeg",
    normalMap: "textures/normal.jpeg",
  });

  const radius = props.radius || 3;

  const mesh = useRef();
  useFrame(() => {
    mesh.current.rotation.x += 0.001;
    mesh.current.rotation.y += 0.0015;
  });

  return (
    <Sphere ref={mesh} position={props.position} args={[radius, 32, 32]}>
      <meshStandardMaterial {...moonTexture} />
    </Sphere>
  );
}

export default Moon;
