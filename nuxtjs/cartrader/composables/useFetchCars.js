export default async (city, filters) => {

  const { data, error } = await useFetch(`/api/cars/${city}`, {
    query: {
      ...filters,
    },
  });
  if (error.value) {
    throw createError({
      statusCode: 500,
      statusMessage: "Unable to fetch cars",
    });
  }
  return data;
};
