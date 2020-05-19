FROM node:10 as build
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . ./
RUN npm run build

FROM nginx:alpine
COPY --from=build /usr/src/app/build /usr/share/nginx/html/app

COPY ./nginx.conf /etc/nginx/
EXPOSE 3001

CMD ["nginx", "-g", "daemon off;"]
