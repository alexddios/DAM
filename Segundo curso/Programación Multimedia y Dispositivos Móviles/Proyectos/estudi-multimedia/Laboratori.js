import { useState } from 'react';
import { Alert, Button, Image, StyleSheet, Text, View } from 'react-native';
import { FlipType, ImageManipulator, SaveFormat } from 'expo-image-manipulator';
import { File } from 'expo-file-system';

const MIDES = [1600, 800, 400];
const SORTIDES = [
  { format: SaveFormat.JPEG, compress: 0.8 },
  { format: SaveFormat.JPEG, compress: 0.4 },
  { format: SaveFormat.WEBP, compress: 0.6 },
];

function midaEnKB(uri) {
  const fitxer = new File(uri);
  return fitxer.exists ? Math.round(fitxer.size / 1024) : null;
}

function descriure({ format, compress, width, height, kb }) {
  const qualitat = format === SaveFormat.PNG ? '' : ` ${compress}`;
  return `${format.toUpperCase()}${qualitat} · ${width} × ${height} px · ${kb ?? '?'} KB`;
}

export default function Laboratori({ original }) {
  const [resultat, setResultat] = useState(null);
  const [taula, setTaula] = useState([]);
  const [processant, setProcessant] = useState(false);
  const [error, setError] = useState('');

  // Aplica les transformacions, desa el fitxer i en calcula els KB
  async function desar(uri, transformar, format, compress) {
    const context = ImageManipulator.manipulate(uri);
    transformar(context);
    const imatge = await context.renderAsync();
    const desada = await imatge.saveAsync({ format, compress });
    return { ...desada, format, compress, kb: midaEnKB(desada.uri) };
  }

  // Desactiva els botons mentre es processa i controla els errors
  async function executar(operacio) {
    setProcessant(true);
    setError('');
    try {
      await operacio();
    } catch (e) {
      setError(`No s'ha pogut processar: ${e.message}`);
      Alert.alert('No s\'ha pogut processar', e.message);
    } finally {
      setProcessant(false);
    }
  }

  // Demo 2a · Redimensionar i convertir (CA c, d)
  function processar(format) {
    executar(async () => {
      const nova = await desar(
        original.uri,
        (context) => context.resize({ width: 800 }), // l'alçada es calcula sola
        format,
        0.6
      );
      setResultat(nova);
    });
  }

  // Demo 2b · Retallar un quadrat centrat (CA d)
  function retallarQuadrat() {
    executar(async () => {
      const costat = Math.min(original.width, original.height);
      const nova = await desar(
        original.uri,
        (context) =>
          context.crop({
            originX: (original.width - costat) / 2,
            originY: (original.height - costat) / 2,
            width: costat,
            height: costat,
          }),
        SaveFormat.PNG,
        1
      );
      setResultat(nova);
    });
  }

  // Microexercici 2 · Laboratori de formats: 3 mides x 3 sortides
  function omplirTaula() {
    executar(async () => {
      setTaula([]);
      const files = [];
      for (const amplada of MIDES) {
        for (const { format, compress } of SORTIDES) {
          const fila = await desar(
            original.uri,
            (context) => context.resize({ width: amplada }),
            format,
            compress
          );
          files.push(fila);
          setTaula([...files]);
        }
      }
    });
  }

  // Ampliació · Rotar i mirall: s'acumulen sobre el darrer resultat
  function transformarResultat(transformar) {
    executar(async () => {
      const nova = await desar(
        resultat?.uri ?? original.uri,
        transformar,
        resultat?.format ?? SaveFormat.JPEG,
        resultat?.compress ?? 0.9
      );
      setResultat(nova);
    });
  }

  return (
    <View style={styles.laboratori}>
      <Text style={styles.subtitol}>Processar i convertir</Text>

      <Button title="800 px en JPEG" disabled={processant} onPress={() => processar(SaveFormat.JPEG)} />
      <Button title="800 px en WebP" disabled={processant} onPress={() => processar(SaveFormat.WEBP)} />
      <Button title="800 px en PNG" disabled={processant} onPress={() => processar(SaveFormat.PNG)} />
      <Button title="Retallar quadrat" disabled={processant} onPress={retallarQuadrat} />
      <Button
        title="Rotar 90°"
        disabled={processant}
        onPress={() => transformarResultat((context) => context.rotate(90))}
      />
      <Button
        title="Mirall"
        disabled={processant}
        onPress={() => transformarResultat((context) => context.flip(FlipType.Horizontal))}
      />
      <Button title="Laboratori de formats" disabled={processant} onPress={omplirTaula} />

      {processant && <Text style={styles.estat}>Processant…</Text>}
      {error !== '' && <Text style={styles.error}>{error}</Text>}

      {resultat && (
        <View>
          <Image source={{ uri: resultat.uri }} style={styles.imatge} />
          <Text style={styles.estat}>{descriure(resultat)}</Text>
        </View>
      )}

      {taula.length > 0 && (
        <View>
          <Text style={styles.subtitol}>Comparativa</Text>
          <Text style={styles.fila}>
            Original · {original.width} × {original.height} px · {midaEnKB(original.uri) ?? '?'} KB
          </Text>
          {taula.map((fila) => (
            <Text key={fila.uri} style={styles.fila}>
              {descriure(fila)}
            </Text>
          ))}
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  laboratori: {
    width: '100%',
    gap: 12,
  },
  subtitol: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  imatge: {
    width: '100%',
    height: 260,
    resizeMode: 'contain',
  },
  estat: {
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
