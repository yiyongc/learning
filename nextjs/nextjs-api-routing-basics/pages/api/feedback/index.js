import fs from "fs";
import path from "path";

export function buildFeedbackPath() {
  return path.join(process.cwd(), "data", "feedback.json");
}

export function extractFeedback(filePath) {
  const fileData = fs.readFileSync(filePath);
  return JSON.parse(fileData);
}

function handler(req, res) {
  if (req.method === "POST") {
    const { email, feedback } = req.body;

    console.log("Received POST :" + email + ", " + feedback);

    const newFeedback = {
      id: new Date().toISOString(),
      email,
      text: feedback,
    };

    // Store that in a database or in a file
    const filePath = buildFeedbackPath();
    const data = extractFeedback(filePath);
    data.push(newFeedback);
    fs.writeFileSync(filePath, JSON.stringify(data));

    res.status(201).json({
      message: "Success",
      feedback: newFeedback,
    });
  } else if (req.method === "GET") {
    const filePath = buildFeedbackPath();
    const data = extractFeedback(filePath);
    res.status(200).json({
      feedback: data,
    });
  } else {
    res.status(400).json({
      message: "HTTP Method type not supported",
    });
  }
}

export default handler;
