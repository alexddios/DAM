---
tags: [SGE, tema1, MOC]
asignatura: Sistemas de Gestión Empresarial
tema: 1 · Introducción a los sistemas de gestión empresarial
---

# 🗺️ SGE · Tema 1 — Índice

> [!abstract] Qué cubre este tema
> Función de la empresa y su gestión, sistemas de información (SIG/MIS), clasificación de sistemas (TPS → BSC), software horizontal y vertical, historia (BOM → ERP Cloud), servicios cloud y CRM.

## Notas del tema

| Nº | Nota | Actividad relacionada |
|---|---|---|
| 1 | [[01 - Gestión empresarial y objetivos]] | N01 · Objetivos de empresa |
| 2 | [[02 - Sistemas de información]] | N02 · Sistemas no gerenciales |
| 3 | [[03 - Clasificación de sistemas y KPI]] | N03 · TPS, BPM, MIS, ERP, DSS, EIS, BSC |
| 4 | [[04 - Software horizontal y vertical]] | N04 · Mercados verticales |
| 5 | [[05 - Historia y JIT]] | N05 · JIT |
| 6 | [[06 - Servicios cloud]] | N05 / N07 · IaaS, PaaS, SaaS, CaaS |
| 7 | [[07 - CRM]] | N06 · Salesforce vs Zoho |
| 8 | [[08 - Glosario y acrónimos]] | — |
| 9 | [[09 - Autoevaluación]] | Test del libro |

## Mapa conceptual

```mermaid
flowchart TB
    EMP["🏢 Empresa"]
    EMP -->|se compone de| PRO["Procesos"]
    EMP -->|se compone de| DAT["Datos"]
    EMP -->|se compone de| WF["Workflow"]
    EMP -->|vive de| CLI["Clientes"]
    EMP --> HIS["Historia<br/>BOM → MRP → ERP → Cloud"]
    SIG["SIG<br/>Sistema de información gerencial"] -->|gestiona| PRO
    SIG -->|gestiona| DAT
    SIG -->|gestiona| WF
    CRM["CRM"] -->|gestiona| CLI
    SIG --> TPS & BPM & MIS & ERP & DSS & EIS & BSC
```

## Mindmap rápido

```mermaid
mindmap
  root((SGE T1))
    Empresa
      Objetivo: beneficio
      6 vías de mejora
      Competitiva, sostenible, compliance
    Pilares
      Procesos de negocio
      Datos
      Flujo de trabajo
    Sistemas
      TPS registra
      BPM coordina
      MIS informa
      ERP integra
      DSS simula
      EIS resume
      BSC controla con KPI
    Mercados
      Horizontal
      Vertical
    Cloud
      IaaS
      PaaS
      SaaS
      CaaS
    CRM
      Seguimiento
      Comunicación
      Análisis
```

> [!tip] Frase para el examen
> **TPS registra, BPM coordina, MIS informa, ERP integra, DSS simula, EIS resume para directivos y BSC controla la estrategia con KPI.**
