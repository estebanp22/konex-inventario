# ============================
#  Makefile – Konex Inventario
# ============================

# Variables
DB_DIR=db
BACKEND_DIR=backend/inventario
FRONTEND_DIR=frontend/konex-inventario-front



# Marcar como phony para que siempre se ejecuten
.PHONY: all db db-down backend backend-build frontend stop clean

# ----------------------------
# Comando principal
# ----------------------------
all: db
	@echo "Levantando backend y frontend en segundo plano..."
	cd $(BACKEND_DIR) && ( if [ -f "./mvnw" ]; then ./mvnw spring-boot:run & else mvn spring-boot:run & fi )
	cd $(FRONTEND_DIR) && ( npm install && ng serve --open & )
	@echo "✔ Todo lanzado. Revisa los procesos y logs en esta terminal."

# ----------------------------
# Base de datos
# ----------------------------

db:
	@echo "Levantando base de datos Oracle..."
	cd $(DB_DIR) && docker compose up -d
	@echo "Base de datos levantada."

db-down:
	cd $(DB_DIR) && docker compose down
	@echo "Base de datos detenida."

# ----------------------------
# Backend
# ----------------------------

backend:
	@echo "Ejecutando backend Spring Boot..."
	cd $(BACKEND_DIR) && \
	if [ -f "./mvnw" ]; then \
		./mvnw spring-boot:run; \
	else \
		mvn spring-boot:run; \
	fi

backend-build:
	@echo "Compilando backend..."
	cd $(BACKEND_DIR) && \
	if [ -f "./mvnw" ]; then \
		./mvnw clean install; \
	else \
		mvn clean install; \
	fi

# ----------------------------
# Frontend
# ----------------------------

frontend:
	@echo "Levantando frontend Angular..."
	cd $(FRONTEND_DIR) && npm install && ng serve --open

# ----------------------------
# Detener todo
# ----------------------------

stop:
	cd $(DB_DIR) && docker compose down || true
	@echo "Docker detenido (si estaba corriendo)."

# ----------------------------
# Limpiar backend
# ----------------------------

clean:
	@echo "Limpiando build del backend..."
	cd $(BACKEND_DIR) && \
	if [ -f "./mvnw" ]; then \
		./mvnw clean; \
	else \
		mvn clean; \
	fi

