gen:
	protoc -I=proto --go_out=. --go-grpc_out=. --openapiv2_out=swagger --grpc-gateway_out=. proto/*.proto

clean:
	rm pb/*.go

server1:
	go run cmd/server/main.go -port 50051

server2:
	go run cmd/server/main.go -port 50052

server1-tls:
	go run cmd/server/main.go -port 50051 -tls

server2-tls:
	go run cmd/server/main.go -port 50052 -tls

server:
	go run cmd/server/main.go -port 8080

rest:
	go run cmd/server/main.go -port 8081 -type rest -endpoint 0.0.0.0:8080

client:
	go run cmd/client/main.go -address 0.0.0.0:8080

client-tls:
	go run cmd/client/main.go -address 0.0.0.0:8080 -tls

test:
	go test -cover ./...

cert:
	cd ../cert; rm *.pem; rm *.srl; ./gen.sh; cd ../golang

.PHONY: gen clean server1 server2 server1-tls server2-tls server client client-tls test cert