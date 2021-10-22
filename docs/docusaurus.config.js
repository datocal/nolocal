// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: 'NoLocal',
  tagline: 'Create your service easily',
  url: 'https://datocal.github.io',
  baseUrl: '/nolocal/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',
  favicon: 'img/favicon.ico',
  organizationName: 'datocal', // Usually your GitHub org/user name.
  projectName: 'nolocal', // Usually your repo name.
  presets: [
    [
      '@docusaurus/preset-classic',
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          sidebarPath: require.resolve('./sidebars.js'),
          // Please change this to your repo.
          editUrl: 'https://github.com/facebook/docusaurus/edit/main/website/',
          routeBasePath: '/',
        },
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
      }),
    ],
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      navbar: {
        title: 'NoLocal',
        logo: {
          alt: 'NoLocal Logo',
          src: 'img/logo.svg',
        },
        items: [
          {
            href: 'https://codecov.io/gh/datocal/nolocal',
            label: 'Codecov',
            position: 'right',
          },
          {
            href: 'https://codeclimate.com/github/davidtourino/nolocal/maintainability',
            label: 'Codeclimate',
            position: 'right',
          },
          {
            href: 'https://github.com/datocal/nolocal',
            label: 'GitHub',
            position: 'right',
          },
        ],
      },
      footer: {
        style: 'dark',
        copyright: `Copyright © ${new Date().getFullYear()} NoLocal, Inc. Built with Docusaurus.`,
      },
      prism: {
        theme: lightCodeTheme,
        darkTheme: darkCodeTheme,
      },
    }),
};

module.exports = config;
