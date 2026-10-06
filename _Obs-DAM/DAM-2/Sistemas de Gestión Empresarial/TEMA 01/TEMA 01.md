# Tema 1: Introducción a los sistemas de gestión empresarial

> [!abstract] Empresa de ejemplo: CodeMoll Solutions
> 
> Para ilustrar los conceptos de este tema, utilizaremos **CodeMoll Solutions**, una empresa ficticia dedicada al desarrollo de aplicaciones multiplataforma a medida para terceros.
> 
## Glosario de términos

- **Acrónimo**: Conjunto de siglas o partes ordenadas de varias palabras que se lee y pronuncia como una palabra.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > Los desarrolladores de CodeMoll utilizan constantemente acrónimos en su día a día, como "API" o "CRM", para referirse de forma rápida a conceptos complejos.
    
- **Cumplimiento (compliance)**: Procedimientos que garantizan la observancia de la normativa interna, así como de la legislación actual y los códigos éticos, por parte de directivos, empleados y demás actores relacionados con una empresa.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > CodeMoll Solutions tiene un responsable de _compliance_ que asegura que todas las aplicaciones que desarrollan cumplen estrictamente con la Ley de Protección de Datos (RGPD) y con los códigos éticos internos sobre el uso de código libre.
    
- **DataMining (Minería de datos)**: Conjunto de técnicas y tecnologías orientadas a buscar patrones no evidentes, tendencias y reglas que expliquen el comportamiento de los datos en un contexto determinado en grandes bases de datos.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > El equipo de análisis aplica minería de datos sobre los millones de registros (logs) de uso de sus aplicaciones para descubrir patrones ocultos, como qué funciones específicas hacen que la app consuma más batería en móviles Android.
    
- **Data Warehouse**: Almacén de datos que se caracteriza por contener también los metadatos (datos sobre la procedencia, periodicidad de refresco, fiabilidad, cálculos realizados para su obtención) sobre los propios datos.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > CodeMoll dispone de un gran servidor central (_Data Warehouse_) donde guarda el histórico de todos los proyectos finalizados, incluyendo metadatos como qué equipo lo desarrolló, en qué fecha se actualizaron las versiones y la fiabilidad de las pruebas automatizadas.
    
- **Flujo de trabajo (workflow)**: Es la automatización regulada de los procesos de la empresa para que la información y las tareas circulen entre los distintos departamentos siguiendo un cierto orden.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > Cuando un cliente reporta un error (_bug_), el _workflow_ automatizado asigna el ticket primero al departamento de soporte; si es grave, se pasa directamente a los programadores senior y, una vez solucionado, el sistema avisa a facturación.
    
- **KPI (Key Performance Indicator)**: Indicadores clave de desempeño. Indicadores que permiten medir magnitudes de interés.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > Un KPI vital para CodeMoll es el "Tiempo medio de resolución de incidencias" o el "Número de horas facturables frente a horas de desarrollo interno".
    
- **OLAP (On-Line Analytics Processing)**: Bases de datos multidimensionales orientadas al procesamiento analítico.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > CodeMoll utiliza bases de datos OLAP para que la gerencia pueda cruzar datos instantáneamente, como consultar cuánto tiempo han invertido los programadores en el lenguaje Kotlin, filtrado por mes y por cliente.
    
- **ROI (Return On Investment)**: Retorno de la inversión. Cálculo del tiempo que se necesita para recuperar lo invertido en un sistema con los beneficios generados por él.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > Si CodeMoll invierte 5.000€ en adquirir licencias de un nuevo IDE (como IntelliJ IDEA), calculan el ROI estimando cuántos meses tardarán en recuperar ese dinero gracias al aumento de velocidad al programar.
    
- **Sistemas de información**: Conjunto de datos convertidos en información mediante procesos y mecanismos automatizados e interrelacionados.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > El portal del empleado de CodeMoll, donde los trabajadores imputan sus horas de trabajo, gestionan sus vacaciones y descargan sus nóminas de forma centralizada.
    
- **Sostenibilidad**: Búsqueda del equilibrio entre el crecimiento económico y el cuidado del medio ambiente. Cualidad de satisfacer necesidades actuales sin comprometer a generaciones futuras.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > Para mejorar su sostenibilidad, CodeMoll ha programado un script que apaga automáticamente los servidores de prueba los fines de semana, reduciendo el gasto económico y la huella de carbono de la empresa.
    
- **Tecnologías de información y comunicación (TIC)**: Dícese del conjunto de herramientas hardware y software utilizadas para el almacenamiento, tratamiento y transmisión de la información.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > Toda la infraestructura de CodeMoll: los portátiles del equipo de desarrollo, los servidores en la nube, los repositorios de GitHub y las herramientas de mensajería interna.
    
- **Transacción**: Todo aquello que modifica o genera datos de un sistema de información.
    
    > [!example] Ejemplo en CodeMoll Solutions 
    > Cada vez que un desarrollador de CodeMoll registra que ha finalizado una tarea en el gestor de proyectos, se produce una transacción que actualiza el estado del proyecto en la base de datos.


## 1.1. Introducción

- A lo largo de la vida profesional de un programador, es altamente probable que participe en proyectos relacionados con los sistemas de información-gestión empresarial, desempeñando roles como desarrollador, soporte técnico o usuario.
    
- Es fundamental comprender la función de la empresa en la sociedad, la problemática de su gestión y las herramientas informáticas (TIC) que permiten tratar la inmensa cantidad de información que se maneja.
    
- Estos sistemas de gestión han experimentado una gran evolución desde la década de los años sesenta.
    
- La necesidad vital de conseguir y, sobre todo, de mantener clientes, explica el surgimiento de los sistemas CRM (Customer Relationship Management), los cuales facilitan el día a día de la fuerza comercial.
    
- La finalidad última de la gestión empresarial es lograr que la empresa sea viable mediante una correcta planificación y un control de sus aspectos financieros, logísticos, productivos y comerciales.
    

> [!example] Ejemplo en CodeMoll Solutions (1.1) 
> Los programadores de CodeMoll Solutions interactúan diariamente con un sistema interno para registrar los avances en su código, lo que hace viable la empresa mediante un control productivo estricto. A su vez, el equipo comercial utiliza un CRM para hacer seguimiento de todas las reuniones que tienen con otras empresas que les encargan software, buscando mantener y fidelizar a esos clientes a largo plazo.

## 1.2. La gestión empresarial

- La gestión empresarial se entiende como el conjunto de estrategias y acciones que persiguen mejorar el funcionamiento general de un negocio.
    
- Mediante estas estrategias, se busca conseguir un aumento de la productividad, mejorar la competitividad e incrementar la rentabilidad de la empresa.
    

### 1.2.1. Objetivo de la empresa

- Utilizando sus recursos financieros, materiales y humanos, la empresa genera servicios y productos que, al ser comercializados, producen beneficios, los cuales son la razón de su existencia.
    
- Estos beneficios se pueden hacer crecer combinando acciones sobre varios aspectos de la actividad empresarial:
    
    - Maximizando ventas y minimizando costes.
        
    - Eliminando tareas innecesarias y automatizando tareas.
        
    - Optimizando recursos y agilizando procesos cotidianos.
        
    - Controlando minuciosamente todos los detalles de la empresa.
        
- En la actualidad, las empresas no solo deben buscar ser competitivas, sino también preocuparse por ser cada vez más sostenibles y por alcanzar un mayor nivel de cumplimiento normativo, conocido como _compliance_.
    
- La efectividad y la eficiencia son factores que influyen directamente en el diferencial de beneficio.
    

> [!example] Ejemplo en CodeMoll Solutions (1.2.1) 
> CodeMoll Solutions utiliza sus recursos humanos (desarrolladores de software) y materiales (equipos informáticos y servidores) para crear aplicaciones multiplataforma (servicios), generando así beneficios económicos. Para minimizar costes y maximizar el beneficio, automatizan tareas repetitivas como el testeo y despliegue del código. Además, se aseguran de tener un alto nivel de cumplimiento normativo (_compliance_) garantizando que todas sus aplicaciones cumplen con la ley de protección de datos europea.

### 1.2.2. Procesos de negocio, datos y flujo de trabajo

- **Procesos de negocio**: Son un conjunto de tareas relacionadas y ordenadas que proporcionan un servicio o producto, el cual puede ser para el cliente final (externo) o para otro departamento de la misma empresa (interno).
    
- Muchas veces los procesos son secuenciales; la salida que se obtiene de un proceso es el inicio del siguiente.
    
- **Transacciones**: Son procesos empresariales que han sido descompuestos en otros de menor entidad hasta llegar a un nivel que se considera elemental.
    
- **Datos**: En su actividad diaria, las empresas manejan un volumen considerable de datos (desde detalles de transacciones hasta control de almacén, marketing, redes sociales o recursos humanos) que se van acumulando y que deben convertirse en información vital.
    
- **Flujo de trabajo (workflow)**: Es el intercambio de informaciones de manera ordenada y eficiente entre los distintos departamentos. El manejo y tratamiento de datos junto al flujo de trabajo son aspectos primordiales para mejorar el funcionamiento e incrementar el beneficio de la empresa.
    

> [!example] Ejemplo en CodeMoll Solutions (1.2.2) 
> Un **proceso de negocio** en CodeMoll Solutions es la "Gestión de un nuevo proyecto de software", el cual involucra a diseño, programación y finanzas. Este proceso se puede dividir hasta llegar a **transacciones** elementales, como el registro en la base de datos de la compra de una licencia de software. Durante los meses que dura la creación de la app, se maneja un gran volumen de datos (horas de trabajo de los programadores, reportes de _bugs_, facturación mensual) que circulan entre el departamento técnico y administración mediante un **flujo de trabajo** estructurado, permitiendo a la gerencia extraer información valiosa para la empresa.

## 1.3. Sistemas de información de gestión

- La utilización de las Tecnologías de la Información y Comunicación (TIC) para controlar el flujo de trabajo (_workflow_) y tratar datos centralizados ha supuesto la aparición de los sistemas de gestión empresarial.
    
- **SIG (Sistema de Información Gerencial) / SIE (Sistema de Información Empresarial):** Conjunto de aplicaciones que cubren las necesidades de tratamiento simultáneo de información, proporcionando una gestión automatizada del negocio.
    
- Un sistema no es solo la tecnología (hardware/software), sino también la organización de esos recursos y los métodos para obtener la información.
    

> [!example] Ejemplo en CodeMoll Solutions 
> CodeMoll Solutions utiliza un SIG propio que centraliza los repositorios de código de los programadores, la facturación a los clientes y el control de asistencia de recursos humanos en una misma plataforma, permitiendo a la directiva gobernar la empresa eficazmente.

### 1.3.1. La sopa de letras: MIS, SIG, SIE...

Existen innumerables acrónimos para referirse a módulos o herramientas de gestión. Estos son algunos de los más destacados explicados en el temario y en clase:

- **KMS (Knowledge Management System):** Sistema para almacenar el conocimiento de la empresa.
    
- **BI (Business Intelligence):** Inteligencia de negocio. Relaciona datos aislados aplicando el conocimiento del sector. El profesor puso el ejemplo de un camarero que, conociendo su negocio, deduce qué mesas se llenarán a mediodía.
    
- **PLM (Product Lifecycle Management):** Gestiona la información técnica del producto durante todo su ciclo de vida. El profesor usó el ejemplo del ciclo de vida de una silla (desde qué materiales la componen hasta cuándo hay que retirarla).
    

> [!example] Ejemplo en CodeMoll Solutions
> 
> - **KMS:** CodeMoll tiene una wiki interna en Obsidian donde los desarrolladores Senior documentan cómo solucionar errores recurrentes en bases de datos SQL para que los Junior puedan consultarlo.
>     
> - **BI:** Analizando los datos de descargas, deducen que los usuarios de iOS compran más suscripciones los fines de semana, por lo que lanzan las ofertas esos días.
>     
> - **PLM:** CodeMoll gestiona el ciclo de vida de una App: diseño en Figma, programación en React Native, despliegue en la App Store, mantenimiento de _bugs_ y, finalmente, su retirada del mercado.
>     

### 1.3.2. Una clasificación de los sistemas de gestión empresarial

Según su nivel de actuación dentro de la empresa, los sistemas se clasifican en:

1. **TPS (Sistemas de procesamiento de transacciones):** Es el nivel más bajo y operativo. Soporta la rutina diaria comercial y económica. En clase se puso el ejemplo de los cobros con tarjeta Visa o la caja de un supermercado.
    
2. **BPM (Sistemas de gestión por procesos de negocio):** Gestionan acciones físicas ordenadas que realizan personas y máquinas. Ejemplo de clase: una cadena de montaje de coches donde, si falla un paso, se para toda la producción.
    
3. **MIS (Sistemas de información de gestión):** Recogen datos de fuentes internas para elaborar informes y estadísticas que ayudan a tener una visión veraz de la empresa.
    
4. **ERP (Sistemas de colaboración empresarial):** Sistemas integrales con una única base de datos que recogen info de todos los departamentos (ventas, RRHH, finanzas). Ejemplo de clase: el software de una empresa de _catering_ de aviación que coordina las recetas con los pedidos de las aerolíneas.
    
5. **DSS (Sistemas de apoyo a la toma de decisiones):** Permiten realizar simulaciones entre varios escenarios para decidir estratégicamente. Ejemplos de clase: recepcionistas de hotel prediciendo ocupación o sistemas de pronóstico en elecciones.
    
6. **EIS (Sistemas de información ejecutiva):** Información interna y externa resumida en gráficos muy visuales para la alta dirección. Ejemplo de clase: un directivo de farmacia comprobando qué medicamentos se venden por vía privada frente a la Seguridad Social.
    
7. **BSC (Cuadro de mando integral / Dashboard):** Monitoriza el grado de consecución de objetivos mediante indicadores clave (**KPI**).
    

> [!example] Aplicación de la pirámide en CodeMoll Solutions
> 
> - **TPS:** La pasarela de pago (Stripe) que procesa las suscripciones mensuales de sus aplicaciones.
>     
> - **BPM:** Su sistema automatizado de despliegue (CI/CD): el código pasa por testeo, revisión y publicación en orden estricto.
>     
> - **MIS:** El reporte mensual que extrae RRHH para ver cuántas horas extra se han hecho.
>     
> - **ERP:** Utilizan Odoo para que una factura pagada actualice automáticamente el presupuesto del proyecto y el saldo bancario de la empresa.
>     
> - **DSS:** Un simulador que ayuda a la directiva a decidir si es más rentable contratar a dos programadores Junior o a un Senior _freelance_ para el próximo proyecto.
>     
> - **EIS:** Un panel en el iPad del CEO que le muestra con una gráfica circular el porcentaje de beneficios de este trimestre.
>     
> - **BSC:** Un monitor en la oficina que avisa en rojo o verde si se cumple el KPI de "Entregar la aplicación al cliente en menos de 90 días".
>     

### 1.3.3. Los mercados verticales

- **Software Vertical:** Soluciones desarrolladas específicamente para un sector muy concreto (agrícola, inmobiliario, despachos de abogados) frente al software generalista (horizontal).
    

> [!example] Ejemplo en CodeMoll Solutions 
> En lugar de usar un Excel generalista (horizontal), CodeMoll utiliza "Jira", un software vertical diseñado estrictamente para empresas de desarrollo de software que trabajan con metodologías ágiles y control de _bugs_.

## 1.4. Un poco de historia

La evolución de estos sistemas refleja el paso de una gestión manual a la nube:

- **Años 60:** Procesos manuales heredados del sector militar. Aparecen los gestores de listas de materiales o **BOM** (Bill of Materials).
    
- **Años 70 (MRP):** Planificación de Requisitos de Material. Ejecutados en grandes ordenadores (Mainframes) de IBM. Empresas como Bosch fueron pioneras. Su objetivo era calcular qué material faltaba en el almacén para fabricar.
    
- **Años 80 (MRP II):** Con la llegada de los PC, se integran áreas como logística, finanzas y control de costes de mano de obra.
    
- **Años 90 (ERP):** Revolución de la arquitectura cliente-servidor. Una única base de datos centralizada elimina la duplicidad de datos en la empresa.
    
- **Años 2000 (SCM / ERP II):** Expansión de Internet y el comercio electrónico (_E-commerce_), tanto B2B (Business to Business) como B2C (Business to Customer). Aparece la gestión de la cadena de suministro (**SCM**) coordinando empresas con sus proveedores mediante el intercambio electrónico de datos (**EDI**).
    
- **Años 2010+ (Cloud y SaaS):** _Software as a Service_ (Software como servicio). Ya no se instalan servidores locales; todo se ejecuta desde un navegador en la nube, democratizando el acceso para PYMES y autónomos.
    

> [!example] Ejemplo histórico en CodeMoll Solutions 
> Si CodeMoll hubiera existido en los 70, tendría que haber comprado un Mainframe carísimo solo para listar qué piezas de hardware tenían (MRP). Hoy, nacidos en la era Cloud (2010+), ni siquiera tienen servidor físico: pagan una suscripción mensual (SaaS) a GitHub y Amazon Web Services para alojar todo su trabajo.

## 1.5. La fidelización de clientes. Concepto de CRM

- El objetivo vital hoy no es solo conseguir clientes, sino **fidelizarlos** (mantener ventas recurrentes), ya que es mucho más barato mantener a un cliente que conseguir uno nuevo.
    
- **CRM (Customer Relationship Management):** Software colaborativo orientado al cliente. Almacena bases de datos, contactos, presupuestos, correos e histórico de operaciones para personalizar estrategias comerciales.
    
- Permite la gestión y control de un **Lead** (una oportunidad de venta, contacto en puerta fría o posibilidad de negocio) para evitar que se pierda.
    
- **ROI (Retorno de Inversión):** Cálculo vital para saber en cuánto tiempo los beneficios generados por el CRM compensarán lo que costó instalarlo.
    
- **Evolución y fabricantes:** Históricamente destacan Goldmine o ACT! en los 80/90, evolucionando a gigantes actuales como Salesforce (pioneros del SaaS en 1999), Zoho, Microsoft Dynamics, SAP u Odoo. Ya no son exclusivos de grandes empresas, existiendo versiones muy asequibles para PYMES.
    

> [!example] Ejemplo de CRM y Leads en CodeMoll Solutions 
> El equipo de marketing de CodeMoll asiste a una feria tecnológica. Allí consiguen la tarjeta de visita del dueño de una inmobiliaria que necesita una app (esto es un **Lead** o tentativa). Introducen ese _Lead_ en su CRM (Salesforce). Si el cliente rechaza el presupuesto inicial, el CRM programa un recordatorio automático para enviarle un correo con un descuento en 3 meses, logrando finalmente la venta y fidelizando la relación a largo plazo. El aumento de estas ventas paga con creces la licencia anual de Salesforce (ROI positivo).