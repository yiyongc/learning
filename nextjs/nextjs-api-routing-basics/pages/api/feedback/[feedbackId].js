import { buildFeedbackPath, extractFeedback } from "./index";

function handler(req, res) {
  if (req.method === "GET") {
    const feedbackId = req.query.feedbackId;

    const filePath = buildFeedbackPath();
    const feedbackData = extractFeedback(filePath);

    const selectedFeedback = feedbackData.find(
      (feedback) => feedback.id === feedbackId
    );

    if (!selectedFeedback) {
      res
        .status(404)
        .json({ message: "Failed to find feedback with given id" });
    } else {
      res.status(200).json({ feedback: selectedFeedback });
    }
  } else {
    res.status(400).json({
      message: "HTTP Method type not supported",
    });
  }
}

export default handler;
