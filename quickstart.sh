docker rm $(docker ps --filter status=exited -q)
#./mvnw clean package
./mvnw clean package -Pnative -Dquarkus.native.container-build=true
docker compose up --no-deps --build

