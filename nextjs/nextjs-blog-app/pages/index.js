import Head from "next/head";

import FeaturedPosts from "../components/home-page/featured-posts";
import Hero from "../components/home-page/hero";

import { getFeaturedPosts } from "../util/posts-util";

function HomePage(props) {
  return (
    <>
      <Head>
        <title>Yi Yong&apos;s Blog</title>
        <meta
          name="description"
          content="I post about programming and random development stuff."
        />
      </Head>
      <Hero />
      <FeaturedPosts posts={props.posts} />
    </>
  );
}

export async function getStaticProps() {
  const featuredPosts = getFeaturedPosts();
  return {
    props: {
      posts: featuredPosts,
    },
    revalidate: 6000,
  };
}

export default HomePage;
