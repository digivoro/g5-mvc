FROM node:alpine as build-stage

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

RUN npm install -g http-server

COPY /dist /usr/share/nginx/html

EXPOSE 8080

CMD ["http-server", "dist"]