import Document, { Html, Head, Main, NextScript } from "next/document";

// This class is used to override the default HTML Document rendered by Next. It allows us to re-arrange / add properties e.g. language / add additional divs if required
class MyDocument extends Document {
  render() {
    return (
      <Html lang="en">
        <Head />
        <body>
          <div id="overlays" />
          <Main />
          <NextScript />
        </body>
      </Html>
    );
  }
}

export default MyDocument;
