import { useState } from 'react';
import { StatusBar } from 'expo-status-bar';
import { Alert, Button, Image, StyleSheet, Text, View } from 'react-native';
import * as ImagePicker from 'expo-image-picker';

// Demo 1a · Triar de la galeria (CA b)
export default function App() {
  const [imatge, setImatge] = useState(null);

  async function triarDeGaleria() {
    const permis = await ImagePicker.requestMediaLibraryPermissionsAsync();
    if (!permis.granted) {
      Alert.alert('Permís denegat', 'Sense accés a la galeria.');
      return;
    }
    const resultat = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ['images'],
      quality: 1,
    });
    if (!resultat.canceled) {
      setImatge(resultat.assets[0]); // { uri, width, height, mimeType }
    }
  }

  return (
    <View style={styles.container}>
      <Text style={styles.titol}>Estudi Multimèdia</Text>
      <Button title="Triar de la galeria" onPress={triarDeGaleria} />

      {imatge && (
        <>
          <Image source={{ uri: imatge.uri }} style={styles.imatge} />
          <Text>
            {imatge.width} × {imatge.height} px · {imatge.mimeType}
          </Text>
        </>
      )}

      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 16,
    gap: 12,
  },
  titol: {
    fontSize: 22,
    fontWeight: 'bold',
  },
  imatge: {
    width: '100%',
    height: 260,
    resizeMode: 'contain',
  },
});
