---
tags: [sge, erp, indice]
asignatura: Sistemas de Gestión Empresarial
tema: 2
---

# 🗂️ Tema 2 · ERP (Enterprise Resource Planning)

> [!abstract] Objetivos del capítulo
> - Comprender el concepto de ERP, sus módulos y su adaptación a los departamentos.
> - Estudiar opciones de implementación: **empaquetado** vs **a medida**.
> - Ventajas e inconvenientes de implantar un ERP.
> - Tendencias del mercado y tipos de licencias.
> - Diferenciar **On-Premise** y **Cloud**.
> - Relacionar ERP, ETL, OLAP, DataWarehouse, DataMining, ScoreCard, BI.
> - Conocer soluciones propietarias y Open Source.

## Notas del tema
1. [[01 - Qué es un ERP]]
2. [[02 - Características, componentes y módulos]]
3. [[03 - Infraestructura, instalación y licencias]]
4. [[04 - Ventajas, inconvenientes y fracaso]]
5. [[05 - Mercado actual y soluciones]]
6. [[06 - Inteligencia de negocio (BI)]]
7. [[07 - Glosario]]
8. [[08 - Actividades y supuestos prácticos]]

## Mapa conceptual
```mermaid
graph TD
    ERP[ERP] --> FO[Módulos Front-Office]
    ERP --> BO[Módulos Back-Office]
    ERP --> MOD[Modularidad]
    FO --> FO1[Clientes / CRM]
    FO --> FO2[TPV]
    FO --> FO3[e-Commerce]
    BO --> BO1[RRHH]
    BO --> BO2[Contabilidad]
    BO --> BO3[Almacén]
    BO --> BO4[Producción]
    ERP --> DES[Desarrollo]
    ERP --> LIC[Licencias]
    ERP --> INS[Instalación]
    DES --> D1[A medida]
    DES --> D2[Empaquetado]
    LIC --> L1[Comercial]
    LIC --> L2[Open Source]
    INS --> I1[On-Premise]
    INS --> I2[Cloud]
    FO --> BI[BBDD / OLAP / Datamining]
    BO --> BI
    BI --> SC[Cuadros de mando ScoreCard]
    SC --> DSS[BI · Toma de decisiones]
```
