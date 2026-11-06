import { router } from 'expo-router';
import {FlatList, Pressable, StyleSheet, Text, View} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import {useLugares} from "../context/lugares";
import {colores} from "../constants/colores";
import TarjetaLugar from "../components/TarjetaLugar";
export default function Home() {
  const {lugares, eliminarLugar} = useLugares();

  return (
    <SafeAreaView style={styles.pantalla} edges={['bottom', 'left', 'right']}>
      <FlatList
        data={lugares}
        keyExtractor={(lugar) => lugar.id}
        contentContainerStyle={styles.lista}
        ListHeaderComponent={
          <View style={styles.introduccion}>
            <Text style={styles.titulo}>Mis lugares</Text>
            <Text style={styles.descripcion}>
              Guarda los sitios que te gustan y vuelve a ellos cuando quieras.
            </Text>
            <Pressable
              accessibilityRole="button"
              accessibilityLabel="Añadir lugar"
              onPress={() => router.push('/nuevo-lugar')}
              style={({ pressed }) => [styles.botonAnadir, pressed && styles.pulsado]}
            >
              <Text style={styles.textoAnadir}>＋ Añadir lugar</Text>
            </Pressable>
            <Pressable
                accessibilityRole="button"
                accessibilityLabel="Descubrir"
                onPress={() => router.push('/descubrir')}
                style={({ pressed }) => [styles.botonDescubrir, pressed && styles.pulsado]}
            >
              <Text style={styles.textoDescubrir}>Descubrir</Text>
            </Pressable>
          </View>
        }
        ListEmptyComponent={
          <View style={styles.vacio}>
            <Text style={styles.tituloVacio}>Todavía no tienes lugares</Text>
            <Text style={styles.textoVacio}>Pulsa «Añadir lugar» para crear el primero</Text>
          </View>
        }
        renderItem={({ item: lugar }) => (
          <TarjetaLugar lugar={lugar} eliminarLugar={eliminarLugar} />
        )}
      />
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  pantalla: { flex: 1 },
  lista: {
    padding: 20,
    gap: 14,
    width: '100%',
    maxWidth: 640,
    alignSelf: 'center',
  },
  introduccion: { gap: 8, marginBottom: 10 },
  titulo: { fontSize: 28, fontWeight: '800', color: colores.texto },
  descripcion: { fontSize: 16, lineHeight: 23, color: colores.textoSuave },
  // Botón naranja en forma de píldora para la acción de añadir.
  botonAnadir: {
    backgroundColor: colores.acento,
    borderRadius: 999,
    paddingHorizontal: 22,
    minHeight: 48,
    justifyContent: 'center',
    alignSelf: 'flex-start',
    marginTop: 6,
  },
  textoAnadir: { color: colores.sobrePrincipal, fontSize: 16, fontWeight: '700' },
  botonDescubrir: {
    backgroundColor: colores.acento,
    borderRadius: 999,
    paddingHorizontal: 22,
    minHeight: 48,
    justifyContent: 'center',
    alignSelf: 'flex-start',
    marginTop: 6,
  },
  textoDescubrir: { color: colores.sobrePrincipal, fontSize: 16, fontWeight: '700' },
  pulsado: { opacity: 0.7 },
  // Mensaje que se muestra cuando la lista no tiene ningún lugar.
  vacio: {
    alignItems: 'center',
    gap: 6,
    paddingVertical: 40,
    paddingHorizontal: 20,
    backgroundColor: colores.superficie,
    borderRadius: 12,
    borderWidth: 1,
    borderStyle: 'dashed',
    borderColor: colores.borde,
  },
  tituloVacio: { fontSize: 18, fontWeight: '700', color: colores.texto },
  textoVacio: { fontSize: 15, color: colores.textoSuave, textAlign: 'center' },
});
