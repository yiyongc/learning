import path from "path";
import fs from "fs/promises";

import Link from "next/link";

function HomePage(props) {
  const { products } = props;
  return (
    <ul>
      {products.map((product) => (
        <li key={product.id}>
          <Link href={`/products/${product.id}`}>{product.title}</Link>
        </li>
      ))}
    </ul>
  );
}

export async function getStaticProps() {
  const filePath = path.join(process.cwd(), "data", "dummy-backend.json");
  const jsonData = await fs.readFile(filePath);
  const data = JSON.parse(jsonData);

  if (!data) {
    return {
      redirect: {
        destination: "/no-data", // Redirect a user to another page
      },
    };
  }

  if (data.products.length === 0) {
    return {
      notFound: true, // Return the 404 page
    };
  }

  return {
    props: {
      products: data.products,
    },
    revalidate: 30, // lets server perform ISR only after 30s has passed
  };
}

export default HomePage;
