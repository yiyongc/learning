function UserIdPage(props) {
  return <h1>{props.id}</h1>;
}

export async function getServerSideProps(context) {
  const { params } = context;
  const userId = params.userId;
  console.log("params", params);

  return {
    props: {
      id: "user-id-" + userId,
    },
  };
}

export default UserIdPage;
