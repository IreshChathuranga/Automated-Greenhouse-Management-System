# Automated Greenhouse Management System (AGMS) — Microservice-Based Application

Microservice-based Automated Greenhouse Management System (AGMS) built using **Spring Boot** and **Spring Cloud**.  
Includes centralized configuration (Spring Cloud Config), service discovery (Eureka), an API Gateway, and domain microservices for:
- Zone Management
- Sensor Telemetry
- Automation & Control (Rule Engine)
- Crop Inventory

This project integrates with a provided **External IoT Backend (Reactive WebFlux)** to fetch real-time telemetry (temperature/humidity).

---

## Architecture Overview

### Infrastructure Services
- **Config Server** (`config-server`) — Port **8888**
- **Service Registry (Eureka)** (`eureka-server`) — Port **8761**
- **API Gateway** (`api-gateway`) — Port **8080**

### Domain Microservices
- **Zone Service** (`zone-service`) — Port **8081**
- **Sensor Service** (`sensor-service`) — Port **8082**
- **Automation Service** (`automation-service`) — Port **8083**
- **Crop Service** (`crop-service`) — Port **8084**

---

## External IoT Backend (Provided / Do Not Implement)

Base URL: `http://104.211.95.241:8080/api`

Used for:
- Register/Login (JWT)
- Create devices
- Fetch telemetry

> This AGMS system integrates via HTTP calls — the IoT backend is an external dependency.

---

## Prerequisites

- Java 17+ (recommended: Java 17)
- Maven 3.9+
- MySQL 8+ (for zone/crop/automation databases if configured with JPA)
- Git

---

## Centralized Configuration (Config Repo)

This project uses a separate configuration repository:

- Config Repo: `IreshChathuranga/agms-config-repo`

Make sure it contains (at minimum):
- `api-gateway.yml`
- `zone-service.yml`
- `sensor-service.yml`
- `automation-service.yml`
- `crop-service.yml`

Each file should define:
- `server.port`
- `eureka.client.service-url.defaultZone`
- service-specific properties (DB, external IoT, etc.)

---

## How to Run (Startup Order)

Start services in this order:

1. **Config Server**
2. **Eureka Server**
3. **API Gateway**
4. **Zone Service**
5. **Automation Service**
6. **Sensor Service**
7. **Crop Service**

### 1) Start Config Server (Port 8888)
From `/config-server`:
```bash
mvn spring-boot:run
```

Verify:
- `http://localhost:8888/zone-service/default`
- `http://localhost:8888/api-gateway/default`

### 2) Start Eureka Server (Port 8761)
From `/eureka-server`:
```bash
mvn spring-boot:run
```

Verify dashboard:
- `http://localhost:8761`

### 3) Start API Gateway (Port 8080)
From `/api-gateway`:
```bash
mvn spring-boot:run
```

### 4) Start Domain Services
From each service folder:
```bash
mvn spring-boot:run
```

---

## Service Discovery Validation (Eureka)

Open:
- `http://localhost:8761`

Confirm all services show **UP**, e.g.:
- `API-GATEWAY`
- `ZONE-SERVICE`
- `SENSOR-SERVICE`
- `AUTOMATION-SERVICE`
- `CROP-SERVICE`

---

## API Endpoints (Through Gateway)

> All external calls should be made via the Gateway at `http://localhost:8080`

### Zone Service
- `POST /api/zones` — Create zone (validates thresholds; registers IoT device; stores returned deviceId)
- `GET /api/zones/{id}`
- `PUT /api/zones/{id}`
- `DELETE /api/zones/{id}`

### Sensor Service
- `GET /api/sensors/latest` — Debug endpoint returning last fetched telemetry

### Automation Service
- `POST /api/automation/process` — internal (sensor pushes telemetry here)
- `GET /api/automation/logs` — list triggered actions

### Crop Service
- `POST /api/crops`
- `PUT /api/crops/{id}/status`
- `GET /api/crops`

---

## Postman Testing (Step-by-Step)

### A) Confirm Config Server
**GET**
- `http://localhost:8888/zone-service/default`

### B) Create a Zone
**POST**
- `http://localhost:8080/api/zones`
Body:
```json
{
  "name": "Tomato Zone",
  "minTemp": 18,
  "maxTemp": 32
}
```

Expected:
- response includes `id`
- response includes `deviceId` (created via external IoT backend)

### C) Sensor Fetch Verification
Wait ~10–20 seconds after starting sensor-service, then:

**GET**
- `http://localhost:8080/api/sensors/latest`

Expected:
- telemetry JSON (temperature/humidity)

### D) Automation Logs
**GET**
- `http://localhost:8080/api/automation/logs`

Expected:
- when telemetry exceeds thresholds, logs include actions:
  - `TURN_FAN_ON` or `TURN_HEATER_ON`

### E) Crop Inventory
**POST**
- `http://localhost:8080/api/crops`
```json
{ "cropName": "Tomato", "quantity": 100 }
```

Then update status:
**PUT**
- `http://localhost:8080/api/crops/{id}/status`
```json
{ "status": "VEGETATIVE" }
```

---

## Troubleshooting

### Config Server request hangs / pending
If `GET http://localhost:8888/<service>/default` is pending:
- Ensure Config Server is running and reachable on port 8888
- Ensure config repo is reachable (GitHub network access)
- Recommended fix for unstable networks: point Config Server to a **local file URI** config repository clone.

### External IoT API errors
If zone creation cannot register device:
- Verify external IoT credentials exist in config:
  - `external.iot.user.username`
  - `external.iot.user.password`
  - `external.iot.base-url`

---

## Required Submission Artifacts Checklist

- [ ] `README.md` (this file)
- [ ] Postman collection JSON in repo root (e.g., `AGMS.postman_collection.json`)
- [ ] `docs/` folder containing Eureka dashboard screenshot (all services UP)

---

## Repository Structure

- `config-server/`
- `eureka-server/`
- `api-gateway/`
- `zone-service/`
- `sensor-service/`
- `automation-service/`
- `crop-service/`
