#Stage 1
FROM node:22-alpine as builder
WORKDIR /app
COPY package*.json .
COPY yarn*.lock .
RUN yarn install
COPY . .
RUN yarn build

#Stage 2
FROM nginx:1.27.0
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=builder /app/build .
ENTRYPOINT ["nginx", "-g", "daemon off;"]

# docker build . -t cbidici/file-preview --no-cache --network=host
# docker run --restart always -p 80:80  --detach cbidici/file-preview

