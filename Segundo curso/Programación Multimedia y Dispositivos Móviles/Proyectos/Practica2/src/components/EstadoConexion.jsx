import { StyleSheet, Text, View } from 'react-native';
import { useNetInfo } from '@react-native-community/netinfo';
import { colores } from '../constants/colores';

// Traduce el tipo de red que da NetInfo (en inglés) a un texto para el usuario.
function nombreRed(tipo) {
    if (tipo === 'wifi') return 'wifi';
    if (tipo === 'cellular') return 'datos móviles';
    if (tipo === 'ethernet') return 'cable';
    return 'red desconocida';
}

// Franja que muestra si hay conexión y de qué tipo. Se actualiza sola al cambiar la red.
export default function EstadoConexion() {
    const red = useNetInfo();

    // Al arrancar isConnected vale null (aún no se sabe): solo es "sin red" si es false.
    const sinConexion = red.isConnected === false;

    return (
        <View style={[styles.franja, sinConexion && styles.franjaSinRed]}>
            <Text style={[styles.texto, sinConexion && styles.textoSinRed]}>
                {sinConexion ? 'Sin conexión' : `Conectado · ${nombreRed(red.type)}`}
            </Text>
        </View>
    );
}

const styles = StyleSheet.create({
    franja: {
        alignSelf: 'flex-start',
        backgroundColor: colores.principalSuave,
        borderRadius: 999,
        paddingHorizontal: 12,
        paddingVertical: 4,
    },
    franjaSinRed: { backgroundColor: colores.peligro },
    texto: { fontSize: 13, fontWeight: '600', color: colores.principal },
    textoSinRed: { color: colores.sobrePrincipal },
});
