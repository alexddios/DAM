import { View, Text, FlatList, Pressable, StyleSheet } from 'react-native';
import { useState, useEffect } from "react";
import { router } from 'expo-router';
import { colores } from '../constants/colores';

export default function descubrir() {
    const [resultados, setResultados] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState(null);
    useEffect(() => {
        async function obtenerDatos() {
            try {
                // Cambiamos la URL por una 100% estable para probar
                const respuesta = await fetch('https://geocoding-api.open-meteo.com/v1/search?name=Palma&count=15&language=es&format=json');
                const datos = await respuesta.json();

// Si la API no encuentra nada, results puede no existir, por lo que ponemos un array vacío por defecto
                setResultados(datos.results || []);
            } catch (e) {
                console.error("Motivo del fallo:", e); // Esto saldrá en tu terminal
                setError("Error al cargar los datos");
            } finally {
                setCargando(false);
            }
        }
        obtenerDatos();
    }, []);
    function volver() {
        if (router.canGoBack()) {
            router.back();
        } else {
            router.replace('/');
        }
    }
    if (cargando) {
        return (
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <Text>Buscando lugares...</Text>
            </View>
        );
    }
    if (error) {
        return (
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <Text style={{ color: 'red' }}>{error}</Text>
            </View>
        );
    }
    return (
        <View style={styles.pantalla}>
            <FlatList
                data={resultados}
                keyExtractor={(item) => item.id.toString()}
                renderItem={({ item }) => (
                    <View style={styles.tarjeta}>
                        <Text style={styles.tituloTarjeta}>{item.name}</Text>
                        <Text style={styles.textoTarjeta}>{item.admin1 ? item.admin1 + ', ' : ''}{item.country}</Text>
                    </View>
                )}
            />
            <Pressable
                accessibilityRole="button"
                onPress={volver}
                style={({ pressed }) => [styles.boton, pressed && styles.pulsado]}
            >
                <Text style={styles.textoBoton}>Volver</Text>
            </Pressable>
        </View>
    );
}
const styles = StyleSheet.create({
    pantalla: { flex: 1, padding: 16, backgroundColor: colores.fondo },
    tarjeta: {
        padding: 16,
        borderBottomWidth: 1,
        borderColor: colores.borde,
        backgroundColor: colores.superficie,
        marginBottom: 8,
        borderRadius: 8
    },
    tituloTarjeta: { fontSize: 16, fontWeight: 'bold', color: colores.texto },
    textoTarjeta: { color: colores.textoSuave },
    boton: {
        borderWidth: 1.5,
        borderColor: colores.principal,
        borderRadius: 999,
        minHeight: 48,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 16,
    },
    pulsado: { opacity: 0.7 },
    textoBoton: { color: colores.principal, fontSize: 16, fontWeight: '700' },
});