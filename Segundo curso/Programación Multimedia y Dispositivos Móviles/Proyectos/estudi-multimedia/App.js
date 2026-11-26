import { useState } from 'react';
import { StatusBar } from 'expo-status-bar';
import { Alert, Button, Image, ScrollView, StyleSheet, Text, View } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import Laboratori from './Laboratori';

// Demo 1a · Triar de la galeria (CA b)
export default function App() {
  const [imatges, setImatges] = useState([]);
  const [missatge,setMissatge] = useState('');

  async function triarDeGaleria() {
    const permis = await ImagePicker.requestMediaLibraryPermissionsAsync();
    if (!permis.granted) {
      setMissatge('Permís denegat: sense accés a la galeria.');
      return;
    }
    setMissatge('');
    try {
      const resultat = await ImagePicker.launchImageLibraryAsync({
        mediaTypes: ['images'],
        quality: 1,
        allowsMultipleSelection: true,
        selectionLimit : 3
      });
      if (!resultat.canceled) {
        setImatges(resultat.assets); // { uri, width, height, mimeType }
      }else{
        setImatges([]);
      }
    }catch (e) {
      Alert.alert('Error de galeria',e.message);
    }
  }

  // Demo 1b · Fer una foto i mostrar-la (CA b, e)
  async function ferFoto() {
    const permis = await ImagePicker.requestCameraPermissionsAsync();
    if (!permis.granted) {
      setMissatge('Permís denegat: sense accés a la càmera.');
      return;
    }
    setMissatge('');
    try {
      const resultat = await ImagePicker.launchCameraAsync({ quality: 0.8 });
      if (!resultat.canceled){
        setImatges([resultat.assets[0]]);
      } else{
        setImatges([]);
      }
    } catch (error) {
      Alert.alert('Error de càmera', error.message); // sense càmera...
    }
  }

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.titol}>Estudi Multimèdia</Text>
      <Button title="Triar de la galeria" onPress={triarDeGaleria} />
      <Button title="Fer una foto" onPress={ferFoto} />

      {missatge !== '' && <Text style={styles.missatge}>{missatge}</Text>}

      {imatges.length ? (
          <View style={styles.fila}>
            {imatges.map(imatge => (
                <View key={imatge.uri} style={styles.targeta}>
                  <Image source={{ uri: imatge.uri }} style={styles.imatge} />
                  <Text style={styles.fitxa}>
                    {imatge.width} × {imatge.height} px · {((imatge.width*imatge.height)/1_000_000).toFixed(1)} Mpx · {imatge.mimeType}
                  </Text>
                </View>
            ))}
          </View>
      ) : (
          <Text>Cap imatge seleccionada</Text>
      )}

      {/* Es processa la primera imatge; la key reinicia el laboratori si canvia */}
      {imatges.length > 0 && <Laboratori key={imatges[0].uri} original={imatges[0]} />}

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
  titol: {
    fontSize: 22,
    fontWeight: 'bold',
  },
  imatge: {
    width: '100%',
    height: 120,
    resizeMode: 'contain',
  },
  fila: {
    flexDirection: "row",
    gap:12,
    width: '100%'
  },
  targeta: {
    flex:1
  },
  fitxa: {
    fontSize: 11,
    textAlign: 'center',
    color: '#555',
    marginTop: 4,
  },
  missatge: {
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
