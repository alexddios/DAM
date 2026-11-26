import { useState } from 'react';
import { StatusBar } from 'expo-status-bar';
import { Alert, Button, Image, ScrollView, StyleSheet, Text, View } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import Laboratorio from './Laboratorio';

// Demo 1a · Elegir de la galería (CA b)
export default function App() {
  const [imagenes, setImagenes] = useState([]);
  const [mensaje, setMensaje] = useState('');

  async function elegirDeGaleria() {
    const permiso = await ImagePicker.requestMediaLibraryPermissionsAsync();
    if (!permiso.granted) {
      setMensaje('Permiso denegado: sin acceso a la galería.');
      return;
    }
    setMensaje('');
    try {
      const resultado = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ['images'],
        quality: 1,
        allowsMultipleSelection: true,
        selectionLimit : 3
      });
      if (!resultado.canceled) {
        setImagenes(resultado.assets); // { uri, width, height, mimeType }
      }else{
        setImagenes([]);
      }
    }catch (e) {
      Alert.alert('Error de galería',e.message);
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
      if (!resultado.canceled){
        setImagenes([resultado.assets[0]]);
      } else{
        setImagenes([]);
      }
    } catch (error) {
      Alert.alert('Error de cámara', error.message); // sin cámara...
    }
  }

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.titulo}>Estudio Multimedia</Text>
      <Button title="Elegir de la galería" onPress={elegirDeGaleria} />
      <Button title="Hacer una foto" onPress={hacerFoto} />

      {mensaje !== '' && <Text style={styles.mensaje}>{mensaje}</Text>}

      {imagenes.length ? (
          <View style={styles.fila}>
            {imagenes.map(imagen => (
                <View key={imagen.uri} style={styles.tarjeta}>
                  <Image source={{ uri: imagen.uri }} style={styles.imagen} />
                  <Text style={styles.ficha}>
                    {imagen.width} × {imagen.height} px · {((imagen.width*imagen.height)/1_000_000).toFixed(1)} Mpx · {imagen.mimeType}
                  </Text>
                </View>
            ))}
          </View>
      ) : (
          <Text>Ninguna imagen seleccionada</Text>
      )}

      {/* Se procesa la primera imagen; la key reinicia el laboratorio si cambia */}
      {imagenes.length > 0 && <Laboratorio key={imagenes[0].uri} original={imagenes[0]} />}

      <StatusBar style="auto" />
    </ScrollView>
  );
}

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
