import { defineConfig } from 'astro/config';
import starlight from '@astrojs/starlight';
import lucode from 'lucode-starlight';

export default defineConfig({
  site: 'https://n7ghtm4r3.github.io',
  base: '/Equinox',
  trailingSlash: 'always',
  integrations: [starlight({
    title: 'Equinox',
    description: 'Framework integrating Spring Boot backends with Compose Multiplatform applications',
    plugins: [lucode({
      navLinks: [{ label: 'Documentation', link: '/' }],
      footerText: '© 2026 [N7ghtm4r3 - Tecknobit](https://github.com/N7ghtm4r3)',
      // Our footer wraps Lucode's footer and loads the document interactions.
      warnOverrides: false
    })],
    sidebar: [
      { label: 'Equinox', link: '/' },
      { label: 'Backend', items: [{ autogenerate: { directory: 'backend', collapsed: true } }] },
      { label: 'Core', items: [{ autogenerate: { directory: 'core', collapsed: true } }] },
      { label: 'Compose', items: [{ autogenerate: { directory: 'compose', collapsed: true } }] },
      { label: 'Navigation', items: [{ autogenerate: { directory: 'navigation', collapsed: true } }] },
      { label: 'Miscellaneous', items: [{ autogenerate: { directory: 'miscellaneous', collapsed: true } }] }
    ],
    social: [
      { icon: 'github', label: 'GitHub', href: 'https://github.com/N7ghtm4r3/Equinox' },
      { icon: 'email', label: 'Email', href: 'mailto:infotecknobitcompany@gmail.com' }
    ],
    customCss: ['./src/styles/equinox.css'],
    components: { Footer: './src/components/Footer.astro' }
  })]
});
