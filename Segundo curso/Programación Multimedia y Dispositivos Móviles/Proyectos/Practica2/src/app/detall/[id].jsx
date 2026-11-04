import { router, Stack, useLocalSearchParams } from 'expo-router';
import { Pressable, ScrollView, StyleSheet, Text } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import {useLugares} from "../../context/lugares";

export default function Detall() {
  const { id } = useLocalSearchParams();
  const {lugares} = useLugares();
  const lugar = lugares.find((/** @type {{ id: string }} */ lugar) => lugar.id === id);

  function volver() {
    // Un enlace directo puede abrir el detalle sin pasar por la home.
    if (router.canGoBack()) {
      router.back();
    } else {
      router.replace('/');
    }
  }

  return (
    <SafeAreaView style={styles.pantalla} edges={['bottom', 'left', 'right']}>
      <Stack.Screen options={{ title: lugar ? lugar.nombre : 'Lugar no encontrado' }} />
      <ScrollView contentContainerStyle={styles.contenido}>
        <Text style={styles.etiqueta}>{lugar ? lugar.categoria : 'NO DISPONIBLE'}</Text>
        <Text style={styles.titulo}>{lugar ? lugar.nombre : 'Lugar no encontrado'}</Text>
        <Text style={styles.descripcion}>
          {lugar ? lugar.descripcion : 'No existe un lugar con este identificador. Vuelve a la lista para elegir otro.'}
        </Text>
        <Pressable
          accessibilityRole="button"
          onPress={volver}
          style={({ pressed }) => [styles.boton, pressed && styles.pulsado]}
        >
          <Text style={styles.textoBoton}>Volver</Text>
        </Pressable>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  pantalla: { flex: 1 },
  contenido: {
    padding: 24,
    gap: 24,
    width: '100%',
    maxWidth: 640,
    alignSelf: 'center',
  },
  etiqueta: { fontSize: 12, fontWeight: '700', letterSpacing: 1, color: '#52644e' },
  titulo: { fontSize: 34, fontWeight: '700', color: '#234c3b' },
  descripcion: { fontSize: 19, lineHeight: 30, color: '#555b50' },
  boton: {
    backgroundColor: '#234c3b',
    borderRadius: 10,
    padding: 14,
    minHeight: 48,
    alignItems: 'center',
  },
  pulsado: { opacity: 0.7 },
  textoBoton: { color: '#ffffff', fontSize: 16, fontWeight: '600' },
});
