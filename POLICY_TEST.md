# CoReview policy test

Isolated PR for one policy. Do not merge.

- **Policy id:** `block-application-prod-yml`
- **Name:** Block application-prod.yml
- **Glob:** `**/application-prod.yml`
- **Severity / action:** `error` + `block_merge`
- **Dummy file:** `src/main/resources/application-prod.yml`
- **Expected:** Hard block: policy comment, failed check, AI review skipped. GitHub may close this PR.
