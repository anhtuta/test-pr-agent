# CoReview policy test

Isolated PR for one policy. Do not merge.

- **Policy id:** `flag-terraform-1788843286277`
- **Name:** Flag Terraform changes
- **Glob:** `**/*.{tf,tfvars}`
- **Severity / action:** `warn` + `comment`
- **Dummy file:** `infra/main.tf`
- **Expected:** Warn comment only. Check should succeed. AI review may still run.
