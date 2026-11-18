# Las interfaces entregan los datos mínimos, inmutables si se puede, nunca objetos internos

Ningún método de una interfaz (los contratos del paquete contract) entrega referencias a objetos internos de quien lo implementa. Entrega solo los datos mínimos que necesita quien lo llama y, siempre que se pueda, inmutables: números, textos, records y listas que no se pueden cambiar. Lo que no puede ser inmutable, como una BufferedImage, va como copia.

Por qué: es la regla general de la que salen las decisiones sobre datos entre capas (035 a 041). Cada interfaz enseña al alumno de 2º DAM qué necesita de verdad el otro lado, y nadie puede cambiar desde fuera lo que no es suyo.
