// @ts-check
import { defineConfig } from "astro/config";
import tailwindcss from "@tailwindcss/vite";
import sitemap from "@astrojs/sitemap";

export default defineConfig({
  site: "https://path.vercel.app",
  output: "server",
  vite: {
    plugins: [tailwindcss()],
  },
  integrations: [sitemap()],
});
