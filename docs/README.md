# Equinox documentation

This folder contains the entire Astro and Starlight documentation project with the Lucode theme, independently of the Java/Kotlin modules.
Node.js 22.12 or newer is required.

From the repository root:

```shell
cd docs
npm ci
npm run dev
```

Open `http://localhost:4321/Equinox/`.

- Edit pages in `src/content/docs/`. Frontmatter `title` controls the page title; `slug` preserves its public URL.
- Store images and videos in `public/`, preserving their paths relative to the original documentation site.
- Module navigation is generated automatically from the content directories, including nested API pages on mobile.
- Styling and document interactions live in `src/styles/`, `src/components/` and `src/scripts/`.

Run the following commands from this folder:

```shell
npm run build
npm run docs:verify
npm run preview
```

The verifier checks generated pages, asset integrity, local links, anchors, code examples, tabs, callouts and the search index.
The static output is `dist/`, served under `/Equinox/`. The documentation keeps the existing content, code examples,
media, module structure, purple accent and light/dark themes, with Lucode navigation and Pagefind search.
The light theme keeps Equinox's original `#7e56c2`; the dark theme uses a lighter purple for readable links and controls.
Lucode color tokens are customized in `src/styles/equinox.css`.

To serve the production site in Docker, from this folder run:

```shell
docker build -t equinox-docs .
docker run --rm -p 8080:80 equinox-docs
```

Open `http://localhost:8080/Equinox/`.

GitHub requires workflow files under `.github/workflows/`, so that is the only documentation tooling kept outside this
folder. It validates documentation changes on branches and pull requests, then publishes the verified build to the
existing `gh-pages` branch only on pushes to `main`.
