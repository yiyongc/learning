import { useEffect, useState } from "react";
import useSWR from "swr";

const fetcher = (url) => fetch(url).then((res) => res.json());

function LastSalesPage(props) {
  const [salesData, setSalesData] = useState(props.salesData);
  // const [isLoading, setIsLoading] = useState(false);

  // useEffect(() => {
  //     setIsLoading(true);
  //     fetch(process.env.NEXT_PUBLIC_SERVER_API)
  //       .then((response) => {
  //           return response.json();
  //         })
  //         .then((data) => {
  //             const transformedSales = [];
  //             for (const key in data) {
  //                 transformedSales.push({
  //                     id: key,
  //                     username: data[key].username,
  //                     volume: data[key].volume,
  //                   });
  //                 }
  //                 setSalesData(transformedSales);
  //                 setIsLoading(false);
  //               });
  //           }, []);

  // if (isLoading) {
  //   return <p>Loading...</p>;
  // }

  // if (!salesData) {
  //   return <p>No data yet</p>;
  // }

  const { data, error } = useSWR(process.env.NEXT_PUBLIC_SERVER_API, fetcher);

  useEffect(() => {
    if (data) {
      console.log(data);
      const transformedSales = [];
      for (const key in data) {
        transformedSales.push({
          id: key,
          username: data[key].username,
          volume: data[key].volume,
        });
      }
      setSalesData(transformedSales);
    }
  }, [data]);

  if (error) {
    return <p>Failed to load</p>;
  }

  if (!data && !salesData) {
    return <p>Loading...</p>;
  }

  return (
    <ul>
      {salesData.map((sale) => (
        <li key={sale.id}>
          {sale.username} - ${sale.volume}
        </li>
      ))}
    </ul>
  );
}

export async function getStaticProps() {
  const response = await fetch(process.env.NEXT_PUBLIC_SERVER_API);
  const data = await response.json();

  const transformedSales = [];
  for (const key in data) {
    transformedSales.push({
      id: key,
      username: data[key].username,
      volume: data[key].volume,
    });
  }

  return {
    props: {
      salesData: transformedSales,
    },
    // revalidate: 10,
  };
}

export default LastSalesPage;
