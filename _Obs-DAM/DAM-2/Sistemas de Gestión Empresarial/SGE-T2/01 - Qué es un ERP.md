---
tags: [sge, erp, concepto]
---

# 01 · Qué es un ERP

Volver a [[SGE-T2]]

## Problema de partida
Los procesos de negocio son **multifuncionales** (implican varios departamentos). Si cada departamento usa un software independiente con su propia base de datos, el flujo de información **no es automático ni eficiente**. Por eso estos sistemas se han ido sustituyendo por ERP.

## Definición
> [!info] ERP
> **Sistema (de) planificación de recursos empresariales**: sistema de información **integrado**, compuesto por **módulos** que se corresponden con funciones departamentales y que usa una **base de datos común**.

Permite:
- Automatizar procesos.
- Compartir información entre departamentos y gestionar el flujo de trabajo.
- Dar a los directivos información consolidada para la **toma de decisiones**.

El software de cada módulo es distinto, pero su **esquema de BD es compatible** con la BD común.

> [!tip] ERP > suma de módulos
> Un ERP es más que sus módulos por la **interoperabilidad**. Es mejor que elegir módulos de distintos fabricantes, porque no compartirían BD ni esquema.

> [!important] Objetivos básicos de un ERP
> 1. Acceso a la información de forma **inmediata, confiable y precisa**.
> 2. **Optimización** de procesos y eliminación de operaciones innecesarias.
> 3. **Compartición** de información entre departamentos.

## Ejemplo: proceso de ventas (equipo informático)
1. **Marketing**: campaña → un cliente muestra interés.
2. **Comercial**: da de alta al cliente en el CRM y envía presupuesto.
3. **Contabilidad/Finanzas**: comprueba solvencia y forma de pago.
4. **RRHH / Dpto. Técnico / Proyectos**: asigna técnico para configurar.
5. **Almacén y Logística**: comprueba stock (o pide a Compras) y envía.
6. **Facturación/Contabilidad**: factura y apuntes contables.

Todas las transacciones modifican la BD y el ERP las **recoge y consolida**.

> [!note] En pymes y micropymes
> Los "departamentos" se difuminan (p. ej. un autónomo hace todo o externaliza). Aun así los procesos se ejecutan y el ERP permite gestionarlos desde **un único punto**. Hay que elegir el ERP adecuado al tamaño y usar solo los módulos necesarios (p. ej. sin RRHH ni contabilidad si están externalizados, pero pudiendo **exportar datos** al proveedor).

## Origen y evolución
| Generación | Qué aporta |
|---|---|
| **MRP II** | Fabricación y finanzas (antecesor) |
| **ERP** (Gartner, **1990**) | Supera MRP II, incluye otras funcionalidades internas |
| **ERP II** (Gartner, 2000, *"ERP is dead. Long live ERP II"*) | Por el **e-commerce**: relación con proveedores y administraciones (SRM, PLM) |
| **ERP III** | Integra clientes, colaboración interna y externa, **nube, SaaS**, transacciones sin contacto, redes sociales, IoT, **C-Commerce** |
| **ERP IV** | Añade socios, competidores y otros actores; usa **BI** e **inteligencia artificial** |

ERP = procesos internos · ERP II = interacción con el entorno.

Siguiente: [[02 - Características, componentes y módulos]]
