import { MongoClient } from "mongodb";

async function handler(req, res) {
  if (req.method === "POST") {
    const { email, name, message } = req.body;

    if (
      !email ||
      !email.includes("@") ||
      !name ||
      name.trim() === "" ||
      !message ||
      message.trim() === ""
    ) {
      res.status(422).json({
        message: "Invalid input.",
      });
      return;
    }

    const newMessage = {
      email,
      name,
      message,
    };

    // Store it in a database
    const databaseName = process.env.mongodb_database;
    const mongoConnectionUrl = `mongodb+srv://${process.env.DB_USER}:${process.env.DB_PASS}@cluster0.r8bzz.mongodb.net/${databaseName}?retryWrites=true&w=majority`;
    let client;

    try {
      client = await MongoClient.connect(mongoConnectionUrl);
    } catch (error) {
      res.status(500).json({ message: "Could not connect to database." });
      return;
    }

    const db = client.db();
    try {
      const result = await db.collection("messages").insertOne(newMessage);
      newMessage.id = result.insertedId;
      client.close();
    } catch (error) {
      client.close();
      res.status(500).json({ message: "Storing message failed!" });
      return;
    }

    res.status(201).json({
      status: "Successfully stored message!",
      message: newMessage,
    });
  }
}

export default handler;
