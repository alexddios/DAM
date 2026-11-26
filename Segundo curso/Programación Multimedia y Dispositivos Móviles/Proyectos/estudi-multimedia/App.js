// useState sirve para guardar datos que, al cambiar, vuelven a pintar la pantalla
import { useState } from 'react';
import { StatusBar } from 'expo-status-bar';
import { Alert, Button, Image, ScrollView, StyleSheet, Text, View } from 'react-native';
// Librería de los apuntes para usar la galería y la cámara
import * as ImagePicker from 'expo-image-picker';
import Laboratorio from './Laboratorio';
import DemoAudio from './DemoAudio';

// Demo 1a · Elegir de la galería (CA b)
export default function App() {
  // [valor, función para cambiarlo] = useState(valor inicial)
  const [imagenes, setImagenes] = useState([]); // lista de imágenes elegidas
  const [mensaje, setMensaje] = useState('');   // aviso de permiso denegado

  // "async" y "await": la función espera a que el usuario responda
  async function elegirDeGaleria() {
    // 1. Pedir permiso
    const permiso = await ImagePicker.requestMediaLibraryPermissionsAsync();
    if (!permiso.granted) {
      setMensaje('Permiso denegado: sin acceso a la galería.');
      return;
    }
    setMensaje('');
    // 2. Abrir la galería. Si algo falla, se salta al "catch"
    try {
      const resultado = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ['images'],
        quality: 1,
        allowsMultipleSelection: true, // permite elegir varias
        selectionLimit: 3,             // como máximo 3
      });
      // canceled es true si el usuario cierra la galería sin elegir
      if (!resultado.canceled) {
        setImagenes(resultado.assets); // { uri, width, height, mimeType }
      } else {
        setImagenes([]);
      }
    } catch (error) {
      Alert.alert('Error de galería', error.message);
    }
  }

  // Demo 1b · Hacer una foto y mostrarla (CA b, e)
  async function hacerFoto() {
    const permiso = await ImagePicker.requestCameraPermissionsAsync();
    if (!permiso.granted) {
      setMensaje('Permiso denegado: sin acceso a la cámara.');
      return;
    }
    setMensaje('');
    try {
      const resultado = await ImagePicker.launchCameraAsync({ quality: 0.8 });
      if (!resultado.canceled) {
        // La cámara devuelve una foto: la metemos en una lista de 1 elemento
        setImagenes([resultado.assets[0]]);
      } else {
        setImagenes([]);
      }
    } catch (error) {
      Alert.alert('Error de cámara', error.message); // sin cámara...
    }
  }

  // Lo que se ve en pantalla. ScrollView permite desplazarse hacia abajo
  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.titulo}>Estudio Multimedia</Text>
      <Button title="Elegir de la galería" onPress={elegirDeGaleria} />
      <Button title="Hacer una foto" onPress={hacerFoto} />

      {/* "condición && algo" pinta ese algo solo si la condición es verdad */}
      {mensaje !== '' && <Text style={styles.mensaje}>{mensaje}</Text>}

      {/* Si hay imágenes (?) se pintan sus fichas; si no (:) un texto */}
      {imagenes.length > 0 ? (
          <View style={styles.fila}>
            {/* "map" recorre la lista y pinta una tarjeta por imagen */}
            {imagenes.map((imagen) => (
                <View key={imagen.uri} style={styles.tarjeta}>
                  <Image source={{ uri: imagen.uri }} style={styles.imagen} />
                  {/* Megapíxeles = ancho × alto / 1 millón, con 1 decimal */}
                  <Text style={styles.ficha}>
                    {imagen.width} × {imagen.height} px · {((imagen.width * imagen.height) / 1000000).toFixed(1)} Mpx · {imagen.mimeType}
                  </Text>
                </View>
            ))}
          </View>
      ) : (
          <Text>Ninguna imagen seleccionada</Text>
      )}

      {/* Se procesa la primera imagen; la key reinicia el laboratorio si cambia */}
      {imagenes.length > 0 && <Laboratorio key={imagenes[0].uri} original={imagenes[0]} />}

      {/* Demos 3a y 3b */}
      <DemoAudio />

      <StatusBar style="auto" />
    </ScrollView>
  );
}

// Estilos: parecido a CSS
const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 16,
    paddingVertical: 48,
    gap: 12,
  },
  titulo: {
    fontSize: 22,
    fontWeight: 'bold',
  },
  imagen: {
    width: '100%',
    height: 120,
    resizeMode: 'contain',
  },
  fila: {
    flexDirection: "row",
    gap:12,
    width: '100%'
  },
  tarjeta: {
    flex:1
  },
  ficha: {
    fontSize: 11,
    textAlign: 'center',
    color: '#555',
    marginTop: 4,
  },
  mensaje: {
    color: '#b00020',
    backgroundColor: '#fdecea',
    borderColor: '#b00020',
    borderWidth: 1,
    borderRadius: 8,
    paddingVertical: 8,
    paddingHorizontal: 12,
    textAlign: 'center',
  },
});
