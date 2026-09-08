# CoReview policy test

Isolated PR for one policy. Do not merge.

- **Policy id:** `block-secret-and-credential-files`
- **Name:** Block secret and credential files
- **Glob:** `**/*.{pem,key,crt}`
- **Severity / action:** `error` + `block_merge`
- **Dummy file:** `certs/dummy.pem`
- **Expected:** Hard block: policy comment, failed check, AI review skipped. GitHub may close this PR.
