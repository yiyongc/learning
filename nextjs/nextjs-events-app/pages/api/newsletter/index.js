import { connectDatabase, insertDocument } from "../../../helpers/db-util";

async function handler(req, res) {
  if (req.method === "POST") {
    const { email } = req.body;

    if (!email || !email.includes("@")) {
      res.status(422).json({
        message: "Invalid email address received",
      });
      return;
    }

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

    const newsletterSubscription = {
      email,
    };
    try {
      await insertDocument(client, "newsletter", newsletterSubscription);
      client.close();
    } catch (error) {
      console.error(error);
      res.status(500).json({
        message: "Insertion of newsletter subscription to database failed",
      });
      client.close();
      return;
    }

    console.log("Sign up email received ", email);
    res.status(201).json({
      message: "SUCCESS",
    });
  } else {
    res.status(400).json({
      message: "Unsupported HTTP Method",
    });
  }
}

export default handler;
