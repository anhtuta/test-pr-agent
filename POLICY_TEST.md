# CoReview policy test

Isolated PR for one policy. Do not merge.

- **Policy id:** `flag-migrations-1787752724480`
- **Name:** Flag database migrations
- **Glob:** `db/migrations/**`
- **Severity / action:** `warn` + `comment`
- **Dummy file:** `db/migrations/0001_init.sql`
- **Expected:** Warn comment only. Check should succeed. AI review may still run.
