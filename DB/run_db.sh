docker build -t scale-app-postgres . &&
docker run -d --name pg-scale-app -p 5432:5432 scale-app-postgres