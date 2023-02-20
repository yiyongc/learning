import Head from "next/head";

import dynamic from "next/dynamic";
import MainContent from "../components/main-content";

function HomePage() {
  // Dynamic instantiation is required because of threejs
  const Banner = dynamic(() => import("../components/ui/banner"), {
    ssr: false,
  });

  return (
    <>
      <Head>
        <title>Plane of Saviour</title>
        <meta
          name="description"
          content="A NFT collection of planes required to save the planet and acquire Plutonium!"
        />
      </Head>
      <Banner />
      <MainContent />
    </>
  );
}

export default HomePage;
