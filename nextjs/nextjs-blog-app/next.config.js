const { PHASE_DEVELOPMENT_SERVER } = require("next/constants");

module.exports = (phase) => {
  if (phase === PHASE_DEVELOPMENT_SERVER) {
    return {
      reactStrictMode: true,
      env: {
        mongodb_database: "my-site-dev",
      },
    };
  }
  return {
    reactStrictMode: true,
    env: {
      mongodb_database: "my-site",
    },
  };
};
