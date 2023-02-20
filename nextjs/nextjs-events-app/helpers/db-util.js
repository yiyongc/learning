import { MongoClient } from "mongodb";

export async function connectDatabase(db) {
  const client = await MongoClient.connect(
    `mongodb://myadmin1:myadmin@localhost:27017/${db}?authSource=admin`
  );
  return client;
}

export async function getAllDocuments(client, collection, sort, filter = {}) {
  const db = client.db();
  const documents = await db
    .collection(collection)
    .find(filter)
    .sort(sort)
    .toArray();
  return documents;
}

export async function insertDocument(client, collection, document) {
  const db = client.db();
  const response = await db.collection(collection).insertOne(document);
  console.log("Inserted: ", response);
  return response;
}
