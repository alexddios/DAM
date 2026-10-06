import React, { useState } from 'react';
import { TouchableOpacity, Text, StyleSheet } from 'react-native';

interface BotonProps {
    titulo: string;
}

export default function BotonContador({ titulo }: BotonProps) {
    const [clicks, setClicks] = useState(0);

    return (
        <TouchableOpacity
            style={styles.boton}
            onPress={() => setClicks(clicks + 1)}
        >
            <Text style={styles.textoBoton}>
                {titulo}: {clicks}
            </Text>
        </TouchableOpacity>
    );
}

const styles = StyleSheet.create({
    boton: {
        backgroundColor: '#007AFF',
        padding: 15,
        borderRadius: 8,
        alignItems: 'center',
        marginTop: 10,
    },
    textoBoton: {
        color: '#fff',
        fontWeight: 'bold',
        fontSize: 16,
    },
});
