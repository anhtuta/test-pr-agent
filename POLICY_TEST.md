# CoReview policy test

Isolated PR for one policy. Do not merge.

- **Policy id:** `block-env-file`
- **Name:** Block .env file
- **Glob:** `**/.env*`
- **Severity / action:** `error` + `block_merge`
- **Dummy file:** `.env.local`
- **Expected:** Hard block: policy comment, failed check, AI review skipped. GitHub may close this PR.
