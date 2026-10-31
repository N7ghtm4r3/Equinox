# Equinox releases

`release.yml` runs on pushes to `develop` and `main`. All orchestration lives in
workflow YAML: existing Gradle tasks, Vanniktech, GitHub Actions, `gh`, `jq` and
small inline Bash steps. Python is used only to install/run MkDocs, not for release logic.

## Versions and publication

Each module owns its project version and Maven coordinates in its
`build.gradle.kts`. Keep both values equal, using stable `X.Y.Z` base versions.

- `develop` derives `X.Y.Z-rc.<GITHUB_RUN_NUMBER>` in the CI checkout only.
- `main` publishes the declared `X.Y.Z` version unchanged.
- Retries of the same run keep the same RC version; a new run gets a new RC.
- All module versions are prepared in every job so project dependencies use the
  same RC coordinates as their publications. No version changes are committed.

Adding a module requires updating both the validation and publication matrices.

Before any Maven upload, the `validate` matrix builds and tests all seven modules
through `publish-module.yml` with `validate-only: true`. It receives no publishing
secrets and uses `fail-fast: true`: a failed case cancels the other validation
cases. Documentation builds in parallel on main. Core publication starts only
when every validation case succeeds and the applicable documentation build passes.
On develop, skipped documentation does not block publication.

Publication then follows the existing order: core first, then compose/backend and
the two core-dependent miscellaneous modules, then navigation and
backend-membership. Independent publications within a matrix run concurrently,
with `fail-fast: false` so started publications can finish their recovery checks.
Validation and publication use the same setup and derived release versions.
Each publication job rebuilds from the same commit; build artifacts are not
transferred from validation jobs.
Each job uses macOS for Apple targets and installs JDKs 18/21 and the Android
platform selected by the version catalog. Gradle's setup action validates the
wrapper and has caching disabled.

The standard Gradle build/check gate is kept separate so an existing Maven
version cannot hide a compilation or test failure through publication recovery.
Gradle can reuse its local task outputs when Vanniktech prepares the publication.
Build/check failures stop the job. Only Vanniktech publication uses
`continue-on-error`. Vanniktech handles signing, uploading, releasing and its
normal publication status checks.

If publication fails, an inline recovery step checks Maven Central for the exact
requested coordinates: the root module metadata, all publications referenced by
its `available-at` variants, their POMs/POM signatures and metadata-referenced
files. Missing metadata/files or HTTP errors fail the job after bounded retries.
A root POM alone does not count as a complete publication.

Existing immutable Maven versions are authoritative. Recovery does not compare
published bytes against the current source tree or verify signatures locally.
Changes to a published package require a new version; a workflow retry cannot
replace it. Legacy publications without Gradle module metadata fail recovery
rather than being silently accepted.

## GitHub releases

A GitHub release is created only after every module job succeeds. On `main`,
documentation must also build successfully. On `develop`, documentation jobs are
skipped without blocking the prerelease.

Release tags use the following rules:

- First managed release on a channel, or changed core version: `v<core-version>`.
  On `develop`, the derived core version includes `-rc.<GITHUB_RUN_NUMBER>`.
- Unchanged core: `vYYYY.MM.DD-<12-character-commit-SHA>` on `main`, or
  `vYYYY.MM.DD-rc.<12-character-commit-SHA>` on `develop`.
- The UTC date comes from the workflow run's creation time, so rerunning the same
  run across midnight preserves its identity.

The baseline is selected by native `git describe` among ancestor releases on the
same channel. Only tags
starting with `v` are considered managed releases; the existing unprefixed legacy
tags do not establish a baseline. The previous base core version is read from the
source at that tag, including for date-based releases. On `develop`, the derived
RC differs from that base, so each new run uses `v<core-base>-rc.<run-number>`.

A published release for the same commit/channel is reused. A tag pointing at
another commit fails the job and is never moved. RC releases are marked
prerelease and never latest; only a stable release for the current head of main
is explicitly marked latest.

The body of merged PRs associated with the pushed head commit is prepended to
GitHub-generated notes, followed by the module version table. A direct push
without an associated merged PR uses GitHub-generated notes with an explanatory
line. PR bodies are processed as data through `jq` and `--notes-file`.

## Documentation

The original `mkp.yml` is retained as a reusable workflow with separate `build`
and `deploy` jobs. `release.yml` calls its build phase in parallel with module
validation on main, then calls its deploy phase only after the GitHub release succeeds.
It has no independent push trigger, so the documentation is never deployed ahead
of the release.

The build retains MkDocs, Material and glightbox, with pinned dependencies and
wheel hashes. `mkdocs build` compiles the site and `actions/upload-artifact`
saves it under a run/attempt-specific name for 30 days. The deploy phase downloads
that artifact, configures the original GitHub Actions bot identity and uses
`ghp-import --nojekyll --push --force --branch gh-pages` to publish it.

MkDocs 1.6.1 always builds when `mkdocs gh-deploy` is called; there is no no-build
flag. `ghp-import` is the deployment component called by that command, used here
directly so the already validated site is published without rebuilding. No
custom Python or manual Git init/commit/push implementation is introduced.

Keep **Settings → Pages → Source → Deploy from a branch → gh-pages → /(root)**,
as with the original workflow. The Pages deployment API, OIDC permissions and
GitHub Actions publishing-source migration are not used. Remote settings have
not been changed. Avoid redeploying an old run over a newer release.

## Credentials and repository setup

Configure these repository secrets without placing their values in files:

| Secret | Purpose |
| --- | --- |
| `MAVEN_CENTRAL_USERNAME` | Central Portal user-token username |
| `MAVEN_CENTRAL_PASSWORD` | Central Portal user-token password |
| `SIGNING_KEY` | Signing key supported by Vanniktech in-memory signing |
| `SIGNING_PASSWORD` | Signing-key password |
| `SIGNING_KEY_ID` | Optional signing-key ID |

These secrets are mapped explicitly to the reusable workflow and exposed only
to the credential-presence check and publication step. In validation-only mode,
credential checks, publication and Maven recovery are skipped. Publishing callers
must provide the credentials; empty required values fail before upload.
Default repository permissions are read-only. Only
GitHub release creation receives `contents: write` and `pull-requests: read`.
Documentation build has read-only permissions; its separate deployment job has
`contents: write` for the gh-pages push. The token is exposed through `GH_TOKEN`
only to the ghp-import command, and Git uses the gh credential helper.

Protect `main`, `develop` and workflow changes against unauthorized modification.
The publication step executes repository Gradle code with signing/publishing
credentials, so branch review/access controls are part of the trust boundary.
Confirm that repository policies permit the intended release and Pages
deployments. No secrets, branch protections or hosting settings are configured by
these files.

## Retry and recovery

The entire release flow is serialized across both branches without canceling an
active publication. `queue: max` retains up to 100 pending runs; GitHub cancels
additional runs when that queue is full. Queue order reflects when runs start
waiting and is not a guarantee of commit order.

- Retry the failed jobs after correcting a transient failure. Successful Maven
  publications remain immutable and are accepted by the recovery check.
- If Central has a failed/incomplete/pending deployment, inspect it in Central
  Portal before retrying. The workflow does not delete or reset deployments.
- A module-validation or documentation-build failure prevents all Maven uploads
  and GitHub release creation. Fix the failure before retrying validation.
- A later publication failure can leave a partial Maven release. Completed
  publications are not rolled back; existing Maven recovery checks remain active.
- A documentation-deploy failure leaves Maven and the GitHub release intact.
  Retry the failed deploy within artifact retention. After expiration, rerun the
  entire workflow to rebuild the site; the existing GitHub release is reused.
- Prefer the current main run when retrying deployment; avoid redeploying an old
  run over a newer successful release. Artifact names include the build attempt,
  so retrying a failed deploy uses the original successful build artifact.

Local module equivalents are `./gradlew :<module>:properties -q`,
`./gradlew :<module>:build` and
`./gradlew :<module>:publishAndReleaseToMavenCentral --no-configuration-cache`.
The last command publishes externally and must only be run intentionally with
appropriate credentials. The local documentation equivalent is `mkdocs build`
in an environment containing the dependencies listed in the workflow.

## Validation boundaries

Static validation covers YAML, Bash syntax and GitHub Actions expressions.
Actionlint 1.7.12 does not yet recognize the documented `concurrency.queue` key;
its exact unknown-key diagnostic is excluded while other diagnostics remain
active. No remote release, credential operation, Gradle build or documentation
build is implied by those static checks.
