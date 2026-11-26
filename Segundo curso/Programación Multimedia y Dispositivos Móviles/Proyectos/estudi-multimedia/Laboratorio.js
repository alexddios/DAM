import { useState } from 'react';
import { Alert, Button, Image, StyleSheet, Text, View } from 'react-native';
import { FlipType, ImageManipulator, SaveFormat } from 'expo-image-manipulator';
import { File } from 'expo-file-system';

const TAMANOS = [1600, 800, 400];
const SALIDAS = [
  { format: SaveFormat.JPEG, compress: 0.8 },
  { format: SaveFormat.JPEG, compress: 0.4 },
  { format: SaveFormat.WEBP, compress: 0.6 },
];

function tamanoEnKB(uri) {
  const archivo = new File(uri);
  return archivo.exists ? Math.round(archivo.size / 1024) : null;
}

function describir({ format, compress, width, height, kb }) {
  const calidad = format === SaveFormat.PNG ? '' : ` ${compress}`;
  return `${format.toUpperCase()}${calidad} · ${width} × ${height} px · ${kb ?? '?'} KB`;
}

export default function Laboratorio({ original }) {
  const [resultado, setResultado] = useState(null);
  const [tabla, setTabla] = useState([]);
  const [procesando, setProcesando] = useState(false);
  const [error, setError] = useState('');

  // Aplica las transformaciones, guarda el archivo y calcula sus KB
  async function guardar(uri, transformar, format, compress) {
    const context = ImageManipulator.manipulate(uri);
    transformar(context);
    const imagen = await context.renderAsync();
    const guardada = await imagen.saveAsync({ format, compress });
    return { ...guardada, format, compress, kb: tamanoEnKB(guardada.uri) };
  }

  // Desactiva los botones mientras se procesa y controla los errores
  async function ejecutar(operacion) {
    setProcesando(true);
    setError('');
    try {
      await operacion();
    } catch (e) {
      setError(`No se ha podido procesar: ${e.message}`);
      Alert.alert('No se ha podido procesar', e.message);
    } finally {
      setProcesando(false);
    }
  }

  // Demo 2a · Redimensionar y convertir (CA c, d)
  function procesar(format) {
    ejecutar(async () => {
      const nueva = await guardar(
        original.uri,
        (context) => context.resize({ width: 800 }), // la altura se calcula sola
        format,
        0.6
      );
      setResultado(nueva);
    });
  }

  // Demo 2b · Recortar un cuadrado centrado (CA d)
  function recortarCuadrado() {
    ejecutar(async () => {
      const lado = Math.min(original.width, original.height);
      const nueva = await guardar(
        original.uri,
        (context) =>
          context.crop({
            originX: (original.width - lado) / 2,
            originY: (original.height - lado) / 2,
            width: lado,
            height: lado,
          }),
        SaveFormat.PNG,
        1
      );
      setResultado(nueva);
    });
  }

  // Microejercicio 2 · Laboratorio de formatos: 3 tamaños x 3 salidas
  function rellenarTabla() {
    ejecutar(async () => {
      setTabla([]);
      const filas = [];
      for (const ancho of TAMANOS) {
        for (const { format, compress } of SALIDAS) {
          const fila = await guardar(
            original.uri,
            (context) => context.resize({ width: ancho }),
            format,
            compress
          );
          filas.push(fila);
          setTabla([...filas]);
        }
      }
    });
  }

  // Ampliación · Rotar y espejo: se acumulan sobre el último resultado
  function transformarResultado(transformar) {
    ejecutar(async () => {
      const nueva = await guardar(
        resultado?.uri ?? original.uri,
        transformar,
        resultado?.format ?? SaveFormat.JPEG,
        resultado?.compress ?? 0.9
      );
      setResultado(nueva);
    });
  }

  return (
    <View style={styles.laboratorio}>
      <Text style={styles.subtitulo}>Procesar y convertir</Text>

      <Button title="800 px en JPEG" disabled={procesando} onPress={() => procesar(SaveFormat.JPEG)} />
      <Button title="800 px en WebP" disabled={procesando} onPress={() => procesar(SaveFormat.WEBP)} />
      <Button title="800 px en PNG" disabled={procesando} onPress={() => procesar(SaveFormat.PNG)} />
      <Button title="Recortar cuadrado" disabled={procesando} onPress={recortarCuadrado} />
      <Button
        title="Rotar 90°"
        disabled={procesando}
        onPress={() => transformarResultado((context) => context.rotate(90))}
      />
      <Button
        title="Espejo"
        disabled={procesando}
        onPress={() => transformarResultado((context) => context.flip(FlipType.Horizontal))}
      />
      <Button title="Laboratorio de formatos" disabled={procesando} onPress={rellenarTabla} />

      {procesando && <Text style={styles.estado}>Procesando…</Text>}
      {error !== '' && <Text style={styles.error}>{error}</Text>}

      {resultado && (
        <View>
          <Image source={{ uri: resultado.uri }} style={styles.imagen} />
          <Text style={styles.estado}>{describir(resultado)}</Text>
        </View>
      )}

      {tabla.length > 0 && (
        <View>
          <Text style={styles.subtitulo}>Comparativa</Text>
          <Text style={styles.fila}>
            Original · {original.width} × {original.height} px · {tamanoEnKB(original.uri) ?? '?'} KB
          </Text>
          {tabla.map((fila) => (
            <Text key={fila.uri} style={styles.fila}>
              {describir(fila)}
            </Text>
          ))}
        </View>
      )}
    </View>
  );
}

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
  estado: {
    textAlign: 'center',
    color: '#555',
  },
  error: {
    textAlign: 'center',
    color: '#b00020',
  },
  fila: {
    paddingVertical: 6,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
});
