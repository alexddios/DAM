// useState sirve para guardar datos que, al cambiar, vuelven a pintar la pantalla
import { useState } from 'react';
import { Alert, Button, Image, StyleSheet, Text, View } from 'react-native';
// Librerías de los apuntes: una modifica imágenes y la otra lee archivos
import { FlipType, ImageManipulator, SaveFormat } from 'expo-image-manipulator';
import { File } from 'expo-file-system';

// Demo 2a: devuelve lo que ocupa un archivo en KB (null si no existe)
function tamanoEnKB(uri) {
  const archivo = new File(uri);
  // condición ? valor si es verdad : valor si es mentira
  return archivo.exists ? Math.round(archivo.size / 1024) : null;
}

// "original" es la imagen que nos pasa App.js: { uri, width, height }
export default function Laboratorio({ original }) {
  const [resultado, setResultado] = useState(null); // última imagen procesada
  const [tabla, setTabla] = useState([]);           // lista del microejercicio 2
  const [procesando, setProcesando] = useState(false); // true = botones desactivados

  // Los 4 pasos de los apuntes siempre son:
  // 1. manipulate(uri)   2. transformar   3. renderAsync()   4. saveAsync()
  // Las funciones son "async" porque tardan: "await" espera a que terminen.

  // Demo 2a · Redimensionar y convertir (CA c, d)
  async function procesar(formato) {
    setProcesando(true);
    try {
      const context = ImageManipulator.manipulate(original.uri);
      context.resize({ width: 800 }); // la altura se calcula sola
      const imagen = await context.renderAsync();
      const guardada = await imagen.saveAsync({ format: formato, compress: 0.6 });
      // "...guardada" copia sus datos (uri, width, height) y añadimos dos más
      setResultado({ ...guardada, kb: tamanoEnKB(guardada.uri), format: formato });
    } catch (error) {
      Alert.alert('No se ha podido procesar', error.message);
    }
    setProcesando(false);
  }

  // Demo 2b · Recortar un cuadrado centrado (CA d)
  async function recortarCuadrado() {
    setProcesando(true);
    try {
      // El lado del cuadrado es la medida más pequeña de la imagen
      const lado = Math.min(original.width, original.height);
      const context = ImageManipulator.manipulate(original.uri);
      context.crop({
        originX: (original.width - lado) / 2,  // dónde empieza en horizontal
        originY: (original.height - lado) / 2, // dónde empieza en vertical
        width: lado,
        height: lado,
      });
      const imagen = await context.renderAsync();
      const guardada = await imagen.saveAsync({ format: SaveFormat.PNG });
      setResultado({ ...guardada, kb: tamanoEnKB(guardada.uri), format: SaveFormat.PNG });
    } catch (error) {
      Alert.alert('No se ha podido recortar', error.message);
    }
    setProcesando(false);
  }

  // Ampliación · Rotar 90° o hacer espejo
  async function girar(tipo) {
    setProcesando(true);
    try {
      // Si ya hay un resultado seguimos sobre él; si no, usamos la original
      let uri = original.uri;
      if (resultado !== null) {
        uri = resultado.uri;
      }

      const context = ImageManipulator.manipulate(uri);
      if (tipo === 'rotar') {
        context.rotate(90);
      } else {
        context.flip(FlipType.Horizontal);
      }
      const imagen = await context.renderAsync();
      const guardada = await imagen.saveAsync({ format: SaveFormat.JPEG, compress: 0.9 });
      setResultado({ ...guardada, kb: tamanoEnKB(guardada.uri), format: SaveFormat.JPEG });
    } catch (error) {
      Alert.alert('No se ha podido girar', error.message);
    }
    setProcesando(false);
  }

  // Sirve para el microejercicio 2: crea UNA fila de la tabla
  async function crearFila(ancho, formato, calidad) {
    const context = ImageManipulator.manipulate(original.uri);
    context.resize({ width: ancho });
    const imagen = await context.renderAsync();
    const guardada = await imagen.saveAsync({ format: formato, compress: calidad });
    return {
      ...guardada,
      kb: tamanoEnKB(guardada.uri),
      format: formato,
      compress: calidad,
    };
  }

  // Microejercicio 2 · 3 tamaños, y cada uno en 3 formatos (9 filas)
  async function laboratorioDeFormatos() {
    setProcesando(true);
    try {
      const anchos = [1600, 800, 400];
      const filas = []; // lista vacía que vamos llenando con push

      // El bucle se repite una vez por cada ancho
      for (const ancho of anchos) {
        filas.push(await crearFila(ancho, SaveFormat.JPEG, 0.8));
        filas.push(await crearFila(ancho, SaveFormat.JPEG, 0.4));
        filas.push(await crearFila(ancho, SaveFormat.WEBP, 0.6));
      }
      setTabla(filas);
    } catch (error) {
      Alert.alert('No se ha podido procesar', error.message);
    }
    setProcesando(false);
  }

  // Lo que se ve en pantalla
  return (
    <View style={styles.laboratorio}>
      <Text style={styles.subtitulo}>Procesar y convertir</Text>

      {/* "() => procesar(...)" significa: ejecútalo solo al pulsar el botón */}
      <Button title="800 px en JPEG" disabled={procesando} onPress={() => procesar(SaveFormat.JPEG)} />
      <Button title="800 px en WebP" disabled={procesando} onPress={() => procesar(SaveFormat.WEBP)} />
      <Button title="800 px en PNG" disabled={procesando} onPress={() => procesar(SaveFormat.PNG)} />
      <Button title="Recortar cuadrado" disabled={procesando} onPress={recortarCuadrado} />
      <Button title="Rotar 90°" disabled={procesando} onPress={() => girar('rotar')} />
      <Button title="Espejo" disabled={procesando} onPress={() => girar('espejo')} />
      <Button title="Laboratorio de formatos" disabled={procesando} onPress={laboratorioDeFormatos} />

      {/* "condición && algo" pinta ese algo solo si la condición es verdad */}
      {procesando && <Text style={styles.texto}>Procesando…</Text>}

      {resultado && (
        <View>
          <Image source={{ uri: resultado.uri }} style={styles.imagen} />
          <Text style={styles.texto}>
            {resultado.format} · {resultado.width} × {resultado.height} px · {resultado.kb} KB
          </Text>
        </View>
      )}

      {/* "map" recorre la lista y pinta un Text por cada fila */}
      {tabla.map((fila) => (
        <Text key={fila.uri} style={styles.fila}>
          {fila.format} {fila.compress} · {fila.width} × {fila.height} px · {fila.kb} KB
        </Text>
      ))}
    </View>
  );
}

// Estilos: parecido a CSS
const styles = StyleSheet.create({
  laboratorio: {
    width: '100%',
    gap: 12,
  },
  subtitulo: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  imagen: {
    width: '100%',
    height: 260,
    resizeMode: 'contain',
  },
  texto: {
    textAlign: 'center',
    color: '#555',
  },
  fila: {
    paddingVertical: 6,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
});
