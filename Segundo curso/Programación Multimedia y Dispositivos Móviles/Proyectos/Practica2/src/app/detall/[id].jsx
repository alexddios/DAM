import { router, Stack, useLocalSearchParams } from 'expo-router';
import { Pressable, ScrollView, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import {useLugares} from "../../context/lugares";
import {colores} from "../../constants/colores";

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
        {/* Bloque de cabecera con el color principal: categoría y nombre. */}
        <View style={styles.cabecera}>
          <Text style={styles.etiqueta}>{lugar ? lugar.categoria : 'No disponible'}</Text>
          <Text style={styles.titulo}>{lugar ? lugar.nombre : 'Lugar no encontrado'}</Text>
        </View>
        <View style={styles.tarjeta}>
          <Text style={styles.subtitulo}>Descripción</Text>
          <Text style={styles.descripcion}>
            {lugar ? lugar.descripcion : 'No existe un lugar con este identificador. Vuelve a la lista para elegir otro.'}
          </Text>
        </View>
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
    padding: 20,
    gap: 16,
    width: '100%',
    maxWidth: 640,
    alignSelf: 'center',
  },
  cabecera: {
    backgroundColor: colores.principal,
    borderRadius: 16,
    padding: 22,
    gap: 6,
  },
  etiqueta: {
    fontSize: 13,
    fontWeight: '700',
    textTransform: 'uppercase',
    letterSpacing: 1,
    color: colores.principalSuave,
  },
  titulo: { fontSize: 28, fontWeight: '800', color: colores.sobrePrincipal },
  tarjeta: {
    backgroundColor: colores.superficie,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: colores.borde,
    padding: 20,
    gap: 8,
  },
  subtitulo: { fontSize: 15, fontWeight: '700', color: colores.acento },
  descripcion: { fontSize: 17, lineHeight: 26, color: colores.textoSuave },
  // Botón "Volver" solo con borde: es una acción secundaria.
  boton: {
    borderWidth: 1.5,
    borderColor: colores.principal,
    borderRadius: 999,
    minHeight: 48,
    justifyContent: 'center',
    alignItems: 'center',
  },
  pulsado: { opacity: 0.7 },
  textoBoton: { color: colores.principal, fontSize: 16, fontWeight: '700' },
});
