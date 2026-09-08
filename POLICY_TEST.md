# CoReview policy test

Isolated PR for one policy. Do not merge.

- **Policy id:** `flag-lockfiles-1788843204146`
- **Name:** Flag dependency lockfile changes
- **Glob:** `**/{package-lock.json,yarn.lock,pnpm-lock.yaml,poetry.lock,Cargo.lock}`
- **Severity / action:** `warn` + `comment`
- **Dummy file:** `package-lock.json`
- **Expected:** Warn comment only. Check should succeed. AI review may still run.
