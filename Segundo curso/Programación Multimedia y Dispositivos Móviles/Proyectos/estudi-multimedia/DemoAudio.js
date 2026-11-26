// useEffect ejecuta código solo cuando aparece el componente
import { useEffect, useState } from 'react';
import { Alert, Button, StyleSheet, Text, View } from 'react-native';
// Librería de los apuntes para grabar y reproducir audio
import {
  RecordingPresets,
  requestRecordingPermissionsAsync,
  setAudioModeAsync,
  useAudioPlayer,
  useAudioPlayerStatus,
  useAudioRecorder,
  useAudioRecorderState,
} from 'expo-audio';

export default function DemoAudio() {
  // Demo 3a: la grabadora y su estado (isRecording, durationMillis)
  const grabadora = useAudioRecorder(RecordingPresets.HIGH_QUALITY);
  const estadoGrabadora = useAudioRecorderState(grabadora);
  const [uriGrabacion, setUriGrabacion] = useState(null); // dónde está el audio

  // Demo 3b: el reproductor de esa grabación y su estado
  const reproductor = useAudioPlayer(uriGrabacion);
  const estado = useAudioPlayerStatus(reproductor);

  // Al aparecer el componente: pedir permiso y preparar el audio
  useEffect(() => {
    async function pedirPermiso() {
      const permiso = await requestRecordingPermissionsAsync();
      if (!permiso.granted) {
        Alert.alert('Sin micrófono no se puede grabar');
        return;
      }
      await setAudioModeAsync({ playsInSilentMode: true, allowsRecording: true });
    }
    pedirPermiso();
  }, []); // [] = solo una vez

  // Demo 3a · Grabar (CA b, e)
  async function iniciar() {
    try {
      await grabadora.prepareToRecordAsync(); // 1. preparar
      grabadora.record();                     // 2. grabar
    } catch (error) {
      Alert.alert('No se puede grabar', error.message);
    }
  }

  async function detener() {
    try {
      await grabadora.stop();          // 3. parar
      setUriGrabacion(grabadora.uri);  // 4. guardar dónde ha quedado
    } catch (error) {
      Alert.alert('No se puede detener', error.message);
    }
  }

  // Demo 3b · Reproducir (CA e, g)
  function reproducir() {
    // Si ya había llegado al final, volver al principio (no lo hace solo)
    if (estado.didJustFinish || estado.currentTime >= estado.duration) {
      reproductor.seekTo(0);
    }
    reproductor.play();
  }

  return (
    <View style={styles.audio}>
      <Text style={styles.subtitulo}>Grabar y reproducir</Text>

      <Button title="Grabar" onPress={iniciar} />
      <Button title="Detener" onPress={detener} disabled={!estadoGrabadora.isRecording} />

      {uriGrabacion && (
        <View style={styles.audio}>
          <Button title="Reproducir" onPress={reproducir} />
          {/* toFixed(1) = mostrar con 1 decimal */}
          <Text style={styles.texto}>
            {estado.currentTime.toFixed(1)} / {estado.duration.toFixed(1)} s
          </Text>
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  audio: {
    width: '100%',
    gap: 12,
  },
  subtitulo: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  texto: {
    textAlign: 'center',
    color: '#555',
  },
});
