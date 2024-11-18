module.exports = {
  ci: {
    collect: {
      staticDistDir: "./dist",
      startServerCommand: "yarn start",
      url: ["http://localhost:5173"],
      collect: {
        numberOfRuns: 3,
      },
    },
    upload: {
      target: "filesystem",
      outputDir: "./lhci_reports",
      reportFilenamePattern: "%%PATHNAME%%-%%DATETIME%%-report.%%EXTENSION%%",
    },
    assert: {
      preset: "lighthouse:recommended",
    },
  },
};
