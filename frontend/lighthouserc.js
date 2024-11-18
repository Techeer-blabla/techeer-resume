module.exports = {
  ci: {
    collect: {
      staticDistDir: "./dist",
      startServerCommand: "yarn run dev",
      url: ["http://localhost:5173"],
      numberOfRuns: 3,
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
