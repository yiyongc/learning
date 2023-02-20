import { useThree } from "@react-three/fiber";
import { TextureLoader } from "three";

function SpaceBackground() {
  const { scene } = useThree();
  const spaceTexture = new TextureLoader().load("textures/space2.jpg");
  console.log(spaceTexture);
  scene.background = spaceTexture;
  return null;
}

export default SpaceBackground;
