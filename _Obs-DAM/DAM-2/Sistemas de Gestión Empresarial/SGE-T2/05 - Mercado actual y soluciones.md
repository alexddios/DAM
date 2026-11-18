---
tags: [sge, erp, mercado]
---

# 05 · Mercado actual y soluciones

Anterior: [[04 - Ventajas, inconvenientes y fracaso]] · Siguiente: [[06 - Inteligencia de negocio (BI)]]

## Historia del mercado
- **Finales 60 / principios 70**: nace el mercado de software de gestión empresarial, dominado por **IBM**. Aparecen **SAP** y **J.D. Edwards**.
- **Años 80**: la revolución de la **microinformática** → más empresas usan software de gestión. Aparecen **Navision** (danesa), **PeopleSoft**, **Sage** y **SP Editores** (España: ContaPlus y FacturaPlus, vendidos en kioscos).
- Después: adquisiciones, fusiones y absorciones hasta la situación actual.
- Última década: cambio del modelo basado en venta de licencias propietarias por la proliferación del **Open Source**.
- Hoy: mercado repartido entre **fabricantes propietarios** y **comunidades de software libre** (que han evolucionado a empresas que venden servicios y mejoras).

## Características del mercado actual
- Amplia variedad de **ERP verticales**.
- Irrupción de fabricantes de software en el mercado **pyme**.
- Consagración del software libre (**Open Source**).
- **Usabilidad** por mimetización gráfica con soluciones de productividad personal y uso de **navegadores** en el cliente.
- Soluciones **Cloud** y **SaaS**.

> [!tip] Toma nota
> La elección de un ERP debe basarse en dos premisas:
> 1. Posibilidad de **prueba real** del sistema por la mayor parte de estamentos de la empresa.
> 2. Estudio detallado de **actualización, soporte y mantenimiento** del fabricante/distribuidor (o de la empresa comercializadora en Open Source).

Si ya existían programas equivalentes → asegurar la **migración de datos** (ej.: tener la contabilidad de los últimos 4-5 años por motivos fiscales).

## 🔒 Soluciones propietarias
Características: **licencias de pago** + coste recurrente de actualización · **código fuente no disponible** · cierta **dificultad para modificar**.

- **SAP Business One**: uno de los más implantados en **fabricación e industria**. Empresa alemana. Lenguaje **ABAP IV**; gran demanda de profesionales.
- **Oracle NetSuite**: muy extendido en empresas de venta **online** y sistemas **omnicanal**. Oracle es conocida por su SGBD. Según Gartner (2018) líder del sector. También **Oracle ERP Cloud**.
- **Microsoft Dynamics**: interfaz familiar de **Office**, el más fácil para usuarios nuevos. Antes **Navision** → **Dynamics NAV** → **Dynamics 365**. Según Gartner, mayor índice de satisfacción. Incluye BI.
- También: **Sage (200 Cloud)** en España, **Epicor (iScala)** en Hispanoamérica.

## 🔓 Soluciones Open Source
Características: **código fuente disponible** · modificaciones accesibles y repositorios abiertos · implementación exige **mayores conocimientos técnicos**.

- A priori no tienen coste pero **no siempre son gratuitas**: licencia básica sí, pero hace falta personal técnico o ayuda externa.
- Empresas desarrollan **módulos propietarios** y ofrecen instalación, parametrización, formación, soporte y mantenimiento (= coste).
- Ejemplo: **OpenBravo** pasó a ser solución comercial de pago.
- Más conocidas: **Odoo** (líder Open Source), **Dolibarr**, **Adempiere** (viene de Compiere), **Apache OFBiz**, **ERPNext**.

## 🛠️ Tecnologías utilizadas
| Fabricante | Producto | Base de datos | Lenguaje |
|---|---|---|---|
| Microsoft | Dynamics | SQL | C++, C/AL, VB |
| Oracle | NetSuite | Oracle | C, C++, Java |
| SAP | Business One | SAP Hana | C, C++ |
| Odoo | Odoo | PostgreSQL | **Python** |
| Dolibarr | ERP & CRM | MySQL y PostgreSQL | **PHP** |
| Adempiere | ERP | Oracle y PostgreSQL | **Java** |
