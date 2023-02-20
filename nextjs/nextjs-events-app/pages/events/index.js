import Head from "next/head";
import EventList from "../../components/events/event-list";
import EventsSearch from "../../components/events/events-search";
import { Fragment } from "react";
import { useRouter } from "next/dist/client/router";
import { getAllEvents } from "../../helpers/api-util";

function EventsPage(props) {
  const router = useRouter();
  function findEventsHandler(year, month) {
    router.push(`/events/${year}/${month}`);
  }

  const { events } = props;

  return (
    <Fragment>
      <Head>
        <title>All Events</title>
        <meta
          name="description"
          content="Find a lot of great events that allow you to evolve.."
        />
      </Head>
      <EventsSearch onSearch={findEventsHandler} />
      <EventList items={events} />
    </Fragment>
  );
}

export async function getStaticProps() {
  const events = await getAllEvents();
  return {
    props: {
      events,
    },
    revalidate: 60,
  };
}

export default EventsPage;
