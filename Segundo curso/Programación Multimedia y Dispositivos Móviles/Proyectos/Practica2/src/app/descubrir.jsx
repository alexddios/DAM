import { View, Text, FlatList, Pressable, StyleSheet } from 'react-native';
import { useState, useEffect } from "react";
import { router } from 'expo-router';
import { colores } from '../constants/colores';
import * as Location from 'expo-location';

// Centro de Palma. La API devuelve los artículos de Wikipedia que están cerca de este punto.
const LATITUD = 39.5694;
const LONGITUD = 2.6502;
const RADIO_METROS = 10000; // 10 km es el máximo que permite la API

// Monta la URL de la API con las coordenadas que se le pasan.
function crearUrl(latitud, longitud) {
    return 'https://es.wikipedia.org/w/api.php?action=query&list=geosearch&format=json&origin=*' +
        `&gscoord=${latitud}%7C${longitud}&gsradius=${RADIO_METROS}&gslimit=30`;
}

// Muestra la distancia en metros o en kilómetros según lo lejos que esté.
function formatearDistancia(metros) {
    if (metros < 1000) return `a ${Math.round(metros)} m`;
    return `a ${(metros / 1000).toFixed(1)} km`;
}

export default function Descubrir() {
    const [coordenadas,setCoordenadas] = useState({
        latitud: LATITUD,
        longitud: LONGITUD
    });
    const [resultados, setResultados] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState(null);
    // Cada vez que cambia, el useEffect vuelve a hacer la consulta.
    const [intento, setIntento] = useState(0);
    // null y no '': con '' el && dejaría un texto vacío fuera de <Text> y en el móvil falla.
    const [avisoUbicacion, setAvisoUbicacion] = useState(null);

    useEffect(() => {
        async function obtenerDatos() {
            try {
                // Wikipedia rechaza las peticiones que no dicen qué aplicación las hace.
                const respuesta = await fetch(crearUrl(coordenadas.latitud, coordenadas.longitud), {
                    headers: { 'User-Agent': 'MyPlaces/1.0 (practica DAM)' },
                });
                if (!respuesta.ok) throw new Error(`Respuesta HTTP ${respuesta.status}`);
                const datos = await respuesta.json();
                // Si no hay resultados, query o geosearch pueden no existir.
                setResultados(datos.query?.geosearch || []);
            } catch (e) {
                // console.warn y no console.error: en Expo Go el error tapa la pantalla.
                console.warn('Fallo al consultar la API:', e);
                setError('No se han podido cargar los lugares. Revisa la conexión.');
            } finally {
                setCargando(false);
            }
        }
        obtenerDatos();
    }, [intento,coordenadas]);

    // Vuelve al estado de carga y cambia "intento" para que se repita la consulta.
    function reintentar() {
        setCargando(true);
        setError(null);
        setIntento((n) => n + 1);
    }
    // Pide permiso de ubicación y, si se concede, busca lugares cerca del usuario.
    async function usarMiUbicacion(){
        setAvisoUbicacion(null);
        const { status } = await Location.requestForegroundPermissionsAsync();
        if (status !== 'granted'){
            setAvisoUbicacion('No has dado permiso de ubicación. Se muestran lugares de Palma.');
            return;
        }
        try {
            const pos = await Location.getCurrentPositionAsync({});
            setCargando(true);
            setCoordenadas({latitud: pos.coords.latitude,longitud: pos.coords.longitude});
        } catch (e) {
            // Por ejemplo, si el GPS del móvil está desactivado.
            console.warn('Error de ubicación:', e);
            setAvisoUbicacion('No se ha podido obtener la ubicación.');
        }
    }

    if (cargando) {
        return (
            <View style={styles.centrado}>
                <Text style={styles.textoEstado}>Buscando lugares...</Text>
            </View>
        );
    }

    if (error) {
        return (
            <View style={styles.centrado}>
                <Text style={styles.textoError}>{error}</Text>
                <Pressable
                    accessibilityRole="button"
                    onPress={reintentar}
                    style={({ pressed }) => [styles.boton, pressed && styles.pulsado]}
                >
                    <Text style={styles.textoBoton}>Reintentar</Text>
                </Pressable>
            </View>
        );
    }

    return (
        <View style={styles.pantalla}>
            <FlatList
                data={resultados}
                keyExtractor={(item) => item.pageid.toString()}
                contentContainerStyle={styles.lista}
                ListHeaderComponent={
                    <View style={styles.cabeceraCondicionada}>
                        <Text style={styles.cabecera}>
                            {coordenadas.latitud === LATITUD ? "Lugares de interés cerca de Palma" : "Lugares de interés cerca de ti"}
                        </Text>

                        <Text style={styles.textoEstado}>
                            {"Coordenadas: " + coordenadas.latitud.toFixed(5)+ " , " + coordenadas.longitud.toFixed(5) }
                        </Text>

                        {avisoUbicacion && <Text style={styles.textoError}>{avisoUbicacion}</Text>}

                        <Pressable
                            accessibilityRole="button"
                            onPress={usarMiUbicacion}
                            style={({ pressed }) => [styles.boton, pressed && styles.pulsado]}
                        >
                            <Text style={styles.textoBoton}>Usar mi ubicación</Text>
                        </Pressable>
                    </View>
                }
                ListEmptyComponent={
                    <View style={styles.vacio}>
                        <Text style={styles.tituloVacio}>No se han encontrado lugares</Text>
                        <Text style={styles.textoEstado}>Prueba de nuevo más tarde.</Text>
                    </View>
                }
                renderItem={({ item }) => (
                    <View style={styles.tarjeta}>
                        <Text style={styles.tituloTarjeta}>{item.title}</Text>
                        <Text style={styles.textoTarjeta}>{formatearDistancia(item.dist)}</Text>
                    </View>
                )}
            />
            <Pressable
                accessibilityRole="button"
                onPress={() => (router.canGoBack() ? router.back() : router.replace('/'))}
                style={({ pressed }) => [styles.boton, pressed && styles.pulsado]}
            >
                <Text style={styles.textoBoton}>Volver</Text>
            </Pressable>
        </View>
    );
}

const styles = StyleSheet.create({
    pantalla: { flex: 1, padding: 16, backgroundColor: colores.fondo },
    lista: { gap: 8, paddingBottom: 8 },
    cabecera: { fontSize: 15, color: colores.textoSuave, marginBottom: 4 },
    cabeceraCondicionada:{gap: 8, marginBottom: 8},
    centrado: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 24,
        gap: 16,
        backgroundColor: colores.fondo,
    },
    textoEstado: { fontSize: 15, color: colores.textoSuave, textAlign: 'center' },
    textoError: { fontSize: 16, color: colores.peligro, textAlign: 'center' },
    vacio: {
        alignItems: 'center',
        gap: 6,
        paddingVertical: 40,
        backgroundColor: colores.superficie,
        borderRadius: 12,
        borderWidth: 1,
        borderStyle: 'dashed',
        borderColor: colores.borde,
    },
    tituloVacio: { fontSize: 18, fontWeight: '700', color: colores.texto },
    tarjeta: {
        padding: 16,
        backgroundColor: colores.superficie,
        borderRadius: 8,
        borderWidth: 1,
        borderColor: colores.borde,
        gap: 4,
    },
    tituloTarjeta: { fontSize: 16, fontWeight: 'bold', color: colores.texto },
    textoTarjeta: { color: colores.textoSuave },
    boton: {
        alignSelf: 'stretch',
        borderWidth: 1.5,
        borderColor: colores.principal,
        borderRadius: 999,
        minHeight: 48,
        paddingHorizontal: 24,
        justifyContent: 'center',
        alignItems: 'center',
        marginTop: 16,
    },
    pulsado: { opacity: 0.7 },
    textoBoton: { color: colores.principal, fontSize: 16, fontWeight: '700' },
});
