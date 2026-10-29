import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { createHash } from 'node:crypto';
import { parse } from 'yaml';
import { load } from 'cheerio';

const project = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..');
const dist = path.join(project, 'dist');
const hash = file => createHash('sha256').update(fs.readFileSync(file)).digest('hex');
const failures = [];
const fragments = [];
const documents = new Map();
const cleanCode = text => text.split('\n').map(line => line.trim()).join('\n').trim();
const summary = { pages: 0, assets: 0, linksChecked: 0, fragmentsChecked: 0, codeBlocks: 0, tabs: 0, callouts: 0 };
const artifact = url => {
  let local = decodeURIComponent(new URL(url, 'https://local.invalid').pathname).replace(/^\/Equinox\/?/, '');
  if (!local || local.endsWith('/')) local += 'index.html';
  return path.join(dist, local);
};
function walk(dir) {
  return fs.readdirSync(dir, { withFileTypes: true }).flatMap(entry => {
    const file = path.join(dir, entry.name);
    return entry.isDirectory() ? walk(file) : [file];
  });
}
const report = { pages: [], counts: { tabs: 0, callouts: 0 } };
for (const file of walk(path.join(project, 'src/content/docs')).filter(file => file.endsWith('.md'))) {
  const source = path.relative(path.join(project, 'src/content/docs'), file).split(path.sep).join('/');
  const content = fs.readFileSync(file, 'utf8').replace(/\r\n/g, '\n');
  const frontmatter = content.match(/^---\n([\s\S]*?)\n---\n/);
  if (!frontmatter) { failures.push(`Missing frontmatter: ${source}`); continue; }
  const metadata = parse(frontmatter[1]);
  const slug = metadata.slug ?? source.replace(/(^|\/)index\.md$/, '$1').replace(/\.md$/, '').replace(/\/$/, '');
  report.pages.push({ source, route: source === '404.md' ? '/Equinox/404.html' : '/Equinox/' + (slug ? slug + '/' : ''), title: metadata.title });
  report.counts.tabs += [...content.matchAll(/role="tabpanel"/g)].length;
  report.counts.callouts += [...content.matchAll(/class="equinox-callout /g)].length;
}
for (const file of walk(path.join(project, 'public'))) {
  const source = path.relative(path.join(project, 'public'), file);
  const output = path.join(dist, source);
  if (!fs.existsSync(output) || hash(output) !== hash(file)) failures.push(`Asset mismatch: ${source}`);
  else summary.assets++;
}
for (const page of report.pages) {
  const output = artifact(page.route);
  if (!fs.existsSync(output)) { failures.push(`Missing page: ${page.route}`); continue; }
  const $ = load(fs.readFileSync(output, 'utf8'));
  documents.set(output, $);
  summary.pages++;
  summary.tabs += $('[role="tabpanel"]').length;
  summary.callouts += $('.equinox-callout').length;
  if ($('h1').first().text().trim() !== page.title.replace(/`/g, '')) failures.push(`Title mismatch: ${page.source}`);
  const content = $('.sl-markdown-content');
  if (/(^|\n)\s*(===\s+"|!!!\s|\?\?\?\s)/.test(content.text())) failures.push(`Unconverted syntax: ${page.source}`);
  const original = fs.readFileSync(path.join(project, 'src/content/docs', page.source), 'utf8').replace(/\r\n/g, '\n');
  const examples = [...original.matchAll(/^\s*```[^\n]*\n([\s\S]*?)^\s*```\s*$/gm)];
  const rendered = $('pre code').map((_, code) => {
    const lines = $(code).find('.ec-line .code');
    return cleanCode(lines.length ? lines.map((_, line) => $(line).text().replace(/\n/g, '')).get().join('\n') : $(code).text());
  }).get();
  for (const [, code] of examples) {
    if (!rendered.includes(cleanCode(code))) failures.push(`Code block mismatch: ${page.source}: ${code.trim().slice(0, 55)}`);
    else summary.codeBlocks++;
  }
  $('[href], [src]').each((_, element) => {
    const href = $(element).attr('href') || $(element).attr('src');
    if (!href || /^(?:[a-z][\w+.-]*:|\/\/)/i.test(href)) return;
    const url = new URL(href, 'https://local.invalid' + page.route);
    const destination = artifact(url.href);
    summary.linksChecked++;
    if (!fs.existsSync(destination)) failures.push(`Missing target from ${page.source}: ${href}`);
    else if (url.hash && $(element).is('a') && url.pathname.startsWith('/Equinox/')) fragments.push({ from: page.source, destination, hash: decodeURIComponent(url.hash.slice(1)), href });
  });
}
for (const fragment of fragments) {
  let $ = documents.get(fragment.destination);
  if (!$ && fragment.destination.endsWith('.html')) {
    $ = load(fs.readFileSync(fragment.destination, 'utf8'));
    documents.set(fragment.destination, $);
  }
  if ($ && !$('[id]').toArray().some(element => $(element).attr('id') === fragment.hash)) failures.push(`Missing anchor from ${fragment.from}: ${fragment.href}`);
  else summary.fragmentsChecked++;
}
if (summary.tabs !== report.counts.tabs) failures.push('Tab count differs');
if (summary.callouts !== report.counts.callouts) failures.push('Callout count differs');
if (!fs.existsSync(path.join(dist, 'pagefind/pagefind.js'))) failures.push('Search index missing');
if (!fs.readFileSync(path.join(dist, '404.html'), 'utf8').includes('Katerina Limpitsouni')) failures.push('Custom 404 illustration missing');

const result = { ...summary, failures: [...new Set(failures)] };
fs.writeFileSync(path.join(project, 'docs-verification.json'), JSON.stringify(result, null, 2));
console.log(JSON.stringify({ ...result, failures: result.failures.slice(0, 20), totalFailures: result.failures.length }, null, 2));
process.exitCode = failures.length ? 1 : 0;
