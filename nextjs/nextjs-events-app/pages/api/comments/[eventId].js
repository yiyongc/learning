import {
  connectDatabase,
  insertDocument,
  getAllDocuments,
} from "../../../helpers/db-util";

async function handler(req, res) {
  const eventId = req.query.eventId;

  let client;
  try {
    client = await connectDatabase("events");
    console.log("Successfully connected to mongodb!");
  } catch (error) {
    console.error(error);
    res.status(500).json({
      message: "Connection to database failed",
    });
    return;
  }

  if (req.method === "POST") {
    const { name, email, comment } = req.body;
    console.log(name, email, comment);

    // Add server side validation
    if (
      !email ||
      email.trim() === "" ||
      !email.includes("@") ||
      !name ||
      name.trim() === "" ||
      !comment ||
      comment.trim() === ""
    ) {
      res.status(422).json({
        message: "Error in request body",
      });
      client.close();
      return;
    }

    const newComment = {
      email,
      name,
      comment,
      eventId,
    };

    try {
      const response = await insertDocument(client, "comments", newComment);
      newComment._id = response.insertedId;
      res.status(201).json({
        message: "SUCCESS",
        comment: newComment,
      });
    } catch (error) {
      console.error(error);
      res.status(500).json({
        message: "Insertion of comment to database failed",
      });
    }
  } else if (req.method === "GET") {
    console.log("Successfully acquired connection for GET comments");
    const sort = { _id: -1 }; // Sort in descending order
    const filter = { eventId }; // Filter comments by eventId
    try {
      const documents = await getAllDocuments(client, "comments", sort, filter);
      res.status(200).json({
        comments: documents,
      });
    } catch (error) {
      console.error(error);
      res
        .status(500)
        .json({ message: "Failed to fetch all comments from database" });
    }
  } else {
    res.status(400).json({
      message: "Unsupported HTTP Method",
    });
  }

  // Close connection once all operations are done, we can remove this if using connection pooling
  client.close();
}

export default handler;
