# CoReview policy test

Isolated PR for one policy. Do not merge.

- **Policy id:** `flag-ci-config-1788843290354`
- **Name:** Flag CI/CD pipeline changes
- **Glob:** `**/{.github/**,.gitlab-ci.yml,azure-pipelines*.yml,Jenkinsfile}`
- **Severity / action:** `warn` + `comment`
- **Dummy file:** `.github/workflows/ci.yml`
- **Expected:** Warn comment only. Check should succeed. AI review may still run.
