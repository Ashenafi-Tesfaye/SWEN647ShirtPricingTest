# Define Variables
APP_NAME=SWEN647ShirtPricingTest
TEST_CLASS=swen647.umgc.SWEN647ShirtPricingTest.ShirtPricingUITest
PORT=8080

# Run all commands in sequence
all: build run wait_for_spring test clean_up

# 1️⃣ Build the application using Maven
build:
	@echo "Building the Spring Boot application..."
	mvn clean package -DskipTests

# 2️⃣ Start the Spring Boot application using Maven
run:
	@echo "🔄 Starting Spring Boot Application using Maven..."
	nohup mvn spring-boot:run > springboot.log 2>&1 &

# 3️⃣ Wait for Spring Boot to start before running tests
wait_for_spring:
	@echo "⏳ Waiting for Spring Boot to start on port $(PORT)..."
	@until curl --output /dev/null --silent --head --fail http://localhost:$(PORT)/index.html; do \
	    echo "⌛ Still waiting..."; \
	    sleep 5; \
	done
	@echo "✅ Spring Boot is up and running!"

# 4️⃣ Run Selenium TestNG Tests
test:
	@echo "🧪 Running Selenium TestNG UI tests..."
	mvn test -Dtest=$(TEST_CLASS)

# 5️⃣ Stop Spring Boot Application after tests
clean_up:
	@echo "🛑 Stopping Spring Boot Application..."
	@pkill -f "mvn spring-boot:run" || echo "Application already stopped"

# 6️⃣ Clean the project
clean:
	@echo "🧹 Cleaning project..."
	mvn clean
	rm -f nohup.out springboot.log

.PHONY: all build run wait_for_spring test clean_up clean
