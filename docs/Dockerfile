FROM node:22-alpine AS build
WORKDIR /app
ENV ASTRO_TELEMETRY_DISABLED=1
COPY package.json package-lock.json ./
RUN npm ci --no-audit --no-fund
COPY astro.config.mjs ./
COPY src ./src
COPY public ./public
COPY scripts/docs-verify.mjs ./scripts/docs-verify.mjs
RUN npm run build && npm run docs:verify

FROM nginx:alpine
COPY scripts/docs-nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html/Equinox
EXPOSE 80
